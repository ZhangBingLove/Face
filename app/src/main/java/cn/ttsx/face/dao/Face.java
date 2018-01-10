package cn.ttsx.face.dao;

import com.arcsoft.facerecognition.AFR_FSDKFace;
import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;


/**
 * 本地face的数据模型
 *
 * @Description:  (用一句话描述该类作用)
 * @author  zhangbing
 * @CreateDate:  2017/12/29 11:12
 * @email  314835006@qq.com
 *
 * @UpdateUser:  zhangbing
 * @UpdateDate:  2017/12/29 11:12
 * @UpdateRemark:
 */
@Entity
public class Face{

    @Id
    private Long id;

    private String username;

    @Property
    @Convert(converter = CatConverter.class, columnType = String.class)
    private AFR_FSDKFace faceregist;

    @Generated(hash = 1987236073)
    public Face(Long id, String username, AFR_FSDKFace faceregist) {
        this.id = id;
        this.username = username;
        this.faceregist = faceregist;
    }

    @Generated(hash = 601504354)
    public Face() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AFR_FSDKFace getFaceregist() {
        return this.faceregist;
    }

    public void setFaceregist(AFR_FSDKFace faceregist) {
        this.faceregist = faceregist;
    }

    public static class CatConverter implements PropertyConverter<AFR_FSDKFace, String> {
        @Override
        public AFR_FSDKFace convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }
            return new Gson().fromJson(databaseValue, AFR_FSDKFace.class);
        }

        @Override
        public String convertToDatabaseValue(AFR_FSDKFace entityProperty) {
            if (entityProperty == null) {
                return null;
            }
            return new Gson().toJson(entityProperty);
        }
    }



}
