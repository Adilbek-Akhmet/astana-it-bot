package soft.astanaitbot.telegramBotService.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import soft.astanaitbot.telegramBotService.model.BotState;

public interface InputMessageHandler {

    SendMessage handle(Message message);

    BotState getHandlerName();
}
