package ru.chaban.inno;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chaban.inno.data.BalanceData;
import ru.chaban.inno.service.Loadable;
import ru.chaban.inno.service.UnitImpl;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class Balance extends UnitImpl implements Loadable {
    private BalanceData curData = new BalanceData();
    private BalanceData snapshot = new BalanceData();

    public Balance(Cur cur, BigDecimal val) {
        setBalance(cur, val);
    }

    public void setBalance(Cur cur, BigDecimal val) {
        if (val.compareTo(BigDecimal.valueOf(0)) < 0) {
            throw new RuntimeException("Сумма должна быть больше нуля");
        }

        Cur oldCur = curData.getCur();
        BigDecimal oldValue = curData.getVal();

        putCommand(() -> {
            this.curData.setCur(oldCur);
            this.curData.setVal(oldValue);
        });

        this.getCurData().setCur(cur);
        this.curData.setVal(val);
    }

    @Override
    public void load() {
        curData.setVal(snapshot.getVal());
        curData.setCur(snapshot.getCur());
    }

    @Override
    public void save() {
        snapshot.setCur(this.curData.getCur());
        snapshot.setVal(this.curData.getVal());
    }
}
