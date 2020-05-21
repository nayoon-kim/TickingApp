package com.project.mobpro.time;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder{
    public TextView mIndex;
    public TextView mName;
    public RecyclerViewHolder(View itemView){
        super(itemView);
        mIndex = (TextView) itemView.findViewById(R.id.index);
        mName = (TextView) itemView.findViewById(R.id.name);
    }
}
