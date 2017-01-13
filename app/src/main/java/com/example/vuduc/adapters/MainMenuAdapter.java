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

import com.example.vuduc.apptuthien.ListProjectActivity;
import com.example.vuduc.apptuthien.ProjectDetailActivity;
import com.example.vuduc.apptuthien.R;
import com.example.vuduc.model.HomeCard;

import java.util.List;


public class MainMenuAdapter extends RecyclerView.Adapter<MainMenuAdapter.ViewHolder>{

    private Context rootContext;
    private List<HomeCard> homeCardLists;

    public MainMenuAdapter(Context context, List<HomeCard> homeCardLists){
        this.rootContext = context;
        this.homeCardLists = homeCardLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final HomeCard homeCard = homeCardLists.get(position);

        holder.tvMenuTitle.setText(homeCard.getTenChucNang());

        holder.cvMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xuLyDoiManHinhTrongMenu(homeCard);
                //rootContext.startActivity(new Intent(rootContext, ListProjectActivity.class));
            }
        });
    }

    private void xuLyDoiManHinhTrongMenu(HomeCard homeCard) {
        switch (homeCard.getTenChucNang()){
            case "Dự án từ thiện":
                rootContext.startActivity(new Intent(rootContext,ListProjectActivity.class));
                break;
            case "Điểm nóng":
                rootContext.startActivity(new Intent(rootContext,ListRedPointAdapter.class));
                break;
            case "Duyệt điểm nóng":
                break;
            default:
                rootContext.startActivity(new Intent(rootContext,ListProjectActivity.class));
        }
    }

    @Override
    public int getItemCount() {
        return homeCardLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cvMainMenu;
        ImageView ivMenuImage;
        TextView tvMenuTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            cvMainMenu = (CardView) itemView.findViewById(R.id.cvMainMenu);
            ivMenuImage = (ImageView) itemView.findViewById(R.id.ivMenuImage);
            tvMenuTitle = (TextView) itemView.findViewById(R.id.tvMenuTitle);
        }
    }
}
