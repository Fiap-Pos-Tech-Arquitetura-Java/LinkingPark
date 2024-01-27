package br.com.fiap.postech.linkingpark.message.sender;

import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import br.com.fiap.postech.linkingpark.message.config.QueueConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSender {
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
        //Send message to delay queue
        amqpTemplate.convertAndSend(
                QueueConfig.CUSTOM_EXCHANGE,
                queueName + "RoutingQueue",
                idCompraTempo.toString(),
            message -> {
                message.getMessageProperties().setHeader("x-delay", delay * 60 * 1000);
                return message;
            }
        );
    }

    /*private void send(String queueName, Long  idCompraTempo, Long delay) {
        byte[] messageBodyBytes = idCompraTempo.toString().getBytes();
        AMQP.BasicProperties.Builder props = new AMQP.BasicProperties.Builder();
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("x-delay", delay);
        props.headers(headers);
        try {
            new MeuChannel().getChannel().basicPublish(queueName, "", props.build(), messageBodyBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/
}
