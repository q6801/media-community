package com.example.mediacommunity.service.member;

import com.example.mediacommunity.domain.member.LoginDto;
import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.MemberRepository;
import com.example.mediacommunity.domain.member.SignUpDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService{
    @Autowired
    MemberRepository memberRepository;

    @Override
    public Member save(Member member) {
        try {
            return memberRepository.save(member);
        } catch(DataAccessException e) {
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
    public Member login(LoginDto loginDto, BindingResult bindingResult) {
        String loginId = loginDto.getLoginId();
        Optional<Member> member = memberRepository.findByLoginId(loginId);

        if (member.isEmpty()) {
            bindingResult.reject("idFail");
        }
        else if (!passwordEquals(loginDto, member.get())) {
            bindingResult.reject("passwordFail");
        }
        return member.orElseGet(() -> new Member());
    }

    private boolean passwordEquals(LoginDto loginDto, Member member) {
        return member.getPassword().equals(loginDto.getPassword());
    }

    @Override
    public void signUp(SignUpDto signUpDto, BindingResult bindingResult) {
        Optional<Member> duplicatedId = memberRepository.findByLoginId(signUpDto.getLoginId());
        Optional<Member> duplicatedName = memberRepository.findByNickName(signUpDto.getNickname());

        if (bindingResult.hasErrors()) return;                              // blank가 있는 경우
        if (duplicatedId.isEmpty() && duplicatedName.isEmpty()) {                 // id가 중복되지 않는 경우
            save(new Member(signUpDto.getLoginId(),
                    signUpDto.getPassword(), signUpDto.getNickname()));
        } else {
            bindingResult.reject("signUpFail");
        }
    }

    @Override
    public void clear() {
        memberRepository.clear();
    }
}
