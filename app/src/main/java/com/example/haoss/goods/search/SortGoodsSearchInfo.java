package com.example.haoss.goods.search;

import java.util.Comparator;

//排序
public class SortGoodsSearchInfo implements Comparator<GoodsSearchInfo> {

    int flag;   //1：销量由大到小，2：价格由大到小，3：价格有小到大

    public SortGoodsSearchInfo(int flag) {
        this.flag = flag;
    }

    @Override
    public int compare(GoodsSearchInfo g1, GoodsSearchInfo g2) {
        int result = 0;
        switch (flag) {
            case 1:
                result = g1.getSaies() < g2.getSaies() ? 1 : (g1.getSaies() == g2.getSaies() ? 0 : -1);
                break;
            case 2:
                result = g1.getPrice() < g2.getPrice() ? 1 : (g1.getPrice() == g2.getPrice() ? 0 : -1);
                break;
            case 3:
                result = g1.getPrice() > g2.getPrice() ? 1 : (g1.getPrice() == g2.getPrice() ? 0 : -1);
                break;
        }
        return result;
    }
}
