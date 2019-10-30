package com.jslps.pgmisnew;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jslps.pgmisnew.adapter.PgPaymentReceiptReportAdapter;
import com.jslps.pgmisnew.database.PaymentReceiptReportModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.PgReceiptDisData;
import com.jslps.pgmisnew.database.ReceiptAmountSumModel;
import com.jslps.pgmisnew.interactor.PaymentReceiptRepotInteractor;
import com.jslps.pgmisnew.presenter.PaymentReceiptReportPresenter;
import com.jslps.pgmisnew.view.PaymentReceiptReport;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PgPaymentReceiptReport extends AppCompatActivity implements PaymentReceiptReport, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView81)
    TextView textView81;
    @BindView(R.id.textView82)
    TextView textView82;
    @BindView(R.id.imageView19)
    ImageView imageView19;
    @BindView(R.id.textView83)
    TextView textView83;
    @BindView(R.id.textView84)
    TextView textView84;
    @BindView(R.id.imageView20)
    ImageView imageView20;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
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
    @BindView(R.id.constraintLayout4)
    ConstraintLayout constraintLayout4;
    @BindView(R.id.textView722)
    TextView textView722;
    @BindView(R.id.textView733)
    TextView textView733;
    @BindView(R.id.textView744)
    TextView textView744;
    @BindView(R.id.textView755)
    TextView textView755;
    @BindView(R.id.textView766)
    TextView textView766;
    @BindView(R.id.textView777)
    TextView textView777;
    @BindView(R.id.textView788)
    TextView textView788;
    @BindView(R.id.textView799)
    TextView textView799;
    @BindView(R.id.constraintLayout5)
    ConstraintLayout constraintLayout5;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.parentContainer)
    ConstraintLayout parentContainer;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.imageView21)
    ImageView imageView21;



    //Class Globals
    PaymentReceiptReportPresenter presenter;
    List<PgReceiptDisData> receiptDisDataList;
    List<ReceiptAmountSumModel> receiptAmountSumModelList;
    boolean isMatched;
    boolean isMatched1;
    boolean isMatched2;
    List<PgPaymentTranstbl> pgPaymentTranstblList;
    List<PgPaymentTranstbl> pgPaymentTranstblListSum;
    List<PaymentReceiptReportModel> paymentReceiptReportModelList;
    List<PaymentReceiptReportModel> paymentReceiptReportModelListNotMatched;
    PgPaymentReceiptReportAdapter aAdapter;
    List<String> budgetidreportfinallist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_payment_receipt_report);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        //initialization
        presenter = new PaymentReceiptReportPresenter(new PaymentReceiptRepotInteractor(), this);
        presenter.setPgName();
        receiptDisDataList = presenter.getReceiptAmountData(PgActivity.pgCodeSelected);

        //adding amount for same budgetid for receiptDisDataList
        addAmountreceiptDisDataList();
    }

    private void addAmountreceiptDisDataList() {
        receiptAmountSumModelList = new ArrayList<>();
        if (receiptDisDataList.size() > 0) {
            for (int i = 0; i < receiptDisDataList.size(); i++) {
                isMatched = false;
                ReceiptAmountSumModel item = new ReceiptAmountSumModel();
                item.setBudgetid(receiptDisDataList.get(i).getBudgetid());
                item.setAmount(receiptDisDataList.get(i).getEkoshamount());
                item.setHeadname(receiptDisDataList.get(i).getBudgethead());
                if (receiptAmountSumModelList.size() == 0) {
                    //for size zero
                    receiptAmountSumModelList.add(item);
                } else {
                    for (int j = 0; j < receiptAmountSumModelList.size(); j++) {
                        String budgetid = receiptAmountSumModelList.get(j).getBudgetid();
                        String amount = receiptAmountSumModelList.get(j).getAmount();
                        if (budgetid.equals(receiptDisDataList.get(i).getBudgetid())) {
                            double newAmount = Double.parseDouble(amount) + Double.parseDouble(receiptDisDataList.get(i).getEkoshamount());
                            item.setAmount(newAmount + "");
                            receiptAmountSumModelList.set(j, item);
                            isMatched = true;
                            //since matched terminate internal loop
                            j = receiptAmountSumModelList.size();
                        }
                    }
                    //
                    if (!isMatched) {
                        receiptAmountSumModelList.add(item);
                    }
                }

            }
        }
    }

    @OnClick({R.id.imageView19, R.id.imageView20, R.id.button5,R.id.imageView21})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView19:
                presenter.openCalender("from");
                break;
            case R.id.imageView20:
                presenter.openCalender("to");
                break;
            case R.id.button5:
                boolean result = validation();
                if (result) {
                    pgPaymentTranstblList = presenter.getListPaymentTranstableDateWise(textView82.getText().toString(), textView84.getText().toString(), PgActivity.pgCodeSelected);
                    //sum paymentamount for same budgetid
                    sumAmountPgpaymentTrans();
                    presenter.getReport();
                    System.out.println("");
                }
                break;
            case R.id.imageView21:
                presenter.clearDates();
                break;
        }
    }

    private void sumAmountPgpaymentTrans() {
        pgPaymentTranstblListSum = new ArrayList<>();
        if (pgPaymentTranstblList.size() > 0) {
            for (int i = 0; i < pgPaymentTranstblList.size(); i++) {
                isMatched1 = false;
                PgPaymentTranstbl item = new PgPaymentTranstbl();
                item.setBudgetcode(pgPaymentTranstblList.get(i).getBudgetcode());
                item.setAmount(pgPaymentTranstblList.get(i).getAmount());
                item.setHeadname(pgPaymentTranstblList.get(i).getHeadname());
                if (pgPaymentTranstblListSum.size() == 0) {
                    //for size zero
                    pgPaymentTranstblListSum.add(item);
                } else {
                    for (int j = 0; j < pgPaymentTranstblListSum.size(); j++) {
                        String budgetid = pgPaymentTranstblListSum.get(j).getBudgetcode();
                        String amount = pgPaymentTranstblListSum.get(j).getAmount();
                        if (budgetid.equals(pgPaymentTranstblList.get(i).getBudgetcode())) {
                            double newAmount = Double.parseDouble(amount) + Double.parseDouble(pgPaymentTranstblList.get(i).getAmount());
                            item.setAmount(newAmount + "");
                            pgPaymentTranstblListSum.set(j, item);
                            isMatched1 = true;
                            //since matched terminate internal loop
                            j = pgPaymentTranstblListSum.size();
                        }
                    }
                    //
                    if (!isMatched1) {
                        pgPaymentTranstblListSum.add(item);
                    }
                }

            }
        }
    }

    private boolean validation() {
        boolean result;
        String dateFrom = textView82.getText().toString();
        String dateTo = textView84.getText().toString();
        if (dateFrom.equals("Select Date") && dateTo.equals("Select Date")) {
            result = false;
            presenter.selectAtLeastOneCalender();
        } else {
            result = true;
        }
        return result;
    }

    @Override
    public void setPgName() {
        textView23.setText(PgActivity.pgNameSelected);
    }

    @Override
    public void openCalender(String from) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PgPaymentReceiptReport.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getFragmentManager(), from);
    }

    @Override
    public void selectAtLeastOneCalender() {
        new StyleableToast
                .Builder(this)
                .text(getString(R.string.atleast_one_date))
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    @Override
    public void getReport() {
        paymentReceiptReportModelList = new ArrayList<>();
        paymentReceiptReportModelListNotMatched = new ArrayList<>();
        double receivedAmounttotal = 0;
        double paymentAmounttotal = 0;
        double balanceAmounttotal = 0;
        for (int i = 0; i < receiptAmountSumModelList.size(); i++) {
            isMatched2 = false;
            String budgetid = receiptAmountSumModelList.get(i).getBudgetid();
            String amount = receiptAmountSumModelList.get(i).getAmount();
            for (int j = 0; j < pgPaymentTranstblListSum.size(); j++) {
                String budgetidlocal = pgPaymentTranstblListSum.get(j).getBudgetcode();
                if (budgetid.equals(budgetidlocal)) {
                    PaymentReceiptReportModel model = new PaymentReceiptReportModel();
                    model.setHeadname(pgPaymentTranstblListSum.get(j).getHeadname());
                    model.setBudgetid(pgPaymentTranstblListSum.get(j).getBudgetcode());
                    model.setReceivedamount(amount);
                    model.setPaymentamount(pgPaymentTranstblListSum.get(j).getAmount());

                    double balance = Double.parseDouble(amount) - Double.parseDouble(pgPaymentTranstblListSum.get(j).getAmount());
                    receivedAmounttotal = receivedAmounttotal + Double.parseDouble(amount);
                    paymentAmounttotal = paymentAmounttotal + Double.parseDouble(pgPaymentTranstblListSum.get(j).getAmount());
                    balanceAmounttotal = balanceAmounttotal + balance;


                    model.setBalance(balance + "");
                    paymentReceiptReportModelList.add(model);
                    isMatched2 = true;
                    j = pgPaymentTranstblListSum.size();
                }
            }
            if(!isMatched2){
                PaymentReceiptReportModel model = new PaymentReceiptReportModel();
                model.setHeadname(receiptAmountSumModelList.get(i).getHeadname());
                model.setBudgetid(receiptAmountSumModelList.get(i).getBudgetid());
                model.setReceivedamount(amount);
                model.setPaymentamount("0");
                model.setBalance(amount);
                receivedAmounttotal = receivedAmounttotal + Double.parseDouble(amount);
                balanceAmounttotal = balanceAmounttotal + Double.parseDouble(amount);
                paymentReceiptReportModelListNotMatched.add(model);

            }

        }


        paymentReceiptReportModelList.addAll(paymentReceiptReportModelListNotMatched);
        //showing payments whose receipt is not present
        budgetidreportfinallist = new ArrayList<>();
        for(int a=0;a<paymentReceiptReportModelList.size();a++){
            budgetidreportfinallist.add(paymentReceiptReportModelList.get(a).getBudgetid());
        }


        for(int k=0;k<pgPaymentTranstblListSum.size();k++){
            String budgetid = pgPaymentTranstblListSum.get(k).getBudgetcode();
             if(!budgetidreportfinallist.contains(budgetid)){
                 PaymentReceiptReportModel model = new PaymentReceiptReportModel();
                 model.setBudgetid(pgPaymentTranstblListSum.get(k).getBudgetcode());
                 model.setHeadname(pgPaymentTranstblListSum.get(k).getHeadname());
                 model.setReceivedamount("0");
                 model.setPaymentamount(pgPaymentTranstblListSum.get(k).getAmount());
                 double balance = 0-Double.parseDouble(pgPaymentTranstblListSum.get(k).getAmount());
                 model.setBalance(balance+"");
                 paymentAmounttotal = paymentAmounttotal + Double.parseDouble(pgPaymentTranstblListSum.get(k).getAmount());
                 balanceAmounttotal = balanceAmounttotal + balance;
                 paymentReceiptReportModelList.add(model);
             }

        }


        aAdapter = new PgPaymentReceiptReportAdapter(this, paymentReceiptReportModelList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);

        //setting totals
        textView777.setText(receivedAmounttotal + "");
        textView788.setText(paymentAmounttotal + "");
        textView799.setText(balanceAmounttotal + "");


    }

    @Override
    public void clearDates() {
        textView82.setText("Select Date");
        textView84.setText("Select Date");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        String newDay = dayOfMonth + "";
        String newMonth = (monthOfYear + 1) + "";


        if ((monthOfYear + 1) < 10) {
            newMonth = "0" + newMonth;
        }

        if (dayOfMonth < 10) {
            newDay = "0" + dayOfMonth;
        }

        String newDate = year + "/" + newMonth + "/" + newDay;


        SimpleDateFormat sdf = new SimpleDateFormat("d/M/yyyy");
        String currentDate = new SimpleDateFormat("d/M/yyyy", Locale.getDefault()).format(new Date());

        Date date1 = null, date2 = null;
        try {
            date1 = sdf.parse(date);
            date2 = sdf.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date1 != null && date1.compareTo(date2) > 0) {
            Toast.makeText(PgPaymentReceiptReport.this, "Please Select Valid Date", Toast.LENGTH_LONG).show();
        } else {
            if (view.getTag().equals("from")) {

                //condition to check from date from date should be less than to date
                if (textView84.getText().toString().equals("Select Date")) {
                    textView82.setText(newDate);
                } else {
                    int newDateCom = Integer.parseInt(newDate.replaceAll("/", ""));
                    int toDate = Integer.parseInt(textView84.getText().toString().replaceAll("/", ""));
                    if (newDateCom <= toDate) {
                        textView82.setText(newDate);
                    } else {
                        Toast.makeText(PgPaymentReceiptReport.this, "From Date Can't be Greater than To Date", Toast.LENGTH_LONG).show();
                    }

                }

            } else {

                //condition to check from date to date should be greater than from date
                if (textView82.getText().toString().equals("Select Date")) {
                    textView84.setText(newDate);
                } else {
                    int newDateCom = Integer.parseInt(newDate.replaceAll("/", ""));
                    int fromDate = Integer.parseInt(textView82.getText().toString().replaceAll("/", ""));
                    if (fromDate <= newDateCom) {
                        textView84.setText(newDate);
                    } else {
                        Toast.makeText(PgPaymentReceiptReport.this, "To Date Can't be less than From Date", Toast.LENGTH_LONG).show();
                    }

                }
            }

        }
    }
}
