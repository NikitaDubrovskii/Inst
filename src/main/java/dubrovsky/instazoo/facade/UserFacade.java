package dubrovsky.instazoo.facade;

import dubrovsky.instazoo.dto.UserDTO;
import dubrovsky.instazoo.entity.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserFacade {

    public UserDTO userToUserDTO(UserModel user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstname(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setUsername(user.getUsername());
        userDTO.setBio(user.getBio());

        return userDTO;
    }

}
