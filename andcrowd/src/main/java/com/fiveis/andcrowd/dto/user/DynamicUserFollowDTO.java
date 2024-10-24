package com.fiveis.andcrowd.dto.user;

import lombok.*;

public class DynamicUserFollowDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int uFollowId;
        private int userId;
    }
}
