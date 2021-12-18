//package com.example.mediacommunity.community.repository.reply;
//
//import com.example.mediacommunity.community.domain.reply.Reply;
//import lombok.RequiredArgsConstructor;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.jdbc.support.KeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.SQLException;
//import java.util.List;
//
////@Repository
//@RequiredArgsConstructor
//public class JdbcReplyRepository implements ReplyRepository {
//    private final JdbcTemplate jdbcTemplate;
//
//    @Override
//    public Reply saveReply(Reply reply) {
//        String sql = "insert into reply(boardId, content, replyer)  values(?, ?, ?)";
////        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
////        jdbcInsert.withTableName("reply").usingGeneratedKeyColumns("id");
////
////        HashMap<String, Object> replyMap = new HashMap<>();
////        replyMap.put("boardId", reply.getBoardId());
////        replyMap.put("replyer", reply.getReplyer());
////        replyMap.put("content", reply.getContent());
////        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(replyMap));
////        reply.setId(key.longValue());
//        KeyHolder keyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(new PreparedStatementCreator() {
//            @Override
//            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
//                PreparedStatement psmt = con.prepareStatement(sql,
//                        new String[]{"id"}
//                );
//                psmt.setString(1, Long.toString(reply.getBoardId()));
//                psmt.setString(2, reply.getContent());
//                psmt.setString(3, reply.getReplyer());
//                return psmt;
//            }
//        }, keyHolder);
//        Long keyValue = keyHolder.getKey().longValue();
//        reply.setId(keyValue);
//        return reply;
//    }
//
//    @Override
//    public List<Reply> findAllReplies(Long boardId) {
//        String sql = "select * from reply where boardId=?";
//        return jdbcTemplate.query(sql, rowMapper(), boardId);
//    }
//
//    RowMapper<Reply> rowMapper() {
//        return (rs, rowNum) -> {
//            Reply reply = new Reply(rs.getLong("boardId"),
//                    rs.getString("content"),
//                    rs.getString("replyer"));
//            reply.setId(rs.getLong("id"));
//            reply.setCreatedAt(rs.getTimestamp("createdAt"));
//            reply.setUpdatedAt(rs.getTimestamp("updatedAt"));
//            return reply;
//        };
//    }
//}
