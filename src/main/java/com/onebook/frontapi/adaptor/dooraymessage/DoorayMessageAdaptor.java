package com.onebook.frontapi.adaptor.dooraymessage;

import com.onebook.frontapi.dto.dooraymessage.DoorayMessageRequestDto;
import com.onebook.frontapi.feign.dooraymessage.DoorayMessageClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DoorayMessageAdaptor {

    private final DoorayMessageClient doorayMessageClient;

    public void send(String code) {

            doorayMessageClient.send(new DoorayMessageRequestDto(
                    "[Web발신]",
                    "https://static.dooray.com/static_images/dooray-bot.png",
                    "[1nebook.shop] " + "인증 번호는 [" + code + "] 입니다."
            )).getBody();

            // TODO 에러 처리 할 것.
    }
}
