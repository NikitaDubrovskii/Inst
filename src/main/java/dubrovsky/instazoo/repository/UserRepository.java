package dubrovsky.instazoo.repository;

import dubrovsky.instazoo.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findUserModelByUsername(String username);

    Optional<UserModel> findUserModelByEmail(String email);

    Optional<UserModel> findUserModelById(Long id);

}
