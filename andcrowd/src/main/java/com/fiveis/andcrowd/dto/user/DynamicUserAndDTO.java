package com.fiveis.andcrowd.dto.user;

import lombok.*;

public class DynamicUserAndDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int uAndId;
        private int andId;
    }
}
