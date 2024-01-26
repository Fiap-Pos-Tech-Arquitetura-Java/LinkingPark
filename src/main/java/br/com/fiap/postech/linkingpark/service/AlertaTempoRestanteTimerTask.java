package br.com.fiap.postech.linkingpark.service;

import br.com.fiap.postech.linkingpark.entities.CompraTempo;
import br.com.fiap.postech.linkingpark.entities.NotificacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class AlertaTempoRestanteTimerTask extends TimerTask {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AlertaTempoRestanteTimerTask.class);

    private final CompraTempoService compraTempoService;

    private final NotificacaoService notificacaoService;

    private final Timer timer;

    private final Long idCompraTempo;

    private final Long delayAlerta;

    public AlertaTempoRestanteTimerTask(CompraTempoService compraTempoService,
                                        NotificacaoService notificacaoService,
                                        Long idCompraTempo, Long delayAlerta, Long periodo) {
        this.compraTempoService = compraTempoService;
        this.notificacaoService = notificacaoService;
        this.idCompraTempo = idCompraTempo;
        this.delayAlerta = delayAlerta;

        long delay = compraTempoService.get(idCompraTempo).getTempoEmMinutos() - this.delayAlerta;
        timer = new Timer();
        timer.scheduleAtFixedRate(this, delay * 60 * 1000, periodo * 60 * 1000);
    }

    @Override
    public void run() {
        CompraTempo compraTempo = compraTempoService.get(idCompraTempo);
        LocalDateTime horaLimite = LocalDateTime.now().plusMinutes(this.delayAlerta);
        if ("FINALIZADO".equals(compraTempo.getStatus())) {
            LOGGER.info(Thread.currentThread().getName() + " " + compraTempo + " já saiu da vaga. " + horaLimite);
            timer.cancel();
        } else {
            LocalDateTime tempoLimite = compraTempo.getHoraCompra().plusMinutes(compraTempo.getTempoEmMinutos());
            LocalDateTime now = LocalDateTime.now();
            LOGGER.info("tempoLimite=" + tempoLimite);
            LOGGER.info("now=" + now);
            if (tempoLimite.isBefore(now)) {
                LOGGER.info(Thread.currentThread().getName() + " " + compraTempo + " tempo comprado expirado. " + horaLimite);
                timer.cancel();
            } else {
                this.notificacaoService.alertaTempoRestante(compraTempo, horaLimite);
            }
        }
    }
}