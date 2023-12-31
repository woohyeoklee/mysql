package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.PageHelper;
import com.example.fastcampusmysql.domain.post.dto.DailyPost;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "Post";

    private static final RowMapper<Post> POST_ROW_MAPPER = (rs, rowNum) -> Post.builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .content(rs.getString("contents"))
            .createdDate(rs.getDate("createdDate").toLocalDate())
            .createdAt(rs.getDate("createdAt").toLocalDate())
            .build();



    private static final RowMapper<DailyPost> DAILY_POST_ROW_MAPPER = (rs, rowNum) -> new DailyPost(
            rs.getLong("memberId"),
            rs.getDate("createdDate").toLocalDate(),
            rs.getLong("post_count")
    );

    public List<DailyPost> getCount(DailyPostCommand command) {
        /*
        [일자, 회원, 게시글수]를 조회하는 쿼리를 작성해주세요.
        */
        var sql = """
                SELECT createdDate, memberId, count(*) as post_count
                FROM post
                WHERE memberId = :memberId AND createdDate BETWEEN :firstDate AND :lastDate
                GROUP BY createdDate, memberId;
                """;
        var params = new BeanPropertySqlParameterSource(command);
        return jdbcTemplate.query(sql, params, DAILY_POST_ROW_MAPPER);
    }

    public Page<Post> findAllByMemberId(Long memberId, Pageable pageable) {
        /*
        memberId로 게시글을 조회하는 쿼리를 작성해주세요.
        */
        var sql = String.format("""
                SELECT *
                FROM post
                WHERE memberId = :memberId
                ORDER BY %s
                LIMIT :size OFFSET :offset;
                """, PageHelper.orderBy(pageable.getSort()));
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("size", pageable.getPageSize())
                .addValue("offset", pageable.getOffset());
        var posts = jdbcTemplate.query(sql, params, POST_ROW_MAPPER);
        return new PageImpl(posts, pageable, getCount(memberId));
    }

    private Long getCount(Long memberId) {
        var sql = """
                SELECT count(*)
                FROM post
                WHERE memberId = :memberId;
                """;
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId);
        return jdbcTemplate.queryForObject(sql, params, Long.class);
    }


    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        } else {
            throw new UnsupportedOperationException();
        }
    }
    public void bulkInsert(List<Post> posts) {
        /*
        [일괄 저장]을 구현해주세요.
        */
        var sql = """
                INSERT INTO post (memberId, contents, createdDate, createdAt)
                VALUES (:memberId, :contents, :createdDate, :createdAt);
                """;
        SqlParameterSource[] params = posts
                .stream()
                .map(BeanPropertySqlParameterSource::new)
                .toArray(SqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .content(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
