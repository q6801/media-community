package com.example.mediacommunity.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class JdbcMemberRepository implements MemberRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("members").usingGeneratedKeyColumns("id");

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("loginId", member.getLoginId());
        memberMap.put("password", member.getPassword());
        memberMap.put("nickname", member.getNickname());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(memberMap));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Member findById(Long id) {
        String sql = "select * from members where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), id);
    }

    @Override
    public Member findByLoginId(String loginId) {
        String sql = "select * from members where loginId = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), loginId);
    }

    @Override
    public Member findByNickName(String nickName) {
        String sql = "select * from members where nickname=?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), nickName);
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from members";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public void clear() {
        jdbcTemplate.update("delete from members");
    }

    private RowMapper<Member> rowMapper() {
        return (rs, rowNum) -> new Member(rs.getLong("id"),
                rs.getString("loginId"),
                rs.getString("password"),
                rs.getString("nickname"));
    }
}
