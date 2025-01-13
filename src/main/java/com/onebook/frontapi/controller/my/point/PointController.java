package com.onebook.frontapi.controller.my.point;

import com.onebook.frontapi.dto.point.MemberPointResponse;
import com.onebook.frontapi.service.point.PointService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/my/point")
public class PointController {

    private final PointService pointService;

    @Autowired
    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/mypoints")  // "/mypoints" 경로
    public ModelAndView getUserPointHistories(HttpSession session) {
        // 로그인 체크를 하지 않고 포인트 내역을 조회할 수 있도록 변경
        List<MemberPointResponse> memberPointHistories = pointService.getMemberPointHistories();

        // 포인트 내역을 my/point/mypoints 뷰로 전달
        ModelAndView modelAndView = new ModelAndView("my/point/mypoints");
        modelAndView.addObject("memberPointHistories", memberPointHistories);
        return modelAndView;
    }
}
