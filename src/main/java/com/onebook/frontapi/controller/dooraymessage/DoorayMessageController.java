package com.onebook.frontapi.controller.dooraymessage;

import com.onebook.frontapi.dto.dooraymessage.AuthRequestDto;
import com.onebook.frontapi.dto.dooraymessage.AuthResponseDto;
import com.onebook.frontapi.service.dooraymessage.DoorayMessageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/dooray-message-authentication")
public class DoorayMessageController {

    private final DoorayMessageService doorayMessageService;

    @GetMapping
    public ResponseEntity<AuthResponseDto> sendAuthenticationMessage(HttpServletRequest request, HttpServletResponse response) {
        AuthResponseDto result =  doorayMessageService.requestDoorayMessage(request, response);
        return ResponseEntity.ok().body(result);
    }

    @PostMapping
    public ResponseEntity<AuthResponseDto> validateAuthenticationMessage(@RequestBody AuthRequestDto code, HttpServletRequest request) throws Exception {
        AuthResponseDto result =  doorayMessageService.validateAuthCode(request, code.authCode());
        return ResponseEntity.ok().body(result);
    }
}
