package com.example.mediacommunity.domain.board;

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
        return board;
    }

    @Override
    public Board findById(Long id) {
        String sql = "select * from board where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), id);
    }

    @Override
    public List<Board> findByWriterId(Long id) {
        String sql = "select * from board where writerId = ?";
        return jdbcTemplate.query(sql, rowMapper(), id);
    }

    @Override
    public List<Board> findAll() {
        String sql = "select * from board";
        return jdbcTemplate.query(sql, rowMapper());
    }

    RowMapper<Board> rowMapper() {
        return new RowMapper<Board>() {
            @Override
            public Board mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Board(
                        rs.getLong("id"),
                        rs.getString("content"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt"),
                        rs.getLong("writerId"),
                        rs.getInt("viewCnt")
                );
            }
        };
    }
}
