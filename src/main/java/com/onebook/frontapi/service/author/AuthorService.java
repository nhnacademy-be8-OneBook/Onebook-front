package com.onebook.frontapi.service.author;


import com.onebook.frontapi.dto.author.AuthorDTO;
import com.onebook.frontapi.feign.author.AuthorClient;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorClient authorClient;


    public AuthorDTO getAuthor(int id) {
        return authorClient.getAuthor(id);
    }

}
