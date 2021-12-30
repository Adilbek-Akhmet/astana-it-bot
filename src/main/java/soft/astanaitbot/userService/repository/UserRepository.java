package soft.astanaitbot.userService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.astanaitbot.userService.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>  {

    void deleteByEmail(String email);

    Optional<User> findByEmail(String email);

}
