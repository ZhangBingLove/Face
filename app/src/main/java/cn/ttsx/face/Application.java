package cn.ttsx.face;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.cookie.CookieJarImpl;
import com.lzy.okgo.cookie.store.SPCookieStore;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreater;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import cn.ttsx.face.dao.DaoMaster;
import cn.ttsx.face.dao.DaoSession;
import cn.ttsx.face.utils.ImagePipelineConfigFactory;
import cn.ttsx.face.utils.Utils;
import okhttp3.OkHttpClient;


/**
 * Created by gqj3375 on 2017/4/28.
 */

public class Application extends android.app.Application {
    private final String TAG = this.getClass().toString();
    public static String FILE_PATH;
    FaceDB mFaceDB;
    Uri mImage;
    private DaoSession daoSession;
    public static Context mContext; //全局的context

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorMain, android.R.color.white)
                ;//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss E"));//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreater(new DefaultRefreshFooterCreater() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorMain, android.R.color.white);
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        Utils.init(this);
        mImage = null;
        initGreenDao();
        initOkGo();

        Fresco.initialize(this, ImagePipelineConfigFactory.getImagePipelineConfig(getApplicationContext(), OkGo.getInstance().getOkHttpClient()));//初始化Fresco

        SpeechUtility.createUtility(getApplicationContext(), "appid=5a40cdeb");
        mContext = getApplicationContext();
    }

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "Face.db");
        SQLiteDatabase db = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();


    }

    public DaoSession getDaoSession() {
        return daoSession;
    }


    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * okGo的全局配置
     */
    private void initOkGo() {
        //################## 构建build #############
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //################### 配置log #####################
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
        //log打印级别，决定了log显示的详细程度
        loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
        //log颜色级别，决定了log在控制台显示的颜色
        loggingInterceptor.setColorLevel(Level.INFO);
        builder.addInterceptor(loggingInterceptor);

        //################ 配置超时时间 默认的超时时间是60秒 ###############
        //全局的读取超时时间
        builder.readTimeout(90000, TimeUnit.MILLISECONDS);
//全局的写入超时时间
        builder.writeTimeout(90000, TimeUnit.MILLISECONDS);
//全局的连接超时时间
        builder.connectTimeout(90000, TimeUnit.MILLISECONDS);

        //################ 使用SP保存cookie #################
        builder.cookieJar(new CookieJarImpl(new SPCookieStore(this)));

        //######################### 配置全局的header ###################
//        HttpHeaders httpHeaders = new HttpHeaders();//服务器约定的全局header
//        httpHeaders.put("clientType", "Android" + android.os.Build.VERSION.RELEASE);//Android版本号
//        httpHeaders.put("clientCompany", android.os.Build.MODEL);//设备型号
////        httpHeaders.put("appVersion", versionName);//版本号
//        httpHeaders.put("requestFrom", "1");//终端来源

        OkGo.getInstance().init(this)                       //必须调用初始化
                .setOkHttpClient(builder.build())               //建议设置OkHttpClient，不设置将使用默认的
                .setCacheMode(CacheMode.NO_CACHE)               //全局统一缓存模式，默认不使用缓存，可以不传
                .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)   //全局统一缓存时间，默认永不过期，可以不传
                .setRetryCount(3);                           //全局统一超时重连次数，默认为三次，那么最差的情况会请求4次(一次原始请求，三次重连请求)，不需要可以设置为0
//                .addCommonHeaders(httpHeaders);                   //全局公共头
        File filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (filePath != null) {
            FILE_PATH = filePath.getAbsolutePath();
        } else {
            FILE_PATH = "";
        }

    }

}
