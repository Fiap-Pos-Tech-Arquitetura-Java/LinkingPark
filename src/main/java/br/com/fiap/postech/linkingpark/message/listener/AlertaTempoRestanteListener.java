package br.com.fiap.postech.linkingpark.message.listener;

import br.com.fiap.postech.linkingpark.controller.exception.ControllerNotFoundException;
import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import br.com.fiap.postech.linkingpark.service.NotificacaoService;
import br.com.fiap.postech.linkingpark.message.sender.QueueSender;
import br.com.fiap.postech.linkingpark.service.CompraTempoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AlertaTempoRestanteListener {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AlertaTempoRestanteListener.class);

    @Autowired
    private CompraTempoService compraTempoService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Value("${alert.tempo.antes.fim}")
    private Long delayAlerta;

    @Autowired
    private QueueSender queueSender;

    @RabbitListener(queues = {"${queue.alerta.name}"})
    public void receive(@Payload String fileBody) {
        LOGGER.info("Message " + fileBody);
        Long idCompraTempo = Long.valueOf(fileBody);
        run(idCompraTempo);
    }

    public void run(Long idCompraTempo) {
        CompraTempo compraTempo;
        try {
            compraTempo = compraTempoService.get(idCompraTempo);
        } catch (ControllerNotFoundException e) {
            LOGGER.warn(e.getMessage());
            return;
        }
        LocalDateTime horaLimite = LocalDateTime.now().plusMinutes(this.delayAlerta);
        if ("FINALIZADO".equals(compraTempo.getStatus())) {
            LOGGER.info(Thread.currentThread().getName() + " " + compraTempo + " já saiu da vaga. " + horaLimite);
            return;
        }
        //TODO VERIFICAR SE AINDA É NECESSARIO COM O ALERTA SENDO ENVIADO SÓ UMA VEZ PARA TIPO FIXO
        /*LocalDateTime tempoLimite = compraTempo.getHoraCompra().plusMinutes(compraTempo.getTempoEmMinutos());
        LocalDateTime now = LocalDateTime.now();
        LOGGER.info("tempoLimite=" + tempoLimite);
        LOGGER.info("now=" + now);
        if (tempoLimite.isBefore(now)) {
            LOGGER.info(Thread.currentThread().getName() + " " + compraTempo + " tempo comprado expirado. " + horaLimite);
            return;
        }*/
        this.notificacaoService.alertaTempoRestante(compraTempo, horaLimite);
    }
}