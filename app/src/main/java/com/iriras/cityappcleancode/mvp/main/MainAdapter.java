package com.iriras.cityappcleancode.mvp.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.iriras.cityappcleancode.R;
import com.iriras.cityappcleancode.api.model.CityListDataModel;

import java.util.List;

/**
 * Created by irfan on 6/18/17.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private List<CityListDataModel> mData;
    private Context mContext;

    public MainAdapter(Context mContext, List<CityListDataModel> mData, OnItemClickListener mOnItemClickListener) {
        this.mContext = mContext;
        this.mData = mData;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_row, null);
        mView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("Kota ", mData.get(position).getName() + " Img " + mData.get(position).getBackground());
        holder.mTtxCity.setText(mData.get(position).getName());
        holder.mTxtHotel.setText(mData.get(position).getDescription());
        holder.onitemClick(mData.get(position), mOnItemClickListener);
        Glide.with(mContext)
                .load(mData.get(position).getBackground())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true)
                .into(holder.mImgBackground);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.txt_main_city)
        TextView mTtxCity;
//        @BindView(R.id.txt_main_hotels)
        TextView mTxtHotel;
//        @BindView(R.id.img_main)
        ImageView mImgBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            mTtxCity = itemView.findViewById(R.id.txt_main_city);
            mTxtHotel = itemView.findViewById(R.id.txt_main_hotels);
            mImgBackground = itemView.findViewById(R.id.img_main);
        }

        public void onitemClick(final CityListDataModel cityListDataModel, final OnItemClickListener onItemClickListener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(cityListDataModel);
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onClick(CityListDataModel cityListDataModel);
    }
}
