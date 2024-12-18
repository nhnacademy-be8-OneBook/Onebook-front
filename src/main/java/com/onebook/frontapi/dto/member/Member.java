package com.onebook.frontapi.dto.member;


import com.onebook.frontapi.dto.role.Role;
import com.onebook.frontapi.dto.grade.Grade;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@NoArgsConstructor
@Setter
@Getter
public class Member {


    private Long id;

    private Grade grade;

    private Role role;

    private String name;

    private String loginId;

    private String password;

    private LocalDate dateOfBirth;

    private Gender gender;

    private String email;

    private String phoneNumber;

    private Status status; // default: ACTIVE

    private LocalDateTime createdAt;

    private LocalDateTime lastLoginAt;

    // 멤버 상태 - 활성화, 비활성화(휴면), 삭제, 정지 / default: INACTIVE
    public enum Status {
        ACTIVE, INACTIVE, DELETED, SUSPENDED
    }

    // 멤버 성별 - 남,여
    public enum Gender {
        M, F
    }


}
