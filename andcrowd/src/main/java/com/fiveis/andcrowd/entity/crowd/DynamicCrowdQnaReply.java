package com.fiveis.andcrowd.entity.crowd;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicCrowdQnaReply {

    private int qnaReplyId;

    private int crowdQnaId;

    private int crowdId;

    private int userId;

    private String qnaReplyContent;

    private LocalDateTime publishedAt;

    private boolean isDeleted;
}