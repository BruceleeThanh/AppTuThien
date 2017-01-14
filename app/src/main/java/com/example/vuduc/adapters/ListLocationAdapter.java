package com.example.vuduc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vuduc.apptuthien.R;
import com.example.vuduc.model.Location;

import java.util.List;

/**
 * Created by VuDuc on 1/12/2017.
 */

public class ListLocationAdapter extends RecyclerView.Adapter<ListLocationAdapter.LocationViewHoder> {
    private Context context;
    private List<Location> locationList;

    public ListLocationAdapter(Context context, List<Location> locationList) {
        this.context = context;
        this.locationList = locationList;
    }

    @Override
    public LocationViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_location,parent,false);
        LocationViewHoder locationViewHoder=new LocationViewHoder(view);
        return locationViewHoder;
    }

    @Override
    public void onBindViewHolder(LocationViewHoder holder, int position) {
        final Location location = locationList.get(position);

        holder.txtLocation.setText(location.getWard()+" - "+location.getDistrict()+" - "+location.getCity());
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

    public class LocationViewHoder extends RecyclerView.ViewHolder{
        TextView txtLocation;
        public LocationViewHoder(View itemView) {
            super(itemView);
            txtLocation= (TextView) itemView.findViewById(R.id.txtLocation);
        }
    }
}
