package ru.chaban.inno;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.chaban.inno.data.AccountData;
import ru.chaban.inno.service.Loadable;
import ru.chaban.inno.service.UnitImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Slf4j
public class Account extends UnitImpl implements Loadable {
    private AccountData curData = new AccountData();
    private AccountData snapshot = new AccountData();

    /*public Account(String nameSet, List<Balance> balances) {
        curData = new AccountData(nameSet, balances);
    }*/

    public void setBalance(Cur cur, BigDecimal value) {
//        if (curData.getBalance().size() >0){
        Optional<Balance> bal = curData.getBalance()
                .stream()
                .filter(x -> x.getCurData().getCur().getCurData().getName().equals(cur.getCurData().getName()))
                .findFirst();

        AccountData oldAccoutValue = new AccountData();
        oldAccoutValue.setBalance(new ArrayList<>(curData.getBalance()));
        oldAccoutValue.setName(curData.getName());

        putCommand(() -> {
            this.curData.setBalance(oldAccoutValue.getBalance());
            this.curData.setName(oldAccoutValue.getName());
        });

        if (bal.isPresent()
        ) {
            log.info("Валюта ранее была создана");
            bal.get().getCurData().setVal(value);
        } else {
            log.info("Валюта ранее отсутствовала, добавим");
            this.curData.getBalance().add(new Balance(cur, value));
        }
    }


    @Override
    public void load() {
        curData.setBalance(new ArrayList<>(curData.getBalance()));
        curData.setName(snapshot.getName());
    }

    @Override
    public void save() {
        snapshot.setName(this.getCurData().getName());
        snapshot.setBalance(new ArrayList<>(this.getSnapshot().getBalance()));
    }
}
