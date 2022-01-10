package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.Exception.ExceptionEnum;
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
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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

    @Value("${path.img}")
    private String path;


    @Override
    public Boolean encodeAndSave(SignUpDto signUpDto) {
        Optional<Member> duplicatedId = memberRepository.findByLoginId(signUpDto.getLoginId());
        Optional<Member> duplicatedName = memberRepository.findByNickname(signUpDto.getNickname());

        if (duplicatedId.isEmpty() && duplicatedName.isEmpty()) {                 // id가 중복되지 않는 경우
            Member localMember = Member.createLocalMember(signUpDto, passwordEncoder,
                    amazonS3Service.searchDefaultProfile());
            memberRepository.save(localMember);
            return true;
        }
        return false;
    }

    @Override
    public Member findMemberById(String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new UserNotExistException(ExceptionEnum.USER_NOT_EXIST));
        return member;
    }

    @Override
    public Member findMemberByName(String nickName) {
        Member member = memberRepository.findByNickname(nickName)
                .orElseThrow(() -> new UserNotExistException(ExceptionEnum.USER_NOT_EXIST));
        return member;
    }

    @Override
    public List<Member> findAllMembers() {
        try {
            return memberRepository.findAll();
        } catch (DataAccessException e) {
            log.warn("class: MemberServiceImpl, method: findAllMembers, ", e);
            return new ArrayList<>();
        }
    }

    /***
     * 닉네임 변경에 실패해면 null, 아니면 imageUrl
     * @param loginId
     * @param memberEditDto
     * @return imageUrl
     * @throws IOException
     */
    @Override
    public Optional<String> updateProfile(String loginId, MemberEditDto memberEditDto) throws IOException {
        MultipartFile file = memberEditDto.getFile();
        String newNickname = memberEditDto.getNickname();
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() -> new RuntimeException("member 없음"));
        String imageUrl;

        if (!updateNickname(member, newNickname)) {
            return Optional.empty();
        }

        if(file != null) {
            String ext = extractExt(file.getOriginalFilename());
            String storeFileName = loginId + "Profile" + "." + ext;
            amazonS3Service.uploadImg(path + storeFileName, memberEditDto.getFile());
            amazonS3Service.searchImage(path, storeFileName);
            imageUrl = amazonS3Service.searchImage(path, storeFileName);
            member.setImageUrl(imageUrl);
        } else {
            imageUrl = member.getImageUrl();
        }
        return Optional.ofNullable(imageUrl);
    }

    public Boolean updateNickname(Member member, String newNickname) {
        Optional<Member> nameDuplicatedMember = memberRepository.findByNickname(newNickname);

        if (nameDuplicatedMember.isPresent() && compareloginId(member.getLoginId(), nameDuplicatedMember)) { // 중복인 id가 있고 자기 자신이 아니라면
            return false;
        } else {
            member.setNickname(newNickname);
        }
        return true;
    }

    private boolean compareloginId(String loginId, Optional<Member> foundMember) {
        return !foundMember.get().getLoginId().equals(loginId);
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
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
