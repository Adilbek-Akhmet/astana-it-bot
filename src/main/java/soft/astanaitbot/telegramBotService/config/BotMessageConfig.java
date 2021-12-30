package soft.astanaitbot.telegramBotService.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties("telegram.bot.messages")
public class BotMessageConfig {

    private String localeTag;

    public Locale getLocale() {
        return Locale.forLanguageTag(localeTag);
    }

}
