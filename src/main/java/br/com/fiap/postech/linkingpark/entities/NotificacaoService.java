package br.com.fiap.postech.linkingpark.entities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NotificacaoService {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(NotificacaoService.class);

    public void alertaRecargaAutomatica(CompraTempo compraTempo, LocalDateTime now) {
        LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                Thread.currentThread().getName() + " " + compraTempo + "\r\n uma recarga foi feita de forma automática pois a compra de tempo anterior ainda não foi finalizada " + now);
    }

    public void alertaTempoRestante(CompraTempo compraTempo, LocalDateTime now) {
        LOGGER.info(new Object(){}.getClass().getEnclosingMethod().getName() + " " +
                Thread.currentThread().getName() + " " + compraTempo + "\r\n o tempo comprado irá acabar " + now);
    }
}
