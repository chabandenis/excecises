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

        cur.setName("222");
        cur.save();
        log.info("2. cur: {}", cur);

        assertEquals("111", ((Cur)cur.undo()).getCurData().getName());;
        log.info("3. cur: {}", cur);

        cur.load();
        log.info("4. cur: {}", cur);
    }
}