package ru.generator.loanoffers.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponseNodeRepository extends JpaRepository<ResponseNode, Long> {
    List<ResponseNode> findResponseNodeByOfferId(Long id);
}
