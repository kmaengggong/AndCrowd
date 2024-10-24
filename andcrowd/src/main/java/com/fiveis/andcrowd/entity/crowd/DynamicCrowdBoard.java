package com.fiveis.andcrowd.entity.crowd;

import lombok.*;

import java.time.LocalDateTime;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DynamicCrowdBoard {

    private int crowdBoardId;

    private int crowdId;

    private int userId;

    private int crowdBoardTag; // 공지종류 (1:공지), (0:새소식)

    private String crowdBoardTitle;

    private String crowdBoardContent;

    private String crowdImg;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private int viewCount;

    private boolean isDeleted;
}
