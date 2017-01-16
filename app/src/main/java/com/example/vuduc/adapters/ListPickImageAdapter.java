package com.example.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.vuduc.apptuthien.R;
import com.example.vuduc.model.Image;

import java.util.List;

/**
 * Created by Brucelee Thanh on 16/01/2017.
 */

public class ListPickImageAdapter extends RecyclerView.Adapter<ListPickImageAdapter.ViewHolder> {
    private Context context;
    private List<Image> pickImageList;

    public ListPickImageAdapter(Context context, List<Image> pickImageList) {
        this.context = context;
        this.pickImageList = pickImageList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chon_anh, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Image img = pickImageList.get(position);
        holder.img_chon_anh.setImageBitmap(img.getImg());
    }

    @Override
    public int getItemCount() {
        return pickImageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_chon_anh;
        public ViewHolder(View itemView) {
            super(itemView);
            img_chon_anh = (ImageView) itemView.findViewById(R.id.img_chon_anh);
        }
    }
}
