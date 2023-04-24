package com.secondWind.modooDiary.api.diary.domain.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@NoArgsConstructor
public class MemberLoginDTO {

    private String loginId;
    private String password;
    private Boolean isAdmin;

    @Builder(builderMethodName = "of", builderClassName = "of")
    public MemberLoginDTO(String loginId, String password, Boolean isAdmin) {
        this.loginId = loginId;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(loginId, password);
    }
}
