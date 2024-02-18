package br.com.devkemc.dto;
import java.time.ZonedDateTime;
import java.util.List;

public record ResouceDto(
        long total,
        long limite,
        ZonedDateTime data_extrato,
        List<TransactionDto> ultimas_transacoes) {
}
