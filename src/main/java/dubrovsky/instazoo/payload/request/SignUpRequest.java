package dubrovsky.instazoo.payload.request;

import dubrovsky.instazoo.annotations.PasswordMatches;
import dubrovsky.instazoo.annotations.ValidEmail;
import lombok.Data;

import jakarta.validation.constraints.*;

@Data
@PasswordMatches
public class SignUpRequest {

    @Email(message = "It should have email format")
    @NotBlank(message = "User email is required")
    @ValidEmail
    private String email;

    @NotEmpty(message = "Please enter your name")
    private String firstname;

    @NotEmpty(message = "Please enter your lastname")
    private String lastname;

    @NotEmpty(message = "Please enter your username")
    private String username;

    @NotEmpty(message = "Password is required")
    @Size(min = 6)
    private String password;
    private String confirmPassword;

}
