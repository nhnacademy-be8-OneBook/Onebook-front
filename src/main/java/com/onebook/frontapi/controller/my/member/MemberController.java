package com.onebook.frontapi.controller.my.member;

import com.onebook.frontapi.dto.grade.GradeViewDto;
import com.onebook.frontapi.dto.member.MemberModifyRequestDto;
import com.onebook.frontapi.dto.member.MemberViewDto;
import com.onebook.frontapi.dto.member.MembershipCheckRequestDto;
import com.onebook.frontapi.service.grade.GradeService;
import com.onebook.frontapi.service.member.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/my")
public class MemberController {

    private final MemberService memberService;
    private final GradeService gradeService;

    // 개인정보 수정 폼
    @GetMapping("/member/modify")
    public String memberInfoModifyForm(Model model) {
        MemberViewDto member = memberService.getMember();
        model.addAttribute("member", member);
        return "my/member/modify";
    }

    // 개인정보 수정
    @PostMapping("/member/modify")
    public String memberInfoModify(@ModelAttribute @Valid MemberModifyRequestDto memberModifyRequestDto) {
        MemberViewDto member = memberService.modifyMember(memberModifyRequestDto);
        return "redirect:/my/home";
    }

    /**
     * 회원 등급 페이지
     * 1. 회원 등급 정보
     * 2. 최근 3개월 간의 순수 구매 내역
     * 3. 등급 안내
     */
    @GetMapping("/member/grade")
    public String memberGradeInfo(Model model) {
        GradeViewDto memberGrade = memberService.getMemberGrade();
        List<GradeViewDto> gradeList = gradeService.getGrades();
        // TODO Joo - 최근 3개월 순수 구매 내역 조회.
        model.addAttribute("memberGrade", memberGrade);
        model.addAttribute("gradeList", gradeList);
        return "my/member/grade";
    }

    // 회원 여부 조회 - 비밀번호 입력.
    @PostMapping("/member/membership")
    public String checkMembership(@ModelAttribute MembershipCheckRequestDto membershipCheckRequestDto) {
        boolean result = memberService.checkMembership(membershipCheckRequestDto);

        if(result) {
            return "redirect:/my/member/leave-agreement";
        }

        return "redirect:/error/401";
    }

    // 회원 탈퇴 동의 폼
    @GetMapping("/member/leave-agreement")
    public String checkAgreement() {
        return "my/member/leaveAgreement";
    }

    // 회원 탈퇴 폼
    @GetMapping("/member/leave-form")
    public String memberLeaveForm() {
        return "my/member/leaveForm";
    }

    // 회원 탈퇴
    @GetMapping("/member/leave")
    public String memberLeave() {
        boolean result = memberService.deleteMember();

        if(result) {
            return "redirect:/logout";
        }

        return "redirect:/error/401";
    }


}
