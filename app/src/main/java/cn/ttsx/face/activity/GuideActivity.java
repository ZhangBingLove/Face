package cn.ttsx.face.activity;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.ttsx.face.MainActivity;
import cn.ttsx.face.R;
import cn.ttsx.face.base.BaseActivity;
import cn.ttsx.face.utils.GlobalUtils;
import cn.ttsx.face.utils.SPUtils;
import me.relex.circleindicator.CircleIndicator;

/**
 * 引导页
 *
 * @author zhangbing
 * @Description: (用一句话描述该类作用)
 * @CreateDate: 2017/12/29 11:11
 * @email 314835006@qq.com
 */
public class GuideActivity extends BaseActivity implements View.OnClickListener {

    private ViewPager guidePager;
    private CircleIndicator indicator;
    private List<View> viewList;
    private View view1, view2, view3;
    private TextView mTxtStartLogin;
    private pagerAdapter pager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView() {
        guidePager = (ViewPager) findViewById(R.id.guide_viewpager);
        indicator = (CircleIndicator) findViewById(R.id.indicator);
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.guide_layout_1, null);
        view2 = inflater.inflate(R.layout.guide_layout_2, null);
        view3 = inflater.inflate(R.layout.guide_layout_3, null);
        mTxtStartLogin = (TextView) view3.findViewById(R.id.txt_start);

        viewList = new ArrayList<>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        pager = new pagerAdapter();
        guidePager.setAdapter(pager);
        indicator.setViewPager(guidePager);
        guidePager.setOnPageChangeListener(mOnPageChangeListener);

        mTxtStartLogin.setOnClickListener(this);
        mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageSelected(int currentID) {
                if (currentID == 3) {
                    indicator.setVisibility(View.GONE);
                } else {
                    indicator.setVisibility(View.VISIBLE);
                }
            }
        };

    }

    /**
     * 设置为全屏的
     *
     * @return
     */
    @Override
    public boolean getAllowFullScreen() {
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_start:
                startActivity(new Intent(GuideActivity.this, HomeActivity.class));
                SPUtils.setParam(GuideActivity.this, GlobalUtils.IS_GUIDE, true);
                this.finish();
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * viewPager的Adapter
     */
    class pagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View view, Object arg1) {
            return view == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    }


}
