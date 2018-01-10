package cn.ttsx.face.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.ttsx.face.R;
import cn.ttsx.face.utils.ActivityManagerUtil;


/**
 * Activity的基类
 *
 * @author zhangbing
 * @Description: (用一句话描述该类作用)
 * @CreateDate: 2017/12/29 11:11
 * @email 314835006@qq.com
 */
public abstract class BaseActivity extends AppCompatActivity {


    private final String TAG = this.getClass().getSimpleName();
    private Toast toast;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        Log.d(TAG, "Activity生命周期 --------> onCreate()");
        ActivityManagerUtil.getInstance().pushOneActivity(this);

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        if (getAllowFullScreen()) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        if (getSteepStatusBar()) {
            steepStatusBar();
        }

        setContentView(setContentViewId());
        initView();
    }

    /**
     * 加载布局Activity的布局
     *
     * @author zhangbing
     * @date 2017/9/27 15:48
     * @email 314835006@qq.com
     */
    protected abstract int setContentViewId();

    /**
     * 初始化布局
     *
     * @author zhangbing
     * @date 2017/9/27 15:51
     * @email 314835006@qq.com
     */
    protected abstract void initView();


    /**
     * 设置全屏  默认不是全屏的 需要设置全屏的话 在子类中重写这个方法
     *
     * @Params isAllowFullScreen 是否设置全屏
     * @author zhangbing
     * @date 2017/9/27 16:00
     * @email 314835006@qq.com
     */
    public boolean getAllowFullScreen() {
        return false;
    }

    public boolean getSteepStatusBar() {
        return false;
    }

    /**
     * [沉浸状态栏]
     */
    private void steepStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
            getWindow().addFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManagerUtil.getInstance().popOneActivity(this);
//        OkGo.getInstance().cancelTag(this);//取消网络请求
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_left_in, R.anim.to_right_out);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.from_right_in, R.anim.to_left_out);
    }

    /**
     * 防止用户多次点击的吐司
     */
    public void showToast(String text) {

        if (toast == null) {
            toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);

        } else {
            toast.setText(text);

        }
        toast.show();

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
//            OkGo.getInstance().cancelTag(this);//取消网络请求
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
