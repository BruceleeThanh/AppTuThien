package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vuduc.apptuthien.ProjectDetailActivity;
import com.example.vuduc.apptuthien.ProjectDetailMeActivity;
import com.example.vuduc.apptuthien.R;


public class ListProjectMeAdapter extends RecyclerView.Adapter<ListProjectMeAdapter.ViewHolder>{

    private Context rootContext;

    public ListProjectMeAdapter(Context rootContext) {
        this.rootContext = rootContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_shortcut, parent, false);
        ListProjectMeAdapter.ViewHolder viewHolder = new ListProjectMeAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.cvProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootContext.startActivity(new Intent(rootContext, ProjectDetailMeActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cvProject;

        public ViewHolder(View itemView) {
            super(itemView);
            cvProject = (CardView) itemView.findViewById(R.id.cvProject);
        }
    }
}
