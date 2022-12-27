package io.github.akhabali.service;

import io.github.akhabali.errors.DealerAlreadyExistsException;
import io.github.akhabali.errors.DealerNotFoundException;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Dealer Service: responsible for orchestrating data access and data transformation
 */
@Service
@RequiredArgsConstructor
public class DealerService {

    private final DealerRepository dealerRepository;

    public Dealer findById(Long id) {
        return dealerRepository.findById(id).orElseThrow(() -> new DealerNotFoundException(id));
    }

    public List<Dealer> findAll() {
        return dealerRepository.findAll();
    }

    public Dealer newDealer(Dealer dealer) {
        if (dealer.getId() != null) {
            throw new DealerAlreadyExistsException(dealer.getId());
        }
        return this.dealerRepository.save(dealer);
    }

    public void delete(Long id) {
        try {
            this.dealerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new DealerNotFoundException(id);
        }
    }
}
