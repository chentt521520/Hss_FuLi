package com.example.applibrary.custom.viewfragment;

import java.util.Comparator;

//排序
public class SortFragmentDataInfo implements Comparator<FragmentDataInfo> {
    @Override
    public int compare(FragmentDataInfo o1, FragmentDataInfo o2) {
        return o1.getOrder() > o2.getOrder() ? 1 : -1;
    }
}