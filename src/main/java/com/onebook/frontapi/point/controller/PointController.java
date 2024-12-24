package com.onebook.frontapi.point.controller;

import java.util.List;

import com.onebook.frontapi.point.dto.UserPointResponse;
import com.onebook.frontapi.point.service.PointService;
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

    @GetMapping("/my_point")
    public ModelAndView getUserPointHistories() {
        ModelAndView mv = new ModelAndView();
        List<UserPointResponse> userPointHistories = pointService.getUserPointHistories();

        mv.addObject("userPointHistories", userPointHistories);
        mv.setViewName("point/point_history");

        return mv;
    }
}
