package top.wjsaya.app.studyprojectscollection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button bu_ListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bu_ListView = findViewById(R.id.bu_ListView);
        bu_ListView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bu_ListView:
                Log.d("onClick调试", "bu_ListView被点击了");
                Toast.makeText(this, "即将切换到ListView练习", Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(MainActivity.this, ActivityListView.class);
                startActivity(newIntent);
                break;
        }
    }
}
