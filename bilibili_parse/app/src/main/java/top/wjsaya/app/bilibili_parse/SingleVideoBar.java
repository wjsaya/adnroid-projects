package top.wjsaya.app.bilibili_parse;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by caobotao on 15/12/9.
 */
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
     * 声明布局属性
     */
    private LayoutParams buttonLayoutParams;
    private LayoutParams descLayoutParams;
    private LayoutParams contentLayoutParams;
    private LayoutParams titleLayoutParams;

    //创建layout
    private LinearLayout buttonLayout;
    private LinearLayout descLayout;
    private LinearLayout contentLayout;
    private LinearLayout titleLayouts;


    //声明左右按钮点击监听
    private SingleVideoBarOnClickListener listener;

    //创建一个监听左右按钮点击的接口
    public interface SingleVideoBarOnClickListener {
        public void btnCombineOnClick();//合并按钮被点击

        public void tvDetailsOnClick();//详情被点击
    }

    //向外提供一个设置监听的方法
    public void setSingleVideoBarOnClickListener(SingleVideoBarOnClickListener listener) {
        this.listener = listener;
    }

    //重写构造方法
    public SingleVideoBar(Context context, AttributeSet attr) {
        super(context, attr);
        /*
    public SingleVideoBar(Context context) {
        super(context);
        */
        /**
         * 用TypedArray可以获取用户在xml中声明的此控件的所有属性,以键值对存储,
         * K:资源文件(例 R.styleable.topBar_leftText)
         * V:属性值
         */
        TypedArray ta = context.obtainStyledAttributes(attr, R.styleable.SingleVideoBar);

        //为左按钮的属性赋值
        textTitle = ta.getString(R.styleable.SingleVideoBar_videoname);
        videoImg = ta.getDrawable(R.styleable.SingleVideoBar_videoImg);
        textSize = ta.getString(R.styleable.SingleVideoBar_videoSize);
        //使用完TypedArray之后需要调用其recycle()方法,以便重用
        ta.recycle();

        //实例化三个控件
        btnCombine = new Button(context);
        tvTitle = new Button(context);
        tvSize = new Button(context);
        tvDetails = new TextView(context);

        //设置左按钮的属性
        btnCombine.setText("合并");
        tvTitle.setText(textTitle);
        tvSize.setText(textSize);
        tvDetails.setText("详情  >");


        //实例化最右边的两个按钮(btnLayoutParams)的布局属性
        /*
        buttonLayout = new LinearLayout(context);
        buttonLayout.setOrientation(LinearLayout.VERTICAL);
        buttonLayout.setWeightSum(1);
        //将两个按钮添加到本自定义控件中
        buttonLayout.addView(btnCombine, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        buttonLayout.addView(tvDetails, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT));
        addView(buttonLayout);
*/
/*
        private LayoutParams contentLayoutParams;
        private LayoutParams titleLayoutParams;
*/
        descLayout = new LinearLayout(context);
        descLayout.setOrientation(LinearLayout.VERTICAL);
        descLayout.setWeightSum(4);
        descLayoutParams = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT);
        descLayout.addView(tvTitle, descLayoutParams);
        descLayout.addView(tvSize, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0));
      //  descLayout.addView(tvSize, descLayoutParams);
        addView(buttonLayout);
        addView(descLayout);

        contentLayout = new LinearLayout(context);
        titleLayouts = new LinearLayout(context);


/*
        //实例化左按钮的布局属性
        leftBtnLayoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置左按钮靠左显示
        leftBtnLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        //将左按钮添加到本自定义控件中
        addView(leftBtn,leftBtnLayoutParams);

        addView(btnCombine, ivImgLayoutParams);


        //回调左按钮的监听事件
        leftBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.leftBtnOnClick();
            }
        });

        //回调右按钮的监听事件
        rightBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.rightBtnOnClick();
            }
        });
    }
    //当然,我们还可以添加其他控制此控件的方法,如设置左按钮是否可见等等,大家可根据自己的需求进行扩展
    public void setLeftBtnVisible(boolean isVisible){
        leftBtn.setVisibility(isVisible ? VISIBLE : INVISIBLE);
        */
    }

}