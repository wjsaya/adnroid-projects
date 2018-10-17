package top.wjsaya.app.bilibili_parse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PicturePreviewActivity extends AppCompatActivity {
    private TextView tv_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_preview);
        tv_url = findViewById(R.id.tv_url);
        String imgUrl = this.getIntent().getStringExtra("imgUrl");
        tv_url.setText(imgUrl);
    }
}
