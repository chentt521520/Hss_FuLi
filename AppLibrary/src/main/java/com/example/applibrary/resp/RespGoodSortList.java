package com.example.applibrary.resp;

import com.example.applibrary.entity.GoodList;

import java.util.List;

public class RespGoodSortList {

    /**
     * code : 200
     * msg : ok
     * data : {"cate_name":"新鲜水果","list":[{"id":60,"store_name":"《当日精选鲜货》埃及橙脐橙夏橙新鲜水果当季橙子果冻橙冰糖橙带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png","sales":1365,"price":"35.49","stock":1996,"is_show":1,"is_del":0,"store_type":0},{"id":61,"store_name":"《当日精选鲜货》四川安岳黄柠檬 新鲜当季水果现摘一级果鲜青柠檬批发带包装5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/db7104eeab24bb9b76562846f34ae5dd.png","sales":1478,"price":"24.64","stock":19999,"is_show":1,"is_del":0,"store_type":0},{"id":65,"store_name":"《当日精选鲜货》百香果热带水果新鲜西番莲鸡蛋果带箱10-20粒装大红果当季整箱","cate_id":"587","image":"http://qiniu.haoshusi.com/images/e121c6c286bbd18b7c9b758a392788dc.png","sales":888,"price":"14.25","stock":1999,"is_show":1,"is_del":0,"store_type":0},{"id":66,"store_name":"《当日精选鲜货》海南三亚冰糖心香水菠萝 时令特产新鲜水果带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/f14e36f66ee617aed19a7700ee2b85ad.png","sales":902,"price":"42.40","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":67,"store_name":"《当日精选鲜货》海南菠萝蜜（黄肉）新鲜水果热带三亚果园现摘现发 木菠萝波罗蜜带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/3f9a31b6624fb8270bb67fbc194af682.png","sales":1314,"price":"42.40","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":74,"store_name":"《当日精选鲜货》越南红尘妃子笑荔枝新鲜包邮当季特产水果应季新鲜荔枝带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/62c33f554a3ba83ff8b78ed40f1e51e6.png","sales":1142,"price":"83.12","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":76,"store_name":"《当日精选鲜货》贵妃芒果新鲜 当季水果整箱中大果小红金龙青贵妃芒带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/ff623275174d4f74043cfe8fb27c7fb0.png","sales":1441,"price":"38.59","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":77,"store_name":"《当日精选鲜货》海南红心火龙果新鲜水果当季大果红肉带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/45c479eae237fafed66a4fe431c12a3c.png","sales":1242,"price":"61.85","stock":42624,"is_show":1,"is_del":0,"store_type":0},{"id":78,"store_name":"《当日精选鲜货》现摘黑布林李子黑布朗新鲜现货孕妇红心水果红布林带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/1e9f25134b3411a0ec5acd549226e7ce.png","sales":1413,"price":"56.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":79,"store_name":"《当日精选鲜货》蓝莓鲜果 当季新鲜蓝莓4盒装125g*4","cate_id":"587","image":"http://qiniu.haoshusi.com/images/07376f7029fa276f4e8cdb6cfd3738e5.png","sales":242,"price":"39.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":80,"store_name":"《当日精选鲜货》黑美人西瓜水果新鲜 当季薄皮进口黑籽带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/ead1c39fb08b46096756a719287d255c.png","sales":1277,"price":"11.20","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":84,"store_name":"《当日精选鲜货》红提葡萄新鲜提子应季当季水果时令鲜果带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/7d7a5f2ae0013477f5fd58cf36483395.png","sales":2771,"price":"63.00","stock":22220,"is_show":1,"is_del":0,"store_type":0},{"id":86,"store_name":"《当日精选鲜货》红心火龙果 新鲜水果一级大果红肉金都一号（带箱5-10斤装）","cate_id":"587","image":"http://qiniu.haoshusi.com/images/e3840201907061606133146.jpg","sales":2424,"price":"56.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":87,"store_name":"《当日精选鲜货》正宗红心火龙果二级果新鲜当季热带水果现摘现发 （5-10斤装）","cate_id":"587","image":"http://qiniu.haoshusi.com/images/43c24ca4e0c5b2b706ec5ac3b9d871bb.png","sales":2727,"price":"31.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":89,"store_name":"《当日精选鲜货》小米蕉新鲜酸甜香蕉水果banana芭蕉苹果粉蕉非帝王蕉带箱发货5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/f554de2a75d17a280b4d1f816614edb3.png","sales":1472,"price":"62.00","stock":4000,"is_show":1,"is_del":0,"store_type":0},{"id":91,"store_name":"《当日精选鲜货》现摘黄油桃纯甜黄金油桃黄皮黄心新鲜水果大果水蜜桃现货带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/e8fe8276093da5b4f675ab631f0784da.png","sales":653,"price":"5.67","stock":999,"is_show":1,"is_del":0,"store_type":0},{"id":92,"store_name":"《当日精选鲜货》灭霸同款火参果火生果新鲜水果刺角瓜火星果带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/2f83a3c5fe7e46c549c7ab2d35515b8f.png","sales":757,"price":"75.00","stock":20000,"is_show":1,"is_del":0,"store_type":0},{"id":93,"store_name":"《当日精选鲜货》火龙果白心新鲜热带白肉火龙果一级中果当季进口整箱5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/8b887b65eed77edd932e3044d6bbda11.png","sales":274,"price":"31.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":94,"store_name":"《当日精选鲜货》白心二级火龙果 应季新鲜水果带箱5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/d5c9f89517eef536847ab85fd9fad7f2.png","sales":1247,"price":"26.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":95,"store_name":"《当日精选鲜货》佳沛奇异果 进口黄心猕猴桃 阳光金果 新鲜水果 一盒（6粒）","cate_id":"587","image":"http://qiniu.haoshusi.com/images/f5a87303a0866c0d14b26b98a29fc10b.png","sales":2427,"price":"62.35","stock":2000,"is_show":1,"is_del":0,"store_type":0}]}
     * count : 0
     */

    private int code;
    private String msg;
    private GoodSort data;
    private int count;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public GoodSort getData() {
        return data;
    }

    public void setData(GoodSort data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static class GoodSort {
        /**
         * cate_name : 新鲜水果
         * list : [{"id":60,"store_name":"《当日精选鲜货》埃及橙脐橙夏橙新鲜水果当季橙子果冻橙冰糖橙带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/b9150df9e76e2bacc35ef80ca2b84e2f.png","sales":1365,"price":"35.49","stock":1996,"is_show":1,"is_del":0,"store_type":0},{"id":61,"store_name":"《当日精选鲜货》四川安岳黄柠檬 新鲜当季水果现摘一级果鲜青柠檬批发带包装5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/db7104eeab24bb9b76562846f34ae5dd.png","sales":1478,"price":"24.64","stock":19999,"is_show":1,"is_del":0,"store_type":0},{"id":65,"store_name":"《当日精选鲜货》百香果热带水果新鲜西番莲鸡蛋果带箱10-20粒装大红果当季整箱","cate_id":"587","image":"http://qiniu.haoshusi.com/images/e121c6c286bbd18b7c9b758a392788dc.png","sales":888,"price":"14.25","stock":1999,"is_show":1,"is_del":0,"store_type":0},{"id":66,"store_name":"《当日精选鲜货》海南三亚冰糖心香水菠萝 时令特产新鲜水果带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/f14e36f66ee617aed19a7700ee2b85ad.png","sales":902,"price":"42.40","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":67,"store_name":"《当日精选鲜货》海南菠萝蜜（黄肉）新鲜水果热带三亚果园现摘现发 木菠萝波罗蜜带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/3f9a31b6624fb8270bb67fbc194af682.png","sales":1314,"price":"42.40","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":74,"store_name":"《当日精选鲜货》越南红尘妃子笑荔枝新鲜包邮当季特产水果应季新鲜荔枝带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/62c33f554a3ba83ff8b78ed40f1e51e6.png","sales":1142,"price":"83.12","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":76,"store_name":"《当日精选鲜货》贵妃芒果新鲜 当季水果整箱中大果小红金龙青贵妃芒带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/ff623275174d4f74043cfe8fb27c7fb0.png","sales":1441,"price":"38.59","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":77,"store_name":"《当日精选鲜货》海南红心火龙果新鲜水果当季大果红肉带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/45c479eae237fafed66a4fe431c12a3c.png","sales":1242,"price":"61.85","stock":42624,"is_show":1,"is_del":0,"store_type":0},{"id":78,"store_name":"《当日精选鲜货》现摘黑布林李子黑布朗新鲜现货孕妇红心水果红布林带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/1e9f25134b3411a0ec5acd549226e7ce.png","sales":1413,"price":"56.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":79,"store_name":"《当日精选鲜货》蓝莓鲜果 当季新鲜蓝莓4盒装125g*4","cate_id":"587","image":"http://qiniu.haoshusi.com/images/07376f7029fa276f4e8cdb6cfd3738e5.png","sales":242,"price":"39.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":80,"store_name":"《当日精选鲜货》黑美人西瓜水果新鲜 当季薄皮进口黑籽带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/ead1c39fb08b46096756a719287d255c.png","sales":1277,"price":"11.20","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":84,"store_name":"《当日精选鲜货》红提葡萄新鲜提子应季当季水果时令鲜果带箱5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/7d7a5f2ae0013477f5fd58cf36483395.png","sales":2771,"price":"63.00","stock":22220,"is_show":1,"is_del":0,"store_type":0},{"id":86,"store_name":"《当日精选鲜货》红心火龙果 新鲜水果一级大果红肉金都一号（带箱5-10斤装）","cate_id":"587","image":"http://qiniu.haoshusi.com/images/e3840201907061606133146.jpg","sales":2424,"price":"56.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":87,"store_name":"《当日精选鲜货》正宗红心火龙果二级果新鲜当季热带水果现摘现发 （5-10斤装）","cate_id":"587","image":"http://qiniu.haoshusi.com/images/43c24ca4e0c5b2b706ec5ac3b9d871bb.png","sales":2727,"price":"31.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":89,"store_name":"《当日精选鲜货》小米蕉新鲜酸甜香蕉水果banana芭蕉苹果粉蕉非帝王蕉带箱发货5-10斤装","cate_id":"587","image":"http://qiniu.haoshusi.com/images/f554de2a75d17a280b4d1f816614edb3.png","sales":1472,"price":"62.00","stock":4000,"is_show":1,"is_del":0,"store_type":0},{"id":91,"store_name":"《当日精选鲜货》现摘黄油桃纯甜黄金油桃黄皮黄心新鲜水果大果水蜜桃现货带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/e8fe8276093da5b4f675ab631f0784da.png","sales":653,"price":"5.67","stock":999,"is_show":1,"is_del":0,"store_type":0},{"id":92,"store_name":"《当日精选鲜货》灭霸同款火参果火生果新鲜水果刺角瓜火星果带箱装5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/2f83a3c5fe7e46c549c7ab2d35515b8f.png","sales":757,"price":"75.00","stock":20000,"is_show":1,"is_del":0,"store_type":0},{"id":93,"store_name":"《当日精选鲜货》火龙果白心新鲜热带白肉火龙果一级中果当季进口整箱5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/8b887b65eed77edd932e3044d6bbda11.png","sales":274,"price":"31.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":94,"store_name":"《当日精选鲜货》白心二级火龙果 应季新鲜水果带箱5-10斤","cate_id":"587","image":"http://qiniu.haoshusi.com/images/d5c9f89517eef536847ab85fd9fad7f2.png","sales":1247,"price":"26.00","stock":2000,"is_show":1,"is_del":0,"store_type":0},{"id":95,"store_name":"《当日精选鲜货》佳沛奇异果 进口黄心猕猴桃 阳光金果 新鲜水果 一盒（6粒）","cate_id":"587","image":"http://qiniu.haoshusi.com/images/f5a87303a0866c0d14b26b98a29fc10b.png","sales":2427,"price":"62.35","stock":2000,"is_show":1,"is_del":0,"store_type":0}]
         */

        private String cate_name;
        private List<GoodList> list;

        public String getCate_name() {
            return cate_name;
        }

        public void setCate_name(String cate_name) {
            this.cate_name = cate_name;
        }

        public List<GoodList> getList() {
            return list;
        }

        public void setList(List<GoodList> list) {
            this.list = list;
        }
    }
}
