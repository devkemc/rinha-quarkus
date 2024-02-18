package br.com.devkemc.infrastructure.web.controller;

import br.com.devkemc.domain.entity.Customer;
import br.com.devkemc.domain.entity.Transaction;
import br.com.devkemc.domain.useCase.customer.GetResources;
import br.com.devkemc.domain.useCase.transaction.MakeTransaction;
import br.com.devkemc.infrastructure.web.dto.customer.CustomerResource;
import br.com.devkemc.infrastructure.web.dto.customer.Resouce;
import br.com.devkemc.infrastructure.web.dto.customer.TransactionDto;
import br.com.devkemc.infrastructure.web.dto.transaction.NewTransaction;
import br.com.devkemc.infrastructure.web.dto.transaction.SucessTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestPath;
import org.jboss.resteasy.reactive.RestResponse;

import java.time.ZonedDateTime;

@Path("/clientes")
public class CustomerController {

    @Inject
    protected MakeTransaction makeTransaction;

    @Inject
    protected GetResources getResources;


    @POST
    @Path("/{customerId}/transacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<SucessTransaction>> makeTransaction(@RestPath final Long customerId, final NewTransaction transactionDto) {
        final var customer = new Customer(customerId);
        final var transaction = new Transaction(transactionDto.valor(), transactionDto.descricao(), transactionDto.tipo(), customer, ZonedDateTime.now());
        return makeTransaction.execute(transaction).onItem().transform(updatedCustomer -> {
            return RestResponse.ok(new SucessTransaction(updatedCustomer.getCreditLimit(), updatedCustomer.getBalance()));
        });
    }

    @GET
    @Path("/{customerId}/extrato")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<CustomerResource>> getResource(@RestPath final Long customerId) {
        var customer = new Customer(customerId);
        return getResources.execute(customer).map(transactions -> {
            return new CustomerResource(new Resouce(customer.getBalance(), customer.getCreditLimit(), ZonedDateTime.now(), transactions.stream()
                    .map(transaction -> new TransactionDto(transaction.getValue(), transaction.getDescription(), transaction.getType(), transaction.getAccomplishedAt()))
                    .toList()));
        }).map(RestResponse::ok);
    }
}