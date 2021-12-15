package com.example.mediacommunity.security.service;

import com.example.mediacommunity.community.domain.member.Member;
import com.example.mediacommunity.community.domain.member.MemberRepository;
import com.example.mediacommunity.community.domain.member.SignUpDto;
import com.example.mediacommunity.community.service.AmazonS3Service;
import com.example.mediacommunity.security.userInfo.UserInfo;
import com.example.mediacommunity.security.BadProviderException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AmazonS3Service amazonS3Service;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(() ->
                new InternalAuthenticationServiceException("UserService return null member, which means id is wrong"));
        if(!member.getProvider().equals("local")) {
            throw new BadProviderException("provider is not local");
        }
        UserInfo userInfo = new UserInfo(member);
        return userInfo;
    }

    @Transactional
    public void save(SignUpDto signUpDto, BindingResult bindingResult) {
        Optional<Member> duplicatedId = memberRepository.findByLoginId(signUpDto.getLoginId());
        Optional<Member> duplicatedName = memberRepository.findByNickname(signUpDto.getNickname());

        if (bindingResult.hasErrors()) return;                              // blank가 있는 경우
        if (duplicatedId.isEmpty() && duplicatedName.isEmpty()) {                 // id가 중복되지 않는 경우
            memberRepository.save(new Member(signUpDto.getLoginId(),
                    passwordEncoder.encode(signUpDto.getPassword()),
                    signUpDto.getNickname(), "local", amazonS3Service.searchDefaultProfile()));
        } else {
            bindingResult.reject("signUpFail");
        }
    }
}
