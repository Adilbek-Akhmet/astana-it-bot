package soft.astanaitbot.telegramBotService.services;

import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface TelegramBotFacadeService {
    BotApiMethod<?> handleUpdate(Update update);
}
