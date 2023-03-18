package dubrovsky.instazoo.service;

import dubrovsky.instazoo.dto.UserDTO;
import dubrovsky.instazoo.entity.UserModel;
import dubrovsky.instazoo.entity.enums.ERole;
import dubrovsky.instazoo.exceptions.UserExistException;
import dubrovsky.instazoo.payload.request.SignUpRequest;
import dubrovsky.instazoo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class UserService {
    public static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserModel createUserModel(SignUpRequest userIn) {
        UserModel user = new UserModel();
        user.setEmail(userIn.getEmail());
        user.setName(userIn.getFirstname());
        user.setLastname(userIn.getLastname());
        user.setUsername(userIn.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userIn.getPassword()));
        user.getRole().add(ERole.ROLE_USER);

        try {
            LOG.info("Saving User {}", userIn.getEmail());
            return userRepository.save(user);
        } catch (Exception ex) {
            LOG.error("Error during registration. {}", ex.getMessage());
            throw new UserExistException("The user " + user.getUsername() + " already exists. Please check credentials");
        }
    }

    public UserModel updateUser(UserDTO userDTO, Principal principal) {
        UserModel user = getUserByPrincipal(principal);
        user.setName(userDTO.getFirstname());
        user.setLastname(userDTO.getLastname());
        user.setBio(userDTO.getBio());

        return userRepository.save(user);
    }

    public UserModel getCurrentUser(Principal principal) {
        return getUserByPrincipal(principal);
    }

    private UserModel getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserModelByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found with username " + username));
    }

    public UserModel getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
