package com.example.jia.news.Activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jia.news.Activity.CollectionData;
import com.example.jia.news.R;

import java.util.ArrayList;

/**
 * Created by jia on 2017/5/20.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private ArrayList<CollectionData> mCollectionDataList=new ArrayList<>();
    public CollectionAdapter(ArrayList<CollectionData> mCollectionDataList){
        this.mCollectionDataList=mCollectionDataList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_collection;
        private TextView tv_collcetion;
        public ViewHolder(View itemView) {
            super(itemView);
            img_collection= (ImageView) itemView.findViewById(R.id.img_collection_result);
            tv_collcetion= (TextView) itemView.findViewById(R.id.tv_collection_result);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_result,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_collcetion.setText(mCollectionDataList.get(position).getTittle());
        Glide.with(holder.img_collection.getContext())
                .load(mCollectionDataList.get(position).getImg_url())
                .into(holder.img_collection);

    }

    @Override
    public int getItemCount() {
        return mCollectionDataList.size();
    }


}
