package com.nopecho.utils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class Throw {

    private static final String URL_PATTERN = "^(https?|ftp)://(-\\.)?([^\\s/?\\.#-]+\\.?)+(/[^\\s?#]*)*(\\?(([^\\s&?=]+)(=([^&]*))?)(&(([^\s&?=]+)(=([^&]*))?))*)?$";

    public static void ifNull(Object o, String errorHead) {
        if (Objects.isNull(o)) {
            throw new IllegalStateException(String.format("[%s] 이(가) Null 일 수 없습니다.", errorHead));
        }
    }

    public static void ifNull(Object o) {
        if (Objects.isNull(o)) {
            throw new IllegalStateException("값이 Null 일 수 없습니다.");
        }
    }

    public static void ifBlank(String s, String errorHead) {
        if (s.isBlank()) {
            throw new IllegalStateException(String.format("[%s] 이(가) Blank 일 수 없습니다.", errorHead));
        }
    }

    public static void ifBlank(String s) {
        if (s.isBlank()) {
            throw new IllegalStateException("문자열이 Blank 일 수 없습니다.");
        }
    }


    public static void ifNullOrBlank(String o, String errorHead) {
        ifNull(o, errorHead);
        ifBlank(o, errorHead);
    }

    public static void ifNullOrBlank(String o) {
        ifNull(o);
        ifBlank(o);
    }

    public static void ifZero(Integer l, String errorHead) {
        if (l == 0) {
            throw new IllegalStateException(String.format("[%s] 이(가) 0 일 수 없습니다.", errorHead));
        }
    }

    public static void ifZero(Integer l) {
        if (l == 0) {
            throw new IllegalStateException("값이 0 일 수 없습니다.");
        }
    }

    public static void ifEmpty(List<?> list, String errorHead) {
        if(list.isEmpty()) {
            throw new IllegalStateException(String.format("[%s List] 이(가) Empty 일 수 없습니다.", errorHead));
        }
    }

    public static void ifEmpty(List<?> list) {
        if(list.isEmpty()) {
            throw new IllegalStateException("List 가 Empty 일 수 없습니다.");
        }
    }

    public static void ifNullOrEmpty(List<?> list, String errorHead) {
        ifNull(list, errorHead);
        ifEmpty(list, errorHead);
    }

    public static void ifInvalidUrl(String url) {
        if(isInvalidUrl(url)) {
            throw new IllegalArgumentException(String.format("[%s] 가 유효하지 않은 URL 입니다.", url));
        }
    }

    private static boolean isInvalidUrl(String url) {
        return !isValidUrl(url);
    }

    private static boolean isValidUrl(String url) {
        Pattern pattern = Pattern.compile(URL_PATTERN);
        return pattern.matcher(url).matches();
    }
}
