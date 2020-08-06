package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dominate.UserDetiles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rsEvent")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsEventEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String eventName;
    private String keyWord;
    private String userId;

}
