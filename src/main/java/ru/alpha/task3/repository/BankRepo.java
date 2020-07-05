package ru.alpha.task3.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alpha.task3.model.entity.Branch;

import java.util.Optional;

@Repository
public interface BankRepo extends JpaRepository<Branch, Long> {

    Optional<Branch> findById(Long id);
}
