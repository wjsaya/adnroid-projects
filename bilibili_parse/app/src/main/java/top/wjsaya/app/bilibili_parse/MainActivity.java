package top.wjsaya.app.bilibili_parse;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import top.wjsaya.app.bilibili_parse.SingleVideo.singlevideo;

public class MainActivity extends AppCompatActivity {
    File[] AllDirs;
    final private int CHANGE_UI = 1;
    private TextView tv_top, tv_content;
    private EditText et_avid;
    private LinearLayout mainLayout;
    private ScrollView mainScrollList;

    private int screenWidth;
    private int screenHeight;

    private static final int REQUEST_CODE = 0; // 请求码
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
/*

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
*/

        @SuppressLint("HandlerLeak")
        private Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == CHANGE_UI) {
                    Log.i("MainActivity:", "收到子线程更改图片的请求");
                    Bitmap bitmap = (Bitmap) msg.obj;

                    Bundle bundleData = msg.getData();
                    int reavid = Integer.valueOf(bundleData.getString("avid", "233333"));
                    ImageView iv_now = findViewById(reavid);

                    Log.i("MainActivity:", "子线程返回avid：" + String.valueOf(reavid));
                    iv_now.setImageBitmap(bitmap);

                    tv_content.setText(reavid);
                }
                else {
                    Log.i("MainActivity:", "收到子线程不是更改图片的消息");
                }
            }
        };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void init() {
        String ExtRootDir = Environment.getExternalStorageDirectory().toString();
        Log.e("init()", "init()被调用， 开始初始化首页的ScrollView");
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
            addAvWidgets(mainLayout, file);
            addSlash(mainLayout);
        }
        mainScrollList.addView(mainLayout);
        setContentView(mainScrollList);
    }

    public void addSlash(LinearLayout mainLayout) {
        LinearLayout slash = new LinearLayout(this);
        slash.setBackgroundColor(Color.GRAY);            //TODO 暂时用空白layout分隔
        mainLayout.addView(slash,  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20));
    }

    public void addAvWidgets(LinearLayout mainLayout, File file) {
        singlevideo temp = new singlevideo(file.getName().toString().trim());
        final Map<String, String> remap = temp.getInfoMAP();
//        Log.wtf("返回数组", remap.get("avid"));
//        Log.wtf("返回数组", remap.get("title"));
//        Log.wtf("返回数组", remap.get("cover"));
//        Log.wtf("返回数组", remap.get("is_completed"));
//        Log.wtf("返回数组", remap.get("downloaded_bytes"));
//        Log.wtf("返回数组", remap.get("total_bytes"));
//        Log.wtf("返回数组", remap.get("type_tag"));
//        Log.wtf("返回数组", remap.get("avFilePath"));
//

        //        Point screenSize = new Point();
        //        MainActivity.this.getWindowManager().getDefaultDisplay().getSize(screenSize);
        //        int screenWidth = screenSize.x;
        //        int screenHeight = screenSize.y;
        //        int titleTextSize = (screenWidth / 720) * 12;
        //
        //        Log.e("screenWidth", String.valueOf(screenWidth));
        //        Log.e("screenWidth/12", String.valueOf(screenWidth/12));
        //        Log.e("字体大小", String.valueOf(titleTextSize));

        SingleVideoBar tempBar;
        tempBar = new SingleVideoBar(this);
        tempBar.setWidgetHeight(200);

        Bitmap img = getBitImg(remap.get("cover"));
        tempBar.setavId(Integer.valueOf(remap.get("avid")));
        tempBar.setImgAV(img);                                              //封面图片
        tempBar.setTextTitle(remap.get("title"), 20);              //设置标题
        tempBar.setTextDetails("详情 >", 20);          //设置详情按钮文字
        tempBar.setTextTvSize(Formatter.formatFileSize(this, Long.valueOf(remap.get("total_bytes"))), 20);            //设置av大小的文字
        tempBar.initLayout(this);

        //        final AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        //        builder.setTitle(avInfo.get("avid"));
        //        builder.setMessage(avInfo.get("avFilePath"));
        //        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        //        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() { //设置确定按钮
        //                        @Override
        //                        public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); //关闭dialog
        //                        }
        //                    });
        //            tempBar.setDrableImg("@drawable/ic_launcher_foreground");
        tempBar.setSingleVideoBarOnClickListener(new SingleVideoBar.SingleVideoBarOnClickListener() {
            @Override
            public void videoViewOnClick() {//TODO 点击后，弹出一个Dialog，显示对应视频的详细信息
                Toast.makeText(MainActivity.this, "未完成，弹出一个Dialog，", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void tvDetailsOnClick() {//TODO 点击后，跳转到新的activity，用来展示分p信息
                Toast.makeText(MainActivity.this, "点击后，跳转到新的activity，用来展示分p信息", Toast.LENGTH_SHORT).show();
            }
        });
        mainLayout.addView(tempBar);
        new Thread(runnable).start();
    }

    Runnable runnable = new Runnable(){
        private String url;
        private String avid;
        private URL imgUrl;
        private Bitmap reBitmap;
        private HttpURLConnection conn;

        void runnable(String url, String avid) {
            this.url = url;
            this.avid = avid;
        }
        @Override
        public void run() {
            try {
                //2，打开图片url对应http链接
                imgUrl = new URL(this.url);
                conn = (HttpURLConnection) imgUrl.openConnection();   //connection有很多返回，强制转换为http的链接
                //3，获取服务器返回
                int responseCode = conn.getResponseCode();
                Log.i("连接状态码:", String.valueOf(responseCode));
                if (responseCode == 200) {
                    String contentType = conn.getContentType();
                    Log.i("连接的返回类型:", contentType);
                    InputStream is = conn.getInputStream();
                    reBitmap = BitmapFactory.decodeStream(is);
                    is.close();

                    Message msg = new Message();
                    msg.what = CHANGE_UI;
                    msg.obj = reBitmap;
                    Bundle bundleData = new Bundle();
                    bundleData.putString("avid", this.avid);
                    msg.setData(bundleData);

                    handler.sendMessage(msg);
                    //                            iv_img.setImageBitmap(reBitmap);
                }
            } catch (Exception e) {
                Message msg = new Message();
                msg.what = CHANGE_UI;
                msg.obj = "下载图片出错";
                handler.sendMessage(msg);
                e.printStackTrace();
            }
        }
    };

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

    public Bitmap getBitImg(String url) {
        String TAG = "getBitImg:";
            URL myFileUrl = null;
            Bitmap bitmap = null;
            try {
                Log.d(TAG, url);
                myFileUrl = new URL(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setConnectTimeout(0);
                conn.setDoInput(true);
                conn.connect();
                InputStream is = conn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
    }
}
