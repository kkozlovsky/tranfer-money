package ru.kerporation.tranfermoney.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kerporation.tranfermoney.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findAccountById(Long id);

}
