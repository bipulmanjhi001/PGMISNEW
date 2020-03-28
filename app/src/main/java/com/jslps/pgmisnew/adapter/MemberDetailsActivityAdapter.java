package com.jslps.pgmisnew.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.presenter.MDAPresenter;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by sonu on 4/4/2018.
 */

public class MemberDetailsActivityAdapter extends RecyclerView.Adapter<MemberDetailsActivityAdapter.MyViewHolder> {

    private List<Pgmemtbl> list;
    private List<Pgmemtbl> newItemList;
    private MDAPresenter presenter;


    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView farmername,fatherhusbandshg,shg,fathername,husbandname,designation,primaryactivity,fishery,hva,livestock,ntfp,memfee,sharecapital;
        View viewlayout;
        ConstraintLayout constraintLayout,constraintLayout1;
        ImageView dropDown,edit,delete;
        MyViewHolder(View view) {
            super(view);
            farmername = view.findViewById(R.id.textView9);
            fatherhusbandshg = view.findViewById(R.id.textView12);
            shg = view.findViewById(R.id.textView15);
            fathername = view.findViewById(R.id.textView18);
            husbandname = view.findViewById(R.id.textView21);
            designation = view.findViewById(R.id.textView27);
            primaryactivity = view.findViewById(R.id.textView30);
            fishery = view.findViewById(R.id.textView33);
            hva = view.findViewById(R.id.textView36);
            livestock = view.findViewById(R.id.textView39);
            ntfp = view.findViewById(R.id.textView42);
            memfee = view.findViewById(R.id.textView45);
            sharecapital = view.findViewById(R.id.textView48);
            constraintLayout = view.findViewById(R.id.secondLayout);
            constraintLayout1 = view.findViewById(R.id.firstLayout);
            viewlayout = view.findViewById(R.id.view);
            dropDown = view.findViewById(R.id.imageView7);
            edit = view.findViewById(R.id.imageView10);
            delete = view.findViewById(R.id.imageView6);
            }

    }

    public MemberDetailsActivityAdapter(MDAPresenter presenter, List<Pgmemtbl> itemList) {
        this.presenter = presenter;
        this.list = itemList;
        this.newItemList = new ArrayList<>();
        this.newItemList.addAll(list);

    }

    @NotNull
    @Override
    public MemberDetailsActivityAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_memberdetails, parent, false);

        return new MemberDetailsActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MemberDetailsActivityAdapter.MyViewHolder holder, int position) {
        presenter.setViewAdapter(
                holder.constraintLayout1,
                holder.edit,
                holder.delete,
                holder.dropDown,
                holder.farmername,
                holder.fatherhusbandshg,
                holder.shg,
                holder.fathername,
                holder.husbandname,
                holder.designation,
                holder.primaryactivity,
                holder.fishery,
                holder.hva,
                holder.livestock,
                holder.ntfp,
                holder.memfee,
                holder.sharecapital,
                holder.viewlayout,
                holder.constraintLayout,
                position); }



    @Override
    public int getItemCount() {
        return list.size();
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        list.clear();
        if (charText.length() == 0) {
            list.addAll(newItemList);
        } else {
            for (Pgmemtbl dd : newItemList) {
                if(dd.getMembername()!=null && dd.getGrpname()!=null){
                    if (dd.getMembername().toLowerCase(Locale.getDefault())
                            .contains(charText)||dd.getGrpname().toLowerCase(Locale.getDefault())
                            .contains(charText)) {
                        list.add(dd);
                    }
                }

            }
        }
        notifyDataSetChanged();
    }
}
