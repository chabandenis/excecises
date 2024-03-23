package ru.chaban.inno;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.chaban.inno.data.BalanceData;
import ru.chaban.inno.service.Loadable;
import ru.chaban.inno.service.UnitImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class Balance extends UnitImpl implements Loadable {
    private BalanceData curData = new BalanceData();
    private List<BalanceData> snapshot = new ArrayList<>();

    private BalanceData getLastSnapshot() {
        return this.getSnapshot().get(this.getSnapshot().size() - 1);
    }

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
    public void load(int item) {
        if (item >= snapshot.size() || item < -1) {
            throw new RuntimeException("Запрошено восстановление несуществующей точки");
        }

        if (item == -1) {
            curData.setVal(getLastSnapshot().getVal());
            curData.setCur(getLastSnapshot().getCur());
            return;
        }
        curData.setVal(snapshot.get(item).getVal());
        curData.setCur(snapshot.get(item).getCur());
    }

    @Override
    public int save() {
        BalanceData balanceData = new BalanceData();

        balanceData.setCur(this.curData.getCur());
        balanceData.setVal(this.curData.getVal());

        snapshot.add(balanceData);
        return snapshot.size() - 1;
    }
}
