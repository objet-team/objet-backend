package com.server.objet.global.entity;

import com.server.objet.global.enums.OAuthProvider;
import com.server.objet.global.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Table(name = "user_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;


    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private Role role; //USER(=Non Artist), ADMIN, ARTIST로 정의

    //    @Column(nullable = false)
    private String accessToken;

    //    @Column(nullable = false)
    private String refreshToken;

    @Builder
    public User(String email, String name, OAuthProvider oAuthProvider, Role role, String accessToken, String refreshToken) {
        this.email = email;
        this.name = name;
        this.oAuthProvider = oAuthProvider;
        this.role = role;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }


    public void update(Role role){
        this.role = role;
    }
}