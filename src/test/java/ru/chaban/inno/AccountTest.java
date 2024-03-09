package ru.chaban.inno;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AccountTest {

    @Test
    void getName() {
        String nameSet = "nameSet";
        Cur cur = new Cur("RUB");
        Balans balans = new Balans(cur, BigDecimal.valueOf(15));
        List<Balans> balanses = new ArrayList<>();
        balanses.add(balans);
        Account account = new Account(nameSet, balanses);
        assertEquals(nameSet, account.getName());
    }

    @Test
    void getBalans() {
    }

    @Test
    void setName() {
    }

    @Test
    void setBalans() {
        String nameSet = "nameSet";
        Cur cur = new Cur("RUB");
        Balans balans = new Balans(cur, BigDecimal.valueOf(1));
        List<Balans> balanses = new ArrayList<>();
        balanses.add(balans);
        Account account = new Account(nameSet, balanses);
        account.setBalance(new Cur("EUR"), BigDecimal.valueOf(15));
        account.setBalance(new Cur("EUR"), BigDecimal.valueOf(17));

        log.info("account {}", account);
        assertEquals(account.getBalance().size(), 2);
    }

}