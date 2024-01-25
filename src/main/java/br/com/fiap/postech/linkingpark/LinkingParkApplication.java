package br.com.fiap.postech.linkingpark;

import br.com.fiap.postech.linkingpark.dto.FormaPagamentoDTO;
import br.com.fiap.postech.linkingpark.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LinkingParkApplication implements CommandLineRunner {
    @Autowired
    private FormaPagamentoService formaPagamentoService;

    public static void main(String[] args) {
        SpringApplication.run(LinkingParkApplication.class, args);
    }
    @Override
    public void run(String... args) throws Exception {
        this.formaPagamentoService.save(new FormaPagamentoDTO(null,"PIX"));
        this.formaPagamentoService.save(new FormaPagamentoDTO(null,"Cartão de débito"));
        this.formaPagamentoService.save(new FormaPagamentoDTO(null,"Cartão de crédito"));
    }

}
