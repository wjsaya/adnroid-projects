package top.wjsaya.app.intent_switch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class second extends AppCompatActivity {
    private TextView tv_username, tv_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        tv_username = findViewById(R.id.tv_username);
        tv_password = findViewById(R.id.tv_password);

        tv_username.setText(this.getIntent().getStringExtra("username"));
        tv_password.setText(this.getIntent().getStringExtra("password"));
    }

    public void back(View v) {
        Intent in = new Intent(this, MainActivity.class);
        startActivity(in);
    }
}
