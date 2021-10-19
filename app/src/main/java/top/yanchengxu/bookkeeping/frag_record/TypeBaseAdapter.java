package top.yanchengxu.bookkeeping.frag_record;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Type;
import java.util.List;

import top.yanchengxu.bookkeeping.R;
import top.yanchengxu.bookkeeping.db.TypeBean;

public class TypeBaseAdapter extends BaseAdapter {

    Context context;
    List<TypeBean> mDatas;
    int selectedPos = 0; // 默认选中位置
    public TypeBaseAdapter(Context context, List<TypeBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_record_frag_gv, parent, false);
        // 查找布局中的控件
        ImageView iv = convertView.findViewById(R.id.item_recordfrag_iv);
        TextView tv = convertView.findViewById(R.id.item_recordfrag_tv);
        TypeBean typeBean = mDatas.get(position);
        tv.setText(typeBean.getTypename());
        // 如果当前位置是选中的 则载入带颜色的图片
        if (selectedPos == position) {
            // 选中的图标
            iv.setImageResource(typeBean.getSimageId());
        } else {
            // 没选中的图标
            iv.setImageResource(typeBean.getImageId());
        }
        return convertView;
    }
}
