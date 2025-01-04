package com.onebook.frontapi.feign.author;

import com.onebook.frontapi.dto.author.AuthorDTO;
import com.onebook.frontapi.dto.book.BookAuthorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "authorClient", url = "http://localhost:8510")
public interface AuthorClient {

    @GetMapping("/task/author/{authorId}")
    AuthorDTO getAuthor(@PathVariable("authorId") int authorId);

}