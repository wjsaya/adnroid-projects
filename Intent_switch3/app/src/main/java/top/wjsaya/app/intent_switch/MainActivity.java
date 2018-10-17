package top.wjsaya.app.intent_switch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText et_username, et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
    }

    public void tosecond(View v) {
        Intent te = new Intent(this, second.class);
        te.putExtra("username", et_username.getText().toString());
        te.putExtra("password", et_password.getText().toString());
        startActivity(te);
    }
}
