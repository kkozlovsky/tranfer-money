package ru.kerporation.tranfermoney;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.kerporation.tranfermoney.domain.Account;
import ru.kerporation.tranfermoney.repository.AccountRepository;

import java.math.BigDecimal;

@SpringBootApplication
public class TransferMoneyApplication {

    @Autowired
    AccountRepository accountRepository;

    public static void main(String[] args) {
        SpringApplication.run(TransferMoneyApplication.class, args);
    }


    @Bean
    InitializingBean fillDatabase() {
        return () -> {
            Account account1 = new Account();
            account1.setOwnerName("Kirill");
            account1.setBalance(new BigDecimal(1000));

            Account account2 = new Account();
            account2.setOwnerName("Max");
            account2.setBalance(new BigDecimal(2000));

            Account account3 = new Account();
            account3.setOwnerName("Ivan");
            account3.setBalance(new BigDecimal(5000));

            accountRepository.save(account1);
            accountRepository.save(account2);
            accountRepository.save(account3);
        };
    }
}
