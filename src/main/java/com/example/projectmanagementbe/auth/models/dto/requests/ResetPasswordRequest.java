package com.example.projectmanagementbe.auth.models.dto.requests;

import com.example.projectmanagementbe.auth.utils.validator.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordRequest {

    @NotBlank(message = "Username cannot be blank")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @ValidPassword
    private String password;

    @NotBlank(message = "Confirm password cannot be blank")
    @ValidPassword
    private String confirmPassword;

}