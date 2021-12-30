package soft.astanaitbot.notificationService.services;

import soft.astanaitbot.notificationService.model.NotificationByEmail;

public interface MailNotificationService {
    void sendMessage(NotificationByEmail notificationByEmail);
}
