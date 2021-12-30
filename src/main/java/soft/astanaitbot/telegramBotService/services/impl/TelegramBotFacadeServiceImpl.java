package soft.astanaitbot.telegramBotService.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import soft.astanaitbot.complaintService.model.ComplaintType;
import soft.astanaitbot.telegramBotService.cache.UserDataCache;
import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.telegramBotService.model.LanguageTypes;
import soft.astanaitbot.telegramBotService.services.MessageHandlerService;
import soft.astanaitbot.telegramBotService.services.TelegramBotFacadeService;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class TelegramBotFacadeServiceImpl implements TelegramBotFacadeService {

    private final MessageHandlerService messageHandlerService;
    private final UserDataCache userDataCache;

    @Override
    public BotApiMethod<?> handleUpdate(Update update) {
        SendMessage replyMessage = null;

        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
            return processCallbackQuery(callbackQuery);
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                log.info("New message from User: {}, chatId: {}, with text: {}",
                        message.getFrom().getUserName(), message.getChatId(), message.getText());
                replyMessage = handleInputMessage(message);
            }
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message) {
        String inputMessage = message.getText();
        Long userId = message.getFrom().getId();
        BotState botState;
        SendMessage replyMessage;

        if ("/start".equals(inputMessage)) {
            botState = BotState.PICK_LANGUAGE;
        } else {
            botState = userDataCache.getUserCurrentBotState(userId);
        }
        userDataCache.setUserCurrentBotState(userId, botState);
        replyMessage = messageHandlerService.processInputMessage(botState, message);
        return replyMessage;
    }

    private BotApiMethod<?> processCallbackQuery(CallbackQuery callbackQuery) {
        Long userId = callbackQuery.getFrom().getId();
        Long chatId = callbackQuery.getMessage().getChatId();
        BotApiMethod<?> callBackAnswer = null;

        List<InlineKeyboardButton> buttons = new ArrayList<>();
        buttons.add(
                InlineKeyboardButton.builder()
                        .text("Авторизация")
                        .callbackData(BotState.AUTHORIZATION.name())
                        .build()
        );

        if (callbackQuery.getData().equals(LanguageTypes.KAZAKH.name())) {
            callBackAnswer = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("Қайырлы күн\n" +
                            "Менің атым Дана, мен сіздің виртуалды кеңесшісімін.\n" +
                            "\n" +
                            "Жекешелендірілген жауаптар алу үшін авторизациядан өтуді ұсынамын.")
                    .replyMarkup(
                            InlineKeyboardMarkup.builder()
                                    .keyboardRow(buttons)
                                    .build()
                    )
                    .build();

            userDataCache.setUserCurrentBotState(userId, BotState.AUTHORIZATION);

        } else if (callbackQuery.getData().equals(LanguageTypes.RUSSIA.name())) {
            callBackAnswer = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("Добрый день\n" +
                            "Меня зовут Дана, я Ваш виртуальный консультант.\n" +
                            "\n" +
                            "Для получения персонализированных ответов предлагаю авторизоваться.")
                    .replyMarkup(
                            InlineKeyboardMarkup.builder()
                                    .keyboardRow(buttons)
                                    .build()
                    )
                    .build();

            userDataCache.setUserCurrentBotState(userId, BotState.AUTHORIZATION);
        } else if (callbackQuery.getData().equals(BotState.AUTHORIZATION.name())) {
            userDataCache.setUserCurrentBotState(userId, BotState.WRITE_EMAIL);
            callBackAnswer = new SendMessage(chatId.toString(), "Введите Ваш Email Университета: ");
        } else if (callbackQuery.getData().equals(BotState.COMPLAINTtoSTUDENT.name())){
            userDataCache.setUserCurrentBotState(userId, BotState.COMPLAINTtoSTUDENT);
            callBackAnswer = new SendMessage(chatId.toString(), "Напишите жалобу: ");
        } else if(callbackQuery.getData().equals(BotState.COMPLAINTtoTEACHER.name())) {
            userDataCache.setUserCurrentBotState(userId, BotState.COMPLAINTtoTEACHER);
            callBackAnswer = new SendMessage(chatId.toString(), "Напишите жалобу: ");
        } else if(callbackQuery.getData().equals(BotState.COMPLAINTtoADMINISTRATION.name())) {
            userDataCache.setUserCurrentBotState(userId, BotState.COMPLAINTtoADMINISTRATION);
            callBackAnswer = new SendMessage(chatId.toString(), "Напишите жалобу: ");
        }
        return callBackAnswer;
    }
}
