package com.jslps.pgmisnew.adapter;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.database.PgMeetingtbl;
import com.jslps.pgmisnew.presenter.MeetingPresenter;
import com.jslps.pgmisnew.presenter.PgActivityPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * Created by sonu on 4/4/2018.
 */

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.MyViewHolder> {

    private List<PgMeetingtbl> list;
    private MeetingPresenter presenter;


    class MyViewHolder extends RecyclerView.ViewHolder  {

        ImageView edit,delete;
        TextView date,no_of_people,cadre;
        TextView date1,no_of_people1,cadre1;
        ConstraintLayout constraintLayoutedit,constraintLayout;
        MyViewHolder(View view) {
            super(view);
            edit = view.findViewById(R.id.imageView12);
            delete = view.findViewById(R.id.imageView13);
            date = view.findViewById(R.id.textView67);
            no_of_people = view.findViewById(R.id.textView68);
            cadre = view.findViewById(R.id.textView69);
            date1 = view.findViewById(R.id.textView677);
            no_of_people1 = view.findViewById(R.id.textView688);
            cadre1 = view.findViewById(R.id.textView699);
            constraintLayoutedit = view.findViewById(R.id.constraintLayout6);
            constraintLayout = view.findViewById(R.id.secondLayout);
            }

    }

    public MeetingAdapter(MeetingPresenter presenter, List<PgMeetingtbl> itemList) {
        this.presenter = presenter;
        this.list = itemList;

    }

    @NotNull
    @Override
    public MeetingAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_meeting, parent, false);

        return new MeetingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MeetingAdapter.MyViewHolder holder, int position) {
        PgMeetingtbl item = list.get(position);
        if(item.getIsxported().equals("1")){
            holder.constraintLayoutedit.setVisibility(View.GONE);
            holder.constraintLayout.setVisibility(View.VISIBLE);
            presenter.adapter(
                    holder.edit,holder.delete,holder.date1,holder.no_of_people1,holder.cadre1,holder.constraintLayout,holder.constraintLayoutedit,position);

        }else{
            holder.constraintLayoutedit.setVisibility(View.VISIBLE);
            holder.constraintLayout.setVisibility(View.GONE);
            presenter.adapter(
                    holder.edit,holder.delete,holder.date,holder.no_of_people,holder.cadre,holder.constraintLayout,holder.constraintLayoutedit,position);

        }


    }



    @Override
    public int getItemCount() {
        return list.size();
    }
}
