package com.onebook.frontapi.dto.publisher;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class PublisherDTO {

    private long publisherId;


    private String name;
}
