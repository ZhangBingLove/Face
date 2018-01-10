package cn.ttsx.face.view;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.ttsx.face.R;


/**
 * Created by XL on 2017/2/13.
 */

public class TopBar extends RelativeLayout {
    public RelativeLayout getmParent() {
        return mParent;
    }

    public void setmParent(RelativeLayout mParent) {
        this.mParent = mParent;
    }

    private RelativeLayout mParent;
    private Context mContext;
    private Paint mPaint;
    private LinearLayout ll_back;
    private ImageView mIvMonitor;
    private ImageView mIvSearch;
    private ImageView mIvDelete;
    private ImageView ivBack;
    private TextView tvSave;
    private TextView tvDelete;
    private TextView topBarTitle;

    public TopBar(Context context) {
        this(context, null);

    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public TopBar(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_topbar, this);
        ll_back = ((LinearLayout) view.findViewById(R.id.ll_back));
        ivBack = ((ImageView) view.findViewById(R.id.iv_back));

        mIvMonitor = ((ImageView) view.findViewById(R.id.img_monitor));
        mIvDelete = ((ImageView) view.findViewById(R.id.img_delete));
        mIvSearch = ((ImageView) view.findViewById(R.id.iv_search));
        tvSave = (TextView) view.findViewById(R.id.tv_save);
        tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        topBarTitle = (TextView) view.findViewById(R.id.topBar_title);
        mParent = (RelativeLayout) view.findViewById(R.id.rl__parent);
    }


    public void setBackClickListener(OnClickListener onClickListener) {
        if (ll_back != null && onClickListener != null) {
            ivBack.setVisibility(View.VISIBLE);
            ll_back.setOnClickListener(onClickListener);
        }
    }

    public void setMonitorClickListener(OnClickListener onClickListener) {
        if (mIvMonitor != null && onClickListener != null) {
            mIvMonitor.setVisibility(VISIBLE);
            mIvMonitor.setOnClickListener(onClickListener);
        }
    }


    public void setDeleteListener(OnClickListener onClickListener) {
        if (tvDelete != null && onClickListener != null) {
            tvDelete.setVisibility(VISIBLE);
            tvDelete.setOnClickListener(onClickListener);
        }
    }

    public void setSaveClickListener(OnClickListener onClickListener) {
        if (tvSave != null && onClickListener != null) {
            tvSave.setVisibility(VISIBLE);
            tvSave.setOnClickListener(onClickListener);
        }
    }


    public void setSearchClickListener(OnClickListener onClickListener) {
        if (mIvSearch != null && onClickListener != null) {
            mIvSearch.setVisibility(VISIBLE);
            mIvSearch.setOnClickListener(onClickListener);
        }
    }

    public void setTopBarTitle(String title) {
        if (topBarTitle != null) {
            topBarTitle.setText(title);
        }
    }

    public void setSaveText(String text) {
        if (tvSave != null) {
            tvSave.setText(text);
        }
    }


    /**
     * 让保存按钮消失
     */
    public void setSaveGone() {
        tvSave.setVisibility(View.GONE);
    }

    /**
     * 让保存按钮显示
     */
    public void setSaveVisible() {
        tvSave.setVisibility(View.VISIBLE);
    }

    /**
     * 化验单保存按钮的点击事件
     *
     * @param onClickListener
     */
    public void setSaveClickListenerForAssay(OnClickListener onClickListener) {
        if (tvSave != null && onClickListener != null) {
            tvSave.setOnClickListener(onClickListener);
        }
    }
}
