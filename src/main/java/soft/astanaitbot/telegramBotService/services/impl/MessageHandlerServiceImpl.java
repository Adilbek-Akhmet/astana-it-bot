package soft.astanaitbot.telegramBotService.services.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import soft.astanaitbot.telegramBotService.handlers.InputMessageHandler;
import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.telegramBotService.services.MessageHandlerService;

import java.util.Map;

import static soft.astanaitbot.telegramBotService.model.BotState.*;

@Service
public class MessageHandlerServiceImpl implements MessageHandlerService {

    @Autowired
    private Map<BotState, InputMessageHandler> messageHandlerMap;

    @Override
    public SendMessage processInputMessage(BotState currentState, Message message) {
        InputMessageHandler messageHandler = findMessageHandler(currentState);
        return messageHandler.handle(message);
    }

    private InputMessageHandler findMessageHandler(BotState currentState) {
        if (isProcessAuthorizationState(currentState)) {
            return messageHandlerMap.get(AUTHORIZATION);
        }

        if (isProcessComplaintState(currentState)) {
            return messageHandlerMap.get(COMPLAINT);
        }
        return messageHandlerMap.get(currentState);
    }

    private boolean isProcessComplaintState(BotState currentState) {
        switch (currentState) {
            case COMPLAINTtoTEACHER:
            case COMPLAINTtoADMINISTRATION:
            case COMPLAINTtoSTUDENT:
            case COMPLAINT:
                return true;
            default:
                return false;
        }
    }

    private boolean isProcessAuthorizationState(BotState currentState) {
        switch (currentState) {
            case AUTHORIZED:
            case CONFIRM_EMAIL:
            case WRITE_EMAIL:
            case AUTHORIZATION:
                return true;
            default:
                return false;
        }
    }
}
