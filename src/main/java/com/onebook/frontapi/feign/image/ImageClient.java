package com.onebook.frontapi.feign.image;

import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.dto.image.ImageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

//@FeignClient(name = "task-api", url = "http://localhost:8510")
@FeignClient(name = "imageClient", url = "http://localhost:8510")
public interface ImageClient {

    @GetMapping("/task/image/{bookId}")
    ImageDTO getNewBooks(@PathVariable("bookId") long bookId);

}