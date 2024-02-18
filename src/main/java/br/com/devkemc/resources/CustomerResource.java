package br.com.devkemc.resources;

import br.com.devkemc.dto.*;
import br.com.devkemc.domain.entity.Transaction;
import br.com.devkemc.domain.repository.CustomerRepository;
import br.com.devkemc.domain.repository.TransactionRepository;
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
public class CustomerResource {

    @Inject
    protected CustomerRepository customerRepository;
    @Inject
    protected TransactionRepository transactionRepository;


    @POST
    @Path("/{customerId}/transacoes")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<SucessTransactionDto>> makeTransaction(@RestPath final Long customerId, final NewTransactionDto transactionDto) {
        if (transactionDto.descricao().isBlank() || transactionDto.descricao().length() > 10 || transactionDto.tipo() != 'c' && transactionDto.tipo() != 'd') {
            return Uni.createFrom().item(RestResponse.status(422));
        }
        return customerRepository.findById(customerId)
                .onItem().transformToUni(customer -> {
                    if (customer == null) {
                        return Uni.createFrom().item(RestResponse.status(404));
                    }
                    if (transactionDto.tipo() == 'd' && transactionDto.valor() > customer.getBalance() + customer.getCreditLimit()) {
                        return Uni.createFrom().item(RestResponse.status(422));
                    }
                    if (transactionDto.tipo() == 'd' && transactionDto.valor() > customer.getBalance()) {
                        customer.setBalance(customer.getBalance() -transactionDto.valor());
                    } else if (transactionDto.tipo() == 'd') {
                        customer.setBalance(customer.getBalance() - transactionDto.valor());
                    } else {
                        customer.setBalance(customer.getBalance() + transactionDto.valor());
                    }
                    return transactionRepository.persistAndFlush(new Transaction(transactionDto.valor(), transactionDto.descricao(), transactionDto.tipo(), customer, ZonedDateTime.now()))
                            .map(transaction -> RestResponse.ok(new SucessTransactionDto(customer.getCreditLimit(), customer.getBalance())));
                });
    }

    @GET
    @Path("/{customerId}/extrato")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<RestResponse<CustomerResourceDto>> getResource(@RestPath final Long customerId) {
        return customerRepository.findById(customerId)
                .onItem().transformToUni(customer -> {
                    if (customer == null) {
                        return Uni.createFrom().item(RestResponse.status(404));
                    }
                    return transactionRepository.findByCustomerId(customerId)
                            .map(transactions -> new CustomerResourceDto(new ResouceDto(customer.getBalance(), customer.getCreditLimit(), ZonedDateTime.now(), transactions.stream()
                                    .map(transaction -> new TransactionDto(transaction.getValue(), transaction.getDescription(), transaction.getType(), transaction.getAccomplishedAt()))
                                    .toList())))
                            .map(RestResponse::ok);
                });
    }
}