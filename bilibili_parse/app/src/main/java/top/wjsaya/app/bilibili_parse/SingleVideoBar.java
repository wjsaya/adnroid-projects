package top.wjsaya.app.bilibili_parse;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleVideoBar extends LinearLayout {
    /**
     * 声明控件
     */
    private Button btnCombine;//合并BU
    private ImageView imgAV;//视频封面图片IV
    private TextView tvTitle, tvSize, tvDetails;//视频标题TV, 视频大小TV，视频详情TV

    /**
     * 声明属性
     */
    //声明左按钮的属性
    private String textTitle;//标题文本
    private String textSize;//大小文本
    private String textDetails;//详情文本
    private Drawable videoImg;//图片来源

    /**
     * 创建Linerlayout
     */
    private LinearLayout buttonLayout;
    private LinearLayout descLayout;
    private LinearLayout contentLayout;
    private LinearLayout titleLayouts;

    /**
     * 创建一个监听点击的接口
     */
    public interface SingleVideoBarOnClickListener {
        public void btnCombineOnClick();//合并按钮被点击
        public void tvDetailsOnClick();//详情被点击
    }

    /**
     * 声明点击监听
     */
    private SingleVideoBarOnClickListener listener;

    /**
     * 向外提供一个设置监听的方法
     */
    public void setSingleVideoBarOnClickListener(SingleVideoBarOnClickListener listener) {
        this.listener = listener;
    }

    /**
     * 重写构造方法
     */

    public SingleVideoBar(Context context, AttributeSet attr) {
        //super(context, attr);
        this(context);
    }


    public SingleVideoBar(Context context) {
        super(context);
        /*
    public SingleVideoBar(Context context) {
        super(context);
        */
        /**
         * 用TypedArray可以获取用户在xml中声明的此控件的所有属性,以键值对存储,
         * K:资源文件(例 R.styleable.topBar_leftText)
         * V:属性值
         */
        TypedArray taThis = context.obtainStyledAttributes(R.styleable.SingleVideoBar);

        //为左按钮的属性赋值
        textTitle = taThis.getString(R.styleable.SingleVideoBar_videoname);
        videoImg = taThis.getDrawable(R.styleable.SingleVideoBar_videoImg);
        textSize = taThis.getString(R.styleable.SingleVideoBar_videoSize);
        //使用完TypedArray之后需要调用其recycle()方法,以便重用
        taThis.recycle();

        //实例化所有控件
        btnCombine = new Button(context);
        tvTitle = new TextView(context);
        tvSize = new TextView(context);
        tvDetails = new TextView(context);
        imgAV = new ImageView(context);

        //设置控件属性
        btnCombine.setText("合并");
        tvTitle.setText(textTitle);
        tvSize.setText(textSize);
        tvDetails.setText("详情  >");
        imgAV.setImageDrawable(videoImg);

        //实例化最右边的两个按钮(btnLayoutParams)的布局属性
        buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        //将两个按钮添加到本自定义控件中
        buttonLayout.addView(btnCombine, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
        buttonLayout.addView(tvDetails, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1));
//        addView(buttonLayout);

        //实例化descLayout的布局属性
        descLayout = new LinearLayout(context);
        descLayout.setOrientation(LinearLayout.VERTICAL);
        descLayout.addView(tvTitle, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 3 ));
        descLayout.addView(tvSize, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
//        addView(descLayout);

        //实例化contentLayout的布局属性
        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.HORIZONTAL);
        contentLayout.addView(descLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 4));
        contentLayout.addView(buttonLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
//        addView(contentLayout);

        //实例化titleLayouts的布局属性
        titleLayouts = new LinearLayout(context);
        titleLayouts.setOrientation(LinearLayout.HORIZONTAL);
        titleLayouts.addView(imgAV, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        titleLayouts.addView(contentLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 3));
        addView(titleLayouts, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200, 3));


        //回调左按钮的监听事件
        btnCombine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.btnCombineOnClick();
            }
        });

        //回调右按钮的监听事件
        tvDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.tvDetailsOnClick();
            }
        });
    }

    public void setTextTitle(String in, int FontSize) {
        tvTitle.setText(in);
        tvTitle.setTextSize(FontSize);
    }

    public void setTextTvSize(String in) {
        tvSize.setText(in);
    }

    public void setTextBtnCombine(String in) {
        btnCombine.setText(in);
    }
/*
    public void setDrableImg(String in) {
        Drawable temp;
        TypedArray taThis = MainActivity.obtainStyledAttributes(R.styleable.SingleVideoBar);

        videoImg = taThis.getDrawable(in);

        imgAV.setImageDrawable(in);
    }
*/




}