package com.lida.carcare.data;

import android.content.Context;

import com.midian.base.base.BaseListDataSource;
import com.midian.base.bean.NetResult;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ActivitySpendingDetailData extends BaseListDataSource {
    public ActivitySpendingDetailData(Context context) {
        super(context);
    }

    @Override
    protected ArrayList load(int page) throws Exception {
        ArrayList<NetResult> models = new ArrayList<>();
        for(int i=0;i<5;i++){
            models.add(new NetResult());
        }
        hasMore = false;
        return models;
    }
}
