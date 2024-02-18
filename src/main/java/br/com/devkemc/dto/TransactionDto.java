package br.com.devkemc.dto;

import java.time.ZonedDateTime;

public record TransactionDto(long valor, String descricao, char tipo, ZonedDateTime realizado_em) {
}
