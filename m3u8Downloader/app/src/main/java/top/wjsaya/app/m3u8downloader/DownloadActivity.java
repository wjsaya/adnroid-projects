package top.wjsaya.app.m3u8downloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class DownloadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
    }

    protected void backtoMain(View view) {
        Log.d("downloadActivity", "back to Main");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
