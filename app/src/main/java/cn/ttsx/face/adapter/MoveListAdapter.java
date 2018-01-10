package cn.ttsx.face.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import cn.ttsx.face.R;
import cn.ttsx.face.dao.Move;

public class MoveListAdapter extends BaseQuickAdapter<Move, BaseViewHolder> {
    private boolean isFastConve = false;

    public MoveListAdapter(@LayoutRes int layoutResId, @Nullable ArrayList<Move> data, boolean isFastConve) {
        super(layoutResId, data);
        this.isFastConve = isFastConve;
    }

    public MoveListAdapter(int layoutResId, @Nullable List<Move> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Move item) {

        helper.setText(R.id.tv_name, item.getMoveName())
                .setText(R.id.tv_address, item.getMoveAddress())
                .setText(R.id.tv_start_time, item.getMoveStartTime() + "  开始")
                .setText(R.id.tv_end_time, item.getMoveEndTime() + "  结束")
        ;

    }
}
