package com.jslps.pgmisnew.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.presenter.MFAPresenter;
import com.jslps.pgmisnew.presenter.SHAPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by sonu on 4/4/2018.
 */

public class ShareCapitalActivityAdapter extends RecyclerView.Adapter<ShareCapitalActivityAdapter.MyViewHolder> {

    private List<Pgmemtbl> list;
    private SHAPresenter presenter;


    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView farmer,total,paid,remaining,textView62;
        ConstraintLayout firstLayout;
        TextInputEditText enterAmount;
        CheckBox checkBox;
        View viewlayout;
        MyViewHolder(View view) {
            super(view);
            farmer = view.findViewById(R.id.textView7);
            total = view.findViewById(R.id.textView57);
            paid = view.findViewById(R.id.textView58);
            remaining = view.findViewById(R.id.textView61);
            firstLayout = view.findViewById(R.id.firstLayout);
            viewlayout = view.findViewById(R.id.view);
            enterAmount = view.findViewById(R.id.et_member_fee);
            checkBox = view.findViewById(R.id.checkBox);
            textView62 = view.findViewById(R.id.textView62);

            presenter.addTextChangeListner(firstLayout,
                    farmer,total,paid,remaining,enterAmount,viewlayout,getAdapterPosition(),checkBox,textView62);

            }


    }

    public ShareCapitalActivityAdapter(SHAPresenter presenter, List<Pgmemtbl> itemList) {
        this.presenter = presenter;
        this.list = itemList;

    }

    @NotNull
    @Override
    public ShareCapitalActivityAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_membershipfee, parent, false);

        return new ShareCapitalActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull ShareCapitalActivityAdapter.MyViewHolder holder, int position) {
        presenter.setViewAdapter(
               holder.firstLayout,
                holder.farmer,
                holder.total,
                holder.paid,
                holder.remaining,
                holder.enterAmount,
                holder.viewlayout,
                position,
                holder.checkBox,
                holder.textView62);


    }





    @Override
    public int getItemCount() {
        return list.size();
    }
}
