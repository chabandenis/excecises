package ru.chaban.inno.data;

import lombok.Data;
import ru.chaban.inno.Cur;

import java.math.BigDecimal;

@Data
public class BalanceData {
    // валюта
    private Cur cur;
    // значение
    private BigDecimal val;
}
