package com.lida.carcare.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.TextView;

import com.lida.carcare.R;
import com.lida.carcare.adapter.AdapterGrade;
import com.lida.carcare.bean.GradeBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 选择客户级别
 * Created by WeiQingFeng on 2016/10/28 0028.
 */

public class DialogChooseGrade extends Dialog {
    @BindView(R.id.listView)
    InnerListView listView;

    private Context context;
    private TextView textView;
    private List<GradeBean.DataBean> data;

    public DialogChooseGrade(Context context, TextView textView, List<GradeBean.DataBean> data) {
        super(context, R.style.diy_dialog);
        this.textView = textView;
        this.data = data;
        init(context);
    }

    public DialogChooseGrade(Context context, int themeResId) {
        super(context, R.style.diy_dialog);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View v = View.inflate(context, R.layout.dialog_choosegrade, null);
        this.setContentView(v);
        ButterKnife.bind(this, v);
        listView.setAdapter(new AdapterGrade(context,data));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                textView.setText(data.get(position).getCustomerLevelName());
                textView.setTag(data.get(position).getId());
                dismiss();
            }
        });
    }
}
