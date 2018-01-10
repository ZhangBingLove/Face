package cn.ttsx.face.activity;


import android.content.Intent;
import android.text.TextUtils;


import cn.ttsx.face.MainActivity;
import cn.ttsx.face.R;
import cn.ttsx.face.base.BaseActivity;
import cn.ttsx.face.utils.GlobalUtils;
import cn.ttsx.face.utils.SPUtils;

/**
 * 闪屏页面
 *
 * @author zhangbing
 * @Description: (用一句话描述该类作用)
 * @CreateDate: 2017/12/29 11:15
 * @email 314835006@qq.com
 */
public class SplashActivity extends BaseActivity {

    private Boolean isGuide;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;
    protected static final int REQUEST_PHONE_STATE_PERMISSION = 103;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {

        //当前系统的时间
        long currentTime = System.currentTimeMillis();
        //七天的时间
        long oneDayTime = 1000 * 60 * 60 * 24 * 7;
        //登录时保存的时间
        String loginTime = (String) SPUtils.getParam(SplashActivity.this, GlobalUtils.CURRENT_SYSTEM_TIME, "");
        //如果当前时间 - 登录时的时间 >= 一天的时间 就清除sp中保存的密码的的数据
        if (!TextUtils.isEmpty(loginTime)) {
            if (currentTime - Long.parseLong(loginTime) >= oneDayTime) {
                SPUtils.removeParam(SplashActivity.this, GlobalUtils.USER_PASSWORD);
            }
        }


        isGuide = (Boolean) SPUtils.getParam(this, GlobalUtils.IS_GUIDE, false);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.CAMERA,
//                    getString(R.string.mis_permission_rationale),
//                    CAMERA);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED) {
//            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    getString(R.string.mis_permission_rationale_write_storage),
//                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                != PackageManager.PERMISSION_GRANTED) {
//
//            requestPermission(Manifest.permission.READ_PHONE_STATE,
//                    "本程序需要您提供读取本机设备码权限",
//                    REQUEST_PHONE_STATE_PERMISSION);
//        } else {
//
//            interActivity();
//        }

        interActivity();
    }


    private void interActivity() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (isGuide) {
//                    isAutoLogin();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SplashActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(SplashActivity.this, HomeActivity.class));
                                    finish();
                                }
                            });
                        }
                    }).start();

                } else {
                    startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    finish();
                }

            }
        }).start();
    }

//    /**
//     * 请求权限     6.0以后
//     *
//     * @param permission  权限
//     * @param rationale   文字
//     * @param requestCode 请求码
//     */
//    private void requestPermission(final String permission, String rationale, final int requestCode) {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.mis_permission_dialog_title)
//                    .setMessage(rationale)
//                    .setPositiveButton(R.string.mis_permission_dialog_ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(SplashActivity.this, new String[]{permission}, requestCode);
//                        }
//                    })
//                    .setNegativeButton(R.string.mis_permission_dialog_cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            finish();
//                        }
//                    })
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case CAMERA:
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED) {
//                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                            getString(R.string.mis_permission_rationale_write_storage),
//                            REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//                } else {
//                    requestPermission(Manifest.permission.READ_PHONE_STATE,
//                            "本程序需要您提供读取本机设备码权限",
//                            REQUEST_PHONE_STATE_PERMISSION);
//                }
//
//                break;
//            case REQUEST_STORAGE_WRITE_ACCESS_PERMISSION:
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//                        && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
//                        != PackageManager.PERMISSION_GRANTED) {
//
//                    requestPermission(Manifest.permission.READ_PHONE_STATE,
//                            "本程序需要您提供读取本机设备码权限",
//                            REQUEST_PHONE_STATE_PERMISSION);
//
//                } else {
//
////                    interActivity();
//                }
//                break;
//            case REQUEST_PHONE_STATE_PERMISSION:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//                    String deviceId;
//                    try {
////                        deviceId = tm.getDeviceId();
//                    } catch (Exception e) {
//                        deviceId = "";
//                    }
//
//                }
//                interActivity();
//                break;
//        }
//    }

    @Override
    public boolean getAllowFullScreen() {
        return true;
    }

    /**
     * 判断是否自动登录
     */
//    public void isAutoLogin() {
//
//        final String userName = (String) SPUtils.getParam(this, GlobalUtils.USER_NAME, "");
//        final String password = (String) SPUtils.getParam(this, GlobalUtils.USER_PASSWORD, "");
//        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(password)) {
//            String passWordToMD5 = Md5Utils.getMD5(password);
//            OkGo.<UserBean>post(HttpUrl.LOGIN)
//                    .tag(this)
//                    .params("userName", userName)
//                    .params("userPassWord", passWordToMD5)
//                    .execute(new JsonCallbackBase<UserBean>() {
//
//
//                        @Override
//                        public void onStart(Request<UserBean, ? extends Request> request) {
//                            super.onStart(request);
//
//                        }
//
//                        @Override
//                        public void onError(com.lzy.okgo.model.Response<UserBean> response) {
//                            super.onError(response);
//                            if (!NetworkUtils.checkNetwork(SplashActivity.this)) {
//                                showToast("请检查网络~");
//                            } else {
//                                showToast("未知错误,请联系管理员");
//                            }
//
//                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                            finish();
//
//                        }
//
//                        @Override
//                        protected void serverError(String code, String msg, APIException e) {
////                        LogUtils.e("失败了");
//                            Log.e("haha", "失败了111111");
//                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                            finish();
//                        }
//
//                        @Override
//                        public void onError(Call call, Response response, Exception e) {
//                            super.onError(call, response, e);
//
//
//                        }
//
//                        @Override
//                        public void onSuccess(UserBean httpBean, Call call, Response response) {
//                            Log.v("hahahahahaha", httpBean.toString());
////                        LogUtils.e("成功了");
//                            Log.v("hahahahahaha", "失败了aaaaaaaa");
//                            if ("1".equals(httpBean.getCode())) {
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_NAME, userName);
//
//                                //获取当前系统的时间
//                                long currentSystemTime = System.currentTimeMillis();
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.CURRENT_SYSTEM_TIME, currentSystemTime + "");
//
//                                if (TextUtils.isEmpty(httpBean.getData().getUserView())) {
//                                    SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_VIEW, "");
//                                } else {
//                                    SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_VIEW, httpBean.getData().getUserView());
//                                }
//
//                                if (TextUtils.isEmpty(httpBean.getData().getUserVilage())) {
//                                    SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_VILAGE, "");
//                                } else {
//                                    SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_VILAGE, httpBean.getData().getUserVilage());
//                                }
//
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_TYPE, httpBean.getData().getType());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_REAL_NAME, httpBean.getData().getUserRealName());
//                                if (TextUtils.isEmpty(httpBean.getData().getUserAddress())) {
//                                    if (httpBean.getData().getType().equals("1")) {
//                                        SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_ADDRESS, httpBean.getData().getVillageName());
//                                    } else {
//                                        SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_ADDRESS, "");
//                                    }
//
//                                } else {
//                                    SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_ADDRESS, httpBean.getData().getUserAddress());
//                                }
//
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_BIRTHDAY, httpBean.getData().getUserBirthday());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_ID, httpBean.getData().getId());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_PHONE, httpBean.getData().getUserPhone());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_SHEN_FEN_ZHENG, httpBean.getData().getUserIdCode());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_CUN_INFO, httpBean.getData().getVillageName());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_PASSWORD, password);
//                                if (httpBean.getData().getSysDepartment() != null && !TextUtils.isEmpty(httpBean.getData().getSysDepartment().getDeptName())) {
//                                    SPUtils.setParam(SplashActivity.this, GlobalUtils.SUPERIOR_TOWN, httpBean.getData().getSysDepartment().getDeptName());
//                                }
//
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_CUN_ID, httpBean.getData().getVillageId());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_ZHEN_ID, httpBean.getData().getTownId());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_QU_ID, httpBean.getData().getAreaId());
//                                SPUtils.setParam(SplashActivity.this, GlobalUtils.USER_CURRENT_TIME, httpBean.getData().getCrtTime());
//
//                                startActivity(new Intent(SplashActivity.this, HomeTextActivity.class));
//                                finish();
//                            } else {
//                                showToast(httpBean.getMsg());
//                                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//                                finish();
//                            }
//
//                        }
//                    });
//
//        } else {
//            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
//            finish();
//        }
//
//
//    }


}
