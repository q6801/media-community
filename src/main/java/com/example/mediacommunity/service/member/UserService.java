package com.example.mediacommunity.service.member;

import com.example.mediacommunity.domain.member.Member;
import com.example.mediacommunity.domain.member.MemberRepository;
import com.example.mediacommunity.domain.member.SignUpDto;
import com.example.mediacommunity.domain.member.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new InternalAuthenticationServiceException("UserService return null member, which means id is wrong"));
        UserInfo userInfo = new UserInfo(member);
        return userInfo;
    }

    public void save(SignUpDto signUpDto, BindingResult bindingResult) {
        Optional<Member> duplicatedId = memberRepository.findByLoginId(signUpDto.getLoginId());
        Optional<Member> duplicatedName = memberRepository.findByNickName(signUpDto.getNickname());

        if (bindingResult.hasErrors()) return;                              // blank가 있는 경우
        if (duplicatedId.isEmpty() && duplicatedName.isEmpty()) {                 // id가 중복되지 않는 경우
            memberRepository.save(new Member(signUpDto.getLoginId(),
                    passwordEncoder.encode(signUpDto.getPassword()), signUpDto.getNickname()));
        } else {
            bindingResult.reject("signUpFail");
        }
    }
}
