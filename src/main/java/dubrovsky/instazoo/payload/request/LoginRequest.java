package dubrovsky.instazoo.payload.request;

import lombok.Data;

import jakarta.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Username cannot be empty")
    private String username;

    @NotEmpty(message = "Username cannot be empty")
    private String password;

}
