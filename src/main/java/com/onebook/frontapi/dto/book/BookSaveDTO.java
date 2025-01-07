package com.onebook.frontapi.dto.book;

import com.onebook.frontapi.dto.author.AuthorDTO;
import com.onebook.frontapi.dto.publisher.PublisherDTO;
import com.onebook.frontapi.dto.tag.TagDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class BookSaveDTO {
    @NotBlank(message = "title은 필수입니다 !")
    @Length(max = 100)
    private String title;
    @Valid
    private AuthorDTO author;
    @NotBlank
    private String content;
    @NotBlank
    private String pubdate;
    @NotBlank
    private String description;
    @NotBlank
    @Length(max = 13)
    private String isbn13;
    @NotNull
    @Positive
    private Integer priceSales;
    @NotNull
    @Positive
    private Integer price;
    @NotNull
    private int categoryId;
    @Valid
    private PublisherDTO publisher;
    @Valid
    private TagDTO tag;
    @NotNull
    @Positive
    private Integer stock;
}
