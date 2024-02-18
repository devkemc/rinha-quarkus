package br.com.devkemc.infrastructure.web.dto.customer;

import java.time.ZonedDateTime;
import java.util.List;

public record Resouce(
        long total,
        long limite,
        ZonedDateTime data_extrato,
        List<TransactionDto> ultimas_transacoes) {
}
