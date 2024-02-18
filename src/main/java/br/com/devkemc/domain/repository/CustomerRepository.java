package br.com.devkemc.domain.repository;

import br.com.devkemc.domain.entity.Customer;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerRepository implements PanacheRepository<Customer>{
}
