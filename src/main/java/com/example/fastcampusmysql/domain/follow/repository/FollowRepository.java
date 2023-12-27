package com.example.fastcampusmysql.domain.follow.repository;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
    public static final String Table = "Follow";
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Follow> rowMapper = (rs, rowNum) -> Follow.builder()
            .id(rs.getLong("id"))
            .fromMemberId(rs.getLong("fromMemberId"))
            .toMemberId(rs.getLong("toMemberId"))
            .createdAt(rs.getObject("createdAt", LocalDate.class))
            .build();

    public List<Follow> findAllByFromMemberId(Long fromMemberId) {
        var sql = "select * from " + Table + " where fromMemberId = :fromMemberId";
        var params = new MapSqlParameterSource().addValue("fromMemberId", fromMemberId);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    public List<Follow> findAllByToMemberId(Long toMemberId) {
        var sql = "select * from " + Table + " where toMemberId = :toMemberId";
        var params = new MapSqlParameterSource().addValue("toMemberId", toMemberId);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    public Follow save(Follow follow) {
        if (follow.getId() == null) {
            return insert(follow);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public Follow insert(Follow follow) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName(Table)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Follow.builder()
                .id(id)
                .fromMemberId(follow.getFromMemberId())
                .toMemberId(follow.getToMemberId())
                .createdAt(follow.getCreatedAt())
                .build();
    }
}