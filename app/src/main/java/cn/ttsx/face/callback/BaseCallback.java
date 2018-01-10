package cn.ttsx.face.callback;

import com.lzy.okgo.callback.AbsCallback;

import okhttp3.Call;
import okhttp3.Response;

/**
 * author: XL
 * versionName:
 * creatTime:2017/6/1
 * describe:
 */

public abstract class BaseCallback<T> extends AbsCallback<T> {

    public static final String TAG = BaseCallback.class.getSimpleName();

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        if (response.getException() instanceof Exception) {
            onError(response.getRawCall(), response.getRawResponse(), ((Exception) response.getException()));
        }else {
            onError(response.getRawCall(), response.getRawResponse(), new Exception(response.getException().getMessage(),response.getException()));
        }
    }

    public void onError(Call call, Response response, Exception e){

    }


    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        onSuccess(response.body(),response.getRawCall(),response.getRawResponse());
    }



    public abstract void onSuccess(T t, Call call, Response response);

}
