package ru.chaban.inno;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Balans {
    // валюта
    private Cur cur;
    // значение
    private BigDecimal val;

}
