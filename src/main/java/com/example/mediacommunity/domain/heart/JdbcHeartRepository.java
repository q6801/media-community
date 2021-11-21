package com.example.mediacommunity.domain.heart;

import com.example.mediacommunity.domain.board.Board;
import com.example.mediacommunity.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Repository
public class JdbcHeartRepository implements HeartRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Heart findTheHeart(Heart heart) {
        String sql = "select * from heart where boardId=? and memberId=?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), heart.getBoardId(), heart.getMemberId());
    }

    @Override
    public Heart addHeart(Heart heart) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("heart").usingGeneratedKeyColumns("id");

        HashMap<String, Object> heartMap = new HashMap<>();
        heartMap.put("boardId", heart.getBoardId());
        heartMap.put("memberId", heart.getMemberId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(heartMap));
        heart.setId(key.longValue());
        return heart;
    }

    @Override
    public List<Heart> findLikingBoards(String memberId) {
        String sql = "select * from heart where memberId=?";
        return jdbcTemplate.query(sql, rowMapper(), memberId);
    }

    @Override
    public List<Heart> findLikingMembers(Long boardId) {
        String sql = "select * from heart where boardId=?";
        return jdbcTemplate.query(sql, rowMapper(), boardId);
    }

    @Override
    public Long cntHearts(Long boardId) {
        String sql = "select count(*) from heart where boardId=?";
        return jdbcTemplate.queryForObject(sql, Long.class, boardId);
    }

    @Override
    public void deleteHeart(Long id) {
        String sql = "delete from heart where id = ?";
        jdbcTemplate.update(sql, id);
        log.info("heart delete successfully");
    }

    private RowMapper<Heart> rowMapper() {
        return (rs, rowNum) -> {
            Heart heart = new Heart(
                    rs.getLong("boardId"),
                    rs.getString("memberId"));
            heart.setId(rs.getLong("id"));
            return heart;
        };
    }
}
