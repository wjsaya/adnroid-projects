package top.wjsaya.app.studyprojectscollection;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityListView extends AppCompatActivity implements View.OnClickListener {
    private Button bu_add;
    private ListView lv_main;
    private TextView et_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        bu_add = findViewById(R.id.bu_add);
        lv_main = findViewById(R.id.lv_main);
        et_name = findViewById(R.id.et_name);

        bu_add.setOnClickListener(this);
        lv_main.setAdapter(new MyListAdapter(ActivityListView.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_add:
                String name = et_name.getText().toString().trim();
                Toast.makeText(this, "输入了" + name, Toast.LENGTH_SHORT).show();
                break;
        }
    }

}
