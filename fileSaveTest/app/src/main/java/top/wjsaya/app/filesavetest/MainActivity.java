package top.wjsaya.app.filesavetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button in, ext;
    private TextView tv_top;
    private EditText et_top;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        in = (Button) findViewById(R.id.bt_internal);
        ext = (Button) findViewById(R.id.bt_external);
        tv_top = (TextView) findViewById(R.id.tv_top);
        et_top = (EditText) findViewById(R.id.et_top);
        read();
        in.setOnClickListener(this);
        ext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_internal:
                Toast.makeText(this, "内部", Toast.LENGTH_SHORT).show();
                func_internal();
                break;
            case R.id.bt_external:
                Toast.makeText(this, "外部", Toast.LENGTH_SHORT).show();
                func_external();
                break;
            default:
                Toast.makeText(this, "未匹配到内外部", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void read() {
        File file = new File(this.getFilesDir().toString(), "info.txt");
        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String tempread = br.readLine();
                tv_top.setText(tempread);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void func_internal() {
        File file = new File(this.getFilesDir().toString(), "info.txt");
        try {
            String tempwrite = et_top.getText().toString();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(tempwrite.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        read();
    }

    public void func_external() {
        String temp = et_top.getText().toString();
        tv_top.setText(temp);
    }


}

