package com.example.mediacommunity.community.service;

import com.example.mediacommunity.Exception.custom.BlankExistException;
import com.example.mediacommunity.Exception.custom.NicknameAlreadyExistException;
import com.example.mediacommunity.Exception.custom.UserAlreadyExistException;
import com.example.mediacommunity.community.domain.board.Board;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.RoleType;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.community.service.member.MemberServiceImpl;
import com.example.mediacommunity.security.userInfo.UserInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {
    @Mock
    MemberRepository memberRepository;
    @Mock
    AmazonS3Service amazonS3Service;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    SecurityContext securityContext;

    @InjectMocks
    MemberServiceImpl memberService;

    @Test
    @DisplayName("local 회원가입 성공")
    void successToEncodeAndSave() {
        //given
        String defaultImageURL = "something url";
        SignUpDto signUpDto = new SignUpDto("id", "pw","pw", "nickname");
        given(memberRepository.findByLoginId("id")).willReturn(Optional.empty());
        given(memberRepository.findByNickname("nickname")).willReturn(Optional.empty());
        given(amazonS3Service.searchDefaultProfile()).willReturn(defaultImageURL);
        given(passwordEncoder.encode(signUpDto.getPassword())).willReturn("encoded pw");
        //when
        Member savedMember = memberService.encodeAndSave(signUpDto);
        //then
        assertThat(savedMember.getLoginId())
                .isEqualTo(signUpDto.getLoginId());
        assertThat(savedMember.getPassword())
                .isEqualTo("encoded pw");
        assertThat(savedMember.getNickname())
                .isEqualTo(signUpDto.getNickname());
        assertThat(savedMember.getProvider())
                .isEqualTo("local");
    }

    @Test
    @DisplayName("id, pw, nickname중 하나가 blank라서 회원가입 실패")
    void failToSignUpWithEmptyValue() {
        //given
        SignUpDto signUpDto0 = new SignUpDto("", "pw","pw", "nickname");
        SignUpDto signUpDto1 = new SignUpDto("id", "","", "nickname");
        SignUpDto signUpDto2 = new SignUpDto("id", "pw","pw", "");

        //when
        assertThatThrownBy(() -> memberService.encodeAndSave(signUpDto0))
                .isInstanceOf(BlankExistException.class);
        assertThatThrownBy(() -> memberService.encodeAndSave(signUpDto1))
                .isInstanceOf(BlankExistException.class);
        assertThatThrownBy(() -> memberService.encodeAndSave(signUpDto2))
                .isInstanceOf(BlankExistException.class);
    }

    @Test
    @DisplayName("회원가입 id 중복으로 인한 실패")
    void failToSignUpWithExistId() {
        //given
        SignUpDto signUpDto = new SignUpDto("id", "pw", "pw","nickname");
        given(memberRepository.findByLoginId("id"))
                .willReturn(Optional.of(getStubMemberList().get(0)));
        //when
        assertThatThrownBy(() ->
                memberService.encodeAndSave(signUpDto))
                .isInstanceOf(UserAlreadyExistException.class);
    }

    @Test
    @DisplayName("회원가입 nickname 중복으로 인한 실패")
    void failToSignUpWithExistNickname() {
        //given
        SignUpDto signUpDto = new SignUpDto("id", "pw", "pw","nickname");
        given(memberRepository.findByLoginId("id")).willReturn(Optional.empty());
        given(memberRepository.findByNickname("nickname")).willReturn(Optional.of(getStubMemberList().get(0)));
        //when
        assertThatThrownBy(() ->
                memberService.encodeAndSave(signUpDto))
                .isInstanceOf(NicknameAlreadyExistException.class);
    }



    @Test
    void findMemberById() {
        //given
        Member savedMember = getStubMemberList().get(0);
        savedMember.setBoards(getStubBoardList());
        System.out.println("memberRepository = " + memberRepository);
        given(memberRepository.findByLoginId(savedMember.getLoginId())).willReturn(Optional.of(savedMember));

        //when
        Member foundMember = memberService.findMemberById(savedMember.getLoginId());

        //then
        assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void findMemberByName() {
        //given
        Member savedMember = getStubMemberList().get(0);
        given(memberRepository.findByNickname(savedMember.getNickname())).willReturn(Optional.of(savedMember));

        //when
        Member foundMember = memberService.findMemberByName(savedMember.getNickname());

        //then
        assertThat(foundMember).isEqualTo(savedMember);
    }

    @Test
    void findAllMembers() {
        //given
        Member savedMember1 = getStubMemberList().get(0);
        Member savedMember2= getStubMemberList().get(1);
        given(memberRepository.findAll()).willReturn(getStubMemberList());

        //when
        List<Member> allMembers = memberService.findAllMembers();

        //then
        assertThat(allMembers.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("사용자 권한 수정 성공")
    public void successToUpdateMemberRole() {
        Member member = getStubMemberList().get(0);
        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.of(member));

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new UserInfo(member), member.getPassword()));

        memberService.updateMemberRoleToUser(member.getLoginId());
        assertThat(member.getRoleType()).isEqualTo(RoleType.USER);

        UserInfo userInfo = (UserInfo)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        assertThat(userInfo.getRole()).isEqualTo(RoleType.USER.toString());
    }


    @Test
    @DisplayName("profile 갱신 성공")
    public void successToUpdateProfile() throws IOException {
        Member member = getStubMemberList().get(0);
        member.setImageUrl("default image url");
        String thumbnailPath = "thumbnail path";
        String newImageUrl = "new image url";
        String newNickname = "newNickname";
        MultipartFile file = new MockMultipartFile("name", new byte[]{});

        ReflectionTestUtils.setField(memberService, "thumbnailPath", thumbnailPath);

        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.of(member));
        given(amazonS3Service.updateFile(thumbnailPath,
                member.getImageUrl(), file)).willReturn(newImageUrl);

        Optional<String> result = memberService.updateProfile(member.getLoginId(),
                new MemberEditDto(file, newNickname));

        assertThat(result.get()).isEqualTo(newImageUrl);
    }

    @Test
    @DisplayName("profile nickname 중복으로 인한 실패")
    public void failToUpdateProfile() {
        Member member = getStubMemberList().get(0);
        member.setImageUrl("default image url");
        String thumbnailPath = "thumbnail path";
        String newImageUrl = "new image url";
        String newNickname = getStubMemberList().get(1).getNickname();
        MultipartFile file = new MockMultipartFile("name", new byte[]{});

        ReflectionTestUtils.setField(memberService, "thumbnailPath", thumbnailPath);

        given(memberRepository.findByLoginId(member.getLoginId()))
                .willReturn(Optional.of(member));
        given(memberRepository.findByNickname(newNickname))
                .willReturn(Optional.of(getStubMemberList().get(1)));

        assertThatThrownBy(() -> {
            memberService.updateProfile(member.getLoginId(),
                    new MemberEditDto(file, newNickname));
        }).isInstanceOf(NicknameAlreadyExistException.class);


    }


    private List<Board> getStubBoardList() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now().withNano(0));
        return Arrays.asList(
                Board.builder().content("start content")
                        .updatedAt(timestamp)
                        .title("title").build(),
                Board.builder().content("start 2")
                        .updatedAt(timestamp)
                        .title("title").build(),
                Board.builder().build()
        );
    }

    private List<Member> getStubMemberList() {
        return Arrays.asList(
                Member.builder()
                        .loginId("test121")
                        .nickname("test1!")
                        .password("password0").build(),
                Member.builder()
                        .loginId("test1232")
                        .nickname("test!")
                        .password("password1").build()
        );
    }

}