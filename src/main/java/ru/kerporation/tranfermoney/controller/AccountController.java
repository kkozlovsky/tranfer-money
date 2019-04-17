package ru.kerporation.tranfermoney.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.kerporation.tranfermoney.exception.TransferMoneyException;
import ru.kerporation.tranfermoney.request.ChangeBalanceRequest;
import ru.kerporation.tranfermoney.request.TransferMoneyRequest;
import ru.kerporation.tranfermoney.service.AccountService;
import ru.kerporation.tranfermoney.service.TransferMoneyService;

import javax.validation.Valid;

@RestController
public class AccountController {

    @Autowired
    private TransferMoneyService transferMoneyService;

    @Autowired
    private AccountService accountService;

    @PostMapping(value = "/change-balance", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeBalance(@Valid @RequestBody ChangeBalanceRequest request) throws TransferMoneyException {
            accountService.changeBalance(request.getAccountId(), request.getAmount());
            return ResponseEntity.ok().body("Account balance was changed");
    }

    @PostMapping(value = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> transferMoney(@Valid @RequestBody TransferMoneyRequest request) throws TransferMoneyException {
            transferMoneyService.transferMoney(request.getFromAccountId(), request.getToAccountId(), request.getAmount());
            return ResponseEntity.ok().body("Money transfer is completed");
    }
}
