package skill.project.email;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
  private final JavaMailSender javaMailSender;

  @Override
  public void sendRestoreMessage(Mail mail) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setFrom(mail.getFrom());
    message.setTo(mail.getTo());
    message.setSubject(mail.getSubject());
    message.setText(mail.getText());

    javaMailSender.send(message);
  }
}
