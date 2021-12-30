package soft.astanaitbot.telegramBotService.cache;

import org.springframework.stereotype.Component;
import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.userService.model.User;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache {

    private Map<Long, BotState> usersBotState = new HashMap<>();
    private Map<Long, User> usersData = new HashMap<>();

    @Override
    public void setUserCurrentBotState(Long userId, BotState botState) {
        usersBotState.put(userId, botState);
    }

    @Override
    public BotState getUserCurrentBotState(Long userId) {
        BotState botState = usersBotState.get(userId);
        if (botState == null || botState.equals(BotState.FINISH)) {
            botState = BotState.PICK_LANGUAGE;
        }
        return botState;
    }

    @Override
    public User getUser(Long userId) {
        User user = usersData.get(userId);
        if (user == null) {
            user = new User();
        }
        return user;
    }

    @Override
    public void saveUser(Long userId, User user) {
        usersData.put(userId, user);
    }
}
