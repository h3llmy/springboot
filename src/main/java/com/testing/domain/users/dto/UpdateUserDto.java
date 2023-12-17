package com.testing.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserDto {
    @NotNull
    @NotBlank
    public String username;

    @NotNull
    @NotBlank
    public String password;

    @NotNull(message = "must be filled")
    @NotBlank(message = "must be filled")
    @Email
    public String email;

    public UpdateUserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
