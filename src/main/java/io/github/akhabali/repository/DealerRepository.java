package io.github.akhabali.repository;

import io.github.akhabali.model.Dealer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Dealer Repository: responsible for gathering dealer data from the database
 */
public interface DealerRepository extends JpaRepository<Dealer, Long> {
}
