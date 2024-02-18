package br.com.devkemc.infrastructure.web.dto.transaction;

public record NewTransaction(

        long valor,
        String descricao,
        char tipo) {
}
