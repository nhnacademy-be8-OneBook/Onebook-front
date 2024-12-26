package com.onebook.frontapi.point.controller;

import com.onebook.frontapi.point.service.PointService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
@RequestMapping("/point")
public class PointController {
    private final PointService pointService;

    public PointController(PointService pointService) {
        this.pointService = pointService;
    }

    @GetMapping("/mypoint")
    public String getMemberPointHistories(Model model) {
        try {
            model.addAttribute("memberPointHistories", pointService.getMemberPointHistories());
            return "point/pointhistory";
        } catch (RuntimeException e) {
            throw new ServiceException("An error occurred while fetching point histories", e);
        }
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleServiceException(ServiceException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }
}
