package soft.astanaitbot.notificationService.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import soft.astanaitbot.notificationService.config.EmailConfig;
import soft.astanaitbot.notificationService.model.NotificationByEmail;
import soft.astanaitbot.notificationService.services.MailNotificationService;

@Service
@RequiredArgsConstructor
public class MailNotificationServiceImpl implements MailNotificationService {

    private final JavaMailSender javaMailSender;
    private final EmailConfig emailConfig;

    @Override
    public void sendMessage(NotificationByEmail notificationByEmail) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom(emailConfig.getUsername());
            messageHelper.setTo(notificationByEmail.getRecipient());
            messageHelper.setSubject(notificationByEmail.getSubject());
            messageHelper.setText(notificationByEmail.getText());
        };

        sendToEmail(mimeMessagePreparator);
    }

    private void sendToEmail(MimeMessagePreparator mimeMessagePreparator) {
        try {
            javaMailSender.send(mimeMessagePreparator);
        } catch (MailSendException e) {
            throw new MailSendException("Mail Exception occurred when sending message");
        }
    }

}
