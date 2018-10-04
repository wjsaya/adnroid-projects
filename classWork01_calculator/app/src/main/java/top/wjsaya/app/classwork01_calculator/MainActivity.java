package top.wjsaya.app.classwork01_calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements TextView.OnClickListener{
    private TextView tv_result;
    private Button bu_clear;
    private Button bu_1;
    private Button bu_2;
    private Button bu_3;
    private Button bu_4;
    private Button bu_5;
    private Button bu_6;
    private Button bu_7;
    private Button bu_8;
    private Button bu_9;
    private Button bu_0;
    private Button bu_chu;
    private Button bu_cheng;
    private Button bu_jia;
    private Button bu_jian;
    private Button bu_qiuzhi;
    private Button bu_point;
    private Button bu_square;
    private Button bu_sqrt;


    private String temp = "";
    private String num1="", num2="", sw="";


    private caculator test = new caculator();


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_result = (TextView) findViewById(R.id.tv_result);
        bu_clear = (Button) findViewById(R.id.bu_clear);
        bu_1 = (Button) findViewById(R.id.bu_1);
        bu_2 = (Button) findViewById(R.id.bu_2);
        bu_3 = (Button) findViewById(R.id.bu_3);
        bu_4 = (Button) findViewById(R.id.bu_4);
        bu_5 = (Button) findViewById(R.id.bu_5);
        bu_6 = (Button) findViewById(R.id.bu_6);
        bu_7 = (Button) findViewById(R.id.bu_7);
        bu_8 = (Button) findViewById(R.id.bu_8);
        bu_9 = (Button) findViewById(R.id.bu_9);
        bu_0 = (Button) findViewById(R.id.bu_0);
        bu_chu = (Button) findViewById(R.id.bu_chu);
        bu_cheng = (Button) findViewById(R.id.bu_cheng);
        bu_jia = (Button) findViewById(R.id.bu_jia);
        bu_jian = (Button) findViewById(R.id.bu_jian);
        bu_qiuzhi = (Button) findViewById(R.id.bu_qiuzhi);
        bu_point = (Button) findViewById(R.id.bu_point);
        bu_square = (Button) findViewById(R.id.bu_square);
        bu_sqrt = (Button) findViewById(R.id.bu_sqrt);



        bu_clear.setOnClickListener(this);
        bu_1.setOnClickListener(this);
        bu_2.setOnClickListener(this);
        bu_3.setOnClickListener(this);
        bu_4.setOnClickListener(this);
        bu_5.setOnClickListener(this);
        bu_6.setOnClickListener(this);
        bu_7.setOnClickListener(this);
        bu_8.setOnClickListener(this);
        bu_9.setOnClickListener(this);
        bu_0.setOnClickListener(this);
        bu_chu.setOnClickListener(this);
        bu_cheng.setOnClickListener(this);
        bu_jia.setOnClickListener(this);
        bu_jian.setOnClickListener(this);
        bu_qiuzhi.setOnClickListener(this);
        bu_point.setOnClickListener(this);
        bu_square.setOnClickListener(this);
        bu_sqrt.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bu_0:
                temp += "0";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_1:
                temp += "1";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_2:
                temp += "2";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_3:
                temp += "3";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_4:
                temp += "4";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_5:
                temp += "5";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_6:
                temp += "6";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_7:
                temp += "7";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_8:
                temp += "8";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_9:
                temp += "9";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_point:
                if(TextUtils.isEmpty(temp)) {
                    temp += "0.";
                    test.pushnum(temp);
                    tv_result.setText(temp);
                    break;
                }
                temp += ".";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_jia:
                test.setsw("+");
                temp += "+";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_jian:
                test.setsw("-");
                temp += "-";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_cheng:
                test.setsw("*");
                temp += "*";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;
            case R.id.bu_chu:
                test.setsw("/");
                temp += "/";
                test.pushnum(temp);
                tv_result.setText(temp);
                break;


            case R.id.bu_square:
                //平方
                test.setsw("&");
                temp = test.getResult();
                tv_result.setText(temp);
                temp = "";
                test.init();
                break;

            case R.id.bu_sqrt:
                //开方
                test.setsw("|");
                temp = test.getResult();
                tv_result.setText(temp);
                temp = "";
                test.init();

                break;
            case R.id.bu_qiuzhi:
                temp = test.getResult();
                tv_result.setText(temp);
                temp = "";
                test.init();
                break;
            case R.id.bu_clear:
                temp = "";
                test.init();
                tv_result.setText(temp);
                break;
            default:
                break;
        }
    }
}

class caculator {
    private double num1 = 0.0;
    private double num2 = 0.0;
    private String sw = "";

    public void init() {
        num1 = 0.0;
        num2 = 0.0;
        sw = "";
    }

    public void pushnum(String in) {
        try {
            //直接取double值，取到了则代表输入为空，正在输入被加数。
            this.num1 = Double.valueOf(in);
            Log.d("pushnum直接取num1为：\t", String.valueOf(this.num1));
        } catch (Exception e) {
            //出错？那么应该是有+-*/之一，那么用sw分隔。
            try {
                this.num1 = Double.valueOf(in.split("\\" + sw)[0]);
                this.num2 = Double.valueOf(in.split("\\" + sw)[1]);
                Log.d("pushnum取到num1为：\t", String.valueOf(this.num1));
                Log.d("pushnum取到num2为：\t", String.valueOf(this.num2));
            }
            catch (Exception f) {
                this.num1 = 0;
                this.num2 = 0;
            }
        }
    }

    public Double getnum2() {
        return this.num2;
    }

    public void setsw(String in) {
        this.sw = in;
    }

    public String getResult() {
        double re = 0;
        if(this.sw.equals("")) return "请输入计算表达式";
        switch(this.sw) {
            case "+":
                re = this.num1 + this.num2;
                break;
            case "-":
                re = this.num1 - this.num2;
                break;
            case "*":
                re = this.num1 * this.num2;
                break;
            case "/":
                re = this.num1 / this.num2;
                break;
            case "&":
                re = this.num1 * this.num1;
                break;
            case "|":
                re = Math.sqrt(this.num1);
                break;
        }
        return String.valueOf(re);
    }
}
