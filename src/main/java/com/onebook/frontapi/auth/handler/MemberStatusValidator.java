package com.onebook.frontapi.auth.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class MemberStatusValidator {
    private final Map<String, Runnable> statusHandlers = new HashMap<>();

    private MemberStatusValidator() {
        statusHandlers.put("ACTIVE", this::handleActive);
        statusHandlers.put("INACTIVE", this::handleInActive);
        statusHandlers.put("DELETED", this::handleDeleted);
        statusHandlers.put("SUSPENDED", this::handleSuspended);
    }

    public void validateMemberStatus(String status) {
        Runnable handler = statusHandlers.get(status);

        if(Objects.nonNull(handler)) {
            handler.run();
            return;
        }

        throw new RuntimeException("처리할 수 없는 회원 상태: " + status);
    }

    public void handleActive() {
        return;
    }

    public void handleInActive() {
        throw new AccessNotFoundException("INACTIVE 회원입니다");
    }

    public void handleDeleted() {
        throw new AccessDeniedException("DELETED 회원입니다.");
    }

    public void handleSuspended() {
        throw new AccessDeniedException("SUSPENDED 회원입니다.");
    }
}
