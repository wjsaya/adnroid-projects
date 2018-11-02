package top.wjsaya.app.bilibili_parse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import top.wjsaya.app.bilibili_parse.dao.ParseDao;

public class MainActivity extends AppCompatActivity {
    File[] AllDirs;
    final private int CHANGE_PIC_SUCCESS = 1;
    final private int CHANGE_PIC_FAIL = 2;
    private TextView tv_top, tv_content;
    private EditText et_avid;
    private ListView lv_main;
    private LinearLayout mainLayout;
    private ScrollView mainScrollList;

    private LayoutInflater mLayoutInflater;


    private static final int REQUEST_CODE = 0; // 请求码
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
    };


    @Override
    protected void onResume() {
        super.onResume();

        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                // return;
            }
            checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, REQUEST_CODE);
                // return;
            }
        }
    }


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundleData = msg.getData();
            int reavid = Integer.valueOf(bundleData.getString("avid", "233333"));
            ImageView iv_now = findViewById(reavid);

            if (msg.what == CHANGE_PIC_SUCCESS) {
                Log.i("MainActivity:", "收到子线程更改图片的请求");
                Bitmap bitmap = (Bitmap) msg.obj;
//                Log.i("MainActivity:", "子线程返回avid：" + String.valueOf(reavid));
                iv_now.setImageBitmap(bitmap);
            } else {
                Log.e("MainActivity:", "av号为" + String.valueOf(reavid) + "的封面更新失败");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv_main = findViewById(R.id.lv_main);
        lv_main.setAdapter(new SingleVideoBarAdapter(MainActivity.this));
    }

    class initImg extends Thread {
        private String url;
        private String avid;
        private URL imgUrl;
        private String sizeOfserver, sizeOfdb;
        private transient Bitmap reBitmap;
        private HttpURLConnection conn;
        private int status;  //DONE 初始化状态what
        private ParseDao dao = new ParseDao(MainActivity.this);

        public initImg(String inAvid, String inUrl) {
            url = inUrl;
            avid = inAvid;
//            avid = String.valueOf(32803363);
        }

        @Override
        public void run() {
            //TODO              开启链接，获取链接contentLength，获取数据库中size。
            try {
                imgUrl = new URL(this.url);
                conn = (HttpURLConnection) imgUrl.openConnection();   //connection有很多返回，强制转换为http的链接
                int responseCode = conn.getResponseCode();
                Log.d("连接状态码:", String.valueOf(responseCode));
                if (responseCode == 200) {
                    String contentType = conn.getContentType();
                    Log.d("连接的返回类型:", contentType);
                    sizeOfserver = String.valueOf(conn.getContentLength());
                    Log.d("连接返回的图片大小:", String.valueOf(sizeOfserver));
                    sizeOfdb = dao.selectSize(this.avid);
                    Log.d("数据库取出的图片大小:", String.valueOf(sizeOfdb));
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.status = CHANGE_PIC_FAIL;
            }


            if(this.status != CHANGE_PIC_FAIL) {
                if (sizeOfdb.equals(sizeOfserver)) {
                    Log.e("size相同", "sizeOfdb:" + sizeOfdb + "\t" + "sizeOfserver" + sizeOfserver);
                    //TODO                  一致
                    //                          从数据库取出图片放到reBitmap。
                    //                          修改状态what。
                    try {
                        reBitmap = (Bitmap)dao.selectBitmap(this.avid);
                        this.status = CHANGE_PIC_SUCCESS;
                    } catch (Exception e) {
                        e.printStackTrace();
                        this.status = CHANGE_PIC_FAIL;
                    }
                } else {
                    //TODO                  不一致
                    Log.e("size不同", "sizeOfdb:" + sizeOfdb + "\t" + "sizeOfserver" + sizeOfserver);
                    try {
                        InputStream is = conn.getInputStream();
                        reBitmap = BitmapFactory.decodeStream(is);                    // 下载图片放到reBitmap。
                        dao.add(this.avid, this.url, sizeOfserver, reBitmap);         // 下载完毕，更新或者插入相关信息。
                        this.status = CHANGE_PIC_SUCCESS;                              // 修改状态what。
                    } catch (IOException e) {
                        this.status = CHANGE_PIC_FAIL;
                        e.printStackTrace();
                    }
                }
            }

            //TODO              根据状态封装Message
            //2，打开图片url对应http链接
            Message msg = new Message();
            msg.what = this.status;

            if (this.status == CHANGE_PIC_SUCCESS) msg.obj = reBitmap;

            Bundle bundleData = new Bundle();
            bundleData.putString("avid", this.avid);
            msg.setData(bundleData);

            handler.sendMessage(msg);
        }
    }
}
