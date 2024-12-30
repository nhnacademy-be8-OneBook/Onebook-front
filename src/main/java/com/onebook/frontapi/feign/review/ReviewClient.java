package com.onebook.frontapi.feign.review;

import com.onebook.frontapi.dto.review.ReviewRequestDto;
import com.onebook.frontapi.dto.review.ReviewResponseDto;
import com.onebook.frontapi.dto.review.ReviewPageResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "task-service", url = "http://localhost:8210")
// name = Eureka에 등록된 서비스 이름
// (Gateway가 task-service-dev라는 ID로 라우팅하는 부분과 일치)
public interface ReviewClient {

    /**
     * 리뷰 등록 (POST /task/books/{bookId}/reviews)
     */
    @PostMapping("/task/books/{bookId}/reviews")
    ReviewResponseDto registerReview(@PathVariable("bookId") Long bookId,
                                     @RequestBody ReviewRequestDto reviewRequestDto);

    /**
     * 특정 도서의 리뷰 목록 페이징 조회 (GET /task/books/{bookId}/reviews?page=&size=)
     */
    @GetMapping("/task/books/{bookId}/reviews")
    ReviewPageResponseDto getReviews(@PathVariable("bookId") Long bookId,
                                     @RequestParam(value = "page", defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "10") int size);

    /**
     * 특정 도서의 리뷰 평점 평균 (GET /task/books/{bookId}/reviews/average)
     */
    @GetMapping("/task/books/{bookId}/reviews/average")
    Double getReviewGradeAverage(@PathVariable("bookId") Long bookId);

    /**
     * 리뷰 수정 (PUT /task/books/{bookId}/reviews/{reviewId})
     */
    @PutMapping("/task/books/{bookId}/reviews/{reviewId}")
    ReviewResponseDto updateReview(@PathVariable("bookId") Long bookId,
                                   @PathVariable("reviewId") Long reviewId,
                                   @RequestBody ReviewRequestDto reviewRequestDto);

    /**
     * 리뷰 삭제 (DELETE) - TaskAPI에서는 관리자 권한만 가능
     * 예: DELETE /task/books/{bookId}/reviews/{reviewId}
     * (필요하다면)
     */
    // @DeleteMapping("/task/books/{bookId}/reviews/{reviewId}")
    // ReviewResponseDto deleteReview(...);
}

