package com.artesaniasclient.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.artesaniasclient.model.Craft;

import java.util.ArrayList;

public class adpCrafts extends RecyclerView.Adapter<adpCrafts.ViewHolder> {

    private int resource;
    private ArrayList<Craft> dispositiveList;

    public adpCrafts(ArrayList<Craft> dispositiveList, int resource) {
        this.resource = resource;
        this.dispositiveList = dispositiveList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
