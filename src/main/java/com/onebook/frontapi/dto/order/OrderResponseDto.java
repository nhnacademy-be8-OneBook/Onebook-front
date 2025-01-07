package com.onebook.frontapi.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class OrderResponseDto {
    String orderer;
    LocalDateTime dateTime;
    int deliveryPrice;
    int totalPrice;
    String orderStatusName;
}
