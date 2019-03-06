package com.jslps.pgmisnew.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.Test;
import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.presenter.PgActivityPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by sonu on 4/4/2018.
 */

public class PgActivityAdapter extends RecyclerView.Adapter<PgActivityAdapter.MyViewHolder> {

    private List<PgActivityModel> list;
    private PgActivityPresenter presenter;


    class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView icon1,icon2;
        TextView text1,text2;
        View viewlayout;
        ConstraintLayout constraintLayout,constraintLayout0;
        MyViewHolder(View view) {
            super(view);
            icon1 = view.findViewById(R.id.imageView3);
            icon2 = view.findViewById(R.id.imageView4);
            text1 = view.findViewById(R.id.textView2);
            text2 = view.findViewById(R.id.textView4);
            constraintLayout = view.findViewById(R.id.secondLayout);
            constraintLayout0 = view.findViewById(R.id.firstLayout);
            viewlayout = view.findViewById(R.id.view);
            }

    }

    public PgActivityAdapter(PgActivityPresenter presenter, List<PgActivityModel> itemList) {
        this.presenter = presenter;
        this.list = itemList;

    }

    @NotNull
    @Override
    public PgActivityAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_pg_activity, parent, false);

        return new PgActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull PgActivityAdapter.MyViewHolder holder, int position) {
        presenter.setViewAdapter(
                holder.text1,
                holder.text2,
                holder.icon1,
                holder.icon2,
                holder.constraintLayout0,
                holder.constraintLayout,
                position,
                holder.viewlayout); }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
