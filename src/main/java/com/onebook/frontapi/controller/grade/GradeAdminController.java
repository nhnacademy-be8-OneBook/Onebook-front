package com.onebook.frontapi.controller.grade;

import com.onebook.frontapi.dto.grade.GradeModifyRequest;
import com.onebook.frontapi.dto.grade.GradeRegisterRequest;
import com.onebook.frontapi.dto.grade.GradeResponseForAdmin;
import com.onebook.frontapi.service.grade.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/admin/grades")
public class GradeAdminController {
    private final GradeService gradeService;

    /**
     * 관리자 페이지 - 등급 리스트
     */
    @GetMapping
    public String memberGradeListForm(
            @RequestParam(defaultValue = "0", name="page") int pageNo,
            @RequestParam(defaultValue = "10", name="pageSize") int pageSize,
            @RequestParam(defaultValue = "id", name="criteria") String criteria,
            Model model) {

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(criteria).ascending());
        Page<GradeResponseForAdmin> allGradesForAdmin = gradeService.getAllGradesForAdmin(pageable);

        model.addAttribute("grades", allGradesForAdmin);
        return "admin/grade/list";
    }

    /**
     * 관리자 페이지 - 등급 등록 폼
     */
    @GetMapping("/register")
    public String memberGradeRegisterForm() {
        return "admin/grade/register";
    }

    /**
     * 관리자 페이지 - 등급 등록
     */
    @PostMapping("/register")
    public String memberGradeRegister(GradeRegisterRequest gradeRegisterRequest) {
        gradeService.registerGrade(gradeRegisterRequest);
        return "redirect:/admin/grades";
    }

    /**
     * 관리자 페이지 - 등급 수정 폼
     */
    @GetMapping("/modify/{grade-id}")
    public String memberGradeModifyForm(@PathVariable("grade-id") Integer gradeId,
                                        Model model) {
        GradeResponseForAdmin grade = gradeService.getGradeById(gradeId);

        model.addAttribute("grade", grade);
        return "admin/grade/modify";
    }

    /**
     * 관리자 페이지 - 등급 수정
     */
    @PutMapping("/modify/{grade-id}")
    public String memberGradeModify(@PathVariable("grade-id") Integer gradeId,
                                    @ModelAttribute GradeModifyRequest gradeModifyRequest) {
        gradeService.modifyGrade(gradeId, gradeModifyRequest);
        return "redirect:/admin/grades";
    }

    /**
     * 관리자 페이지 - 등급 삭제
     */
    @DeleteMapping("/remove/{grade-id}")
    public String memberGradeRemove(@PathVariable("grade-id") Integer gradeId) {
        gradeService.removeGrade(gradeId);
        return "redirect:/admin/grades";
    }

}
