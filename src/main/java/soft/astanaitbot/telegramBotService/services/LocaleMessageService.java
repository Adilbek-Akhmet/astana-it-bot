package soft.astanaitbot.telegramBotService.services;

public interface LocaleMessageService {
    String getMessage(String message);
    String getMessage(String message, Object...args);
}
