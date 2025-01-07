package com.onebook.frontapi.dto.delivery;

import lombok.Setter;

import java.time.LocalDate;

@Setter
public class DeliveryRequestDto {
    LocalDate deliveryCompletedDate;
}
