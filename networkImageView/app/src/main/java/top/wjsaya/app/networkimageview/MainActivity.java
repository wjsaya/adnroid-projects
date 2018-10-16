

package top.wjsaya.app.networkimageview;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
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
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncTask{
    private EditText et_url;
    private Button bu_submit;
    private ImageView iv_img;
    final private int CHANGE_UI = 1;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == CHANGE_UI) {
                Log.e("主线程:", "收到图片");
                Bitmap bitmap = (Bitmap) msg.obj;
                iv_img.setImageBitmap(bitmap);

                Bundle bundleData = new Bundle();
                bundleData = msg.getData();
                bu_submit.setText(bundleData.getString("avid", "233333"));
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
        et_url = findViewById(R.id.et_url);
        bu_submit = findViewById(R.id.bu_submit);
        iv_img = findViewById(R.id.iv_img);
        bu_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bu_submit) {
            final String inUrl = et_url.getText().toString();
            new Thread(getImg(inUrl)).start();
        }

            Toast.makeText(this, et_url.getText(), Toast.LENGTH_SHORT).show();
        }


        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            Bitmap bitmap = null;
            try {
                //加载一个网络图片
                InputStream is = new URL(url).openStream();
                bitmap = BitmapFactory.decodeStream(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }
}


