package cn.ttsx.face;

import android.util.Log;

import com.arcsoft.facerecognition.AFR_FSDKEngine;
import com.arcsoft.facerecognition.AFR_FSDKError;
import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.arcsoft.facerecognition.AFR_FSDKVersion;
import com.guo.android_extend.java.ExtInputStream;
import com.guo.android_extend.java.ExtOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 存的是虹软的AppDI
 *
 * @Description:  (用一句话描述该类作用)
 * @author  zhangbing
 * @CreateDate:  2017/12/28 10:58
 * @email  314835006@qq.com
 *
 * @UpdateUser:  zhangbing
 * @UpdateDate:  2017/12/28 10:58
 * @UpdateRemark:
 */
public class FaceDB {
    private final String TAG = this.getClass().toString();

    public static String appid = "4j765EkzepNFJAJRoJadgsxdDJ3B6W3MPi1zwbWMt22m";
    public static String ft_key = "FPeXUBwTvryh3ZD1TiYA17Ljzas4Wmfk8HkuZr8QDguE";
    public static String fd_key = "FPeXUBwTvryh3ZD1TiYA17Ls9z8E55FogoGpHyXYJeb4";
    public static String fr_key = "FPeXUBwTvryh3ZD1TiYA17LzKPPTC7P73snSc8AQjuSt";
    public static String age_key = "FPeXUBwTvryh3ZD1TiYA17Mc8PhFjzzdCcj9bAWFwLjZ";
    public static String gender_key = "FPeXUBwTvryh3ZD1TiYA17MjHnxTLXqg3dA7cTfLRwNb";

    String mDBPath;
    List<FaceRegist> mRegister;
    AFR_FSDKEngine mFREngine;
    AFR_FSDKVersion mFRVersion;
    boolean mUpgrade;

    class FaceRegist {
        String mName;
        List<AFR_FSDKFace> mFaceList;

        public FaceRegist(String name) {
            mName = name;
            mFaceList = new ArrayList<>();
        }
    }

    public FaceDB(String path) {

        mDBPath = path;
        mRegister = new ArrayList<>();
        mFRVersion = new AFR_FSDKVersion();
        mUpgrade = false;
        mFREngine = new AFR_FSDKEngine();
        //初始化人脸识别的引擎
        AFR_FSDKError error = mFREngine.AFR_FSDK_InitialEngine(FaceDB.appid, FaceDB.fr_key);
        if (error.getCode() != AFR_FSDKError.MOK) {
            Log.e(TAG, "AFR_FSDK_InitialEngine fail! error code :" + error.getCode());
        } else {
            //检测人脸识别的版本
            mFREngine.AFR_FSDK_GetVersion(mFRVersion);
            Log.d(TAG, "AFR_FSDK_GetVersion=" + mFRVersion.toString());
        }
    }

    public void destroy() {
        if (mFREngine != null) {
            mFREngine.AFR_FSDK_UninitialEngine();
        }
    }

    private boolean saveInfo() {
        try {

            FileOutputStream fs = new FileOutputStream(mDBPath + "/Face.txt");
            ExtOutputStream bos = new ExtOutputStream(fs);
            bos.writeString(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel());
            bos.close();
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean loadInfo() {
        if (!mRegister.isEmpty()) {
            return false;
        }
        try {
            FileInputStream fs = new FileInputStream(mDBPath + "/Face.txt");
            ExtInputStream bos = new ExtInputStream(fs);
            //load version
            String version_saved = bos.readString();
            if (version_saved.equals(mFRVersion.toString() + "," + mFRVersion.getFeatureLevel())) {
                mUpgrade = true;
            }
            //load all regist name.
            if (version_saved != null) {
                for (String name = bos.readString(); name != null; name = bos.readString()) {
                    if (new File(mDBPath + "/" + name + ".data").exists()) {
                        mRegister.add(new FaceRegist(new String(name)));
                    }
                }
            }
            bos.close();
            fs.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean loadFaces() {
        if (loadInfo()) {
            try {
                for (FaceRegist face : mRegister) {
                    Log.d(TAG, "load name:" + face.mName + "'s Face feature data.");
                    FileInputStream fs = new FileInputStream(mDBPath + "/" + face.mName + ".data");
                    ExtInputStream bos = new ExtInputStream(fs);
                    AFR_FSDKFace afr = null;
                    do {
                        if (afr != null) {
                            if (mUpgrade) {
                                //upgrade data.
                            }
                            face.mFaceList.add(afr);
                        }
                        afr = new AFR_FSDKFace();
                    } while (bos.readBytes(afr.getFeatureData()));
                    bos.close();
                    fs.close();
                    Log.d(TAG, "load name: size = " + face.mFaceList.size());
                }
                return true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


    /**
     * 添加人脸信息
     *
     * @param name
     * @param face
     */
    public void addFace(String name, AFR_FSDKFace face) {
        try {
            //check if already registered.
            boolean add = true;
            for (FaceRegist frface : mRegister) {
                if (frface.mName.equals(name)) {
                    frface.mFaceList.add(face);
                    add = false;
                    break;
                }
            }
            if (add) { // not registered.
                FaceRegist frface = new FaceRegist(name);
                frface.mFaceList.add(face);
                mRegister.add(frface);
            }

            if (saveInfo()) {
                //update all names
                FileOutputStream fs = new FileOutputStream(mDBPath + "/Face.txt", true);
                ExtOutputStream bos = new ExtOutputStream(fs);
                for (FaceRegist frface : mRegister) {
                    bos.writeString(frface.mName);
                }
                bos.close();
                fs.close();

                //save new feature
                fs = new FileOutputStream(mDBPath + "/" + name + ".data", true);
                bos = new ExtOutputStream(fs);
                bos.writeBytes(face.getFeatureData());
                bos.close();
                fs.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean delete(String name) {
        try {
            //check if already registered.
            boolean find = false;
            for (FaceRegist frface : mRegister) {
                if (frface.mName.equals(name)) {
                    File delfile = new File(mDBPath + "/" + name + ".data");
                    if (delfile.exists()) {
                        delfile.delete();
                    }
                    mRegister.remove(frface);
                    find = true;
                    break;
                }
            }

            if (find) {
                if (saveInfo()) {
                    //update all names
                    FileOutputStream fs = new FileOutputStream(mDBPath + "/Face.txt", true);
                    ExtOutputStream bos = new ExtOutputStream(fs);
                    for (FaceRegist frface : mRegister) {
                        bos.writeString(frface.mName);
                    }
                    bos.close();
                    fs.close();
                }
            }
            return find;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean upgrade() {
        return false;
    }
}
