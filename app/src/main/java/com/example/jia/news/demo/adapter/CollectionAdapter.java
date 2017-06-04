package com.example.jia.news.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import data.CollectionData;
import com.example.jia.news.R;
import com.example.jia.news.demo.listener.MyOnItemClickListener;
import com.example.jia.news.demo.listener.MyOnItemLongClickListener;

import java.util.ArrayList;

/**
 * Created by jia on 2017/5/20.
 */

public class CollectionAdapter extends RecyclerView.Adapter<CollectionAdapter.ViewHolder> {
    private ArrayList<CollectionData> mCollectionDataList=new ArrayList<>();
    private MyOnItemClickListener itemClickListener;//实现访问该页新闻
    private MyOnItemLongClickListener itemLongClickListener;//实现删除该收藏
    public CollectionAdapter(ArrayList<CollectionData> mCollectionDataList){
        this.mCollectionDataList=mCollectionDataList;
    }

    public void dataChanged(ArrayList<CollectionData> mCollection) {
        mCollectionDataList=mCollection;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_collection;
        private TextView tv_collection;
        public ViewHolder(View itemView) {
            super(itemView);
            img_collection= (ImageView) itemView.findViewById(R.id.img_collection_result);
            tv_collection= (TextView) itemView.findViewById(R.id.tv_collection_result);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.collection_result,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tv_collection.setText(mCollectionDataList.get(position).getTittle());
        Glide.with(holder.img_collection.getContext())
                .load(mCollectionDataList.get(position).getImg_url())
                .into(holder.img_collection);
         /*自定义item的点击事件不为null，设置监听事件*/
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClickListener(holder.itemView, holder.getLayoutPosition());
                }
            });
        }

        /*自定义item的长按事件不为null，设置监听事件*/
        if (itemLongClickListener != null) {

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.OnItemLongClickListener(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mCollectionDataList.size();
    }

    /**
     * 列表点击事件
     *
     * @param itemClickListener
     *
     */
    public void setOnItemClickListener(MyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    /**
     * 列表长按事件
     *
     * @param itemLongClickListener
     */
    public void setOnItemLongClickListener(MyOnItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }


}
