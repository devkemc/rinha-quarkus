package br.com.devkemc.domain.entity;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Cacheable
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long value;
    @Column(length = 10)
    private String description;
    private char type;
    @ManyToOne
    private Customer customer;
    @Column(name = "accomplished_at")
    private ZonedDateTime accomplishedAt;

    public Transaction(long value, String description, char type, Customer customer, ZonedDateTime accomplishedAt) {
        this.value = value;
        this.description = description;
        this.type = type;
        this.customer = customer;
        this.accomplishedAt = accomplishedAt;
    }

    public Transaction() {

    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ZonedDateTime getAccomplishedAt() {
        return accomplishedAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAccomplishedAt(ZonedDateTime accomplishedAt) {
        this.accomplishedAt = accomplishedAt;
    }
}
