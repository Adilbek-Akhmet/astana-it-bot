package soft.astanaitbot.telegramBotService.handlers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import soft.astanaitbot.telegramBotService.handlers.InputMessageHandler;
import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.telegramBotService.model.LanguageTypes;
import soft.astanaitbot.telegramBotService.services.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LanguageHandler implements InputMessageHandler {

    private final ReplyMessageService replyMessageService;

    @Override
    public SendMessage handle(Message message) {
        return processUserInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.PICK_LANGUAGE;
    }

    private SendMessage processUserInput(Message message) {
        Long chatId = message.getChatId();

        SendMessage replyToUser = replyMessageService.getReplyMessage(chatId, "reply.pickLanguage");
        replyToUser.setReplyMarkup(getInlineMessageButtons());
        return replyToUser;
    }

    private ReplyKeyboard getInlineMessageButtons() {
        List<InlineKeyboardButton> buttons = new ArrayList<>();

        String kazakh = "Қазақша";
        String russia = "Русский";
        buttons.add(
                InlineKeyboardButton.builder()
                        .text(kazakh)
                        .callbackData(LanguageTypes.KAZAKH.name())
                        .build()
        );
        buttons.add(
                InlineKeyboardButton.builder()
                        .text(russia)
                        .callbackData(LanguageTypes.RUSSIA.name())
                        .build()
        );

        return InlineKeyboardMarkup.builder()
                .keyboardRow(buttons)
                .build();
    }
}
