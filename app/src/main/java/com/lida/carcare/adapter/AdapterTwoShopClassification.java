package com.lida.carcare.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.lida.carcare.R;
import com.lida.carcare.activity.ActivityChooseGoodCommen;
import com.lida.carcare.app.AppUtil;
import com.lida.carcare.bean.GoodClassABean;
import com.lida.carcare.bean.GoodListBean;
import com.lida.carcare.widget.BaseApiCallback;
import com.lida.carcare.widget.ItemCanClick2;
import com.midian.base.app.AppContext;
import com.midian.base.bean.NetResult;
import com.midian.base.util.UIHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/6.
 */

public class AdapterTwoShopClassification extends BaseExpandableListAdapter {

    private Activity context;
    private List<GoodClassABean.DataBean> parentData;
    private ExpandableListView listView;
    private Map<String, List<GoodClassABean.DataBean>> data = new HashMap<>();
    private AppContext ac;

    public AdapterTwoShopClassification(Activity context, ExpandableListView listView, List<GoodClassABean.DataBean> parent, Map<String, List<GoodClassABean.DataBean>> data)
    {
        this.context = context;
        this.parentData = parent;
        this.listView = listView;
        this.data = data;
        ac = (AppContext) context.getApplication();
    }

    @Override
    public int getGroupCount()
    {
        return parentData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (data.get(parentData.get(groupPosition).getName()) == null) {
            return 0;
        } else {
            return data.get(parentData.get(groupPosition).getName()).size();
        }
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return data.get(parentData.get(groupPosition));
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (data.get(parentData.get(groupPosition).getName()) != null) {
            return data.get(parentData.get(groupPosition).getName()).get(childPosition);
        } else {
            return null;
        }
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        AdapterGoodsClass.ParentHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_parent, null);
            holder = new AdapterGoodsClass.ParentHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AdapterGoodsClass.ParentHolder) convertView.getTag();
        }
        holder.itemCanClick.setTitle(parentData.get(groupPosition).getName());
        if (data.get(parentData.get(groupPosition).getName()) != null) {
            holder.itemCanClick.setCount(data.get(parentData.get(groupPosition).getName()).size() + "");
        } else {
            holder.itemCanClick.setCount("0");
        }
        if (data.get(parentData.get(groupPosition).getName()) == null) {
            AppUtil.getCarApiClient(ac).getProductCategoryByCode(parentData.get(groupPosition).getCode(), ac.shopId, new BaseApiCallback() {
                @Override
                public void onApiSuccess(NetResult res, String tag) {
                    super.onApiSuccess(res, tag);
                    if (res.isOK()) {
                        GoodClassABean bean = (GoodClassABean) res;
                        if (bean.getData() != null) {
                            List<GoodClassABean.DataBean> child = new ArrayList<>();
                            for (int i = 0; i < bean.getData().size(); i++) {
                                child.add(bean.getData().get(i));
                            }
                            data.put(parentData.get(groupPosition).getName(), child);
                            notifyDataSetChanged();
                        }
                    }
                }
            });//获取二级分类
        }
        holder.itemCanClick.setOnItemClick(new ItemCanClick2.OnItemClick() {
            @Override
            public void onItemOpen(ItemCanClick2 itemCanClick) {
                listView.expandGroup(groupPosition);
            }

            @Override
            public void onItemClose(ItemCanClick2 itemCanClick) {
                listView.collapseGroup(groupPosition);
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        AdapterGoodsClass.ChildHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_child, null);
            holder = new AdapterGoodsClass.ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AdapterGoodsClass.ChildHolder) convertView.getTag();
        }
        if (data.get(parentData.get(groupPosition).getName()) != null) {
            holder.tvName.setText(data.get(parentData.get(groupPosition).getName()).get(childPosition).getName());
            holder.tvCount.setText(data.get(parentData.get(groupPosition).getName()).get(childPosition).getCount());
        }
        holder.tvCount.setVisibility(View.GONE);
        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//跳转商品列表
                //
                /*Bundle bundle = new Bundle();
                bundle.putString("code",data.get(parentData.get(groupPosition).getName()).get(childPosition).getCode());
                UIHelper.jump(context, ActivityChooseGoodCommen.class,bundle);*/
                UIHelper.t(context,data.get(parentData.get(groupPosition).getName()).get(childPosition).getName());
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return false;
    }

    public interface OnItemClickListener {
        void onClick(GoodListBean.DataBean.JfomGoodsBean bean);
    }

    static class ParentHolder {
        @BindView(R.id.itemCanClick)
        ItemCanClick2 itemCanClick;

        ParentHolder(View view)
        {
            ButterKnife.bind(this, view);
        }
    }

    static class ChildHolder {
        @BindView(R.id.tvName)
        TextView tvName;
        @BindView(R.id.tvCount)
        TextView tvCount;
        @BindView(R.id.llItem)
        LinearLayout llItem;

        ChildHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
