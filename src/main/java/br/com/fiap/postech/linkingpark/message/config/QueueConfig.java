package br.com.fiap.postech.linkingpark.message.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class QueueConfig {
    public static final String CUSTOM_EXCHANGE = "linkingParkCustomExchange";

    @Value("${queue.alerta.name}")
    private String alertaTempoQueueName;

    @Value("${queue.recompra.name}")
    private String recompraAutomaticaQueueName;

    private final ObjectMapper objectMapper;

    public QueueConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    CustomExchange linkingParkCustomExchange() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-delayed-type", "direct");
        return new CustomExchange(CUSTOM_EXCHANGE, "x-delayed-message", true, false, args);
    }

    @Bean
    public Queue alertaTempoQueue() {
        return new Queue(alertaTempoQueueName);
    }

    @Bean
    public Queue recompraAutomaticaQueue() {
        return new Queue(recompraAutomaticaQueueName);
    }

    @Bean
    public Binding alertaTempoDelayBinding(CustomExchange linkingParkCustomExchange, Queue alertaTempoQueue) {
        return BindingBuilder
                .bind(alertaTempoQueue)
                .to(linkingParkCustomExchange)
                .with(alertaTempoQueueName + "RoutingQueue")
                .noargs();
    }

    @Bean
    public Binding recompraAutomaticaDelayBinding(CustomExchange linkingParkCustomExchange, Queue recompraAutomaticaQueue) {
        return BindingBuilder
               .bind(recompraAutomaticaQueue)
               .to(linkingParkCustomExchange)
               .with(recompraAutomaticaQueueName + "RoutingQueue")
               .noargs();
    }
}
