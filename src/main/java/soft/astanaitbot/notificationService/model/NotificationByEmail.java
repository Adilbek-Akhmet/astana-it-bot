package soft.astanaitbot.notificationService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationByEmail {

    private String recipient;
    private String subject;
    private String text;

}
