package top.wjsaya.app.m3u8downloader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected void gotoDownload(View view) {
        Log.d("goto", "Download View");
        Intent intent = new Intent(this, DownloadActivity.class);
        startActivity(intent);
    }

}
