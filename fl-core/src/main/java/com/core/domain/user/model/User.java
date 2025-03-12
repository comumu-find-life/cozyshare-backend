package com.core.domain.user.model;

import com.core.domain.base.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Setter
    @Column(name = "profile_url", nullable = true)
    private String profileUrl;

    @Column(name = "fcm_token", nullable = true)
    private String fcmToken;

    @Column(name = "job", nullable = true)
    private String job;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_state", nullable = true)
    private UserState userState;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = true)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "signup_type", nullable = false)
    private SignupType signupType;

    @Column(name = "nationality", nullable = true)
    private String nationality;

    @Column(name = "introduce", nullable = true)
    private String introduce;

    @Setter
    @Column(name = "refreshToken", nullable = true)
    private String refreshToken;

    public boolean isExist(){
        return profileUrl != null;
    }
}