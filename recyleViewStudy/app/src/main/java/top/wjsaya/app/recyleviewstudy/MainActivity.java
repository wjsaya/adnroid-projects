package top.wjsaya.app.recyleviewstudy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rcv;
    private ArrayList<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(); //初始化布局
        initData(); //初始化数据
                    //TODO 设置recyclerView的Adapter适配器
        rcv.setAdapter();
                    //TODO 设置LayoutManager
    }

    private void initData() {
        datas = new ArrayList<>();
        for(int i=0; i<100; i++) {
            datas.add("content_" + String.valueOf(i));
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);

        rcv = (RecyclerView)findViewById(R.id.rcv);
        //创建默认的线性LayoutManager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rcv.setHasFixedSize(true);
        //创建并设置Adapter
    }
}
