package br.com.devkemc.domain.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Cacheable
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long balance;
    @Column(name = "credit_limit")
    private long creditLimit;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private Set<Transaction> transactions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public long getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(long limit) {
        this.creditLimit = limit;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}
