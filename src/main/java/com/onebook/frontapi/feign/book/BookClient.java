package com.onebook.frontapi.feign.book;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import com.onebook.frontapi.dto.book.BookDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

//@FeignClient(name = "task-api", url = "http://localhost:8510")
@FeignClient(name = "bookClient", url = "http://localhost:8510")
public interface BookClient {

    @GetMapping("/task/book/newbooks")
    Page<BookDTO> getNewBooks(@RequestParam("page") int page);

    @GetMapping("/task/book/{bookId}")
    BookDTO getBookById(@PathVariable("bookId") Long bookId);

}
