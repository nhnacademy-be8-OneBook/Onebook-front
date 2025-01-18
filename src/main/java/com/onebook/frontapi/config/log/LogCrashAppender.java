package com.onebook.frontapi.config.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Setter
@Slf4j
public class LogCrashAppender extends AppenderBase<ILoggingEvent> {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final RestTemplate restTemplate = new RestTemplate();

    // 중복 방지를 위한 최근 로그 저장소
    // 지나친 도배 방지 위해..
    private final ConcurrentHashMap<String, Long> recentLogs = new ConcurrentHashMap<>();
    // 2초동안은 같은 에러메세지로 도배하지 않도록..
    // 근데 효과가 있나 싶음
    private static final long DUPLICATE_TIME_WINDOW_MS = TimeUnit.SECONDS.toMillis(2);

    private String url;

    @Override
    protected void append(ILoggingEvent event) {
        String logMessage = event.getFormattedMessage();
        long currentTime = System.currentTimeMillis();

        // 최근 로그 검증: 동일 메시지가 최근에 전송되었는지 확인
        Long lastSentTime = recentLogs.get(logMessage);
        if (lastSentTime != null && (currentTime - lastSentTime) < DUPLICATE_TIME_WINDOW_MS) {
            // 중복 메시지로 간주하여 전송하지 않음
            return;
        }

        // 현재 메시지와 타임스탬프를 저장
        recentLogs.put(logMessage, currentTime);

        LogCrashRequest request = new LogCrashRequest(logMessage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        try {
            String payload = objectMapper.writeValueAsString(request);
            HttpEntity<String> body = new HttpEntity<>(payload, headers);
            restTemplate.postForEntity(url, body, String.class);
        } catch (JsonProcessingException e) {
            addError("Failed to process JSON for log crash request", e);
        }
    }
}
