package com.example.mediacommunity.community.service.member;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberEditDto;
import com.example.mediacommunity.community.domain.member.MemberRepository;
import com.example.mediacommunity.community.service.AmazonS3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final AmazonS3Service amazonS3Service;

    @Value("${path.img}")
    private String path;

    @Override
    public Member save(Member member) {
        try {
            return memberRepository.save(member);
        } catch (DataAccessException e) {
            log.warn("class: MemberServiceImpl, method: save, ", e);
            throw new RuntimeException("saveBoard failed");
        }
    }

    @Override
    public Member findMemberById(String loginId) throws DataAccessException{
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new RuntimeException("MemberServiceImpl, findMemberById"));
        return member;
    }

    @Override
    public Member findMemberByName(String nickName) {
            Member member = memberRepository.findByNickName(nickName)
                    .orElseThrow(() -> new RuntimeException("MemberServiceImpl, findMemberByName"));
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

    @Override
    @Transactional
    public Optional<String> updateProfile(String loginId, MemberEditDto member, BindingResult bindingResult) throws IOException {
        MultipartFile file = member.getFile();
        String newNickname = member.getNickname();
        Optional<String> imageUrl = Optional.empty();
        Optional<Member> foundMember = memberRepository.findByNickName(newNickname);

        if (bindingResult.hasErrors()) {
            return Optional.empty();
        }

        if (foundMember.isEmpty() || compareloginId(loginId, foundMember)) { // 자기 자신이 아니라면
            bindingResult.reject("nicknameDuplicated");
            return Optional.empty();
        } else {
            memberRepository.updateNickname(loginId, member.getNickname());
        }

        if(!file.isEmpty()) {
            String ext = extractExt(file.getOriginalFilename());
            String storeFileName = loginId + "Profile" + "." + ext;

            amazonS3Service.uploadImg(path + storeFileName, member.getFile());
            amazonS3Service.searchImage(path, storeFileName);
            imageUrl = Optional.ofNullable(amazonS3Service.searchImage(path, storeFileName));
            memberRepository.updateImageURL(loginId, imageUrl.get());
        }

        return imageUrl;
    }

    private boolean compareloginId(String loginId, Optional<Member> foundMember) {
        return !foundMember.get().getLoginId().equals(loginId);
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }

    @Override
    public void signOut(Member member) {
        memberRepository.deleteMember(member);
    }

    @Override
    public void clear() {
        memberRepository.clear();
    }
}
