package cn.ttsx.face.bean;

/**
 * 创建时间: 2017/10/25.
 * 编写人:
 * 功能描述:
 */

public class HomeItemBean {

    private int image;
    private String name;
    private int mNun;


    @Override
    public String toString() {
        return "HomeItemBean{" +
                "image=" + image +
                ", name='" + name + '\'' +
                ", mNun=" + mNun +
                '}';
    }

    public int getmNun() {
        return mNun;
    }

    public void setmNun(int mNun) {
        this.mNun = mNun;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
