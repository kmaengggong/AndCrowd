package com.fiveis.andcrowd.entity.and;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAndBoard {

    private int andBoardId;

    private int andId;

    private int userId;

    private Integer andBoardTag;

    private String andBoardTitle;

    private String andBoardContent;

    private String andImg;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private Integer andBoardViewCount;

    private boolean isDeleted;
}
