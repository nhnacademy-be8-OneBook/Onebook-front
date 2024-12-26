package com.onebook.frontapi.feign.dooraymessage;

import com.onebook.frontapi.dto.dooraymessage.DoorayMessageRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="DoorayMessageClient", url = "https://nhnacademy.dooray.com/services")
public interface DoorayMessageClient {

    @PostMapping("/3204376758577275363/3964904430046863536/O9MYgDpOQ6m1cOzp8uve2Q")
        ResponseEntity<String> send(@RequestBody DoorayMessageRequestDto doorayMessageRequestDto);

}