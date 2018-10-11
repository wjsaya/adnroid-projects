package top.wjsaya.app.bilibili_parse;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.Map;

import top.wjsaya.app.bilibili_parse.SingleVideo.singlevideo;

public class MainActivity extends AppCompatActivity {
    File[] AllDirs;
    private TextView tv_top, tv_content;
    private EditText et_avid;
    private LinearLayout mainLayout;
    private ScrollView mainScrollList;

    private static final int REQUEST_CODE = 0; // 请求码
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE);
               // return;
            }else{
                init();
            }
        } else {
            init();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        String ExtRootDir = Environment.getExternalStorageDirectory().toString();
        Log.e("init()", "进入方法");
        File bilibili = new File(ExtRootDir + "/Android/data/tv.danmaku.bili/download/");
        this.AllDirs = bilibili.listFiles();

        try {
            File temp = this.AllDirs[0];
        }
        catch (Exception e) {
            Toast.makeText(this, "bilibili下载目录为空?", Toast.LENGTH_SHORT).show();
            return;
        }

        mainScrollList = new ScrollView(this);
        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        for (File file : this.AllDirs) {
            initList(mainLayout, file);
            //            temp += file.getName().toString() + "\n";
            LinearLayout slash = new LinearLayout(this);
            slash.setBackgroundColor(Color.GRAY);
            //TODO 暂时用空白layout分隔
            mainLayout.addView(slash,  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 10));
        }
        mainScrollList.addView(mainLayout);
        setContentView(mainScrollList);
    }


    public void initList(LinearLayout mainLayout, File file) {
        singlevideo temp = new singlevideo(file.getName().toString().trim());
        Map<String, String> avInfo = temp.getInfo();
        Log.e("getInfo(size)", avInfo.get("size"));
        String size = "";
        try {
            size = Formatter.formatFileSize(MainActivity.this, Long.valueOf(avInfo.get("size")));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

            SingleVideoBar tempBar;
            tempBar = new SingleVideoBar(this);
            tempBar.setTextTitle(avInfo.get("title"), 12);
            tempBar.setTextTvSize(size);
            tempBar.setTextBtnCombine("合并");

            final AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
            builder.setTitle(avInfo.get("avid"));
            builder.setMessage(avInfo.get("avFilePath"));
            builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
            builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { //设置确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss(); //关闭dialog
                        }
                    });
            //            tempBar.setDrableImg("@drawable/ic_launcher_foreground");
            tempBar.setSingleVideoBarOnClickListener(new SingleVideoBar.SingleVideoBarOnClickListener() {
                @Override
                public void btnCombineOnClick() {//TODO 未完成，后续将会起新线程来合并当前按钮对应的视频
                    Toast.makeText(MainActivity.this, "未完成，后续将会起新线程来合并当前按钮对应的视频", Toast.LENGTH_SHORT).show();
//                    builder.create().show();
                }
                @Override
                public void tvDetailsOnClick() {//TODO 未完成，点击后，弹出一个Dialog，显示对应视频的详细信息
                    Toast.makeText(MainActivity.this, "未完成，点击后，弹出一个Dialog，显示对应视频的详细信息", Toast.LENGTH_SHORT).show();
                }
            });
        mainLayout.addView(tempBar);
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
