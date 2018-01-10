package cn.ttsx.face.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.File;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils {

    private static Context context;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param context 上下文
     */
    public static void init(Context context) {
        Utils.context = context.getApplicationContext();
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Context getContext() {
        if (context != null) return context;
        throw new NullPointerException("u should init first");
    }

    public static final int MINUTE_CONVERSION = 60 * 1000;
    public static final int HOUR_CONVERSION = 60 * 60 * 1000;

    /**
     * 获取版本号
     */
    public static String getVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            // int versionCode = packInfo.versionCode;
            return packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取当前屏幕密度
     */
    public static float getScreenDensity(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.density;// 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
    }

    /**
     * 安装apk
     */
    public static void installApk(Context context, String fileName) {
        File apkFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), fileName);
        if (!apkFile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dp2px(FragmentActivity context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(FragmentActivity context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     */
    public static int getScreenWidth(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    /**
     * 获取屏幕的高度
     */
    public static int getScreenHigh(Context c) {
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int hight = display.getHeight();
        Point size = new Point();
        display.getSize(size);
        return hight;
    }

    /**
     * 时间格式转换
     */
    public static String getDateString(String dateString, String initDateFormat, String needDateFormat) {
        SimpleDateFormat format1 = new SimpleDateFormat(initDateFormat, Locale.getDefault());
        SimpleDateFormat format2 = new SimpleDateFormat(needDateFormat, Locale.getDefault());
        Date date = null;
        try {
            date = format1.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return format2.format(date);
    }

    /**
     * 获取现在时间:返回短时间字符串格式yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateStr() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间:返回短时间字符串格式yyyy-MM-dd
     */
    public static String getCurrentDateStr(String timeStr) {
        Date date = null;
        try {
            date =Utils.stringToDate(timeStr,"yyyy-MM-dd");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(date);
    }
    /**
     * 获取现在时间:返回短时间字符串格式 HH:mm:ss
     */
    public static String getCurrentTime(String template) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(template, Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间:返回短时间字符串格式yyyy-MM-dd
     */
    public static String getCurrentDayStr() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return formatter.format(currentTime);
    }

    /**
     * 获取现在时间
     */
    public static Date getDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date parse = null;
        try {
            parse = format.parse(str);
        } catch (ParseException e) {

            return new Date();
        }
        return parse;
    }

//    /**
//     * 获取两个时间差
//     */
//    public static long getTimeInterval(String endStr) {// String startStr,
//        long interval = 0;
//        Date date = Utils.getDate(endStr);
//        if (date != null) {
//            long end = date.getTime();
//            interval = (end - System.currentTimeMillis() + HOUR_CONVERSION) / HOUR_CONVERSION;
//        }
//        return interval;
//    }

    /**
     * 获取可变UUID
     *
     * @return 随机产生的UUID
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid == null ? null : uuid.toString();
    }

    public static String getDeviceId(Context context) {

        return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
    }

//	/**
//	 * 获取本机IP地址
//	 */
//	public static String getLocalHostIp() {
//		try {
//			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
//			// 遍历所用的网络接口
//			while (en.hasMoreElements()) {
//				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
//				Enumeration<InetAddress> inet = nif.getInetAddresses();
//				// 遍历每一个接口绑定的所有IP
//				while (inet.hasMoreElements()) {
//					InetAddress ip = inet.nextElement();
//					if (!ip.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
//						return ip.getHostAddress();
//					}
//				}
//			}
//		} catch (SocketException e) {
//			e.printStackTrace();
//			return "";
//		}
//		return "";
//	}

    /**
     * 格式化时间信息
     */
    public static String longToStringData(long data) {
        Date date = new Date(data * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 格式化时间信息
     */
    public static String longToStringHour(long data) {
        Date date = new Date(data * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return sdf.format(date);
    }

    public static long getNextDay(long data){
        Date date = new Date(data * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String format = sdf.format(date);
        try {
            Date parse = sdf.parse(format);
            return parse.getTime()/1000 + 1000 * 60 * 60 * 24L;
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String getLocalNumData() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssms", Locale.getDefault());
        return sdf.format(date);

    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static long StrToDate(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(str);
            long time = (date.getTime()) / 1000;
            return time;
        } catch (ParseException e) {
            return -1;
        }

    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @return date
     */
    public static long Str2Date(String str) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = null;
        try {
            date = format.parse(str);
            long time = (date.getTime()) / 1000;
            return time;
        } catch (ParseException e) {
            return -1;
        }

    }

    /**
     * 获取是否有网络连接方法
     *
     * @param context
     * @return
     */
    public static NetworkInfo getActiveNetwork(Context context) {
        if (context == null)
            return null;
        ConnectivityManager mConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (mConnMgr == null)
            return null;
        NetworkInfo aActiveInfo = mConnMgr.getActiveNetworkInfo();
        // 获取活动网络连接信息
        return aActiveInfo;
    }

    /**
     * 计划是否开始
     */
    public static boolean IsPlanStart(String time) {
        if (StrToDate(time) != -1) {
            return (StrToDate(time) < (new Date().getTime() / 1000));
        }
        return false;

    }


    public static String RandomId() {

        return UUID.randomUUID().toString();

    }

    /**
     * yyyy-MM-dd HH:mm:ss转换为yyyy-MM-dd
     *
     * @param data
     * @return
     */
    public static String ChengeDateStrDay(String data) {

        return longToStringData(Str2Date(data));

    }


    /**
     * yyyy-MM-dd HH:mm:ss转换为HH:mm:ss
     *
     * @param data
     * @return
     */
    public static String ChengeDateStrHour(String data) {
        try {
            return longToStringHour(Str2Date(data));
        } catch (Exception e){
            return "00:00:00";
        }


    }

    /**
     * yyyy-MM-dd转换为yyyy-MM-dd HH:mm:ss
     *
     * @param data
     * @return
     */
    public static String ChengeStrDate(String data) {

        return longToStringData(StrToDate(data));

    }


//    /**
//     * 获取本机IP地址
//     */
//    public static String getLocalHostIp() {
//        try {
//            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
//            // 遍历所用的网络接口
//            while (en.hasMoreElements()) {
//                NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
//                Enumeration<InetAddress> inet = nif.getInetAddresses();
////				 遍历每一个接口绑定的所有IP
//                while (inet.hasMoreElements()) {
//                    InetAddress ip = inet.nextElement();
//                    if (!ip.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
//                        return ip.getHostAddress();
//                    }
//                }
//            }
//        } catch (SocketException e) {
//            e.printStackTrace();
//            return "";
//        }
//        return "";
//    }
//	/**
//	 * 下载图片并显示到控件上
//	 *
//	 * @param context
//	 * @param img
//	 * @param Url
//	 * @param imgName
//	 */
//	public static void DownLoadImg(final Context context, final ImageView img, String Url, String imgName) {
//
//		File file = new File(context.getExternalFilesDir(HttpUrl.IMAGE_PATH), imgName + ".png");
//		final VolleyImageCacheImpl cache = new VolleyImageCacheImpl(context);
//		Bitmap bitmap = cache.getBitmap(imgName);
//		final String imgpath = imgName + "";
//		// if (file.exists() && bitmap != null) {
//		// ((Activity) context).runOnUiThread(new Runnable() {
//		//
//		// @Override
//		// public void run() {
//		//
//		// img.setImageBitmap(cache.getBitmap(imgpath));
//		// }
//		// });
//		// return;
//		// }
//		HttpClient client = new DefaultHttpClient();
//		HttpPost post = new HttpPost(Url);
//		List<NameValuePair> list = new ArrayList<>();
//		NameValuePair nameValuePair = new BasicNameValuePair("fid", imgName);
//		list.add(nameValuePair);
//		UrlEncodedFormEntity uefEntity;
//		try {
//			uefEntity = new UrlEncodedFormEntity(list, "UTF-8");
//			post.setEntity(uefEntity);
//			HttpResponse execute = client.execute(post);
//			// 如果请求成功
//			if (execute.getStatusLine().getStatusCode() == 200) {
//				HttpEntity entity = execute.getEntity();
//				InputStream is = entity.getContent();
//
//				Log.e("123", is.available() + "");
//				if (file.exists()) {
//					file.delete();
//				}
//				file.createNewFile();
//				FileOutputStream fos = new FileOutputStream(file);
//
//				byte[] buff = new byte[1024];
//				int rc = 0;
//				while ((rc = is.read(buff)) > 0) {
//					fos.write(buff, 0, rc);
//				}
//
//				is.close();
//				fos.close();
//				if (file.exists()) {
//					File scal = PhotoReduce.scal(Uri.parse(file.getAbsolutePath()));
//					cache.putBitmap(imgName, BitmapFactory.decodeFile(scal.getPath()));
//					((Activity) context).runOnUiThread(new Runnable() {
//
//						@Override
//						public void run() {
//							// TODO Auto-generated method stub
//
//							// img.setImageURI(Uri.fromFile(file));
//
//							img.setImageBitmap(cache.getBitmap(imgpath));
//						}
//					});
//				}
//			}
//			// else {
//			//
//			// ((Activity) context).runOnUiThread(new Runnable() {
//			//
//			// @Override
//			// public void run() {
//			// // TODO Auto-generated method stub
//			// if (file.exists()) {
//			// try {
//			// img.setImageBitmap(BitmapFactory.decodeFile(file.getPath()));
//			// } catch (Exception e) {
//			// img.setImageResource(R.drawable.ic_launcher);
//			// }
//			// } else {
//			// img.setImageResource(R.drawable.ic_launcher);
//			// }
//			// }
//			// });
//			//
//			// }
//
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

    public static void getImageList(String urlPath) {
        String urlStr = "http://172.17.54.91:8080/download/1.mp3";
        OutputStream os = null;
    }

//	/**
//	 * 获取本机ip
//	 */
//	public static String getHostIp() {
//		String ipaddress = "";
//		try {
//			Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
//			// 遍历所用的网络接口
//			while (en.hasMoreElements()) {
//				NetworkInterface nif = en.nextElement();// 得到每一个网络接口绑定的所有ip
//				Enumeration<InetAddress> inet = nif.getInetAddresses();
//				// 遍历每一个接口绑定的所有ip
//				while (inet.hasMoreElements()) {
//					InetAddress ip = inet.nextElement();
//					if (!ip.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ip.getHostAddress())) {
//						return ipaddress = ip.getHostAddress();
//					}
//				}
//
//			}
//		} catch (SocketException e) {
//			Log.e("feige", "获取本地ip地址失败");
//			e.printStackTrace();
//		}
//		return ipaddress;
//	}

//	public static Dialog showDialog(final Context mContext) {
//		View view = LayoutInflater.from(mContext).inflate(R.layout.item_loading, null);
//		AlertDialog.Builder builder = new AlertDialog.Builder(mContext, AlertDialog.THEME_HOLO_LIGHT);
//		builder.setView(view);
//		Dialog dialog = builder.create();
//		dialog.setCanceledOnTouchOutside(false);
//		dialog.setOnCancelListener(new OnCancelListener() {
//			@Override
//			public void onCancel(DialogInterface dialog) {
//				// TODO Auto-generated method stub
//				// HttpLoader.getInstance(mContext).cancelRequest(tag);
//				HttpLoader.getInstance(mContext).cancelRequest(mContext);
//			}
//		});
//		return dialog;
//	}

    /**
     * 获取ip地址
     *
     * @return
     */
    public static String getHostIP() {

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        return hostIp;

    }


//
//    public static String serverImgToUri(String imgServerName){
//        return HttpUrl.USER_IAMGE + "?fid=" + imgServerName;
//    }
//
//    public static String serverCirculationImgToUri(String imgServerName){
//        return HttpUrl.CIRCULATION_IAMGE + "?fid=" + imgServerName;
//    }


    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
//                        if (QLog.isColorLevel()) {
//                            KLog.d(ReflecterHelper.class.getSimpleName(), "fixInputMethodManagerLeak break, context is not suitable, get_context=" + v_get.getContext()+" dest_context=" + destContext);
//                        }
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }

//    public static void toSmallImage(SimpleDraweeView simpleDraweeView, String uri, int width, int height){
//        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(Utils.serverImgToUri(uri)))
//                .setResizeOptions(new ResizeOptions(width, height))
//                .build();
//
//        DraweeController controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setOldController(simpleDraweeView.getController())
//                .setControllerListener(new BaseControllerListener<ImageInfo>())
//                .build();
//        simpleDraweeView.setController(controller);
//    }


//    /**
//     * 以高斯模糊显示。
//     *
//     * @param draweeView View。
//     * @param url        url.
//     */
//    public static void showUrlBlur(SimpleDraweeView draweeView, String url) {
//        try {
//            Uri uri = Uri.parse(url);
//            ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
//                    .setPostprocessor(new FastBlurPostprocessor(60f))
//                    .build();
//            AbstractDraweeController controller = Fresco.newDraweeControllerBuilder()
//                    .setOldController(draweeView.getController())
//                    .setImageRequest(request)
//                    .build();
//            draweeView.setController(controller);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    /**
     *
     */
    public static void createShowAnimation(final View parent, final View view) {
        if(view==null)
            return;
//        ObjectAnimator scaleAnimator1 = ObjectAnimator.ofFloat(view,
//                "scaleX", 1, 1.6f);
//        scaleAnimator1.setDuration(500);
        ObjectAnimator scaleAnimator2 = ObjectAnimator.ofFloat(view,
                "translationX", 0f, 200f);
        scaleAnimator2.setDuration(1000);
//        scaleAnimator2.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animator) {
//                parent.setVisibility(View.VISIBLE);
//                view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animator) {
//                view.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animator) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animator) {
//
//            }
//        });
//        ObjectAnimator rotateAnimator3 = ObjectAnimator.ofFloat(view,
//                "rotation", -30, 30, 0);
//        rotateAnimator3.setRepeatMode(ObjectAnimator.REVERSE);
//        rotateAnimator3.setRepeatCount(2);
//        rotateAnimator3.setDuration(100);
//                ObjectAnimator rotateAnimator3 = ObjectAnimator.ofFloat(view,
//                "alpha", 0, 1f);
//        rotateAnimator3.setDuration(500);
//        ObjectAnimator scaleAnimator4 = ObjectAnimator.ofFloat(view,
//                "scaleX", 1.6f, 1.0f);
//        scaleAnimator1.setDuration(500);
//        ObjectAnimator scaleAnimator5 = ObjectAnimator.ofFloat(view,
//                "translationX", 1.6f, 1.0f);
//        scaleAnimator2.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.play(scaleAnimator2);
//                .with(scaleAnimator2);
////        set.play(rotateAnimator3).after(scaleAnimator1);
//        set.play(scaleAnimator4).with(scaleAnimator5).after(scaleAnimator1);
        set.start();
    }
    public static void createHideAnimation(View view) {
        if(view==null)
            return;
//        ObjectAnimator scaleAnimator1 = ObjectAnimator.ofFloat(view,
//                "scaleX", 1, 1.6f);
//        scaleAnimator1.setDuration(500);
        ObjectAnimator scaleAnimator2 = ObjectAnimator.ofFloat(view,
                "translationX", 200f,0f);
        scaleAnimator2.setDuration(1000);
//        ObjectAnimator rotateAnimator3 = ObjectAnimator.ofFloat(view,
//                "rotation", -30, 30, 0);
//        rotateAnimator3.setRepeatMode(ObjectAnimator.REVERSE);
//        rotateAnimator3.setRepeatCount(2);
//        rotateAnimator3.setDuration(100);
//                ObjectAnimator rotateAnimator3 = ObjectAnimator.ofFloat(view,
//                "alpha", 0, 1f);
//        rotateAnimator3.setDuration(500);
//        ObjectAnimator scaleAnimator4 = ObjectAnimator.ofFloat(view,
//                "scaleX", 1.6f, 1.0f);
//        scaleAnimator1.setDuration(500);
//        ObjectAnimator scaleAnimator5 = ObjectAnimator.ofFloat(view,
//                "translationX", 1.6f, 1.0f);
//        scaleAnimator2.setDuration(500);

        AnimatorSet set = new AnimatorSet();
        set.play(scaleAnimator2);
//                .with(scaleAnimator2);
////        set.play(rotateAnimator3).after(scaleAnimator1);
//        set.play(scaleAnimator4).with(scaleAnimator5).after(scaleAnimator1);
        set.start();
    }

    public static long stringToLong(String strTime, String formatType)
    {
        Date date = null; // String类型转成date类型
        try {
            date = stringToDate(strTime, formatType);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }
    // strTime要转换的string类型的时间，formatType要转换的格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日
    // HH时mm分ss秒，
    // strTime的时间格式必须要与formatType的时间格式相同
    public static Date stringToDate(String strTime, String formatType)
            throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        date = formatter.parse(strTime);
        return date;
    }
    // date要转换的date类型的时间
    public static long dateToLong(Date date) {
        return date.getTime();
    }


    // formatType格式为yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    // data Date类型的时间
    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的string类型的时间格式
    public static String longToString(long currentTime, String formatType)
            throws ParseException {
        Date date = longToDate(currentTime, formatType); // long类型转成Date类型
        String strTime = dateToString(date, formatType); // date类型转成String
        return strTime;
    }

    // currentTime要转换的long类型的时间
    // formatType要转换的时间格式yyyy-MM-dd HH:mm:ss//yyyy年MM月dd日 HH时mm分ss秒
    public static Date longToDate(long currentTime, String formatType)
            throws ParseException {
        Date dateOld = new Date(currentTime); // 根据long类型的毫秒数生命一个date类型的时间
        String sDateTime = dateToString(dateOld, formatType); // 把date类型的时间转换为string
        Date date = stringToDate(sDateTime, formatType); // 把String类型转换为Date类型
        return date;
    }


    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkSpecialString(String str){
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean isSHowKeyboard(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
        if (imm.hideSoftInputFromWindow(v.getWindowToken(), 0)) {
            imm.showSoftInput(v, 0);
            return true;
        } else {
            return false;
        }
    }

}