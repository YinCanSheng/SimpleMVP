package ch.erp.management.mvp.model.http.load;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.superrecycleview.superlibrary.recycleview.SuperRecyclerView;

import java.util.Map;

public class ListDataLoadHandler {
    /**
     * 当前容器
     */
    private Context activity;
    /**
     * 下拉刷新时候的布局
     */
    private View refreshView;
    /**
     * 列表控件
     */
    private SuperRecyclerView recyclerView;

    /**
     * 数据容器
     */
    private Map<String, Object> mParams;

    /**
     * 没有该数据的类型logo
     */
    private int nodataDrawable;


    /**
     * 回到顶部按钮
     */
    private Button mToTopShortcut;


    /**
     * 是否显示回到顶部按钮
     */
    private boolean isEnableTotop = true;


    private int mCurrentFlag = -1;
    private OnReloadClickListener mOnReloadClickListener;


    public void setRecyclerView(SuperRecyclerView listView) {
        this.recyclerView = listView;
        this.recyclerView.completeLoadMore();

    }

    /**
     * 针对网络请求后的异常处理显示 @param icon @param title @param msg @param action
     */
    public void showExceptionView(int icon, int title, String msg, int action) {

    }


    /**
     * 停止所有加载动画
     */
    public void stopRefreshAndLoadMore() {
        recyclerView.completeRefresh();
        recyclerView.completeLoadMore();
    }

    /**
     * 有更多页的数据
     */
    public void setCanLoadMore() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLoadMoreEnabled(true);
    }

    /**
     * 无更多页的数据
     */
    public void setCanNotLoadMore() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLoadMoreEnabled(false);
    }

    /**
     * 没有数据
     */
    public void noData(int message) {
        recyclerView.setVisibility(View.GONE);
        refreshView.setVisibility(View.VISIBLE);
        showExceptionView(nodataDrawable, message, "",
                -1);
    }

    public void beData() {
        recyclerView.setVisibility(View.VISIBLE);
        refreshView.setVisibility(View.GONE);
    }

    /**
     * 没有网络
     */
    public void noNet() {
        recyclerView.setVisibility(View.GONE);
        refreshView.setVisibility(View.VISIBLE);
    }

    /**
     * 服务器异常
     */
    public void exception(String msg) {
        recyclerView.setVisibility(View.GONE);
        refreshView.setVisibility(View.VISIBLE);
    }


    public void setOnReloadClickListener(OnReloadClickListener onReloadClickListener) {
        this.mOnReloadClickListener = onReloadClickListener;
    }

    public interface OnReloadClickListener {
        void onReloadClick(View view);
    }


//    private void setTopShortcutOnClickListener() {
//        if (isEnableTotop) {
//            if (mToTopShortcut == null) {
//                mToTopShortcut = activity.getToTopShortcut();
//            }
//            mToTopShortcut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listView.s(0);
//                    mToTopShortcut.setVisibility(View.GONE);
//                }
//            });
//        }
//    }


    public void setRefreshView(View refreshView) {
        this.refreshView = refreshView;
    }

    public void setContext(Context context) {
        this.activity = context;
    }

    public void setParams(Map<String, Object> params) {
        this.mParams = params;
    }


    public void setNoDataDrawable(int nodataDrawable) {
        this.nodataDrawable = nodataDrawable;
    }

    public void setIsEnableTotop(boolean isEnableTotop) {
        this.isEnableTotop = isEnableTotop;
    }


}