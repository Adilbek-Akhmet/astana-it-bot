package soft.astanaitbot.authService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import soft.astanaitbot.authService.model.ConfirmationToken;
import soft.astanaitbot.userService.model.User;

import java.util.Optional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long> {

    Optional<ConfirmationToken> findByToken(String token);

    Optional<ConfirmationToken> findByUser_Email(String email);

    boolean existsByUser(User user);

    void deleteByUser(User user);

}
