package br.com.devkemc.domain.repository;

import br.com.devkemc.domain.entity.Transaction;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class TransactionRepository implements PanacheRepository<Transaction> {
    public Uni<List<Transaction>> findByCustomerId(Long customerId) {
        return find("customer.id = ?1 ORDER BY accomplishedAt DESC",customerId).page(0,10).list();
    }
}
