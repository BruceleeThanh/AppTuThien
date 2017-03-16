package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vuduc.apptuthien.R;
import com.example.vuduc.apptuthien.RedpointDetailActivity;
import com.example.vuduc.helpers.PicassoHelper;
import com.example.vuduc.libs.ApiConstants;
import com.example.vuduc.model.RedPoint;

import java.util.List;

/**
 * Created by VuDuc on 1/10/2017.
 */

public class ListRedPointAdapter extends RecyclerView.Adapter<ListRedPointAdapter.MyViewHolder> {

    private Context context;
    private List<RedPoint> lstRedPoint = null;

    public ListRedPointAdapter(Context context, List<RedPoint> lstRedPoint) {
        this.context = context;
        this.lstRedPoint = lstRedPoint;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_redpoint_shortcut, parent, false);
        ListRedPointAdapter.MyViewHolder myViewHolder = new ListRedPointAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final RedPoint redPoint = lstRedPoint.get(position);
        holder.cvRedPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RedpointDetailActivity.class);
                intent.putExtra(ApiConstants.KEY_ID, redPoint.get_id());
                context.startActivity(intent);
            }
        });
        holder.tvProjectName.setText(redPoint.getTitle());
        holder.txtProjectDescription.setText(redPoint.getContent());
        if (!(redPoint.getImages().isEmpty() || redPoint.getImages() == null))
            PicassoHelper.execPicasso(context, redPoint.getImages().get(0), holder.ivProjectImage);
    }

    @Override
    public int getItemCount() {
        return lstRedPoint == null ? 0 : lstRedPoint.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView cvRedPoint;
        ImageView ivProjectImage;
        TextView tvProjectName;
        TextView txtProjectDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            cvRedPoint = (CardView) itemView.findViewById(R.id.cvRedPoint);
            ivProjectImage = (ImageView) itemView.findViewById(R.id.ivProjectImage);
            tvProjectName = (TextView) itemView.findViewById(R.id.tvProjectName);
            txtProjectDescription = (TextView) itemView.findViewById(R.id.txtProjectDescription);
        }
    }
}
