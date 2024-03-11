package ru.chaban.inno;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class BalanceTest {

    @Test
    void setVal() {
        String nameSet = "nameSet";
        Cur cur = new Cur("RUB");
        Throwable throwable = assertThrows(RuntimeException.class
                , () -> {
                    Balance balance = new Balance(cur, BigDecimal.valueOf(-51));
                }
        );

        assertEquals(throwable.getMessage(), "Сумма должна быть больше нуля");
    }

    @Test
    void SaveLoad() {
        Cur cur = new Cur("USD");
        Balance balance = new Balance(cur, BigDecimal.valueOf(1));
        balance.save();
        log.info("1. cur: {}", balance);

        balance.setBalance(cur, BigDecimal.valueOf(2));
        balance.save();
        log.info("2. cur: {}", balance);

        balance.undo();
        assertEquals(0, balance.getCurData().getVal().compareTo(BigDecimal.valueOf(1)));

        log.info("3. cur: {}", balance);

        balance.load();
        log.info("4. cur: {}", balance);
    }
}