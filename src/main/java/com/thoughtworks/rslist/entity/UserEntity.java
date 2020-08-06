package com.thoughtworks.rslist.entity;

import com.thoughtworks.rslist.dominate.UserDetiles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.annotation.Generated;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "user")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(name = "name")
    private String userName;
    private int age;
    private String gender;
    private String email;
    private String phoneNumber;
    private int votes;
}
