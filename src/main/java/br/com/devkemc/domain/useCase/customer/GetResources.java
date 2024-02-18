package br.com.devkemc.domain.useCase.customer;

import br.com.devkemc.domain.entity.Customer;
import br.com.devkemc.domain.entity.Transaction;
import br.com.devkemc.domain.exception.CustomerNotFoundException;
import br.com.devkemc.domain.repository.TransactionRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

@ApplicationScoped
public class GetResources {
    @Inject
    protected TransactionRepository transactionRepository;
    @Inject
    protected GetCustomer getCustomer;

    @WithSession
    public Uni<List<Transaction>> execute(final Customer customer) {
        return getCustomer.execute(customer).onItem().transformToUni(customerFound -> {
            if (customerFound == null) {
                return Uni.createFrom().failure(new CustomerNotFoundException());
            }
            return transactionRepository.findByCustomerId(customerFound.getId());
        });
    }
}
