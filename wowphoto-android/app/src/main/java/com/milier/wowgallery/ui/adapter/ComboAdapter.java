package com.milier.wowgallery.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.milier.wowgallery.R;
import com.milier.wowgallery.bean.ComboBean;
import com.milier.wowgallery.utils.MilierLog;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jly on 2016/12/23.
 */
public class ComboAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<ComboBean> mList;
    private Context mContext;
    private OnItemClickListener mItemClickListener;

    public ComboAdapter(Context mContext,ArrayList<ComboBean> list) {
        this.mContext = mContext;
        this.mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_combo,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        ComboBean comboBean = mList.get(position);
        MilierLog.i("ComboAdapter","comboBean is :"+comboBean);
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        baseViewHolder.daysTv.setText(comboBean.getTitile());
        baseViewHolder.priceTv.setText(comboBean.getPrice());

        if(position == mList.size() - 1){
            baseViewHolder.separateView.setVisibility(View.GONE);
        }else {
            baseViewHolder.separateView.setVisibility(View.VISIBLE);
        }

        //  点击事件
        if (mItemClickListener != null) {
            ((BaseViewHolder) holder).itemRl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemClickListener.onItemClick(v, position);
                }
            });
        }
    }

    public void setList(ArrayList<ComboBean> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.days_tv)
        TextView daysTv;
        @BindView(R.id.price_tv)
        TextView priceTv;
        @BindView(R.id.separate_view)
        View separateView;
        @BindView(R.id.go_iv)
        ImageView goIv;
        @BindView(R.id.item_rl)
        RelativeLayout itemRl;

        public BaseViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ArrayList<ComboBean> getList() {
        return mList;
    }
}
