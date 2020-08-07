package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dominate.Vote;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "vote")
public class VoteEntity {
    @Id
    @GeneratedValue
    private Integer voteId;

    private Integer userId;
    private Integer rsEventId;
    private String voteTime;
    private int voteNum;

}
