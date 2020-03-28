package com.jslps.pgmisnew.adapter;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.Itempurchasedbypgtbl;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sonu on 4/4/2018.
 */

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.MyViewHolder> {



    private List<Itempurchasedbypgtbl> list;
    private Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView99)
        TextView textView99;
        @BindView(R.id.spinner4)
        Spinner spinner4;
        @BindView(R.id.et_rate)
        TextInputEditText etRate;
        @BindView(R.id.textInputLayout6)
        TextInputLayout textInputLayout6;
        @BindView(R.id.et_quantity)
        TextInputEditText etQuantity;
        @BindView(R.id.textInputLayout7)
        TextInputLayout textInputLayout7;
        @BindView(R.id.textView100)
        TextView textView100;


        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public LoanAdapter(Context context, List<Itempurchasedbypgtbl> itemList) {
        this.context = context;
        this.list = itemList;

    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_get_loan, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        Itempurchasedbypgtbl item = list.get(position);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

}
