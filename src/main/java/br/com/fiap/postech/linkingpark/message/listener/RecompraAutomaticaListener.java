package br.com.fiap.postech.linkingpark.message.listener;

import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import br.com.fiap.postech.linkingpark.service.NotificacaoService;
import br.com.fiap.postech.linkingpark.message.sender.QueueSender;
import br.com.fiap.postech.linkingpark.service.CompraTempoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RecompraAutomaticaListener {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RecompraAutomaticaListener.class);

    @Autowired
    private CompraTempoService compraTempoService;

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private QueueSender queueSender;

    @RabbitListener(queues = {"${queue.recompra.name}"})
    public void receive(@Payload String fileBody) {
        LOGGER.info("Message " + fileBody);
        Long idCompraTempo = Long.valueOf(fileBody);
        run(idCompraTempo);
    }

    public void run(Long idCompraTempo) {
        CompraTempo compraTempo = compraTempoService.get(idCompraTempo);
        LocalDateTime now = LocalDateTime.now();
        if ("FINALIZADO".equals(compraTempo.getStatus())) {
            LOGGER.info(Thread.currentThread().getName() + " " + compraTempo + " já saiu da vaga. " + now);
            return;
        }
        compraTempo.adicionaUmaHora();
        this.compraTempoService.save(compraTempo);
        this.notificacaoService.alertaRecargaAutomatica(compraTempo, now);
        this.queueSender.sendRecompraAutomatica(compraTempo);
    }
}