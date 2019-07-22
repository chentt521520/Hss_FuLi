package com.example.applibrary.utils;

import android.app.Activity;
import android.app.Dialog;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//用于解析json防止long型变异
public class ObjectMapperUtils {

    public static ObjectMapperUtils objectMapperUtils;
    ObjectMapper objectMapper;  //解析处理器

    public static ObjectMapperUtils getUtils() {
        if (objectMapperUtils == null)
            objectMapperUtils = new ObjectMapperUtils();
        return objectMapperUtils;
    }

    private ObjectMapper getMapper() {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();
        return objectMapper;
    }

    //json To map
    public Map<String, Object> jsonToMap(String json) {

        Map<String, Object> map;
        try {
            map = getMapper().readValue(json, HashMap.class);
        } catch (IOException e) {
            return null;
        }
        return map;
    }

    //json To list
    public ArrayList<Object> jsonToList(String json) {
        ArrayList<Object> list;
        try {
            list = getMapper().readValue(json, ArrayList.class);
        } catch (IOException e) {
            return null;
        }
        return list;
    }

    //map To map
    public Map<String, Object> mapToMap(Map<String, Object> map, String code) {
        map = (Map<String, Object>) map.get(code);
        return map;
    }

    //map To list
    public ArrayList<Object> mapToList(Map<String, Object> map, String code) {
        ArrayList<Object> list = (ArrayList<Object>) map.get(code);
        return list;
    }

    //map To String
    public String mapToString(Map<String, Object> map, String code) {
        String string = map.get(code) + "";
        if (string == null)
            string = "";
        return string;
    }

    //map To int
    public int mapToInt(Map<String, Object> map, String code) {
        String string = map.get(code) + "";
        if (string == null)
            string = "0";
        int mInt = Integer.parseInt(string);
        return mInt;
    }

    //map To double
    public double mapToDouble(Map<String, Object> map, String code) {
        String string = map.get(code) + "";
        if (string == null)
            string = "0";
        double mDouble = Double.parseDouble(string);
        return mDouble;
    }

}
