package br.com.devkemc.domain.useCase.customer;

import br.com.devkemc.domain.entity.Customer;
import br.com.devkemc.domain.repository.CustomerRepository;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class GetCustomer {
    @Inject
    protected CustomerRepository customerRepository;

    @WithSession
    public Uni<Customer> execute(final Customer customer) {
        return customerRepository.findById(customer.getId());
    }
}
