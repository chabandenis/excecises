package ru.chaban.inno;

import lombok.Data;
import ru.chaban.inno.data.CurData;
import ru.chaban.inno.service.Loadable;
import ru.chaban.inno.service.UnitImpl;

import java.util.ArrayList;

@Data
public class Cur extends UnitImpl implements Loadable {
    private CurData curData = new CurData();
    private ArrayList<CurData> snapshot = new ArrayList<>();

    public Cur(String name) {
        setName(name);
    }

    private CurData getLastSnapshot() {
        return this.getSnapshot().get(this.getSnapshot().size() - 1);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String oldName = curData.getName();
        putCommand(() -> this.curData.setName(oldName));

        this.curData.setName(new String(name));
    }

    @Override
    public void load(int item) {
        if (item >= snapshot.size() || item < -1) {
            throw new RuntimeException("Запрошено восстановление несуществующей точки");
        }

        if (item == -1) {
            curData.setName(getLastSnapshot().getName());
            return;
        }

        curData.setName(snapshot.get(item).getName());
    }

    @Override
    public int save() {
        CurData curDataForSave = new CurData();
        curDataForSave.setName(new String(this.getCurData().getName()));
        snapshot.add(curDataForSave);
        return snapshot.size() - 1;
    }
}
