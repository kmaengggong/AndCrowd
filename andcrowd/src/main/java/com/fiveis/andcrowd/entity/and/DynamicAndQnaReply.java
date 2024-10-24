package com.fiveis.andcrowd.entity.and;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAndQnaReply {

    private int andReplyId;

    private int andQnaId;

    private int andId;

    private int userId;

//    private int andCategoryId;

    private String andReplyContent;

    private LocalDateTime publishedAt;

    private boolean isDeleted;

}
