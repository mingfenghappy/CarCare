package com.lida.carcare.widget.logisticsview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.lida.carcare.R;
import com.lida.carcare.adapter.AdapterLogistics;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义节点Listview
 * Created by xkr on 2017/8/16.
 */

public class LogisticsNodeListView extends LinearLayout {
    private LogisticsNodeLineView nodeLineView;
    private LogisticsListView listView;
    private BaseAdapter adapter;
    private List<Float> nodeRadiusDistances;

    public LogisticsNodeListView(Context context) {
        this(context, null);
    }

    public LogisticsNodeListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("NewApi")
    public LogisticsNodeListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
        LayoutInflater.from(context).inflate(
                R.layout.logistics_node_listview_layout, this);
        this.nodeLineView = (LogisticsNodeLineView) this
                .findViewById(R.id.nodeLineView);
        this.listView = (LogisticsListView) this.findViewById(R.id.listView);
        this.listView.setEnabled(false);
        this.nodeRadiusDistances = new ArrayList<Float>();
    }

    public void setTopNodePaintStrokeWidth(float topNodePaintStrokeWidth) {
        if (this.nodeLineView != null) {
            this.nodeLineView
                    .setTopNodePaintStrokeWidth(topNodePaintStrokeWidth);
        }
        invalidate();
    }

    public BaseAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
        this.listView.setAdapter(adapter);
        this.nodeRadiusDistances = ((AdapterLogistics) adapter).getNodeRadiusDistances();
        //最后一个用于添加listview底部分割线高度
        this.nodeRadiusDistances.set(this.nodeRadiusDistances.size() - 1, (float)this.listView.getDividerHeight());
        this.setNodeCount(adapter.getCount());
        this.setNodeRadiusDistances(nodeRadiusDistances);
        invalidate();
    }

    private void setNodeRadiusDistances(List<Float> nodeRadiusDistances) {
        this.nodeLineView.setNodeRadiusDistances(nodeRadiusDistances);
        invalidate();
    }

    private void setNodeCount(int nodeCount) {
        this.nodeLineView.setNodeCount(nodeCount);
        invalidate();
    }

    public void setItemPaddingTop(int itemPaddingTop) {
        this.nodeLineView.setItemPaddingTop(itemPaddingTop);
        invalidate();
    }

    public void addHeaderView(View view) {
        this.setOrientation(LinearLayout.VERTICAL);
        this.addView(view, 0);
        invalidate();
    }
}
