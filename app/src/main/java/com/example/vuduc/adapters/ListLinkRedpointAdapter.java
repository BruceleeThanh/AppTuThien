package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuduc.apptuthien.R;
import com.example.vuduc.apptuthien.RedpointDetailActivity;
import com.example.vuduc.model.RedPoint;

import java.util.List;

/**
 * Created by VuDuc on 1/15/2017.
 */

public class ListLinkRedpointAdapter extends RecyclerView.Adapter<ListLinkRedpointAdapter.ViewHoder>{
    private Context context;
    private List<RedPoint> linkRedpointList;

    public ListLinkRedpointAdapter(Context context, List<RedPoint> linkRedpointList) {
        this.context = context;
        this.linkRedpointList = linkRedpointList;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_link_project, parent, false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        RedPoint redPoint = linkRedpointList.get(position);

        holder.txt_link_redpoint.setText(redPoint.getTitle());
        holder.txt_link_redpoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDoiManHinh();
            }
        });
    }

    private void xuLyDoiManHinh() {
        context.startActivity(new Intent(context, RedpointDetailActivity.class));
    }

    @Override
    public int getItemCount() {
        return linkRedpointList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder{
        TextView txt_link_redpoint;
        public ViewHoder(View itemView) {
            super(itemView);
            txt_link_redpoint= (TextView) itemView.findViewById(R.id.txt_link_project);
        }
    }
}
