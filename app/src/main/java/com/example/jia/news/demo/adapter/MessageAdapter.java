package com.example.jia.news.demo.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import data.InternetNewsData;
import com.example.jia.news.demo.listener.MyOnItemClickListener;
import com.example.jia.news.demo.listener.MyOnItemLongClickListener;
import com.example.jia.news.R;
import java.util.List;

/**
 * Created by jia on 2017/5/17.
 */

public class MessageAdapter extends RecyclerView.Adapter <MessageAdapter.ViewHolder> {
    private List<InternetNewsData.ResultBean.DataBean> newMsg;
    private MyOnItemClickListener itemClickListener =null;//
    private MyOnItemLongClickListener itemLongClickListener =null;
    static class ViewHolder extends RecyclerView.ViewHolder{//新闻列表内容，一个图片加新闻内容
        private ImageView newsImg;
        private TextView tvNews;
        public ViewHolder(View itemView) {
            super(itemView);
            newsImg= (ImageView) itemView.findViewById(R.id.img_news);
            tvNews= (TextView) itemView.findViewById(R.id.tv_news);
        }
    }
    public MessageAdapter(List<InternetNewsData.ResultBean.DataBean> newMsg){
        this.newMsg=newMsg;

    }
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message,parent,false);
        RecyclerView.ViewHolder viewHolder=new ViewHolder(view);

        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(final MessageAdapter.ViewHolder holder, int position) {
        holder.tvNews.setText(newMsg.get(position).getTitle() );
        Glide.with(holder.newsImg.getContext()).load(newMsg.get(position).getThumbnail_pic_s()).into(holder.newsImg);
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
        return newMsg.size();
    }
    /**
     * 列表点击事件
     *
     * @param itemClickListener
     */
    public void setOnItemClickListener(MyOnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }



    public void changDate(List<InternetNewsData.ResultBean.DataBean> newsList) {
        this.newMsg=newsList;
        notifyDataSetChanged();

    }
}
