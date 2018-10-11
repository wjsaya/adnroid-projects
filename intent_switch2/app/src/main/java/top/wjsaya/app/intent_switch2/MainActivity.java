package top.wjsaya.app.intent_switch2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // 用户单击Menu键时触发
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 0, 0, "到第二个activity");
        menu.add(0, 1, 0, "退出程序");

        return super.onCreateOptionsMenu(menu);
        //  Toast.makeText(this, "菜单按键被点击", Toast.LENGTH_SHORT).show();
        //  return true;
    }


    // 当用户单击菜单项触发
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                  Toast.makeText(this, "到第二个activity", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent sec = new Intent(this, secondActivity.class);
                startActivity(sec);
                break;
            case 1:
                Toast.makeText(this, "退出程序", Toast.LENGTH_SHORT).show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.onDestroy();
                break;
        }
        return true;
    }
}

