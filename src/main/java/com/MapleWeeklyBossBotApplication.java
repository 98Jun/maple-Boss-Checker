package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * packageName    : PACKAGE_NAME
 * fileName       : MapleBossCheckerApplication
 * author         : jun
 * date           : 25. 12. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 10.        jun       최초 생성
 */
@SpringBootApplication
@EnableScheduling
public class MapleWeeklyBossBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(MapleWeeklyBossBotApplication.class, args);
    }
}