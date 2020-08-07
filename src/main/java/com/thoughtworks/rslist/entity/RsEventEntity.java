package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dominate.UserDetiles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

import javax.persistence.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "rsEvent")
public class RsEventEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String eventName;
    private String keyWord;

    //@ManyToOne
    private int userId;

    private Integer voteNum = 0;

}
