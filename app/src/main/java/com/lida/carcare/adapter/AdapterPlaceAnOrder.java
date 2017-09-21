package com.lida.carcare.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lida.carcare.R;
import com.lida.carcare.activity.ActivityChooseOrderGood;
import com.lida.carcare.bean.OrderClassificationBean;
import com.midian.base.app.AppContext;
import com.midian.base.app.Constant;
import com.midian.base.util.UIHelper;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 订货
 * Created by xkr on 2017/7/24.
 */

public class AdapterPlaceAnOrder extends BaseAdapter {
    private Context context;
    private List<OrderClassificationBean.DataBean> mData = null;
    private AppContext ac;

    public AdapterPlaceAnOrder(Context context,List<OrderClassificationBean.DataBean> mData)
    {
        this.context = context;
        ac = (AppContext) context.getApplicationContext();
        this.mData = mData;
    }

    @Override
    public int getCount()
    {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_activity_place_an_order, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final OrderClassificationBean.DataBean bean = mData.get(position);
        ac.setImage(viewHolder.iv, Constant.BASE+bean.getClassPath());
        viewHolder.tv.setText(bean.getName()==null?"":bean.getName());
        viewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id",bean.getId());
                UIHelper.jump(context,ActivityChooseOrderGood.class,bundle);
            }
        });
        return convertView;
    }

    static class ViewHolder
    {
        @BindView(R.id.button)
        LinearLayout button;
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv)
        TextView tv;

        ViewHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }


}
