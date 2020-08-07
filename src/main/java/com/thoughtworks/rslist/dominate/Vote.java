package com.thoughtworks.rslist.dominate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Vote {
    private int voteNum;
    private Integer userId;
    private Integer rsEventId;
    private String voteTime;

}
