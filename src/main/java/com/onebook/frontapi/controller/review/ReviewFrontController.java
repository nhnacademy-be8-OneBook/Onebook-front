package com.onebook.frontapi.controller.review;

import com.onebook.frontapi.dto.review.ReviewPageResponseDto;
import com.onebook.frontapi.dto.review.ReviewRequestDto;
import com.onebook.frontapi.dto.review.ReviewResponseDto;
import com.onebook.frontapi.feign.review.ReviewClient;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/front/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewFrontController {

    private final ReviewClient reviewClient;

    // 리뷰 등록
    @PostMapping("/{bookId}")
    public ResponseEntity<ReviewResponseDto> createReview(
            @PathVariable Long bookId,
            @RequestBody ReviewRequestDto requestDto

    ) {

        // test

        // TaskAPI로부터 응답(ReviewResponseDto)을 받아서 그대로 반환

        log.info("member id : {}", requestDto.getMemberId());
        ReviewResponseDto response = reviewClient.registerReview(bookId, requestDto);
        return ResponseEntity.ok(response);
    }

    // 특정 도서 리뷰 조회 (페이지네이션)
    @GetMapping("/{bookId}")
    public ResponseEntity<ReviewPageResponseDto> getReviews(
            @PathVariable Long bookId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size
    ) {
        // TaskAPI에서 Page<ReviewResponse> → FrontAPI에서 ReviewPageResponseDto
        ReviewPageResponseDto pageResponse = reviewClient.getReviews(bookId, page, size);
        return ResponseEntity.ok(pageResponse);
    }

    // 리뷰 수정
    @PutMapping("/{bookId}/{reviewId}")
    public ResponseEntity<ReviewResponseDto> updateReview(
            @PathVariable Long bookId,
            @PathVariable Long reviewId,
            @RequestBody ReviewRequestDto requestDto
    ) {
        ReviewResponseDto updated = reviewClient.updateReview(bookId, reviewId, requestDto);
        return ResponseEntity.ok(updated);
    }

    // (필요하다면) 리뷰 삭제
}

