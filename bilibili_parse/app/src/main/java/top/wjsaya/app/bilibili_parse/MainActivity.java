package top.wjsaya.app.bilibili_parse;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import top.wjsaya.app.bilibili_parse.SingleVideo.singlevideo;

public class MainActivity extends AppCompatActivity {
    File[] AllDirs;
    private TextView tv_top, tv_content;
    private EditText et_avid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.sample_single_video_bar);
        /*
        tv_top = (TextView) findViewById(R.id.tv_top);
        tv_content = (TextView) findViewById(R.id.tv_content);
        et_avid = (EditText) findViewById(R.id.et_avid);
        init();
        */
    }

    public void init() {
        String ExtRootDir = Environment.getExternalStorageDirectory().toString();
        this.AllDirs = new File(ExtRootDir + "/Android/data/tv.danmaku.bili/download/").listFiles();
        String temp = "";
        for (File file : this.AllDirs) {
            temp += file.getName().toString() + "\n";
        }
        tv_top.setText(temp);
    }
    /**
     * 获取bilibili下载目录下的文件信息。
     * 当点击按钮时，清除下方的详情页面。
     * 并且获取用户输入的avid，拼接出avid对应的目录。
     * @param v 当前view
     */
    public void getFilesPath(View v) {
        init();
        tv_content.setText("");
        String avid = et_avid.getText().toString().trim();
        if (TextUtils.isEmpty(avid)) avid = "14494920";
        singlevideo parse = new singlevideo(avid);
        String temp = parse.parseVideos();
//        tv_content.setText(parse.type_tag);
        tv_content.setText(temp);
    }
}
