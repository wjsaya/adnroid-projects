package top.wjsaya.app.bilibili_parse.BAK_classes;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import top.wjsaya.app.bilibili_parse.R;
import top.wjsaya.app.bilibili_parse.SingleVideoBar;
import top.wjsaya.app.bilibili_parse.dao.ParseDao;
import top.wjsaya.app.bilibili_parse.videoParse.singlevideo;

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

    private int screenWidth;
    private int screenHeight;

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
        refreshData();
//        init();
    }

    static class viewHolder {
        public TextView av_title, av_fileSize, av_details;
        public ImageView av_cover;
    }

    private void refreshData() {

        lv_main.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {//获取条目总数
                return 100;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                viewHolder myHolder;
                if (convertView == null) {
                    Log.e("getView调试", "缓存view不存在，new TextView " + position);
                    convertView = mLayoutInflater.inflate(R.layout.item_of_recylerview, null);
                    myHolder = new viewHolder();
                    convertView.setTag(myHolder);
                } else {
                    Log.e("getView调试", "缓存view存在，复用之 " + position);
                    myHolder = (viewHolder) convertView.getTag();
                }
                myHolder.av_title.setText("我是标题哈哈哈哈哈哈哈哈哈，我叫标题" + position);
                myHolder.av_details.setText("详情" + position);
                myHolder.av_fileSize.setText(position + ".333mb");
                //                iv_cover.setImageDrawable(R.drawable.ic_launcher_foreground);
                return convertView;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }
        });
    }




    public void init() {
        String ExtRootDir = Environment.getExternalStorageDirectory().toString();
        Log.e("init()", "init()被调用， 开始初始化首页的ScrollView");
        File bilibili = new File(ExtRootDir + "/Android/data/tv.danmaku.bili/download/");
        this.AllDirs = bilibili.listFiles();

        try {
            File temp = this.AllDirs[0];
        } catch (Exception e) {
            Toast.makeText(this, "bilibili下载目录为空?", Toast.LENGTH_SHORT).show();
            return;
        }

        mainLayout = new LinearLayout(this);
        mainLayout.setOrientation(LinearLayout.VERTICAL);
        //        mainLayout.setBackgroundColor(Color.GRAY);


        for (File file : this.AllDirs) {
            addAvWidgets(mainLayout, file);
            addSlash(mainLayout);
        }

        mainScrollList = new ScrollView(this);
        mainScrollList.addView(mainLayout);
        setContentView(mainScrollList);

    }

    public void addSlash(LinearLayout mainLayout) {
        LinearLayout slash = new LinearLayout(this);
        slash.setBackgroundColor(Color.GRAY);            //TODO 暂时用空白layout分隔
        mainLayout.addView(slash, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 20));
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

        try {
            tempBar.setavId(Integer.valueOf(remap.get("avid")));
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        tempBar.setTextTitle(remap.get("title"), 20);              //设置标题
        tempBar.setTextDetails("详情 >", 20);          //设置详情按钮文字
        tempBar.setTextTvSize(Formatter.formatFileSize(this, Long.valueOf(remap.get("total_bytes"))), 20);            //设置av大小的文字
        tempBar.initLayout(this);

        final AlertDialog.Builder infoDialog = new AlertDialog.Builder(this);  //先得到构造器
        infoDialog.setTitle(remap.get("title"));

        String alertMessage = "";
        alertMessage += "av号：" + remap.get("avid");
//        alertMessage += "\n视频标题：" + remap.get("title");
//        alertMessage += "\n已下载完成？" + remap.get("is_completed");
        alertMessage += "\n\n已下载：" + Formatter.formatFileSize(this, Long.valueOf(remap.get("downloaded_bytes")));
        alertMessage += "\n\n总大小：" + Formatter.formatFileSize(this, Long.valueOf(remap.get("total_bytes")));
        alertMessage += "\n\n视频编码：" + remap.get("type_tag");
        alertMessage += "\n\n本地缓存路径：" + remap.get("avFilePath");
        alertMessage += "\n\n封面地址：" + remap.get("cover");


        infoDialog.setMessage(alertMessage);
        infoDialog.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可
        infoDialog.setNeutralButton("复制封面地址", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ClipboardManager clip = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("封面地址",remap.get("cover"));
                clip.setPrimaryClip(clipData);
                Toast.makeText(MainActivity.this, "封面地址已复制，去下载吧~", Toast.LENGTH_SHORT).show();
            }
        });
        infoDialog.setPositiveButton("确认", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); //关闭dialog
            }
        });

        tempBar.setSingleVideoBarOnClickListener(new SingleVideoBar.SingleVideoBarOnClickListener() {
            @Override
            public void imgOnClick() {//TODO 点击后，弹出一个新窗口展示大图，一个button用来保存
                Toast.makeText(MainActivity.this, "未完成，弹出一个新窗口展示大图，一个button用来保存。", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //                intent.setClass(MainActivity.this, PicturePreviewActivity.class);

                intent.putExtra("imgUrl", remap.get("cover"));
                startActivity(intent);
            }

            @Override
            public void videoViewOnClick() {// 点击后，弹出一个Dialog，显示对应视频的详细信息
                infoDialog.show();
            }

            @Override
            public void tvDetailsOnClick() {//TODO 点击后，跳转到新的activity，用来展示分p信息
                Toast.makeText(MainActivity.this, "点击后，跳转到新的activity，用来展示分p信息", Toast.LENGTH_SHORT).show();
            }
        });
        mainLayout.addView(tempBar);
        new Thread(new initImg(remap.get("avid"), remap.get("cover"))).start();
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

        /**
         * 获取bilibili下载目录下的文件信息。
         * 当点击按钮时，清除下方的详情页面。
         * 并且获取用户输入的avid，拼接出avid对应的目录。
         *
         * @param v 当前view
         */
        public void getFilesPath(View v) {
            init();
            tv_content.setText("");
            String avid = et_avid.getText().toString().trim();
            if (TextUtils.isEmpty(avid))
                avid = "14494920";
            singlevideo parse = new singlevideo(avid);
            String temp = parse.parseVideos();
            //        tv_content.setText(parse.type_tag);
            tv_content.setText(temp);
        }
    }
}
