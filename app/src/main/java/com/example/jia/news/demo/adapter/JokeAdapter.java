package com.example.jia.news.demo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jia.news.R;

import java.util.List;

import data.JokeData;

/**
 * Created by jia on 2017/5/31.
 */

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {
    private List<JokeData.ResultBean.DataBean> listJoke;

    public JokeAdapter(List<JokeData.ResultBean.DataBean> listJoke){
        this.listJoke=listJoke;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_show_time;
        private TextView tv_show_joke;
        public ViewHolder(View itemView) {
            super(itemView);
          tv_show_time= (TextView) itemView.findViewById(R.id.tv_showJokeTime);
          tv_show_joke= (TextView) itemView.findViewById(R.id.tv_showJokeText);
        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.joke_result_show,parent,false);
        RecyclerView.ViewHolder holder=new ViewHolder(view);
        return (ViewHolder) holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_show_time.setText(listJoke.get(position).getUpdatetime());
        holder.tv_show_joke.setText(listJoke.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return listJoke.size();
    }
    public void setDataChange(List<JokeData.ResultBean.DataBean> listJoke){
        this.listJoke=listJoke;
        notifyDataSetChanged();
    }
}
