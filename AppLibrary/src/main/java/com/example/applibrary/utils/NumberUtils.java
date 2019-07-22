package com.example.applibrary.utils;

import java.math.BigDecimal;

public class NumberUtils {

    /**
     * 保留2位小数 进位（4舍5入）
     *
     * @param number
     * @return
     */
    public static double getTwoDouble(double number) {
        BigDecimal b = new BigDecimal(number);
        number = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return number;
    }
}
