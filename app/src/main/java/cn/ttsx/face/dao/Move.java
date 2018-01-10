package cn.ttsx.face.dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * 创建时间: 2018/1/4.
 * 编写人:
 * 功能描述:
 */
@Entity
public class Move{

    @Id
    private Long id;

    /**
     * 活动名称
     */
    private String moveName;

    /**
     * 活动地址
     */
    private String moveAddress;

    /**
     * 活动开始时间
     */
    private String moveStartTime;

    /**
     * 活动开始时间
     */
    private String moveEndTime;

    /**
     * 活动描述
     */
    private String moveDescription;

    /**
     * 活动的封面
     */
    private String image;

    @Generated(hash = 1334431636)
    public Move(Long id, String moveName, String moveAddress, String moveStartTime,
            String moveEndTime, String moveDescription, String image) {
        this.id = id;
        this.moveName = moveName;
        this.moveAddress = moveAddress;
        this.moveStartTime = moveStartTime;
        this.moveEndTime = moveEndTime;
        this.moveDescription = moveDescription;
        this.image = image;
    }

    @Generated(hash = 1646020629)
    public Move() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMoveName() {
        return this.moveName;
    }

    public void setMoveName(String moveName) {
        this.moveName = moveName;
    }

    public String getMoveAddress() {
        return this.moveAddress;
    }

    public void setMoveAddress(String moveAddress) {
        this.moveAddress = moveAddress;
    }

    public String getMoveStartTime() {
        return this.moveStartTime;
    }

    public void setMoveStartTime(String moveStartTime) {
        this.moveStartTime = moveStartTime;
    }

    public String getMoveEndTime() {
        return this.moveEndTime;
    }

    public void setMoveEndTime(String moveEndTime) {
        this.moveEndTime = moveEndTime;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMoveDescription() {
        return this.moveDescription;
    }

    public void setMoveDescription(String moveDescription) {
        this.moveDescription = moveDescription;
    }
}
