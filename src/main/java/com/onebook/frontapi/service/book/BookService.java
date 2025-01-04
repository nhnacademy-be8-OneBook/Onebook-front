package com.onebook.frontapi.service.book;

import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.feign.book.BookClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookClient bookClient;

    //getList
    public Page<BookDTO> newBooks(Pageable pageable) {
        return bookClient.getNewBooks(pageable.getPageNumber());
    }
 }
