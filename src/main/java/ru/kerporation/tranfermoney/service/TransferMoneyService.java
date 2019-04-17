package ru.kerporation.tranfermoney.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.kerporation.tranfermoney.exception.TransferMoneyException;

import java.math.BigDecimal;


@Service
public class TransferMoneyService {

    @Autowired
    private AccountService accountService;

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = TransferMoneyException.class)
    public void transferMoney(Long fromAccountId, Long toAccountId, BigDecimal amount) throws TransferMoneyException {
        if (fromAccountId.equals(toAccountId)) {
            throw new TransferMoneyException("Accounts ids are identical");
        }
        Long minAccount = (fromAccountId < toAccountId) ? fromAccountId : toAccountId;
        Long maxAccount = (fromAccountId < toAccountId) ? toAccountId : fromAccountId;
        BigDecimal transferAmount = (fromAccountId < toAccountId) ? amount.negate() : amount;

        accountService.changeBalance(minAccount, transferAmount);
        accountService.changeBalance(maxAccount, transferAmount.negate());
    }

}
