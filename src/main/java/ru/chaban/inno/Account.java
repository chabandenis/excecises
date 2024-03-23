package ru.chaban.inno;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.chaban.inno.data.AccountData;
import ru.chaban.inno.data.BalanceData;
import ru.chaban.inno.service.Loadable;
import ru.chaban.inno.service.UnitImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
@Data
@Slf4j
public class Account extends UnitImpl implements Loadable {
    private AccountData curData = new AccountData();

    private List<AccountData> snapshot = new ArrayList<>();

    private AccountData getLastSnapshot() {
        return this.getSnapshot().get(this.getSnapshot().size() - 1);
    }

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
    public void load(int item) {
        if (item >= snapshot.size() || item < -1) {
            throw new RuntimeException("Запрошено восстановление несуществующей точки");
        }

        if (item == -1) {
            curData.setBalance(new ArrayList<>(curData.getBalance()));
            curData.setName(getLastSnapshot().getName());
            return;
        }

        curData.setBalance(new ArrayList<>(snapshot.get(item).getBalance()));
        curData.setName(snapshot.get(item).getName());
    }

    @Override
    public int save() {
        AccountData accountData = new AccountData();


        accountData.setName(new String(this.getCurData().getName() == null ? "" : this.getCurData().getName()));

        List<Balance> balances = new ArrayList<>();

        for (Balance balanceFrom : this.getCurData().getBalance()) {
            Balance newBalance = new Balance();
            BalanceData newBalanceData = new BalanceData();
            newBalanceData.setCur(balanceFrom.getCurData().getCur());
            newBalanceData.setVal(balanceFrom.getCurData().getVal());

            newBalance.setCurData(newBalanceData);
            balances.add(newBalance);
        }

        accountData.setBalance(balances);
        snapshot.add(accountData);
        return snapshot.size() - 1;
    }
}
