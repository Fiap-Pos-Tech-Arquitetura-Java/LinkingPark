package br.com.fiap.postech.linkingpark;

import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.service.CompraTempoService;
import br.com.fiap.postech.linkingpark.service.FormaPagamentoService;
import br.com.fiap.postech.linkingpark.service.MotoristaService;
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
    @Autowired
    private MotoristaService motoristaService;
    @Autowired
    private CompraTempoService compraTempoService;

    @Value("${linkingpark.mongo.clear.collections}")
    private Boolean clearCollections;

    public static void main(String[] args) {
        SpringApplication.run(LinkingParkApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        if (clearCollections) {
            this.formaPagamentoService.deleteAll();
            this.motoristaService.deleteAll();
            this.compraTempoService.deleteAll();
            this.formaPagamentoService.save(new FormaPagamentoDTO(null,"PIX"));
            this.formaPagamentoService.save(new FormaPagamentoDTO(null,"Cartão de débito"));
            this.formaPagamentoService.save(new FormaPagamentoDTO(null,"Cartão de crédito"));
        }
    }
}
