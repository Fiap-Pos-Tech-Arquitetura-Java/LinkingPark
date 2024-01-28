package br.com.fiap.postech.linkingpark.message.sender;

import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import br.com.fiap.postech.linkingpark.message.config.QueueConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(QueueSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;;

    @Value("${queue.alerta.name}")
    private String alertaTempoQueueName;

    @Value("${queue.recompra.name}")
    private String recompraAutomaticaQueueName;

    @Value("${recarga.intervalo.emminutos}")
    private Long periodoRecarga;

    @Value("${alert.tempo.antes.fim}")
    private Long delayAlerta;

    public void sendAlertaTempoInicial(CompraTempo compraTempo) {
        long delay = compraTempo.getTempoEmMinutos() - this.delayAlerta;
        send(alertaTempoQueueName, compraTempo.getId(), delay);
    }

    public void sendRecompraAutomatica(CompraTempo compraTempo) {
        send(recompraAutomaticaQueueName, compraTempo.getId(), periodoRecarga);
    }

    private void send(String queueName, Long idCompraTempo, Long delay) {
        LOGGER.info("enviando mensagem para a fila " + queueName +
                " da compra " + idCompraTempo + " com o delay " + delay);
        try {
            amqpTemplate.convertAndSend(
                    QueueConfig.CUSTOM_EXCHANGE,
                    queueName + "RoutingQueue",
                    idCompraTempo.toString(),
                    message -> {
                        message.getMessageProperties().setHeader("x-delay", delay * 60 * 1000);
                        return message;
                    }
            );
        } catch (Exception e) {
            LOGGER.error("", e);
        }
    }
}
