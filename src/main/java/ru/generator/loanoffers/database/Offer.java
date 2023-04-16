package ru.generator.loanoffers.database;

import jakarta.persistence.*;
import lombok.*;
import ru.generator.loanoffers.enums.Answer;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date creationDate;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Answer answer;
}
