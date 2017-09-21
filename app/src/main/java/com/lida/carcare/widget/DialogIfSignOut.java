package com.lida.carcare.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.lida.carcare.R;
import com.lida.carcare.activity.MainActivity;
import com.lida.carcare.login.ActivityLogin;
import com.midian.base.app.AppContext;
import com.midian.base.app.AppManager;
import com.midian.base.util.UIHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 退出登录？
 * Created by Administrator on 2016/10/28 0028.
 */

public class DialogIfSignOut extends Dialog {

    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.tv)
    TextView tv;

    private Context context;
    private Activity activity;

    public DialogIfSignOut(Context context) {
        super(context, R.style.diy_dialog);
        init(context);
    }

    public DialogIfSignOut(Context context, int themeResId) {
        super(context, R.style.diy_dialog);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        this.activity = (Activity) context;
        Window w = this.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.gravity = Gravity.CENTER;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        w.setAttributes(lp);
        this.setCanceledOnTouchOutside(true);
        View v = View.inflate(context, R.layout.dialog_ifsignout, null);
        this.setContentView(v);
        ButterKnife.bind(this, v);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_cancel:
                this.dismiss();
                break;
            case R.id.btn_ok:
                dismiss();
                AppManager.getAppManager().finishActivity(MainActivity.class);
                ((AppContext)((Activity) context).getApplication()).clearUserInfo();
                UIHelper.jump((Activity) context,ActivityLogin.class);
                ((Activity) context).finish();
                break;
        }
    }
}
