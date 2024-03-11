package ru.chaban.inno;

import lombok.Data;
import ru.chaban.inno.data.CurData;
import ru.chaban.inno.service.Loadable;
import ru.chaban.inno.service.UnitImpl;

@Data
public class Cur extends UnitImpl implements Loadable {
    private CurData curData = new CurData();
    private CurData snapshot = new CurData();

    public Cur(String name) {
        setName(name);
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        String oldName = curData.getName();
        putCommand(() -> this.curData.setName(oldName));

        this.curData.setName(name);
    }

    @Override
    public void load() {
        curData.setName(snapshot.getName());
    }

    @Override
    public void save() {
        snapshot.setName(this.getCurData().getName());
    }
}
