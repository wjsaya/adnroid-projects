

package top.wjsaya.app.networkimageview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText et_url1, et_url2, et_url3;
    private Button bu_submit1, bu_submit2, bu_submit3;
    private ImageView iv_img1, iv_img2, iv_img3;
    final private int CHANGE_UI = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_UI) {
                Log.e("主线程:", "收到图片");
                Bitmap bitmap = (Bitmap) msg.obj;

                Bundle bundleData = new Bundle();
                bundleData = msg.getData();
                int ivid = bundleData.getInt("ivid");
                ImageView now = findViewById(ivid);
                now.setImageBitmap(bitmap);
//                bu_submit3.setText(bundleData.getString("avid", "233333"));
            }
            else {
                Log.e("主线程:", "没有收到图片");
                Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
//            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_url1 = findViewById(R.id.et_url1);
        bu_submit1 = findViewById(R.id.bu_submit1);
        iv_img1 = findViewById(R.id.iv_img1);
        bu_submit1.setOnClickListener(this);

        et_url2 = findViewById(R.id.et_url2);
        bu_submit2 = findViewById(R.id.bu_submit2);
        iv_img2 = findViewById(R.id.iv_img2);
        bu_submit2.setOnClickListener(this);

        et_url3 = findViewById(R.id.et_url3);
        bu_submit3 = findViewById(R.id.bu_submit3);
        iv_img3 = findViewById(R.id.iv_img3);
        bu_submit3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bu_submit1) {
            final String inUrl = et_url1.getText().toString();
            new Thread(new getImg(inUrl, iv_img1.getId())).start();
        }

        if (v.getId() == R.id.bu_submit2) {
            final String inUrl = et_url2.getText().toString();
            new Thread(new getImg(inUrl, iv_img2.getId())).start();
        }

        if (v.getId() == R.id.bu_submit3) {
            final String inUrl = et_url3.getText().toString();
            new Thread(new getImg(inUrl, iv_img3.getId())).start();
        }
    }

    class getImg extends Thread {
        private String imgUrl;
        private int ivid;

        public getImg(String inurl, int inid) {
            imgUrl = inurl;
            ivid = inid;
        }

        @Override
        public void run() {
            try {
                Log.e("子线程开始执行,", "ivid=" + String.valueOf(ivid));
                URL imgUrl = new URL(this.imgUrl);
                HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
                InputStream is = conn.getInputStream();
                //把流里的数据读取出来，并构造成图片
                Bitmap bm = BitmapFactory.decodeStream(is);


                Message msg = new Message();
                msg.obj = bm;
                msg.what = CHANGE_UI;
                Bundle bundleData = new Bundle();
                bundleData.putInt("ivid", ivid);
                msg.setData(bundleData);
                Log.e("子线程执行完毕", "ivid=" + String.valueOf(ivid));
                handler.sendMessage(msg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

/*
class MyThread implements Runnable {
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Log.d("thread。。。。。。。", "mThread。。。。。。。。");
        Message msg = new Message();
        Bundle b = new Bundle();// 存放数据
        b.putString("color", "我的");
        b.putString("avid", "123456");
        msg.setData(b);

        handler.sendMessage(msg); // 向Handler发送消息，更新UI
    }
}
*/



