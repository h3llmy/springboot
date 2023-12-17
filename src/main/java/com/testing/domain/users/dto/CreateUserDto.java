package com.testing.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateUserDto {
    @NotNull(message = "must be filled")
    @NotBlank(message = "must be filled")
    public String username;

    @NotNull(message = "must be filled")
    @NotBlank(message = "must be filled")
    public String password;

    @NotNull(message = "must be filled")
    @NotBlank(message = "must be filled")
    public String confirmPassword;

    @NotNull(message = "must be filled")
    @NotBlank(message = "must be filled")
    @Email
    public String email;

    public CreateUserDto(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
