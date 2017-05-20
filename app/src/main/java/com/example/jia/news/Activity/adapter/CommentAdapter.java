package com.example.jia.news.Activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jia.news.Activity.CommentsData;
import com.example.jia.news.R;

import java.util.ArrayList;


/**
 * Created by jia on 2017/5/20.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {

    private ArrayList<CommentsData> comments=new ArrayList<>();

    public CommentAdapter(ArrayList<CommentsData> comments){
        this.comments=comments;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tittle_comments_result;
        TextView tv_comments_time;
        TextView tv_comments_result;
            public ViewHolder(View itemView) {
                super(itemView);
               tv_tittle_comments_result= (TextView) itemView.findViewById(R.id.tv_tittle_comments_result);
                tv_comments_time= (TextView) itemView.findViewById(R.id.tv_comments_time);
                tv_comments_result= (TextView) itemView.findViewById(R.id.tv_comments_result);
            }
        }

    @Override
    public CommentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_result,parent,false);
        RecyclerView.ViewHolder viewHolder = new ViewHolder(view);
        return (ViewHolder) viewHolder;
    }

    @Override
    public void onBindViewHolder(CommentAdapter.ViewHolder holder, int position) {
        holder.tv_tittle_comments_result.setText(comments.get(position).getTittle());
        holder.tv_comments_time.setText(comments.get(position).getTime());
        holder.tv_comments_result.setText(comments.get(position).getCommentsResult());
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

}
