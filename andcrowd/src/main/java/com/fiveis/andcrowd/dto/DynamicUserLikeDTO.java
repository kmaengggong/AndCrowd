package com.fiveis.andcrowd.dto;

import lombok.*;

public class DynamicUserLikeDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find{
        private int uLikeId;
        private int projectId;
        private int projectType;
    }
}
