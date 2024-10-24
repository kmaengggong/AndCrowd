package com.fiveis.andcrowd.entity.crowd;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicCrowdQna {

    private int crowdQnaId;

    private int crowdId;

    private int userId;

    private String qnaTitle;

    private String qnaContent;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;
}