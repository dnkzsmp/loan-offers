package ru.generator.loanoffers.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.generator.loanoffers.enums.Answer;

import java.util.Date;
import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    @Query("SELECT e FROM Offer e WHERE e.answer = :answer AND e.creationDate >= :cutoffDate")
    List<Offer> findOfferByAnswerAndCreationDate(
            @Param("answer") Answer answer,
            @Param("cutoffDate") Date creationDate
    );

    List<Offer> findOfferByAnswerIsNull();
}
