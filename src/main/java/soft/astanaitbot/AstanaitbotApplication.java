package soft.astanaitbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ConfigurationPropertiesScan
public class AstanaitbotApplication {

    public static void main(String[] args) {
        SpringApplication.run(AstanaitbotApplication.class, args);
    }

}
