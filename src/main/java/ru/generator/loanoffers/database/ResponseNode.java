package ru.generator.loanoffers.database;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "responseNodes")
public class ResponseNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long offerId;

    @Column(nullable = false)
    private Long userId;
}
