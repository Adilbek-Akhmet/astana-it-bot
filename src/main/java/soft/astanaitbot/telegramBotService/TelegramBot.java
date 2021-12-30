package soft.astanaitbot.telegramBotService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import soft.astanaitbot.telegramBotService.config.BotRegisterConfig;
import soft.astanaitbot.telegramBotService.services.TelegramBotFacadeService;

@Service
@RequiredArgsConstructor
public class TelegramBot extends TelegramWebhookBot {

    private final BotRegisterConfig botConfig;
    private final TelegramBotFacadeService telegramBotFacadeService;

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
        return telegramBotFacadeService.handleUpdate(update);
    }

    @Override
    public String getBotPath() {
        return botConfig.getWebHookPath();
    }
}
