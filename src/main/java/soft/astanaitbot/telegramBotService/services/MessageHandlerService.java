package soft.astanaitbot.telegramBotService.services;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import soft.astanaitbot.telegramBotService.model.BotState;

public interface MessageHandlerService {
    SendMessage processInputMessage(BotState currentState, Message message);
}
