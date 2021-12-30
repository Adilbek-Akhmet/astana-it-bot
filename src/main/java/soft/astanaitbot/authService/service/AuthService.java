package soft.astanaitbot.authService.service;

import soft.astanaitbot.authService.model.ConfirmationToken;
import soft.astanaitbot.userService.model.User;

public interface AuthService {

    void sendToken(String email);

    ConfirmationToken findByToken(String activationLinkToken);

    void deleteByUser(User user);

    ConfirmationToken findByEmail(String email);


}
