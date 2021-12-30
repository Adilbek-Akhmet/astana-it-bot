package soft.astanaitbot.authService.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import soft.astanaitbot.authService.model.ConfirmationToken;
import soft.astanaitbot.notificationService.model.NotificationByEmail;
import soft.astanaitbot.userService.model.User;
import soft.astanaitbot.authService.repository.ConfirmationTokenRepository;
import soft.astanaitbot.authService.service.AuthService;
import soft.astanaitbot.notificationService.services.MailNotificationService;
import soft.astanaitbot.userService.service.UserService;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final MailNotificationService mailNotificationService;
    private final UserService userService;

    @Override
    @Async
    @Transactional
    public void sendToken(String email) {
        if (userService.existsByEmail(email)) {
            User user = userService.findByEmail(email);

            if (confirmationTokenRepository.existsByUser(user)) {
                confirmationTokenRepository.deleteByUser(user);
            }

            String token = generateToken();

            mailNotificationService.sendMessage(
                    new NotificationByEmail(
                            email,
                            "Confirmation Token",
                            token
                    )
            );

            confirmationTokenRepository.save(
                    new ConfirmationToken(token, LocalDateTime.now().plusMinutes(2), user)
            );
            log.info("Confirmation Token sent");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public ConfirmationToken findByToken(String token) {
        return confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }

    @Override
    @Transactional(readOnly = true)
    public ConfirmationToken findByEmail(String email) {
        return confirmationTokenRepository.findByUser_Email(email)
                .orElseThrow(() -> new IllegalArgumentException("Invalid token"));
    }

    @Override
    @Transactional
    public void deleteByUser(User user) {
        confirmationTokenRepository.deleteByUser(user);
    }

    private String generateToken() {
        SecureRandom secureRandom = new SecureRandom();
        int randomInt = secureRandom.nextInt(100_000_00, 999_999_99);
        return String.valueOf(randomInt);
    }
}
