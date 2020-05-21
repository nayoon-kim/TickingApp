package com.project.mobpro.time;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<ItemAlarm> mItems;
    Context mContext;

    public RecyclerViewAdapter(ArrayList itemList, Context context){
        mItems = itemList;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alarm, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.mIndex.setText(mItems.get(position).index);
        holder.mName.setText(mItems.get(position).name);
    }

    @Override
    public int getItemCount() {
        return (null != mItems ? mItems.size() : 0);
    }
}
