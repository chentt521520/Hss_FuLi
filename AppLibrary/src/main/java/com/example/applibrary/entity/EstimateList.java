package com.example.applibrary.entity;

public class EstimateList {
    /**
     * "count": {
     * "all": 744,
     * "good": 744,
     * "middle": 0,
     * "bad": 0,
     * "is_img": 0
     * },
     * "list"
     */
    private EstimateCount count;
    private ReplyInfo list;

    public EstimateCount getCount() {
        return count;
    }

    public void setCount(EstimateCount count) {
        this.count = count;
    }

    public ReplyInfo getList() {
        return list;
    }

    public void setList(ReplyInfo list) {
        this.list = list;
    }

    public static class EstimateCount {
        private int all;//全部
        private int good;//好评
        private int middle;//中评
        private int bad;//差评
        private int is_img;//有图

        public EstimateCount(int all, int good, int middle, int bad, int is_img) {
            this.all = all;
            this.good = good;
            this.middle = middle;
            this.bad = bad;
            this.is_img = is_img;
        }

        public int getAll() {
            return all;
        }

        public void setAll(int all) {
            this.all = all;
        }

        public int getGood() {
            return good;
        }

        public void setGood(int good) {
            this.good = good;
        }

        public int getMiddle() {
            return middle;
        }

        public void setMiddle(int middle) {
            this.middle = middle;
        }

        public int getBad() {
            return bad;
        }

        public void setBad(int bad) {
            this.bad = bad;
        }

        public int getIs_img() {
            return is_img;
        }

        public void setIs_img(int is_img) {
            this.is_img = is_img;
        }
    }
}
