package soft.astanaitbot.userService.service;

import soft.astanaitbot.userService.model.User;

public interface UserService {

    User findByEmail(String email);

    boolean existsByEmail(String email);

    void deleteByEmail(String email);

    void save(User user);

}
