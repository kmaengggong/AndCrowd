package com.fiveis.andcrowd.entity.and;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAndQna {
    private int andQnaId;

    private int andId;

    private int userId;

    private String andQnaTitle;

    private String andQnaContent;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;
}
