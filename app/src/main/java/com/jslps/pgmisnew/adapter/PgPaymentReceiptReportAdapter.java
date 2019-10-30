package com.jslps.pgmisnew.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.objects.AlertAction;
import com.jslps.pgmisnew.PgpaymentActivity;
import com.jslps.pgmisnew.R;
import com.jslps.pgmisnew.database.PaymentReceiptReportModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.presenter.PgPaymentPresenter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by sonu on 4/4/2018.
 */

public class PgPaymentReceiptReportAdapter extends RecyclerView.Adapter<PgPaymentReceiptReportAdapter.MyViewHolder> {



    private List<PaymentReceiptReportModel> list;
    private Context context;


    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.textView72)
        TextView textView72;
        @BindView(R.id.textView73)
        TextView textView73;
        @BindView(R.id.textView74)
        TextView textView74;
        @BindView(R.id.textView75)
        TextView textView75;
        @BindView(R.id.textView76)
        TextView textView76;
        @BindView(R.id.textView77)
        TextView textView77;
        @BindView(R.id.textView78)
        TextView textView78;
        @BindView(R.id.textView79)
        TextView textView79;
        ImageView imageView17;
        @BindView(R.id.imageView18)
        ImageView imgDelete;
        @BindView(R.id.imageView17)
        ImageView imgEdit;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

    }

    public PgPaymentReceiptReportAdapter(Context context, List<PaymentReceiptReportModel> itemList) {
        this.context = context;
        this.list = itemList;

    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_list_pg_payment_activity, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        PaymentReceiptReportModel item = list.get(position);

        holder.textView76.setText(item.getHeadname());
        holder.textView77.setText(item.getReceivedamount());
        holder.textView78.setText(item.getPaymentamount());
        holder.textView79.setText(item.getBalance());

        holder.imgEdit.setVisibility(View.GONE);
        holder.imgDelete.setVisibility(View.GONE);



    }


    @Override
    public int getItemCount() {
        return list.size();
    }



}
