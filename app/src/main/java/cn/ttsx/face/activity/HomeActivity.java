package cn.ttsx.face.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import cn.ttsx.face.Application;
import cn.ttsx.face.R;
import cn.ttsx.face.TextActivity;
import cn.ttsx.face.adapter.MoveListAdapter;
import cn.ttsx.face.base.BaseActivity;
import cn.ttsx.face.dao.DaoSession;
import cn.ttsx.face.dao.Move;
import cn.ttsx.face.dao.MoveDao;
import cn.ttsx.face.view.TopBar;

/**
 * 首页
 *
 * @author zhangbing
 * @Description: (用一句话描述该类作用)
 * @CreateDate: 2018/1/3 14:22
 * @email 314835006@qq.com
 * @UpdateUser: zhangbing
 * @UpdateDate: 2018/1/3 14:22
 * @UpdateRemark:
 */
public class HomeActivity extends BaseActivity {
    private TopBar topBar;
    private RecyclerView mRecycleView;
    private MoveListAdapter mMoveListAdapter;
    private MoveDao moveDao;
    private LinearLayout llEmpty;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {

        DaoSession daoSession = ((Application) HomeActivity.this.getApplicationContext()).getDaoSession();
        moveDao = daoSession.getMoveDao();

        topBar = findViewById(R.id.topbar);
        topBar.setTopBarTitle("face+1");
        llEmpty = findViewById(R.id.ll_emtpy);
        mRecycleView = (RecyclerView) findViewById(R.id.recyclerview);

        topBar.setSaveClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, NewMoveActivity.class));
//                startActivity(new Intent(HomeActivity.this, TextActivity.class));
            }
        });


        SmartRefreshLayout refreshLayout = findViewById(R.id.refreshLayout);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000/*,false*/);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(1000/*,false*/);//传入false表示加载失败
            }
        });
        refreshLayout.setEnableLoadmore(false);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(linearLayoutManager);
        mMoveListAdapter = new MoveListAdapter(R.layout.item_move, null);
        mRecycleView.setAdapter(mMoveListAdapter);

        mMoveListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                Intent intent = new Intent(HomeActivity.this, MoveDetailsActivity.class);

                intent.putExtra("moveName", ((Move) adapter.getItem(position)).getMoveName());
                intent.putExtra("moveId", ((Move) adapter.getItem(position)).getId());

                startActivity(intent);


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Move> moves = moveDao.loadAll();
        if (!moves.isEmpty()) {
            llEmpty.setVisibility(View.GONE);
            mRecycleView.setVisibility(View.VISIBLE);
            mMoveListAdapter.setNewData(moves);

        } else {
            llEmpty.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
        }
    }
}
