package top.wjsaya.app.bilibili_parse;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;

import top.wjsaya.app.bilibili_parse.dao.ParseDao;

public class SingleVideoBarAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int total = 0;

    public SingleVideoBarAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);

        ParseDao tempDaoTotal = new ParseDao(context);
        total = tempDaoTotal.getCount();
    }

    static class viewHolder {
        public TextView av_title, av_fileSize, av_details;
        public ImageView av_cover;
    }

    static class dataHolder {
        public String av_title, av_fileSize, av_details;
        public Bitmap av_cover;
    }


    @Override
    public int getCount() {//获取条目总数
        return this.total;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //myHolder存放标签
        //mydataHolder存放数据
        viewHolder myviewHolder = null;
        dataHolder mydataHolder = null;

        if (convertView == null) {//convertView为空，直接new一个布局，同时把当前new的布局带上myviewHolder的tag.
            Log.e("getView调试", "缓存view不存在，new TextView " + position);
            convertView = mLayoutInflater.inflate(R.layout.item_of_recylerview, null);
            myviewHolder = new viewHolder();

            myviewHolder.av_title = convertView.findViewById(R.id.tv_title);
            myviewHolder.av_fileSize = convertView.findViewById(R.id.tv_fileSize);
            myviewHolder.av_cover = convertView.findViewById(R.id.iv_cover);
            myviewHolder.av_details = convertView.findViewById(R.id.tv_details);
            convertView.setTag(R.string.tag_viewHolder, myviewHolder);

            //上面是布局viewHOlder初始化，下面是dataHolder初始化

            mydataHolder = new dataHolder();
            ParseDao tempDao = new ParseDao(this.mContext);
            final Map<String, String> remap = tempDao.getInfo(position);
            String fileSize = remap.get("totalBytes");
            try {
                fileSize = android.text.format.Formatter.formatFileSize(this.mContext, Long.valueOf(fileSize));
            } catch (Exception e) {
                Log.e("getView()调试", "转换大小失败：" + fileSize);
                e.printStackTrace();
            }
            mydataHolder.av_fileSize = fileSize;
            mydataHolder.av_title = remap.get("av_title");
            mydataHolder.av_details = "详情 >";
            if (tempDao.getBitmap(position).getClass().toString().equals("class android.graphics.Bitmap")) {
                mydataHolder.av_cover = ((Bitmap) tempDao.getBitmap(position));
            }
        } else {
            myviewHolder = (viewHolder) convertView.getTag(R.string.tag_viewHolder);
            mydataHolder = (dataHolder) convertView.getTag(R.string.tag_dataHolder);
        }

        myviewHolder.av_fileSize.setText(mydataHolder.av_fileSize);
        myviewHolder.av_title.setText(mydataHolder.av_title);
        myviewHolder.av_details.setText(mydataHolder.av_details);

        try {
            myviewHolder.av_cover.setImageBitmap(mydataHolder.av_cover);
        } catch (Exception e) {
            myviewHolder.av_cover.setImageResource(R.drawable.ic_launcher_foreground);
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
