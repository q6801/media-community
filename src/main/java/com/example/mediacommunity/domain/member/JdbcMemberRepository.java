package com.example.mediacommunity.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class JdbcMemberRepository implements MemberRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Member save(Member member) {
        String sql = "insert into members values(?, ?, ?)";
        jdbcTemplate.update(sql, member.getLoginId(),
                member.getPassword(), member.getNickname());
        return member;
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
        return (rs, rowNum) -> new Member(
                rs.getString("loginId"),
                rs.getString("password"),
                rs.getString("nickname"));
    }
}
