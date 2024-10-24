package com.fiveis.andcrowd.dto.etc;

import com.fiveis.andcrowd.entity.etc.InfoBoard;
import lombok.*;

import java.time.LocalDateTime;

public class InfoBoardDTO {
    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Find {
        private int infoId;
        private int userId;
        private boolean infoType;
        private String infoTitle;
        private String infoContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }

    @Getter
    @Setter
    @Builder
    @ToString
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private int infoId;
        private int userId;
        private boolean infoType;
        private String infoTitle;
        private String infoContent;
        private LocalDateTime publishedAt;
        private LocalDateTime updatedAt;
        private boolean isDeleted;
    }

    public static InfoBoardDTO.Find convertToInfoFindDTO(InfoBoard infoBoard) {
        InfoBoardDTO.Find convertedDTO = Find.builder()
                .infoId(infoBoard.getInfoId())
                .userId(infoBoard.getUserId())
                .infoType(infoBoard.isInfoType())
                .infoTitle(infoBoard.getInfoTitle())
                .infoContent(infoBoard.getInfoContent())
                .publishedAt(infoBoard.getPublishedAt())
                .updatedAt(infoBoard.getUpdatedAt())
                .isDeleted(infoBoard.isDeleted())
                .build();
        return convertedDTO;
    }

}
