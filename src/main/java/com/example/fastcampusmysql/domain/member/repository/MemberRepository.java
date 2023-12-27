package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbc;
    private static final String TABLE_NAME = "Member";
    public static final RowMapper<Member> rowMapper = (rs, rowNum) -> Member
            .builder()
            .id(rs.getLong("id"))
            .nickname(rs.getString("nickname"))
            .email(rs.getString("email"))
            .birthdate(rs.getDate("birthdate").toLocalDate())
            .createdAt(rs.getDate("createdAt").toLocalDate())
            .build();

    public Optional<Member> findById(Long id) {
        /*
        select * from Member where id = :id;
         */
        var sql = "select * from " + TABLE_NAME + " where id = :id";
        var params = new MapSqlParameterSource()
                .addValue("id", id);
        List<Member> members = namedParameterJdbc.query(sql, params, rowMapper);
        Member nullableMember = DataAccessUtils.singleResult(members);
        return Optional.ofNullable(nullableMember);
    }

    public List<Member> findAllByIdIn(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        var sql = "select * from " + TABLE_NAME + " where id in (:ids)";
        var params = new MapSqlParameterSource()
                .addValue("ids", ids);
        return namedParameterJdbc.query(sql, params, rowMapper);
    }

    public List<Member> findAllByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        String sql = "select * from " + TABLE_NAME + " where id in (:ids)";
        var params = new MapSqlParameterSource()
                .addValue("ids", ids);
        return namedParameterJdbc.query(sql, params, rowMapper);
    }

    public Member save(Member member) {
        if (member.getId() == null) {
            return insert(member);
        }
        return update(member);
    }

    private Member insert(Member member) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbc.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return Member.builder()
                .id(id)
                .nickname(member.getNickname())
                .email(member.getEmail())
                .birthdate(member.getBirthdate())
                .createdAt(member.getCreatedAt())
                .build();
    }

    private Member update(Member member) {
        var sql = "update " + TABLE_NAME + " set nickname = :nickname, email = :email, birthdate = :birthdate where id = :id";
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        namedParameterJdbc.update(sql, params);
        return member;
    }
}
