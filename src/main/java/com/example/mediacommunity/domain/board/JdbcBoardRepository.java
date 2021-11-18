package com.example.mediacommunity.domain.board;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class JdbcBoardRepository implements BoardRepository{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Board save(Board board) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("board").usingGeneratedKeyColumns("id");

        Map<String, Object> boardMap = new HashMap<>();
        boardMap.put("content", board.getContent());
        boardMap.put("createdAt", board.getCreatedAt());
        boardMap.put("updatedAt", board.getUpdatedAt());
        boardMap.put("viewCnt", board.getViewCnt());
        boardMap.put("writerId", board.getWriterId());
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(boardMap));
        board.setId(key.longValue());

        log.info("saved success = {}", board);
        return board;
    }

    @Override
    public Board findById(Long id) {
        String sql = "select * from board where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), id);
    }

    @Override
    public List<Board> findByWriterId(String id) {
        String sql = "select * from board where writerId = ?";
        return jdbcTemplate.query(sql, rowMapper(), id);
    }

    @Override
    public List<Board> findAll() {
        String sql = "select * from board";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public void update(Long boardIdx, Board updateParam) {
        log.info("updateParam = {}", updateParam);
        String sql = "update board set content=?, writerId=?, createdAt=?, updatedAt=?, viewCnt=? where id=?";
        jdbcTemplate.update(sql, updateParam.getContent(), updateParam.getWriterId(), updateParam.getCreatedAt(),
                updateParam.getUpdatedAt(), updateParam.getViewCnt(), boardIdx);
    }

    @Override
    public void increaseViewCnt(Long id, int viewCnt) {
        String sql = "update board set viewCnt=? where id=?";
        jdbcTemplate.update(sql, viewCnt+1, id);
    }

    @Override
    public void delete(Long id) {
        String sql = "delete from board where id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public int getTotalBoardsNum() {
        String sql = "select count(*) from board";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    RowMapper<Board> rowMapper() {
        return (rs, rowNum) -> {
            Board board = new Board(
                    rs.getString("content"),
                    rs.getTimestamp("createdAt"),
                    rs.getTimestamp("updatedAt"),
                    rs.getString("writerId"),
                    rs.getInt("viewCnt")
            );
            board.setId(rs.getLong("id"));
            return board;
        };
    }
}
