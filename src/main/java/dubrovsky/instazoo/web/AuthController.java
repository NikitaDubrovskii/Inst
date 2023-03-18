package dubrovsky.instazoo.web;

import dubrovsky.instazoo.payload.request.LoginRequest;
import dubrovsky.instazoo.payload.request.SignUpRequest;
import dubrovsky.instazoo.payload.response.JWTTokenSuccessResponse;
import dubrovsky.instazoo.payload.response.MessageResponse;
import dubrovsky.instazoo.security.JWTTokenProvider;
import dubrovsky.instazoo.security.SecurityConstants;
import dubrovsky.instazoo.service.UserService;
import dubrovsky.instazoo.validations.ResponseErrorValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@PreAuthorize("permitAll()")
public class AuthController {

    @Autowired
    private ResponseErrorValidation responseErrorValidation;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authentication;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        Authentication auth = authentication.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(auth);

        return ResponseEntity.ok(new JWTTokenSuccessResponse(true, jwt));
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@Valid @RequestBody SignUpRequest signUpRequest,
                                               BindingResult bindingResult) {
        ResponseEntity<Object> errors = responseErrorValidation.mapValidationService(bindingResult);
        if (!ObjectUtils.isEmpty(errors)) return errors;

        userService.createUserModel(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully"));
    }

}
