package soft.astanaitbot.telegramBotService.cache;

import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.userService.model.User;

public interface DataCache {

    void setUserCurrentBotState(Long userId, BotState botState);

    BotState getUserCurrentBotState(Long userId);

    User getUser(Long userId);

    void saveUser(Long userId, User user);

}
