package cn.ttsx.face;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.ttsx.face.utils.AudioUtils;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        AudioUtils.getInstance().init(this); //初始化语音对象
        AudioUtils.getInstance().speakText("孙鑫同学" + "欢迎您,您是世界上最帅的人!!!"); //播放语音

    }
}
