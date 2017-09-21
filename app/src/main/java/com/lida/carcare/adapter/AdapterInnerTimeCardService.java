package com.lida.carcare.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lida.carcare.R;
import com.lida.carcare.bean.TimeCardListBean;
import com.midian.base.bean.NetResult;
import com.midian.base.view.BaseTpl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/5.
 */

public class AdapterInnerTimeCardService extends BaseAdapter {

    List<TimeCardListBean.DataBean.OnceCardProjectListsBean> list ;
    Context context;

    public AdapterInnerTimeCardService(List<TimeCardListBean.DataBean.OnceCardProjectListsBean> list , Context context ){
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public TimeCardListBean.DataBean.OnceCardProjectListsBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_inner_list_service, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

            TimeCardListBean.DataBean.OnceCardProjectListsBean timeBean = (TimeCardListBean.DataBean.OnceCardProjectListsBean)list.get(position);
            viewHolder.projectName.setText(timeBean.getProjectName());
            viewHolder.projectCount.setText(timeBean.getProjectCount());

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.projectName)
        TextView projectName;
        @BindView(R.id.projectCount)
        TextView projectCount;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
