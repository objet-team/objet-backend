package com.server.objet.global.entity;

import com.server.objet.global.enums.OAuthProvider;
import com.server.objet.global.enums.Role;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Table(name = "user_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(name = "pic_url")
    private String profilePicUrl;

    @Enumerated(EnumType.STRING)
    private OAuthProvider oAuthProvider;

    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
    private Role role; //USER(=Non Artist), ADMIN, ARTIST로 정의

    //    @Column(nullable = false)
    private String accessToken;

    //    @Column(nullable = false)
    private String refreshToken;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Like> likes;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<Follow> follows;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<Cart> carts;

    @OneToMany
    @JoinColumn(name = "user_id")
    private Collection<Scrap> scraps;


    @Builder
    public User(String email, Role role, String name, OAuthProvider oAuthProvider) {
        this.email = email;
        this.name = name;
        this.oAuthProvider = oAuthProvider;
        this.role = role;
    }

    public void updateRole(Role role){
        this.role = role;
    }

    public void update(String name, String profilePicUrl) {

    }

    @Override
    public ArrayList<GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(Role.USER.toString()));
        return auth;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}