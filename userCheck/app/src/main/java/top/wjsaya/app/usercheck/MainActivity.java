package top.wjsaya.app.usercheck;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void subbmit(View view) {
        EditText editText = (EditText) findViewById(R.id.userName);
        String username = editText.getText().toString();
        editText = (EditText) findViewById(R.id.passWord);
        String password = editText.getText().toString();

        if (username.equals("wjsaya") && password.equals("123456")) {
            String resp = "登录成功！！!\n\n你好呀" + username;
            Toast toast = Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            ;
            toast.show();


        }
        else {
            String resp = "登录失败！！！\n" + username;
            resp += "\n你的密码是" + password;
            Toast toast = Toast.makeText(getApplicationContext(), resp, Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    }
}
