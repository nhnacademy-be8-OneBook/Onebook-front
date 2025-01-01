package com.onebook.frontapi.controller.point;

import com.onebook.frontapi.dto.point.MemberPointResponse;
import java.util.List;
import com.onebook.frontapi.service.point.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/point")
public class PointController {

    private final PointService pointService;

    @GetMapping("/myPoint")
    public ModelAndView getUserPointHistories() {
        ModelAndView mv = new ModelAndView();
        List<MemberPointResponse> memberPointHistories = pointService.getMemberPointHistories();

        mv.addObject("userPointHistories", memberPointHistories);
        mv.setViewName("mypage/myPointHistory");

        return mv;
    }
}