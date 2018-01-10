package cn.ttsx.face.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ttsx.face.R;
import cn.ttsx.face.bean.HomeItemBean;


public class HomePostAdapter extends BaseQuickAdapter<HomeItemBean, BaseViewHolder> {

    public HomePostAdapter(@LayoutRes int layoutResId, @Nullable List<HomeItemBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItemBean item) {
        helper.setText(R.id.tv_name, item.getName());

    }
}
