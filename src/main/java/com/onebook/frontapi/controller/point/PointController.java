package com.onebook.frontapi.controller.point;

import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;

    @GetMapping("/myPoint")
    public ModelAndView getMemberPointHistories() {
        ModelAndView mv = new ModelAndView();
        List<MemberPointResponse> memberPointHistories = pointService.getMemberPointHistories();

        mv.addObject("memberPointHistories", memberPointHistories);
        mv.setViewName("mypage/mypagePoint");

        return mv;
    }
}