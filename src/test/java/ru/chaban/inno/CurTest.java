package ru.chaban.inno;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class CurTest {

    @Test
    void setName() {
        Cur cur= new Cur("111");
        log.info("1. cur: {}", cur);
        cur.save();

        cur.setName("222");
        cur.save();
        log.info("2. cur: {}", cur);

        log.info("Откатим");
        assertEquals("111", ((Cur)cur.undo()).getCurData().getName());;
        log.info("3. cur: {}", cur);

        log.info("Последнее сохраненное");
        cur.load(-1);
        log.info("4. cur: {}", cur);

        log.info("Проверка сохраненного значения по индексу 1");
        cur.load(1);
        log.info("4. cur: {}", cur);

        log.info("Проверка сохраненного значения по индексу 0");
        cur.load(0);
        log.info("4. cur: {}", cur);

    }
}