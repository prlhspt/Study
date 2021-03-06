package com.prlhspt.market.service;

import com.prlhspt.market.web.dto.MemberResponseDto;
import com.prlhspt.market.repository.MemberRepository;
import com.prlhspt.market.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberResponseDto getMemberInfo(String username) {
        return memberRepository.findByUsername(username)
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("유저 정보가 없습니다."));
    }

    // 현재 SecurityContext 에 있는 유저 정보 가져오기
    public MemberResponseDto getMyInfo() {
        return memberRepository.findByUsername(SecurityUtil.getCurrentMemberUsername())
                .map(MemberResponseDto::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
    }
}
