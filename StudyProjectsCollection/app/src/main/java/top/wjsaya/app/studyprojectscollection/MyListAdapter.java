package top.wjsaya.app.studyprojectscollection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Formatter;
import java.util.Map;

import top.wjsaya.app.studyprojectscollection.dao.MyDbDao;

public class MyListAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int total = 0;

    public MyListAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);

        MyDbDao tempDaoTotal = new MyDbDao(context);
        total = tempDaoTotal.getTotal();
    }

    static class viewHolder {
        public TextView av_title, av_size, av_context;
        public ImageView iv_cover;
    }

    static class dataHolder {
        public String av_title, av_size, av_context;
        public Bitmap iv_cover;
    }

    @Override
    public int getCount() {
        return this.total;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //myHolder存放标签
        //mydataHolder存放数据
        viewHolder myHolder = null;
        dataHolder mydataHolder = null;

        
        if (convertView == null) {
            Log.e("getView调试", position + "没有可复用的convertView，新建之");
            convertView = mLayoutInflater.inflate(R.layout.item_of_listview, null);
            myHolder = new viewHolder();
            myHolder.av_title = convertView.findViewById(R.id.tv_title);
            myHolder.av_context = convertView.findViewById(R.id.tv_details);
            myHolder.av_size = convertView.findViewById(R.id.tv_fileSize);
            myHolder.av_title = convertView.findViewById(R.id.tv_title);
            myHolder.iv_cover = convertView.findViewById(R.id.iv_cover);
            convertView.setTag(R.string.tag_widgets, myHolder);

            mydataHolder = new dataHolder();

            MyDbDao tempDao = new MyDbDao(this.mContext);
            final Map<String, String> remap = tempDao.getInfo(String.valueOf(position));

            String fSize = remap.get("size");
            try {
                fSize = android.text.format.Formatter.formatFileSize(this.mContext, Long.valueOf(fSize));
            } catch (Exception e) {
                Log.e("getView调试", position + "转换大小失败");
            }

            mydataHolder.av_title = remap.get("id") + "\t" + remap.get("avid");
            mydataHolder.av_size = fSize;
            mydataHolder.av_context = "详情" + position;
            if (tempDao.getBitmap(position).getClass().toString().equals("class android.graphics.Bitmap")) {
                mydataHolder.iv_cover = ((Bitmap) tempDao.getBitmap(position));
            }

            convertView.setTag(R.string.tag_values, mydataHolder);


        } else {
            myHolder = (viewHolder) convertView.getTag(R.string.tag_widgets);
            mydataHolder = (dataHolder) convertView.getTag(R.string.tag_values);
        }

        myHolder.av_size.setText(mydataHolder.av_size);
        myHolder.av_title.setText(mydataHolder.av_title);
        myHolder.av_context.setText(mydataHolder.av_context);

        try {
            myHolder.iv_cover.setImageBitmap(mydataHolder.iv_cover);
        } catch (Exception e) {
            myHolder.iv_cover.setImageResource(R.drawable.ic_launcher_foreground);
        }
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
