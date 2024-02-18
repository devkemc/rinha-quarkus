package br.com.devkemc.dto;

public record NewTransactionDto(

        long valor,
        String descricao,
        char tipo) {
}
