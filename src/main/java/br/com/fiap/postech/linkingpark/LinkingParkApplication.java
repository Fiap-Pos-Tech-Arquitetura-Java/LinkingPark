package br.com.fiap.postech.linkingpark;

import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.service.FormaPagamentoService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableRabbit
@SpringBootApplication
public class LinkingParkApplication implements CommandLineRunner {
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Value("${queue.alerta.name}")
    private String alertaTempoQueueName;

    @Value("${queue.recompra.name}")
    private String recompraAutomaticaQueueName;

    public static void main(String[] args) {
        SpringApplication.run(LinkingParkApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        this.formaPagamentoService.save(new FormaPagamentoDTO(null,"PIX"));
        this.formaPagamentoService.save(new FormaPagamentoDTO(null,"Cartão de débito"));
        this.formaPagamentoService.save(new FormaPagamentoDTO(null,"Cartão de crédito"));

        /*try {
            new MeuChannel().getChannel().queueDeclare(alertaTempoQueueName, true, false, true, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            new MeuChannel().getChannel().queueDeclare(recompraAutomaticaQueueName, true, false, true, null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/
    }
}
