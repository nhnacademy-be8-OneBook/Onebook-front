package com.onebook.frontapi.controller.book;

import com.onebook.frontapi.dto.author.AuthorDTO;
import com.onebook.frontapi.dto.book.BookAuthorDTO;
import com.onebook.frontapi.dto.book.BookDTO;
import com.onebook.frontapi.dto.book.BookSaveDTO;
import com.onebook.frontapi.service.author.AuthorService;
import com.onebook.frontapi.service.book.BookAuthorService;
import com.onebook.frontapi.service.book.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
@RequestMapping("/book")
@Slf4j
public class BookController {
    private final BookService bookService;
    private final BookAuthorService bookAuthorService;
    private final AuthorService authorService;

    @GetMapping("/newbooks")
    public String newBooks(Model model, Pageable pageable) {
        Page<BookDTO> bookDTOPage = bookService.newBooks(pageable);
        log.info("page: {}", bookDTOPage);
        model.addAttribute("bookList", bookDTOPage.getContent());

        return "index";
    }


    @GetMapping("/bookDetail")
    public String bookDetail(@RequestParam("bookId") long bookId,
                             @RequestParam("url") String url,
                             Model model) {
        BookDTO book = bookService.getBook(bookId);
        BookAuthorDTO bookAuthor = bookAuthorService.getBookAuthor(bookId);

        AuthorDTO author = authorService.getAuthor(bookAuthor.getAuthor().getAuthorId());

        log.info("bookId: {}", book.getBookId());
        log.info("bookTitle: {}", book.getTitle());
        model.addAttribute("book", book);
        model.addAttribute("url", url);
        model.addAttribute("author", author);

        return "book/bookDetail";
    }

    @GetMapping("/register")
    public String registerForm(){
        return "";
    }



//    @PostMapping("/register")
//    public String register(BookSaveDTO dto, MultipartFile image, Model model) {
//        BookDTO book = bookService.createBook(dto, image);
//    }
}
