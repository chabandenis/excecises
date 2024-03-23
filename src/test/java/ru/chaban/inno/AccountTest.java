package ru.chaban.inno;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.chaban.inno.data.AccountData;

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
        Balance balance = new Balance(cur, BigDecimal.valueOf(15));
        List<Balance> balances = new ArrayList<>();
        balances.add(balance);
        Account account = new Account();
        account.setCurData(new AccountData(nameSet, balances));

        ;
        assertEquals(nameSet, account.getCurData().getName());
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
        Balance balance = new Balance(cur, BigDecimal.valueOf(1));
        List<Balance> balances = new ArrayList<>();
        balances.add(balance);
        Account account = new Account(); //, new BigDecimal(0)
        account.setCurData(new AccountData("RUB", balances));
        account.setBalance(new Cur("EUR"), BigDecimal.valueOf(15));
        account.setBalance(new Cur("EUR"), BigDecimal.valueOf(17));

        log.info("account {}", account);
        assertEquals(account.getCurData().getBalance().size(), 2);
    }

    @Test
    void saveUndo() {

        Cur cur = new Cur("USD");
        Account account = new Account();
        account.setBalance(cur, new BigDecimal(0));
        account.save();
        log.info("1. cur: {}", account);

        account.setBalance(cur, new BigDecimal(2));
        account.save();
        log.info("2. cur: {}", account);

        account.undo();
        assertEquals(false, account.getCurData().getBalance()
                .stream()
                .filter(x -> x.getCurData().getCur().getCurData().getName().equals(cur.getCurData().getName()))
                .findFirst()
                .equals(BigDecimal.valueOf(0)));


        log.info("3. cur: {}", account);

        account.load(-1);
        log.info("4. cur: {}", account);

        log.info("Проверка получения по индексу 1");
        account.load(1);
        log.info("cur: {}", account);

        log.info("Проверка получения по индексу 0");
        account.load(0);
        log.info("cur: {}", account);


    }
}