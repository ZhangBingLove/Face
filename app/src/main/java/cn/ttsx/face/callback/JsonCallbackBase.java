package cn.ttsx.face.callback;

import android.app.Dialog;

import com.lzy.okgo.exception.HttpException;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.SocketException;

import cn.ttsx.face.utils.ToastUtils;
import okhttp3.Call;
import okhttp3.Response;

public abstract class JsonCallbackBase<T> extends BaseCallback<T> {

    private Type type;
    private Class<T> clazz;
    private Dialog mLoadingDialog;

    public JsonCallbackBase() {
    }

    public JsonCallbackBase(Type type) {
        this.type = type;
    }

    public JsonCallbackBase(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        // 主要用于在所有请求之前添加公共的请求头或请求参数
        // 例如登录授权的 token
        // 使用的设备信息
        // 可以随意添加,也可以什么都不传
        // 还可以在这里对所有的参数进行加密，均在这里实现
//        request.headers("header1", "HeaderValue1")//
//                .params("params1", "ParamsValue1")//
//                .params("token", "3215sdf13ad1f65asd4f3ads1f");
//        mLoadingDialog = DialogUtils.createLoadingDialog(ActivityManagerUtil.getInstance().getLastActivity(),"加载中");
//        if (mLoadingDialog != null) {
//            mLoadingDialog.show();
//        }

    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {

        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用
        // 重要的事情说三遍，不同的业务，这里的代码逻辑都不一样，如果你不修改，那么基本不可用

        //详细自定义的原理和文档，看这里： https://github.com/jeasonlzy/okhttp-OkGo/wiki/JsonCallback

        if (type == null) {
            if (clazz == null) {
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                JsonConvert<T> convert = new JsonConvert<>(clazz);
                return convert.convertResponse(response);
            }
        }

        JsonConvert<T> convert = new JsonConvert<>(type);
        return convert.convertResponse(response);
    }

    @Override
    public void onError(Call call, Response response, Exception e) {

        if (e instanceof HttpException) {
            ToastUtils.showShortToast("服务器异常，请联系管理员");
            return;
        }

        if (e instanceof HttpException) {
            ToastUtils.showShortToast("服务器异常，请联系管理员");
            return;
        }
        if (e instanceof SocketException) {
            return;
        }

//        if (e instanceof ConnectException) {
//            ToastUtils.showShortToast("无网络");
//            return;
//        }


//        if (e instanceof TokenException || e instanceof NoLoginException) {
////            App.dialog.dismissDialog();
////            final Activity lastActivity = ActivityManagerUtil.getInstance().getLastActivity();
////
////            App.dialog.MyDialog(lastActivity,"登录失效","登录超时，请重新登录","确认","取消");
////            App.dialog.BtnClick(new DialogListener() {
////                @Override
////                public void LeftBtnOnclick() {
////                    Intent intent = new Intent();
////                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
////                    intent.setClass(App.getInstance(), LoginActivity.class);
////                    App.getInstance().startActivity(intent);
////                    PrefUtils.putLoginStatus(lastActivity,0);
////                    PrefUtils.removeUserInfoAll(lastActivity);
////                    PrefUtils.remove(lastActivity, Const.IS_FAST_MOBILE_NETWORK);//是否支持移动网络
////                }
////
////                @Override
////                public void RightBtnOnclick() {
////
////                }
////            });
//            return;
//        }
//
//        if (e instanceof APIException) {
//            serverError(((APIException) e).getCode(), e.getMessage(), (APIException) e);
//            return;
//        }
    }


    @Override
    public void onFinish() {
        super.onFinish();
//        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
//            mLoadingDialog.dismiss();
//        }
    }

//    protected abstract void serverError(String code, String msg, APIException e);
}