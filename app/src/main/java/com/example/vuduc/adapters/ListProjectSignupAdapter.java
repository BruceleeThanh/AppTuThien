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

import com.example.vuduc.apptuthien.ProjectDetailActivity;
import com.example.vuduc.apptuthien.ProjectDetailSignupActivity;
import com.example.vuduc.apptuthien.R;
import com.example.vuduc.helpers.PicassoHelper;
import com.example.vuduc.model.VoluntaryProject;

import java.util.List;

/**
 * Created by VuDuc on 1/15/2017.
 */

public class ListProjectSignupAdapter extends RecyclerView.Adapter<ListProjectSignupAdapter.ViewHolder>  {
    private Context rootContext;
    private List<VoluntaryProject> lstVoluntaryProject;

    public ListProjectSignupAdapter(Context rootContext, List<VoluntaryProject> lstVoluntaryProject) {
        this.rootContext = rootContext;
        this.lstVoluntaryProject = lstVoluntaryProject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project_shortcut, parent, false);
        ListProjectSignupAdapter.ViewHolder viewHolder = new ListProjectSignupAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VoluntaryProject voluntaryProject = lstVoluntaryProject.get(position);
        holder.cvProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rootContext.startActivity(new Intent(rootContext, ProjectDetailSignupActivity.class));
            }
        });
        holder.tvProjectName.setText(voluntaryProject.getTitle());
        holder.txtProjectDescription.setText(voluntaryProject.getContent());
        if(!(voluntaryProject.getImages().isEmpty() || voluntaryProject.getImages() == null))
            PicassoHelper.execPicasso(rootContext, voluntaryProject.getImages().get(0), holder.ivProjectImage);
    }

    @Override
    public int getItemCount() {
        return lstVoluntaryProject == null ? 0:lstVoluntaryProject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CardView cvProject;
        ImageView ivProjectImage;
        TextView tvProjectName;
        TextView txtProjectDescription;


        public ViewHolder(View itemView) {
            super(itemView);
            cvProject = (CardView) itemView.findViewById(R.id.cvProject);
            ivProjectImage = (ImageView) itemView.findViewById(R.id.ivProjectImage);
            tvProjectName = (TextView) itemView.findViewById(R.id.tvProjectName);
            txtProjectDescription = (TextView) itemView.findViewById(R.id.txtProjectDescription);
        }
    }
}
