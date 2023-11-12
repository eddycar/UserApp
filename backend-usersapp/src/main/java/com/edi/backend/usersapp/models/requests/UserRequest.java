package com.edi.backend.usersapp.models.requests;

import com.edi.backend.usersapp.models.IUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest implements IUser {
    @NotBlank
    @Size(min = 4, max= 8 )
    private String username;

    @NotEmpty
    @Email
    private String email;

    private boolean admin;
}
