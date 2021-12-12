package com.example.mediacommunity.community.service.reply;

import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.domain.reply.Reply;
import com.example.mediacommunity.community.domain.reply.ReplyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ReplyServiceImplTest {
    @Mock
    ReplyRepository replyRepository;

    @InjectMocks
    ReplyServiceImpl replyService;

    @Test
    void findALlReplies() {
        //given
        Reply reply = getStubReplies().get(0);
        BDDMockito.given(replyService.findAllReplies(reply.getBoardId())).willReturn(getStubReplies());

        //when
        List<Reply> allReplies = replyService.findAllReplies(reply.getBoardId());

        //then
        Assertions.assertThat(allReplies).contains(reply);
    }

    private List<Reply> getStubReplies() {
        return Arrays.asList(
                new Reply(12323L, "hello world!!", "test123123"),
                new Reply(3223L, "hello world12!!", "test121232")
        );
    }

}