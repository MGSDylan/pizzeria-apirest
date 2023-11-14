package com.platzi.pizza.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @Column(nullable = false,length = 20)
    private String username;

    @Column(nullable = false,length = 250)
    private String password;

    @Column(length = 60)
    private String email;

    @Column(nullable = false,columnDefinition = "TINYINT")
    private Boolean locked;

    @Column(nullable = false,columnDefinition = "TINYINT")
    private Boolean disabled;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<UserRoleEntity> roles;


    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", locked=" + locked +
                ", disabled=" + disabled +
                ", roles=" + roles +
                '}';
    }
}
