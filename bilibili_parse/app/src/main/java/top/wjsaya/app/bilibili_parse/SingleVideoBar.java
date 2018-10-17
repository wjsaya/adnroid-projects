package top.wjsaya.app.bilibili_parse;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Dimension;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
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
    private ImageView imgAV;//视频封面图片IV
    private TextView tvTitle, tvSize, tvDetails;//视频标题TV, 视频大小TV，视频详情TV

    /**
     * 声明属性
     */
    //声明左按钮的属性
    private String textTitle;           //标题文本
    private String textSize;            //av视频大小文本
    private String textDetails;         //详情文本
    private Drawable videoImg;          //图片来源
    private int avid = 0;               //av号
    private float widgetHeight;     //控件高度
    private float titleTextSize;    //标题文本大小

    /**
     * 创建Linerlayout
     */
    private LinearLayout descLayout;
    private LinearLayout contentLayout;
    private LinearLayout titleLayouts;

    /**
     * 创建一个监听点击的接口
     */
    public interface SingleVideoBarOnClickListener {
        public void imgOnClick();               //图片被点击
        public void videoViewOnClick();         //单个视图被点击
        public void tvDetailsOnClick();         //详情被点击
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
        widgetHeight = taThis.getDimension(R.styleable.SingleVideoBar_widgetHeight, 200);       //控件高度，默认200
        titleTextSize = taThis.getDimension(R.styleable.SingleVideoBar_titleTextSize, 15);      //title字体大小，默认15
        //使用完TypedArray之后需要调用其recycle()方法,以便重用
        taThis.recycle();

        //实例化所有控件
        tvTitle = new TextView(context);
        tvSize = new TextView(context);
        tvDetails = new TextView(context);
        imgAV = new ImageView(context);


        //设置控件默认属性
        tvTitle.setText(textTitle);
        tvTitle.setTextSize(titleTextSize);
        tvTitle.setMaxLines(3);
        tvTitle.setEllipsize(TextUtils.TruncateAt.END);

        tvSize.setText(textSize);
        tvDetails.setText("详情  >");
        tvDetails.setGravity(Gravity.RIGHT);        //设置详情在layout里靠右

        imgAV.setImageDrawable(videoImg);
    }

        public void initLayout(Context context) {
        imgAV.setId(this.avid);
        imgAV.setMaxWidth(100);

        //实例化descLayout的布局属性
        descLayout =  new LinearLayout(context);
        descLayout.setOrientation(LinearLayout.HORIZONTAL);
        descLayout.addView(tvSize, new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        descLayout.addView(tvDetails, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1));
//        addView(descLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        //实例化contentLayout的布局属性
        contentLayout = new LinearLayout(context);
        contentLayout.setOrientation(LinearLayout.VERTICAL);
//        contentLayout.setBackgroundColor(Color.YELLOW);
        contentLayout.addView(tvTitle, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 3 ));
        contentLayout.addView(descLayout, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1));
//        addView(contentLayout);

        //实例化titleLayouts的布局属性
        titleLayouts = new LinearLayout(context);
        titleLayouts.setOrientation(LinearLayout.HORIZONTAL);
        titleLayouts.setPadding(10, 5, 10, 5);
        titleLayouts.addView(imgAV, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        titleLayouts.addView(contentLayout, new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        addView(titleLayouts, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int)widgetHeight));


        //回调 详情 的监听事件
        tvDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.tvDetailsOnClick();
            }
        });
        // 回调 卡片 的监听事件
        titleLayouts.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.videoViewOnClick();
            }
        });
        // 回调 卡片 的监听事件
            imgAV.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.imgOnClick();
                }
            });
    }

    /**
     * 设置单个widget的title
     * @param in    title字符串
     * @param FontSize  卡片中的av视频占用空间大小
     */
    public void setTextTitle(String in, int FontSize) {
        tvTitle.setText(in);
        this.tvTitle.setTextSize(FontSize);
    }

    public void setTextDetails(String in, int FontSize) {
        this.tvDetails.setText(in);
        this.tvDetails.setTextSize(FontSize);
    }
    public void setTextTvSize(String in, int FontSize) {
        tvSize.setText(in + "  ");
        tvSize.setTextSize(FontSize);
    }

    public void setWidgetHeight(int inHeight) {
        widgetHeight = inHeight;
    }

    public void setImgAV(Bitmap in) {
        imgAV.setImageBitmap(in);
    }

    public void setavId(int in) {
        this.avid = in;
    }
}