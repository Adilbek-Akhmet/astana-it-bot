package soft.astanaitbot.telegramBotService.handlers.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import soft.astanaitbot.complaintService.model.Complaint;
import soft.astanaitbot.complaintService.model.ComplaintType;
import soft.astanaitbot.complaintService.repository.ComplaintRepository;
import soft.astanaitbot.telegramBotService.cache.UserDataCache;
import soft.astanaitbot.telegramBotService.handlers.InputMessageHandler;
import soft.astanaitbot.telegramBotService.model.BotState;
import soft.astanaitbot.telegramBotService.services.ReplyMessageService;
import soft.astanaitbot.userService.model.User;
import soft.astanaitbot.userService.repository.UserRepository;
import soft.astanaitbot.userService.service.UserService;

@Log4j2
@Service
@RequiredArgsConstructor
public class ComplaintHandler implements InputMessageHandler {

    private final ReplyMessageService replyMessageService;
    private final UserDataCache userDataCache;
    private final ComplaintRepository complaintRepository;
    private final UserService userService;

    @Override
    public SendMessage handle(Message message) {
        return processUserInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.COMPLAINT;
    }

    private SendMessage processUserInput(Message message) {
        String userAnswer = message.getText();
        Long userId = message.getFrom().getId();
        Long chatId = message.getChatId();
        User userCache = userDataCache.getUser(userId);

        User user = userService.findByEmail(userCache.getEmail());

        if (userDataCache.getUserCurrentBotState(userId).equals(BotState.COMPLAINTtoSTUDENT)) {
            Complaint complaint = new Complaint(userAnswer, ComplaintType.toSTUDENT.name(), user);
            complaintRepository.save(complaint);
        } else if (userDataCache.getUserCurrentBotState(userId).equals(BotState.COMPLAINTtoTEACHER)) {
            Complaint complaint = new Complaint(userAnswer, ComplaintType.toTEACHER.name(), user);
            complaintRepository.save(complaint);
        } else if (userDataCache.getUserCurrentBotState(userId).equals(BotState.COMPLAINTtoADMINISTRATION)) {
            Complaint complaint = new Complaint(userAnswer, ComplaintType.toADMINISTRATION.name(), user);
            complaintRepository.save(complaint);
        }

        userDataCache.setUserCurrentBotState(userId, BotState.FINISH);

        return replyMessageService.getReplyMessage(chatId, "reply.complaintFinish");
    }



}
