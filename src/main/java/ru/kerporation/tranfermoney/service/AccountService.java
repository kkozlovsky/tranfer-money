package ru.kerporation.tranfermoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.kerporation.tranfermoney.domain.Account;
import ru.kerporation.tranfermoney.exception.TransferMoneyException;
import ru.kerporation.tranfermoney.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.Objects;


@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = TransferMoneyException.class)
    public void changeBalance(Long id, BigDecimal amount) throws TransferMoneyException {
        Account account = accountRepository.findAccountById(id);
        if (Objects.isNull(account)) {
            throw new TransferMoneyException("Account id: " + id + " not found");
        }
        BigDecimal newBalance = account.getBalance().add(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new TransferMoneyException("Not enough money in account: " + id);
        }
        account.setBalance(newBalance);
    }

}
