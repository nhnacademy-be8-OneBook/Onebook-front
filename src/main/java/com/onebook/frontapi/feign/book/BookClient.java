package com.onebook.frontapi.feign.book;

import com.onebook.frontapi.adaptor.packaging.PackagingResponseAdaptor;
import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.dto.book.BookSaveDTO;
import com.onebook.frontapi.dto.book.BookUpdateDTO;
import com.onebook.frontapi.feign.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;

@FeignClient(name = "bookClient", url = "${onebook.gatewayUrl}", configuration = FeignConfig.class)
public interface BookClient {

    @GetMapping("/task/book/newbooks")
    Page<BookDTO> getNewBooks(@RequestParam("page") int page);

    @GetMapping("/task/book/{bookId}")
    BookDTO getBookById(@PathVariable("bookId") Long bookId);

    @PostMapping(value = "/task/book", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    BookDTO createBook(@RequestPart(value = "dto") BookSaveDTO dto,
                       @RequestPart(value = "image") MultipartFile image);


    @GetMapping("/task/book/book-list")
    Page<BookDTO> getAllBooks(Pageable pageable);

    @PutMapping("/task/book/{bookId}")
    BookDTO updateBook(@PathVariable("bookId") long bookId, @RequestBody BookUpdateDTO dto);

}
