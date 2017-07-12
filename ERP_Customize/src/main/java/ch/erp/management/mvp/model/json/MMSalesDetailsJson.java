package ch.erp.management.mvp.model.json;

import java.util.List;

import ch.erp.management.mvp.model.entity.MSalesDetailsInfo;
import ch.erp.management.mvp.model.http.MJSONAnalyInterf;

/**
 * 销售明细-Json解析工具
 */
public class MMSalesDetailsJson implements MJSONAnalyInterf<MSalesDetailsInfo> {

    /**
     * 解析集合数据
     */
    @Override
    public List<MSalesDetailsInfo> handleArrayJSON(String mJson) {
        return null;
    }

    /**
     * 解析对象数据
     */
    @Override
    public MSalesDetailsInfo handleObjectJSON(String mJson) {
        MSalesDetailsInfo mSalesDetailsInfo = new MSalesDetailsInfo();
        mSalesDetailsInfo.setmName("天生万物以养人,世人犹怨天不仁.\n不知蝗蠹遍天下，苦尽苍生尽王臣." +
                "\n人之生矣有贵贱,贵人长为天恩眷." +
                "\n人生富贵总由天,草民之穷由天谴." +
                "\n忽有狂徒夜磨刀,帝星飘摇荧惑高." +
                "\n翻天覆地从今始,杀人何须惜手劳." +
                "\n不忠之人曰可杀！不孝之人曰可杀！" +
                "\n不仁之人曰可杀！不义之人曰可杀！" +
                "\n不礼不智不信人,大西王曰杀杀杀！" +
                "\n我生不为逐鹿来,都门懒筑黄金台." +
                "\n状元百官都如狗,总是刀下觳觫材." +
                "\n传令麾下四王子,破城不须封刀匕." +
                "\n山头代天树此碑,逆天之人立死跪亦死!" +
                "\n再次回来加载正式View");
        return mSalesDetailsInfo;
    }
}
