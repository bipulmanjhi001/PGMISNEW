package com.jslps.pgmisnew.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.presenter.APMPresenter;
import com.jslps.pgmisnew.presenter.MDAPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by sonu on 4/4/2018.
 */

public class AddPgMembersAdapter extends RecyclerView.Adapter<AddPgMembersAdapter.MyViewHolder> {

    private List<Shgmemnonpg> list;
    private APMPresenter presenter;


    class MyViewHolder extends RecyclerView.ViewHolder  {
        TextView farmername,fatherhusbandshg;
        ImageView imageView;


        MyViewHolder(View view) {
            super(view);
            farmername = view.findViewById(R.id.textView53);
            fatherhusbandshg = view.findViewById(R.id.textView54);
            imageView = view.findViewById(R.id.imageView9);
            }

    }

    public AddPgMembersAdapter(APMPresenter presenter, List<Shgmemnonpg> itemList) {
        this.presenter = presenter;
        this.list = itemList;

    }

    @NotNull
    @Override
    public AddPgMembersAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_add_pg_members, parent, false);

        return new AddPgMembersAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull AddPgMembersAdapter.MyViewHolder holder, int position) {
        presenter.setViewAdapter(
                holder.farmername,
                holder.fatherhusbandshg,
                position,
                holder.imageView);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
