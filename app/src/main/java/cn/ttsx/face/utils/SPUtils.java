package cn.ttsx.face.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SP的工具类
 *
 * @Description:  (用一句话描述该类作用)
 * @author  zhangbing
 * @CreateDate:  2017/12/29 11:14
 * @email  314835006@qq.com
 */
public class SPUtils {


    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_date";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context 上下文
     * @param key     key
     * @param object  保存的数据
     */
    public static void setParam(Context context, String key, Object object) {

        if (object == null) {
            return;
        }
        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context       上下文
     * @param key           key
     * @param defaultObject 默认数据
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        if (context == null) {
            return "";
        }
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * 从SP中移除数据
     *
     * @param context 上下文对象
     * @param key     key
     */
    public static void removeParam(Context context, String key) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }


    public static String getUserId(Context context) {
        return (String) getParam(context, GlobalUtils.USER_ID, "");

    }

    public static String getUserName(Context context) {
        return (String) getParam(context, GlobalUtils.USER_NAME, "");
    }
//    public static String getPassWord(Context context){
//        return (String) getParam(context, GlobalUtils.USER_PASSWORD, "");
//    }

    public static String getOperId(Context context) {
        return (String) getParam(context, GlobalUtils.OPER_ID, "");
    }

    public static String getCrtIp(Context context) {
        return (String) getParam(context, GlobalUtils.CRT_IP, "");
    }

    public static String getOperName(Context context) {
        return (String) getParam(context, GlobalUtils.OPER_NAME, "");
    }

    public static String getRoleId(Context context) {
        return (String) getParam(context, GlobalUtils.ROLE_ID, "");
    }

    public static String getRoleName(Context context) {
        return (String) getParam(context, "ROLENAME", "");
    }

    public static String getUserView(Context context) {
        return (String) getParam(context, GlobalUtils.USER_VIEW, "");
    }

    public static String getToken(Context context) {
        return (String) getParam(context, GlobalUtils.USER_TOKEN, "");
    }

    public static String getUserPhone(Context context) {
        return (String) getParam(context, GlobalUtils.USER_PHONE, "");
    }

//    public static String getUserData() {
//        return (String) getParam(App.getAppContext(), GlobalUtils.USER_DATA, "");
//    }
//
//    public static String getPlanDefineTime() {
//        return (String) getParam(App.getAppContext(), GlobalUtils.PLAN_DEFINE_TIME, "111");
//    }
//
//    public static void setPlanDefineTime(String data) {
//        setParam(App.getAppContext(), GlobalUtils.PLAN_DEFINE_TIME, data);
//    }
//
//    public static String getOperateDefineTime() {
//        return (String) getParam(App.getAppContext(), GlobalUtils.OPERATE_DEFINE_TIME, "111");
//    }
//
//    public static void setOperateDefineTime(String data) {
//        setParam(App.getAppContext(), GlobalUtils.OPERATE_DEFINE_TIME, data);
//    }
//
//    public static String getAssayDefineTime() {
//        return (String) getParam(App.getAppContext(), GlobalUtils.ASSAY_DEFINE_TIME, "1111");
//    }
//
//    public static void setAssayDefineTime(String data) {
//        setParam(App.getAppContext(), GlobalUtils.ASSAY_DEFINE_TIME, data);
//    }
//
//    public static void setDeviceId(String data) {
//        setParam(App.getAppContext(), GlobalUtils.DEVICE_ID, data);
//    }
//
//    public static String getDeviceId() {
//        return (String) getParam(App.getAppContext(), GlobalUtils.DEVICE_ID, "");
//    }
//
//    public static String getLoginTime() {
//        return (String) getParam(App.getAppContext(), GlobalUtils.LOGIN_TIME, "");
//    }
//
//    public static int getTheme() {
//        return (int) getParam(App.getAppContext(), GlobalUtils.THEME, 0);
//    }
//
//    public static void setTheme(int theme) {
//        setParam(App.getAppContext(), GlobalUtils.THEME, theme);
//    }

}
