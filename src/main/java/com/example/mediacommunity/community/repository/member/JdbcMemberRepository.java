//package com.example.mediacommunity.community.domain.member;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataAccessException;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Slf4j
//@Repository
//public class JdbcMemberRepository implements MemberRepository{
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Override
//    public void updateImageURL(String loginId, String url) {
//        String sql = "update members set imageUrl=? where loginId=?";
//        jdbcTemplate.update(sql, url, loginId);
//    }
//
//    @Override
//    public void updateNickname(String loginId, String nickname) {
//        String sql = "update members set nickname=? where loginId=?";
//        jdbcTemplate.update(sql, nickname, loginId);
//    }
//
//    @Override
//    public Member save(Member member) {
//        String sql = "insert into members values(?, ?, ?, ?, ?)";
//        jdbcTemplate.update(sql, member.getLoginId(),
//                member.getPassword(), member.getNickname(), member.getProvider(), member.getImageUrl());
//        return member;
//    }
//
//    @Override
//    public Optional<Member> findByLoginId(String loginId) {
//        try {
//            String sql = "select * from members where loginId = ?";
//            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper(), loginId));
//        } catch(DataAccessException e) {
//            return Optional.empty();
//        }
//
//    }
//
//    @Override
//    public Optional<Member> findByNickname(String nickname) {
//        try {
//            String sql = "select * from members where nickname=?";
//            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, rowMapper(), nickname));
//        } catch(DataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    @Override
//    public List<Member> findAll() {
//        String sql = "select * from members";
//        return jdbcTemplate.query(sql, rowMapper());
//    }
//
//    @Override
//    public void deleteMember(Member member) {
//        String sql = "delete from members where loginId = ?";
//        jdbcTemplate.update(sql, member.getLoginId());
//    }
//
//    @Override
//    public void clear() {
//        jdbcTemplate.update("delete from members");
//    }
//
//    private RowMapper<Member> rowMapper() {
//        return (rs, rowNum) -> new Member(
//                rs.getString("loginId"),
//                rs.getString("password"),
//                rs.getString("nickname"),
//                rs.getString("provider"),
//                rs.getString("imageUrl"));
//    }
//}
