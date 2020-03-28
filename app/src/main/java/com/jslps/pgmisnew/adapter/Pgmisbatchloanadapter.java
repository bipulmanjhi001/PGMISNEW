package com.jslps.pgmisnew.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.PgmisBatchLoantbl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sonu on 4/4/2018.
 */

public class Pgmisbatchloanadapter extends RecyclerView.Adapter<Pgmisbatchloanadapter.MyViewHolder> {



    private List<PgmisBatchLoantbl> list;
    private Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView105)
        TextView textView105;
        @BindView(R.id.textView106)
        TextView textView106;
        @BindView(R.id.textView107)
        TextView textView107;
        @BindView(R.id.textView108)
        TextView textView108;
        @BindView(R.id.textView109)
        TextView textView109;
        @BindView(R.id.textView110)
        TextView textView110;
        @BindView(R.id.textView111)
        TextView textView111;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public Pgmisbatchloanadapter(Context context, List<PgmisBatchLoantbl> itemList) {
        this.context = context;
        this.list = itemList;

    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_batch_loan_taken, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        PgmisBatchLoantbl item = list.get(position);

        holder.textView105.setText(position+1+".");
        holder.textView110.setText(item.getEntrydate());
        holder.textView111.setText(item.getAmount()+"(Rs)");


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
