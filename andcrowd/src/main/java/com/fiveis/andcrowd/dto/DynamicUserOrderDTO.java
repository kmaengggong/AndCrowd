package com.fiveis.andcrowd.dto;

import lombok.*;

public class DynamicUserOrderDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int uOrderId;
        private int orderId;
    }
}
