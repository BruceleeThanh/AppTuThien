package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuduc.apptuthien.R;
import com.example.vuduc.apptuthien.RedpointDetailActivity;

/**
 * Created by VuDuc on 1/10/2017.
 */

public class ListRedPointAdapter extends RecyclerView.Adapter<ListRedPointAdapter.MyViewHolder> {

    private Context context;

    public ListRedPointAdapter(Context context) {
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redpoint_shortcut,parent,false);
        ListRedPointAdapter.MyViewHolder myViewHolder=new ListRedPointAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.cvRedPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, RedpointDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView cvRedPoint;
        public MyViewHolder(View itemView) {
            super(itemView);
            cvRedPoint= (CardView) itemView.findViewById(R.id.cvRedPoint);
        }
    }
}
