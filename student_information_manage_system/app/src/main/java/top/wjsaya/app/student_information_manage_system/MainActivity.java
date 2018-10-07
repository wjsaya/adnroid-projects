package top.wjsaya.app.student_information_manage_system;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import top.wjsaya.app.student_information_manage_system.db.dao.StudentDao;

public class MainActivity extends AppCompatActivity {
    private EditText et_name;
    private RadioGroup rg_gender;
    private StudentDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name = (EditText) findViewById(R.id.et_name);
        rg_gender = (RadioGroup) findViewById(R.id.rg_gender);
        dao = new StudentDao(this);
    }

    public void save(View v) {
        String name = et_name.getText().toString().trim();
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名为空", 0).show();
            return;
        }

        int id = rg_gender.getCheckedRadioButtonId();
        String gender = "male";
        if (id == R.id.rb_male) {
            gender = "male";
        }
        else {
            gender = "女";
        }
        dao.add(name, gender);
        Toast.makeText(this, "保存完毕", 0).show();

    }
}
