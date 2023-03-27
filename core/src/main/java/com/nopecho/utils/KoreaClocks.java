package com.nopecho.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class KoreaClocks {
    public static String TIMEZONE_KST = "Asia/Seoul";

    public static ZonedDateTime now() {
        return ZonedDateTime.now(ZoneId.of(TIMEZONE_KST));
    }

    public static ZonedDateTime convertToDate(String string) {
        LocalDateTime localDateTime = LocalDateTime.parse(string);
        return ZonedDateTime.of(localDateTime, ZoneId.of(TIMEZONE_KST));
    }
}
