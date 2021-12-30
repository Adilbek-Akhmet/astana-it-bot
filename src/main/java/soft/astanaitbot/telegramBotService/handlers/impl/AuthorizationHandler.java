package soft.astanaitbot.telegramBotService.handlers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import soft.astanaitbot.authService.service.AuthService;
import soft.astanaitbot.telegramBotService.cache.UserDataCache;
import soft.astanaitbot.telegramBotService.handlers.InputMessageHandler;
import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.telegramBotService.services.ReplyMessageService;
import soft.astanaitbot.complaintService.model.ComplaintType;
import soft.astanaitbot.authService.model.ConfirmationToken;
import soft.astanaitbot.userService.model.User;
import soft.astanaitbot.userService.repository.UserRepository;
import soft.astanaitbot.userService.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorizationHandler implements InputMessageHandler {

    private final UserDataCache userDataCache;
    private final AuthService authService;
    private final ReplyMessageService replyMessageService;
    private final UserService userService;

    @Override
    public SendMessage handle(Message message) {
        if (userDataCache.getUserCurrentBotState(message.getFrom().getId()).equals(BotState.AUTHORIZATION)) {
            userDataCache.setUserCurrentBotState(message.getFrom().getId(), BotState.WRITE_EMAIL);
        }
        return processUserInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.AUTHORIZATION;
    }

    private SendMessage processUserInput(Message message) {
        String userAnswer = message.getText();
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();

        User user = userDataCache.getUser(userId);
        BotState botState = userDataCache.getUserCurrentBotState(userId);

        SendMessage replyToUser = null;

        if (botState.equals(BotState.WRITE_EMAIL)) {
            if(userAnswer.endsWith("@gmail.com") || userAnswer.endsWith("@astanait.edu.kz")) {
                if (userService.existsByEmail(userAnswer)) {
                    replyToUser = replyMessageService.getReplyMessage(chatId, "reply.confirmEmail");
                    user.setEmail(userAnswer);
                    authService.sendToken(userAnswer);
                    userDataCache.setUserCurrentBotState(userId, BotState.CONFIRM_EMAIL);
                } else {
                    replyToUser = replyMessageService.getReplyMessage(chatId,"reply.incorrectEmail");
                }
            } else {
                replyToUser = replyMessageService.getReplyMessage(chatId,"reply.incorrectEmailFormat");
            }
        }

        if (botState.equals(BotState.CONFIRM_EMAIL)) {

            ConfirmationToken confirmationToken = authService.findByEmail(user.getEmail());

            if (confirmationToken.getToken().equals(userAnswer)) {
                replyToUser = new SendMessage(chatId.toString(), "Добрый день!, Вы вошли в систему\n" +
                        "\n" +
                        "Меня зовут Дана, я Ваш виртуальный консультант\n" +
                        "\n" +
                        "Обратите внимание: продолжая диалог, Вы даёте согласие на сбор и обработку своих персональных данных. Мы собираем информацию законно и не передаём её посторонним.\n" +
                        "\n" +
                        "А теперь выберите один из видов жалобы. \n");
                replyToUser.setReplyMarkup(getInlineMessageButtons());
                userDataCache.setUserCurrentBotState(userId, BotState.COMPLAINT);
            } else {
                replyToUser = new SendMessage(chatId.toString(), "Вы ввели не верный код.");
            }

        }

        userDataCache.saveUser(userId, user);

        return replyToUser;
    }

    private ReplyKeyboard getInlineMessageButtons() {
        List<InlineKeyboardButton> button1 = new ArrayList<>();
        List<InlineKeyboardButton> button2 = new ArrayList<>();
        List<InlineKeyboardButton> button3 = new ArrayList<>();

        String studentComplaint = "Жалоба к студенту";
        String teacherComplaint = "Жалоба к преподавателю";
        String adminComplaint = "Жалоба к руководству";

        button1.add(
                InlineKeyboardButton.builder()
                        .text(studentComplaint)
                        .callbackData(BotState.COMPLAINTtoSTUDENT.name())
                        .build()
        );
        button2.add(
                InlineKeyboardButton.builder()
                        .text(teacherComplaint)
                        .callbackData(BotState.COMPLAINTtoTEACHER.name())
                        .build()
        );
        button3.add(
                InlineKeyboardButton.builder()
                        .text(adminComplaint)
                        .callbackData(BotState.COMPLAINTtoADMINISTRATION.name())
                        .build()
        );

        return InlineKeyboardMarkup.builder()
                .keyboard(List.of(button1, button2, button3))
                .build();
    }
}
