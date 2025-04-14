package com.example.projectmanagementbe.auth.models.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPasswordResponse {

    private boolean usernameExists;
    private String message;

}
