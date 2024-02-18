package br.com.devkemc.domain.useCase.transaction;

import br.com.devkemc.domain.entity.Customer;
import br.com.devkemc.domain.entity.Transaction;
import br.com.devkemc.domain.exception.CustomerNotFoundException;
import br.com.devkemc.domain.repository.TransactionRepository;
import br.com.devkemc.domain.useCase.customer.GetCustomer;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class MakeTransaction {
    @Inject
    protected TransactionRepository transactionRepository;
    @Inject
    protected GetCustomer getCustomer;

    @WithTransaction
    public Uni<Customer> execute(final Transaction transaction) {
        final var customerUni = getCustomer.execute(transaction.getCustomer());
        return customerUni.onItem().transformToUni(customerFound -> {
            if (customerFound == null) {
                return Uni.createFrom().failure(new CustomerNotFoundException());
            }
            if (transaction.getType() == 'd' && transaction.getValue() > customerFound.getBalance() + customerFound.getCreditLimit()) {
                return Uni.createFrom().failure(new IllegalArgumentException("Invalid transaction"));
            }
            if (transaction.getType() == 'd' && transaction.getValue() > customerFound.getBalance()) {
                customerFound.setBalance(customerFound.getBalance() - transaction.getValue());
            } else if (transaction.getType() == 'd') {
                customerFound.setBalance(customerFound.getBalance() - transaction.getValue());
            } else {
                customerFound.setBalance(customerFound.getBalance() + transaction.getValue());
            }
            transaction.setCustomer(customerFound);
            return transactionRepository.persistAndFlush(transaction)
                    .map(transaction1 -> customerFound);
        });
    }

}
