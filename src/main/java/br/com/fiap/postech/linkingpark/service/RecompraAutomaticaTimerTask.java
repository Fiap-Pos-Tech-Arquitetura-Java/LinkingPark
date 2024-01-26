package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.dto.CompraTempoDTO;
import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import br.com.fiap.postech.linkingpark.entities.NotificacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class RecompraAutomaticaTimerTask extends TimerTask {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(RecompraAutomaticaTimerTask.class);
    private final CompraTempoService compraTempoService;

    private final NotificacaoService notificacaoService;

    private final Timer timer;

    private final Long idCompraTempo;

    public RecompraAutomaticaTimerTask(CompraTempoService compraTempoService, NotificacaoService notificacaoService,
                                       Long idCompraTempo, Long periodo) {
        this.compraTempoService = compraTempoService;
        this.notificacaoService = notificacaoService;
        this.idCompraTempo = idCompraTempo;

        timer = new Timer();
        timer.scheduleAtFixedRate(this, 60 * 1000,periodo * 60 * 1000);
    }

    @Override
    public void run() {
        CompraTempo compraTempo = compraTempoService.get(idCompraTempo);
        LocalDateTime now = LocalDateTime.now();
        if ("FINALIZADO".equals(compraTempo.getStatus())) {
            LOGGER.info(Thread.currentThread().getName() + " " + compraTempo + " já saiu da vaga. " + now);
            timer.cancel();
        } else {
            compraTempo.adicionaUmaHora();
            this.compraTempoService.save(compraTempo);
            this.notificacaoService.alertaRecargaAutomatica(compraTempo, now);
        }
    }
}