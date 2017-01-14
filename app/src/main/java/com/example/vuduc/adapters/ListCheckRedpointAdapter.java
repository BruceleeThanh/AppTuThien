package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuduc.apptuthien.R;
import com.example.vuduc.apptuthien.RedpointCheckDetailActivity;
import com.example.vuduc.apptuthien.RedpointDetailActivity;

/**
 * Created by VuDuc on 1/14/2017.
 */

public class ListCheckRedpointAdapter extends RecyclerView.Adapter<ListCheckRedpointAdapter.ViewHoler> {
    private Context context;

    public ListCheckRedpointAdapter(Context context) {
        this.context=context;
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redpoint_shortcut,parent,false);
        ListCheckRedpointAdapter.ViewHoler viewHoler=new ListCheckRedpointAdapter.ViewHoler(view);
        return viewHoler;
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, int position) {
        holder.cvRedPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, RedpointCheckDetailActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        CardView cvRedPoint;
        public ViewHoler(View itemView) {
            super(itemView);
            cvRedPoint= (CardView) itemView.findViewById(R.id.cvRedPoint);
        }
    }
}
