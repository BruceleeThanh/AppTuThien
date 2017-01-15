package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuduc.apptuthien.ProjectDetailActivity;
import com.example.vuduc.apptuthien.ProjectDetailSignupActivity;
import com.example.vuduc.apptuthien.R;

/**
 * Created by VuDuc on 1/15/2017.
 */

public class ListProjectSignupAdapter extends RecyclerView.Adapter<ListProjectSignupAdapter.ViewHolder>  {
    private Context rootContext;

    public ListProjectSignupAdapter(Context rootContext) {
        this.rootContext = rootContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_shortcut, parent, false);
        ListProjectSignupAdapter.ViewHolder viewHolder = new ListProjectSignupAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cvProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootContext.startActivity(new Intent(rootContext, ProjectDetailSignupActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cvProject;

        public ViewHolder(View itemView) {
            super(itemView);
            cvProject = (CardView) itemView.findViewById(R.id.cvProject);
        }
    }
}
