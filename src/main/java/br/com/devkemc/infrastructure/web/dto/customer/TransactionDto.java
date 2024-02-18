package br.com.devkemc.infrastructure.web.dto.customer;

import java.time.ZonedDateTime;

public record TransactionDto(long valor, String descricao, char tipo, ZonedDateTime realizado_em) {
}
