package com.secondWind.modooDiary.api.member.auth.domain.dto;

import com.secondWind.modooDiary.api.member.domain.entity.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(Member member) {
        this.name = member.getNickName();
        this.email = member.getEmail();
        this.picture = member.getPicture();
    }
}
