package com.onebook.frontapi.controller.order;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {
    // 삭제 필요
    private String name;
    private String phone;
    private String address;

    public Address(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    // Getter/Setter
}
