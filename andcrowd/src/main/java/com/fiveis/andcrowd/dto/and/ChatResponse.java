package com.fiveis.andcrowd.dto.and;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ChatResponse<T> {
    private String resultCode;
    private T result;

    // 성공 리턴
    public static <T> ChatResponse<T> success(T result) {
        return new ChatResponse("SUCCESS", result);
    }
}