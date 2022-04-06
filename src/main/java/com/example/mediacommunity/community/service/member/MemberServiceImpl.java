package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.Exception.ExceptionEnum;
import com.example.mediacommunity.Exception.custom.BlankExistException;
import com.example.mediacommunity.Exception.custom.NicknameAlreadyExistException;
import com.example.mediacommunity.Exception.custom.UserAlreadyExistException;
import com.example.mediacommunity.Exception.custom.UserNotExistException;
import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.RoleType;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.repository.member.MemberRepository;
import com.example.mediacommunity.community.service.AmazonS3Service;
import com.example.mediacommunity.security.userInfo.UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;
    private final PasswordEncoder passwordEncoder;
    
    // 프로필 이미지 저장 위치
    @Value("${path.img}")
    private String thumbnailPath;


    @Override
    public void encodeAndSave(SignUpDto signUpDto) {
        if (signUpDto.getLoginId().isBlank() || signUpDto.getPassword().isBlank() || signUpDto.getNickname().isBlank()) {
            throw new BlankExistException(ExceptionEnum.BLANK_EXIST);
        }

        Optional<Member> duplicatedId = memberRepository.findByLoginId(signUpDto.getLoginId());
        duplicatedId.ifPresent((d) -> {
            throw new UserAlreadyExistException(ExceptionEnum.USER_ALREADY_EXIST);
        });

        Optional<Member> duplicatedName = memberRepository.findByNickname(signUpDto.getNickname());
        duplicatedName.ifPresent((d) -> {
            throw new NicknameAlreadyExistException(ExceptionEnum.NICKNAME_ALREADY_EXIST);
        });

        Member localMember = Member.createLocalMember(signUpDto, passwordEncoder,
                amazonS3Service.searchDefaultProfile());
        memberRepository.save(localMember);
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberById(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotExistException(ExceptionEnum.USER_NOT_EXIST));
    }

    @Override
    @Transactional(readOnly = true)
    public Member findMemberByName(String nickName) {
        return memberRepository.findByNickname(nickName)
                .orElseThrow(() -> new UserNotExistException(ExceptionEnum.USER_NOT_EXIST));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<String> updateProfile(String loginId, MemberEditDto memberEditDto) throws IOException {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("member 없음"));
        MultipartFile file = memberEditDto.getFile();
        String newNickname = memberEditDto.getNickname();
        String newImageUrl;

        updateNickname(member, newNickname);
        if(file != null) {
            newImageUrl = amazonS3Service.updateFile(thumbnailPath, member.getImageUrl(), file);
            member.setImageUrl(newImageUrl);
        } else {
            newImageUrl = member.getImageUrl();
        }
        return Optional.ofNullable(newImageUrl);
    }

    public void updateNickname(Member member, String newNickname) {
        Optional<Member> nameDuplicatedMember = memberRepository.findByNickname(newNickname);

        if (nameDuplicatedMember.isPresent() && compareloginId(member.getLoginId(), nameDuplicatedMember)) { // 중복인 id가 있고 자기 자신이 아니라면
            throw new NicknameAlreadyExistException(ExceptionEnum.NICKNAME_ALREADY_EXIST);
        }
        member.setNickname(newNickname);
    }

    private boolean compareloginId(String loginId, Optional<Member> foundMember) {
        return !foundMember.get().getLoginId().equals(loginId);
    }



    @Override
    public void signOut(String memberId) {
        Member deleteMember = memberRepository.findByLoginId(memberId)
                .orElseThrow(() -> new RuntimeException("member 없음"));
        memberRepository.deleteMember(deleteMember);
    }

    @Override
    public void updateMemberRoleToUser(String memberId) {
        Member member = memberRepository.findByLoginId(memberId).orElseThrow();
        member.setRoleType(RoleType.USER);

        UserInfo userInfo = new UserInfo(member);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Authentication newAuth = new UsernamePasswordAuthenticationToken(userInfo, auth.getCredentials(), userInfo.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

}
