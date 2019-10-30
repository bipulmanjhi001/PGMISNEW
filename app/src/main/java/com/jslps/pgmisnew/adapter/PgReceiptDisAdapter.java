package com.jslps.pgmisnew.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.PgReceiptDisData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sonu on 4/4/2018.
 */

public class PgReceiptDisAdapter extends RecyclerView.Adapter<PgReceiptDisAdapter.MyViewHolder> {



    private List<PgReceiptDisData> list;
    private Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView688)
        TextView textView688;
        @BindView(R.id.textView677)
        TextView textView677;
        @BindView(R.id.textView699)
        TextView textView699;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public PgReceiptDisAdapter(Context context, List<PgReceiptDisData> itemList) {
        this.context = context;
        this.list = itemList;

    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_layout_disbursment_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        PgReceiptDisData item = list.get(position);
        holder.textView677.setText(item.getBudgethead());
        holder.textView688.setText(item.getApproveddate());

//        String boldText = item.getEkoshamount();
//        String normalText = "("+item.getApproveddate()+")";
//        SpannableString str = new SpannableString(boldText + normalText);
//        str.setSpan(new StyleSpan(Typeface.BOLD), 0, boldText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.textView699.setText(item.getEkoshamount());


    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
