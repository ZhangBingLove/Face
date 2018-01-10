package cn.ttsx.face.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.ttsx.face.R;
import cn.ttsx.face.dao.Face;
import cn.ttsx.face.dao.Move;

public class PersonAdapter extends BaseItemDraggableAdapter<Face, BaseViewHolder> {
    private boolean isFastConve = false;

    public PersonAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<Face> data, boolean isFastConve) {
        super(layoutResId, data);
        this.isFastConve = isFastConve;
    }

    public PersonAdapter(int layoutResId, @Nullable List<Face> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Face item) {

        helper.setText(R.id.tv_name, item.getUsername())

        ;

    }
}
