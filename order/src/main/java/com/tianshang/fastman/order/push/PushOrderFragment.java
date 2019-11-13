package com.tianshang.fastman.order.push;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tianshang.common.base.mvp.BaseViewFm;
import com.tianshang.common.entity.app.TaskListEntity;
import com.tianshang.fastman.order.R;
import com.tianshang.fastman.order.R2;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PushOrderFragment extends BaseViewFm<PushOrderFmPresenter, PushOrderFmContract.View> {

    @BindView(R2.id.rv_order)
    RecyclerView rvOrder;
    /**
     * 1:待付款2：待认领3：待收件4：待评价5：已取消
     */
    private int type;
    private PushOrderAdapter adapter;
    public PushOrderFragment(int type) {
        this.type = type;
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.fragment_pull_order;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.VERTICAL);
        rvOrder.setLayoutManager(manager);
        
        ArrayList<String> list = new ArrayList<>();
        list.add("到8栋天天快递取件");
        list.add("到23栋中通快递取件");
        list.add("到19栋申通快递取件");
        list.add("到5栋EMS快递取件");
        list.add("到12栋圆通快递取件");
        list.add("到三食堂买大份黄焖鸡");
        list.add("到8栋天天快递取件");
        list.add("到23栋中通快递取件");
        list.add("到19栋申通快递取件");
        list.add("到5栋EMS快递取件");
        list.add("到12栋圆通快递取件");
        list.add("到三食堂买大份黄焖鸡");
        list.add("到8栋天天快递取件");
        list.add("到23栋中通快递取件");
        list.add("到19栋申通快递取件");
        list.add("到5栋EMS快递取件");
        list.add("到12栋圆通快递取件");
        list.add("到三食堂买大份黄焖鸡");
        adapter = new PushOrderAdapter(list);
        rvOrder.setAdapter(adapter);
    }


    @Override
    public PushOrderFmContract.View getContract() {
        return null;
    }

    @Override
    public PushOrderFmPresenter getPresenter() {
        return new PushOrderFmPresenter();
    }
}
