package soft.astanaitbot.telegramBotService.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import soft.astanaitbot.telegramBotService.handlers.InputMessageHandler;
import soft.astanaitbot.telegramBotService.model.BotState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class BotConfig {

    @Bean
    public Map<BotState, InputMessageHandler> messageHandlerMap(List<InputMessageHandler> messageHandlers) {
        return messageHandlers.stream()
                        .collect(Collectors.toMap(InputMessageHandler::getHandlerName, Function.identity()));
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
