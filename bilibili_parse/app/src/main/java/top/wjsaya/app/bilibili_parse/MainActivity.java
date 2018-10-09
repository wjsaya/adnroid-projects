package top.wjsaya.app.bilibili_parse;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private LinearLayout liner_dynamic;
    private RadioGroup rg_dynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        liner_dynamic = new LinearLayout(this);
        liner_dynamic.setOrientation(LinearLayout.VERTICAL);

        for (int i = 1; i < 3; i++) {
            RelativeLayout rl_temp = new RelativeLayout(this);

            final TextView tv_temp = new TextView(this);
            tv_temp.setText("动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动动");
            rl_temp.addView(tv_temp);

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);		//set 四周距离
            rl_temp.setLayoutParams(params);



            final Button bu_temp = new Button(this);
            bu_temp.setText("合并");
            bu_temp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    temp("合并" + tv_temp.getText().toString());
                }
            });
            rl_temp.addView(bu_temp);


            final Button bu_temp_details = new Button(this);
            bu_temp_details.setText("详情");
            bu_temp_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    temp("显示详情" + tv_temp.getText().toString());
                }
            });
            rl_temp.addView(bu_temp_details);

            liner_dynamic.addView(rl_temp);
        }
        this.setContentView(liner_dynamic);
    }

    public void temp(String s) {
        Toast.makeText(this, s, 0).show();
    }
    /*
    public void test(View v) {
        for(int i=0; i<5; i++) {
            RadioButton rb_temp = new RadioButton(this);
            rb_temp.setText("动态嘿嘿添加" + i);
            rb_temp.setId(i);
            rg_dynamic.addView(rb_temp);
        }
        liner_dynamic.addView(rg_dynamic);
        liner_dynamic.refreshDrawableState();
    }
*/

}

