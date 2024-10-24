package com.fiveis.andcrowd.entity.and;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class DynamicAndApplicant {

    private int applyId;

    private int andId;

    private int userId;

    private int andRoleId;

    private String andApplyTitle;

    private String andApplyContent;

    private String andApplyfile;

    private int andApplyStatus;

    private boolean isDeleted;
}
