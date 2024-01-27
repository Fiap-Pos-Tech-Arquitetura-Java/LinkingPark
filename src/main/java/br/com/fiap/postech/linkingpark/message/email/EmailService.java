package br.com.fiap.postech.linkingpark.message.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${from.email.address}")
    private String from;

    @Value("${mail.envio}")
    private Boolean enviar;

    public void sendEmail(String to, String subject, String body) {
        if (!enviar) {
            LOGGER.warn("""
                    envio de email desligado para evitar \
                    estourar a cota mensal de 200 email no AWS pois estamos usando a SandBox.
                    Para habilitar o email de email alterar a propriedade mail.envio para true \
                    no application.properties"""
            );
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            mailSender.send(message);
        } catch (Exception e) {
            LOGGER.error("Falha no envio de email", e);
        }
    }
}
