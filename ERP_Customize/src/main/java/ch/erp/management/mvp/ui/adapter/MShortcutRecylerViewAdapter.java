package ch.erp.management.mvp.ui.adapter;

import android.content.Context;

import com.superrecycleview.superlibrary.adapter.BaseViewHolder;
import com.superrecycleview.superlibrary.adapter.SuperBaseAdapter;
import com.superrecycleview.superlibrary.adapter.SuperBaseDragAdapter;

import java.util.List;

import ch.erp.management.R;
import ch.erp.management.mvp.model.entity.MShortcutFunctionInfo;

/**
 * 自定义快捷功能-Adapter
 */

public class MShortcutRecylerViewAdapter extends SuperBaseDragAdapter<MShortcutFunctionInfo> {

    public MShortcutRecylerViewAdapter(Context context, List<MShortcutFunctionInfo> data) {
        super(context, data);
    }

    /**
     * 优化类-view设置-数据适配
     *
     * @param holder
     * @param item
     * @param position
     */
    @Override
    protected void convert(BaseViewHolder holder, MShortcutFunctionInfo item, int position) {
        holder.setBackgroundResource(R.id.ImageView_recyclerview_shortcutfuntion, item.getmShortcutIcon());
        holder.setText(R.id.TextView_recyclerview_shortcutfuntion, item.getmShortcutTex());
    }

    /**
     * 加载Item布局
     *
     * @param position
     * @param item
     */
    @Override
    protected int getItemViewLayoutId(int position, MShortcutFunctionInfo item) {
        return R.layout.recyclerview_shortcutfunction;
    }


}
