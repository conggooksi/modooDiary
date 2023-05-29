package com.secondWind.modooDiary.api.member.auth.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PasswordUpdateRequest {
    @NotNull
    private String currentPassword;
    @NotNull
    private String newPassword;
}
