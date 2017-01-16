package com.example.vuduc.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuduc.apptuthien.ProjectDetailActivity;
import com.example.vuduc.apptuthien.R;
import com.example.vuduc.model.VoluntaryProject;

import java.util.List;

/**
 * Created by VuDuc on 1/14/2017.
 */

public class ListLinkProjectAdapter extends RecyclerView.Adapter<ListLinkProjectAdapter.ViewHoder> {
    private Context context;
    private List<VoluntaryProject> linkProjectList;

    public ListLinkProjectAdapter(Context context, List<VoluntaryProject> linkProjectList) {
        this.context = context;
        this.linkProjectList = linkProjectList;
    }

    @Override
    public ViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_link_project,parent,false);
        ViewHoder viewHoder = new ViewHoder(view);
        return viewHoder;
    }

    @Override
    public void onBindViewHolder(ViewHoder holder, int position) {
        VoluntaryProject voluntaryProject = linkProjectList.get(position);

        holder.txt_link_project.setText(voluntaryProject.getTitle());
        holder.txt_link_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDoiManHinh();
            }
        });
    }

    private void xuLyDoiManHinh() {
        context.startActivity(new Intent(context, ProjectDetailActivity.class));
    }

    @Override
    public int getItemCount() {
        return linkProjectList.size();
    }

    public class ViewHoder extends RecyclerView.ViewHolder {
        TextView txt_link_project;
        public ViewHoder(View itemView) {
            super(itemView);
            txt_link_project= (TextView) itemView.findViewById(R.id.txt_link_project);
        }
    }
}
