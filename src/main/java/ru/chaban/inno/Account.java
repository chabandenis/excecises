package ru.chaban.inno;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
@Slf4j
public class Account {
    // владелец
    private String name;

    // баланс
    private List<Balans> balance;


    public void setBalance(Cur cur, BigDecimal value) {
        Optional<Balans> bal = balance
                .stream()
                .filter(x -> x.getCur().getName().equals(cur.getName()))
                .findFirst();

        if (bal.isPresent()
        ) {
            log.info("Валюта ранее была создана");
            bal.get().setVal(value);
        } else {
            log.info("Валюта ранее отсутствовала, добавим");
            this.balance.add(new Balans(cur, value));
        }
    }


}
