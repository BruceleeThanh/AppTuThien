package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuduc.apptuthien.ProjectDetailHistoryActivity;
import com.example.vuduc.apptuthien.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by VuDuc on 1/16/2017.
 */

public class ListEditHistoryAdapter extends RecyclerView.Adapter<ListEditHistoryAdapter.ViewHolder> {

    private Context rootContext;

    public ListEditHistoryAdapter(Context rootContext) {
        this.rootContext = rootContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_edit_history, parent, false);
        ListEditHistoryAdapter.ViewHolder viewHolder = new ListEditHistoryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_history_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootContext.startActivity(new Intent(rootContext, ProjectDetailHistoryActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_history_time)
        TextView tv_history_time;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
