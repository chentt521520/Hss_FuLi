package com.example.applibrary.utils;

import java.math.BigDecimal;

public class DecimalUtils {
    public static double format2Number(double money) {
        BigDecimal b = new BigDecimal(money);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
