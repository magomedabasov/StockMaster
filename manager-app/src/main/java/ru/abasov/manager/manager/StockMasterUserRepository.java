package ru.abasov.manager.manager;

import org.springframework.data.repository.CrudRepository;
import ru.abasov.manager.entity.StockMasterUser;

import java.util.Optional;

public interface StockMasterUserRepository extends CrudRepository<StockMasterUser, Integer> {

    Optional<StockMasterUser> findByUsername(String username);
}
