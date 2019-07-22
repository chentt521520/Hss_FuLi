package com.example.applibrary.utils;

import android.text.TextUtils;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.Map;

//字符串工具集
public class StringUtils {

    //map转jsongString
    public static String mapToJson(Map<String, String> map) {
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    //转码
    public static String decodeUnicode(String dataStr) {
        dataStr = StringEscapeUtils.unescapeJava(dataStr);//去掉转义字符
        if (dataStr.indexOf("\\u") == -1)
            return dataStr;
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    //流转字符串
    public static String fromStream(InputStream is) throws IOException {
        return fromStream(is, Charset.forName("UTF-8"));
    }

    public static String fromStream(InputStream is, Charset charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
        StringBuilder sb = new StringBuilder();
        String line;
        boolean hasData = false;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
            if (!hasData) {
                hasData = true;
            }
        }
        if (sb.toString().endsWith("\n")) {
            sb.deleteCharAt(sb.length() - 1);
        }
        try {
            reader.close();
        } catch (IOException e) {
            // Ignore
        }
        return hasData ? sb.toString() : null;
    }

    public static String format(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }

    //是不是空
    public static boolean isNotEmpty(String string) {
        return !isEmpty(string);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.length() == 0;
    }

    //字符串对比
    public static boolean compare(String str1, String str2) {
        String s1 = str1.replace(" ", "");
        String s2 = str2.replace(" ", "");
        return s1.equalsIgnoreCase(s2);
    }

    //判断有效电话号码
    public static boolean validatePhoneNumber(String mobiles) {
//        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        String telRegex = "[1][3456789]\\d{9}";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    //判断是否包含数字和字母
    public static boolean containLetterAndNumber(String str) {
        if (firstIsLetter(str.substring(0, 1))) {
            if (str.matches("[a-zA-Z]+"))    //纯字母
                return false;
            else if (str.matches("[0-9]+"))  //纯数字
                return false;
            else
                return true;
        } else
            return false;
    }

    //首位是否是字母
    private static boolean firstIsLetter(String str) {
        if (str.matches("[a-zA-Z]+"))    //纯字母
            return true;
        return false;
    }
}
