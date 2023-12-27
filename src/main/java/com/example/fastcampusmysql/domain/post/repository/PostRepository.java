package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPost;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCommand;
import com.example.fastcampusmysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private static final String TABLE_NAME = "Post";

    public static RowMapper<DailyPost> DAILY_POST_ROW_MAPPER = (rs, rowNum) -> new DailyPost(
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
        SqlParameterSource params = new BeanPropertySqlParameterSource(command);
        return jdbcTemplate.query(sql, params, DAILY_POST_ROW_MAPPER);
    }

    public Post save(Post post) {
        if (post.getId() == null) {
            return insert(post);
        } else {
            throw new UnsupportedOperationException();
        }
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
