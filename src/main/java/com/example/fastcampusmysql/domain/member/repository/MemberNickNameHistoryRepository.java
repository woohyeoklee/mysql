package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNickNameHistory;
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
public class MemberNickNameHistoryRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbc;

    private static final String TABLE_NAME = "MemberNickNameHistory";
    public static final RowMapper<MemberNickNameHistory> rowMapper = (rs, rowNum) -> MemberNickNameHistory
            .builder()
            .id(rs.getLong("id"))
            .memberId(rs.getLong("memberId"))
            .nickname(rs.getString("nickname"))
            .createdAt(rs.getObject("createdAt", LocalDate.class))
            .build();

    public List<MemberNickNameHistory> findAllByMemberId(Long memberId) {

        var sql = "select * from " + TABLE_NAME + " where memberId = :memberId";
        var params = new MapSqlParameterSource()
                .addValue("memberId", memberId);

        return namedParameterJdbc.query(sql, params, rowMapper);
    }

    public MemberNickNameHistory save(MemberNickNameHistory memberNickNameHistory) {
        if (memberNickNameHistory.getId() == null) {
            return insert(memberNickNameHistory);
        }
        throw new IllegalArgumentException("MemberNickNameHistory는 수정할 수 없습니다.");
    }

    private MemberNickNameHistory insert(MemberNickNameHistory memberNickNameHistory) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(namedParameterJdbc.getJdbcTemplate())
                .withTableName(TABLE_NAME)
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(memberNickNameHistory);

        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

        return MemberNickNameHistory.builder()
                .id(id)
                .memberId(memberNickNameHistory.getMemberId())
                .nickname(memberNickNameHistory.getNickname())
                .createdAt(memberNickNameHistory.getCreatedAt())
                .build();
    }
}
