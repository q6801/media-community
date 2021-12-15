package com.example.mediacommunity.community.service.heart;

import com.example.mediacommunity.community.domain.heart.Heart;
import com.example.mediacommunity.community.repository.heart.HeartRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class HeartServiceImplTest {
    @Mock
    HeartRepository heartRepository;

    @InjectMocks
    HeartServiceImpl heartService;

    @Test
    void findTheHeart() {
        //given
        Heart heart = getStubHearts().get(0);
        given(heartRepository.findTheHeart(heart.getBoardId(), heart.getMemberId())).willReturn(heart);

        //when
        Optional<Heart> foundHeart = heartService.findTheHeart(heart.getBoardId(), heart.getMemberId());

        //then
        assertThat(heart).isEqualTo(foundHeart.get());
    }

    @Test
    void toggleTheHeart() {
        //given
        Heart notPushedHeart = getStubHearts().get(0);
        Heart pushedHeart = getStubHearts().get(1);
        given(heartRepository.findTheHeart(pushedHeart.getBoardId(), pushedHeart.getMemberId())).willReturn(pushedHeart);
        given(heartRepository.findTheHeart(notPushedHeart.getBoardId(), notPushedHeart.getMemberId())).willThrow(new DataAccessException("error") {
            @Override
            public String getMessage() {
                return super.getMessage();
            }
        });

        //when
        Boolean pushed = heartService.toggleTheHeart(notPushedHeart);
        Boolean notPushed = heartService.toggleTheHeart(pushedHeart);

        //then
        assertThat(pushed).isEqualTo(true);
        assertThat(notPushed).isEqualTo(false);
    }

    private List<Heart> getStubHearts() {
        return Arrays.asList(
                new Heart(12323L, "test123123"),
                new Heart(3223L, "test121232")
        );
    }
}