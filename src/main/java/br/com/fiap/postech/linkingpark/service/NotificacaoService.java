package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.documents.CompraTempo;
import br.com.fiap.postech.linkingpark.message.email.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacaoService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotificacaoService.class);

    @Autowired
    private EmailService emailService;

    public void alertaRecargaAutomatica(CompraTempo compraTempo, LocalDateTime now) {
        String message = new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                Thread.currentThread().getName() + " " + compraTempo +
                "\r\n uma recarga foi feita de forma automática pois a compra de tempo anterior ainda não foi finalizada "
                + now;
        LOGGER.info(message);
        emailService.sendEmail(compraTempo.getMotorista().getEmail(), "Alerta Recarga Automática", message);
    }

    public void alertaTempoRestante(CompraTempo compraTempo, LocalDateTime now) {
        String message = new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                Thread.currentThread().getName() + " " + compraTempo + "\r\n o tempo comprado irá acabar " + now;
        LOGGER.info(message);
        emailService.sendEmail(compraTempo.getMotorista().getEmail(), "Alerta de Tempo Restante", message);
    }
}
