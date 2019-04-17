package ru.kerporation.tranfermoney.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import ru.kerporation.tranfermoney.repository.AccountRepository;
import ru.kerporation.tranfermoney.request.ChangeBalanceRequest;
import ru.kerporation.tranfermoney.request.TransferMoneyRequest;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private AccountRepository repository;

    @Test
    public void addMoneySuccessTest() {
        ChangeBalanceRequest request = new ChangeBalanceRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal(100));
        BigDecimal amountBefore = repository.findAccountById(request.getAccountId()).getBalance();
        assertEquals(0, amountBefore.compareTo(new BigDecimal(1000)));

        ResponseEntity<String> response = restTemplate.postForEntity("/change-balance", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("Account balance was changed"));
        BigDecimal amountAfter = repository.findAccountById(request.getAccountId()).getBalance();
        assertEquals(0, amountAfter.compareTo(new BigDecimal(1100)));
    }

    @Test
    public void withdrawMoneySuccessTest() {
        ChangeBalanceRequest request = new ChangeBalanceRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal(1000).negate());
        BigDecimal amountBefore = repository.findAccountById(request.getAccountId()).getBalance();
        assertEquals(0, amountBefore.compareTo(new BigDecimal(1000)));

        ResponseEntity<String> response = restTemplate.postForEntity("/change-balance", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("Account balance was changed"));
        BigDecimal amountAfter = repository.findAccountById(request.getAccountId()).getBalance();
        assertEquals(0, amountAfter.compareTo(BigDecimal.ZERO));
    }

    @Test
    public void notEnoughMoneyTest() {
        ChangeBalanceRequest request = new ChangeBalanceRequest();
        request.setAccountId(1L);
        request.setAmount(new BigDecimal(1001).negate());
        BigDecimal amountBefore = repository.findAccountById(request.getAccountId()).getBalance();
        assertEquals(0, amountBefore.compareTo(new BigDecimal(1000)));

        ResponseEntity<String> response = restTemplate.postForEntity("/change-balance", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), equalTo("Not enough money in account: 1"));
        BigDecimal amountAfter = repository.findAccountById(request.getAccountId()).getBalance();
        assertEquals(0, amountAfter.compareTo(amountBefore));
    }

    @Test
    public void accountNotFoundTest() {
        ChangeBalanceRequest request = new ChangeBalanceRequest();
        request.setAccountId(4L);
        request.setAmount(new BigDecimal(100).negate());

        ResponseEntity<String> response = restTemplate.postForEntity("/change-balance", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), equalTo("Account id: 4 not found"));
    }

    @Test
    public void transferMoneyCompletedTest() {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal(1000));

        BigDecimal amountBeforeFromAccount = repository.findAccountById(request.getFromAccountId()).getBalance();
        assertEquals(0, amountBeforeFromAccount.compareTo(new BigDecimal(1000)));
        BigDecimal amountBeforeToAccount = repository.findAccountById(request.getToAccountId()).getBalance();
        assertEquals(0, amountBeforeToAccount.compareTo(new BigDecimal(2000)));

        ResponseEntity<String> response = restTemplate.postForEntity("/transfer", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.OK));
        assertThat(response.getBody(), equalTo("Money transfer is completed"));

        BigDecimal amountAfterFromAccount = repository.findAccountById(request.getFromAccountId()).getBalance();
        assertEquals(0, amountAfterFromAccount.compareTo(BigDecimal.ZERO));
        BigDecimal amountAfterToAccount = repository.findAccountById(request.getToAccountId()).getBalance();
        assertEquals(0, amountAfterToAccount.compareTo(new BigDecimal(3000)));
    }

    @Test
    public void transferMoneyNotEnoughMoneyTest() {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(2L);
        request.setAmount(new BigDecimal(1001));

        BigDecimal amountBeforeFromAccount = repository.findAccountById(request.getFromAccountId()).getBalance();
        assertEquals(0, amountBeforeFromAccount.compareTo(new BigDecimal(1000)));
        BigDecimal amountBeforeToAccount = repository.findAccountById(request.getToAccountId()).getBalance();
        assertEquals(0, amountBeforeToAccount.compareTo(new BigDecimal(2000)));

        ResponseEntity<String> response = restTemplate.postForEntity("/transfer", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), equalTo("Not enough money in account: 1"));

        BigDecimal amountAfterFromAccount = repository.findAccountById(request.getFromAccountId()).getBalance();
        assertEquals(0, amountAfterFromAccount.compareTo(amountBeforeFromAccount));
        BigDecimal amountAfterToAccount = repository.findAccountById(request.getToAccountId()).getBalance();
        assertEquals(0, amountAfterToAccount.compareTo(amountBeforeToAccount));
    }

    @Test
    public void transferMoneyAccountNotFoundTest() {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(4L);
        request.setAmount(new BigDecimal(100));

        BigDecimal amountBeforeFromAccount = repository.findAccountById(request.getFromAccountId()).getBalance();
        assertEquals(0, amountBeforeFromAccount.compareTo(new BigDecimal(1000)));

        ResponseEntity<String> response = restTemplate.postForEntity("/transfer", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), equalTo("Account id: 4 not found"));

        BigDecimal amountAfterFromAccount = repository.findAccountById(request.getFromAccountId()).getBalance();
        assertEquals(0, amountAfterFromAccount.compareTo(amountBeforeFromAccount));
    }


    @Test
    public void transferMoneyBetweenAccountTest() {
        TransferMoneyRequest request = new TransferMoneyRequest();
        request.setFromAccountId(1L);
        request.setToAccountId(1L);
        request.setAmount(new BigDecimal(100));

        ResponseEntity<String> response = restTemplate.postForEntity("/transfer", request, String.class);
        assertThat(response.getStatusCode() , equalTo(HttpStatus.BAD_REQUEST));
        assertThat(response.getBody(), equalTo("Accounts ids are identical"));
    }
}