package com.example.q.camara.Statistics;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.q.camara.R;

import java.util.List;

/**
 * Created by q on 27/05/15.
 */
public class StatisticsInfoAdapter extends RecyclerView.Adapter<StatisticsInfoAdapter.StatisticsInfoViewHolder> {

    List<StatisticsInfo> statisticsInfoList;
    Context context;

    public StatisticsInfoAdapter(List<StatisticsInfo> statisticsInfoList, Context context) {
        this.statisticsInfoList = statisticsInfoList;
        this.context = context;
    }

    @Override
    public StatisticsInfoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).
                inflate(R.layout.statistics_items, viewGroup, false);

        return new StatisticsInfoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StatisticsInfoViewHolder statisticsInfoViewHolder, int i) {
        statisticsInfoViewHolder.tv_1.setText(statisticsInfoList.get(i).getOpponent());
        statisticsInfoViewHolder.tv_2.setText(statisticsInfoList.get(i).getData());
        if(statisticsInfoList.get(i).getIsWon())
            statisticsInfoViewHolder.imageView.setBackgroundColor(Color.GREEN);
        else
            statisticsInfoViewHolder.imageView.setBackgroundColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        return statisticsInfoList.size();
    }

    public class StatisticsInfoViewHolder extends RecyclerView.ViewHolder{

        TextView tv_1;
        TextView tv_2;
        ImageView imageView;

        public StatisticsInfoViewHolder(View itemView) {
            super(itemView);
            tv_1 = (TextView)itemView.findViewById(R.id.tv_1);
            tv_2 = (TextView)itemView.findViewById(R.id.tv_2);
            imageView = (ImageView)itemView.findViewById(R.id.imageView);
        }
    }
}