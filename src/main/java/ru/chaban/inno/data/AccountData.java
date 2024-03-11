package ru.chaban.inno.data;

import lombok.*;
import ru.chaban.inno.Balance;

import java.util.ArrayList;
import java.util.List;

@Data
//@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class AccountData {
    // владелец
    private String name;

    // баланс
    private List<Balance> balance = new ArrayList<>();
}
