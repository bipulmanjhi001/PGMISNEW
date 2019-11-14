package com.jslps.pgmisnew;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

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
    @BindView(R.id.imageView22)
    ImageView pdf;





    //Class Globals
    PaymentReceiptReportPresenter presenter;
    List<PgReceiptDisData> receiptDisDataList;
    List<ReceiptAmountSumModel> receiptAmountSumModelList;
    boolean isMatched;
    boolean isMatched1;
    boolean isMatched2;
    List<PgPaymentTranstbl> pgPaymentTranstblList;
    List<PgPaymentTranstbl> pgPaymentTranstblListSum;
    List<PaymentReceiptReportModel> paymentReceiptReportModelList = new ArrayList<>();
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

    @OnClick({R.id.imageView19, R.id.imageView20, R.id.button5,R.id.imageView21,R.id.imageView22})
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

            case R.id.imageView22:
                String dateFrom = textView82.getText().toString();
                String dateTo = textView84.getText().toString();
                if ((dateFrom.equals("Select Date") && dateTo.equals("Select Date"))||paymentReceiptReportModelList.size()==0) {
                    Toast.makeText(PgPaymentReceiptReport.this,"Please see report first",Toast.LENGTH_SHORT).show();


                }else{
                    if (!hasPermissions(this, PERMISSIONS)) {
                        ActivityCompat
                                .requestPermissions(this, PERMISSIONS, 1);

                    }else{
                        //generate pdf here
                        generatePDF(recylerList,dateFrom,dateTo);
                    }
                }
                break;

        }
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
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
        //adding first model to report list for pdf heading display
        PaymentReceiptReportModel model0 = new PaymentReceiptReportModel();
        model0.setHeadname("Head");
        model0.setReceivedamount("ReceivedAmt");
        model0.setPaymentamount("PaymentAmt");
        model0.setBalance("Balance");
        paymentReceiptReportModelList.add(model0);

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


        //adding last model as total to report list for pdf

        PaymentReceiptReportModel model = new PaymentReceiptReportModel();
        model.setHeadname("Total");
        model.setReceivedamount(receivedAmounttotal+"");
        model.setPaymentamount(paymentAmounttotal+"");
        model.setBalance(balanceAmounttotal+"");
        paymentReceiptReportModelList.add(model);


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


    public void generatePDF(RecyclerView view,String fromdate,String todate) {
        RecyclerView.Adapter adapter = view.getAdapter();
        Bitmap bigBitmap = null;
        if (adapter != null) {
            int size = adapter.getItemCount();
            int height = 0;
            final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

            // Use 1/8th of the available memory for this memory cache.
            final int cacheSize = maxMemory / 8;
            LruCache<String, Bitmap> bitmaCache = new LruCache<>(cacheSize);
            for (int i = 0; i < size; i++) {
                RecyclerView.ViewHolder holder = adapter.createViewHolder(view, adapter.getItemViewType(i));
                adapter.onBindViewHolder(holder, i);
                holder.itemView.measure(View.MeasureSpec.makeMeasureSpec(view.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                holder.itemView.layout(0, 0, holder.itemView.getMeasuredWidth(), holder.itemView.getMeasuredHeight());
                holder.itemView.setDrawingCacheEnabled(true);
                holder.itemView.buildDrawingCache();
                Bitmap drawingCache = holder.itemView.getDrawingCache();
                if (drawingCache != null) {

                    bitmaCache.put(String.valueOf(i), drawingCache);
                }

                height += holder.itemView.getMeasuredHeight();
            }

            bigBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), height, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(bigBitmap);
            bigCanvas.drawColor(Color.WHITE);

            Document document = new Document(PageSize.A4,0,0,0,0);
            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PGMISPaymentReceiptPDF";

            File dir = new File(path);
            if(!dir.exists())
                dir.mkdirs();

            final File file = new File(dir, "pgmis.pdf");
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            try {
                PdfWriter.getInstance(document, fOut);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            //

            Font paraFont= new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD);
            Paragraph p1 = new Paragraph(fromdate+"-"+todate,paraFont);
            p1.setAlignment(Paragraph.ALIGN_CENTER);
            p1.breakUp();

            if (!document.isOpen()) {
                document.open();
            }
            try {
                document.add(p1);
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < size; i++) {

                try {
                    //Adding the content to the document
                    Bitmap bmp = bitmaCache.get(String.valueOf(i));
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Image image = Image.getInstance(stream.toByteArray());
                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100; // 0 means you have no indentation. If you have any, change it.
                    image.scalePercent(scaler);
                    image.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER | com.itextpdf.text.Image.ALIGN_TOP);




                    if (!document.isOpen()) {
                        document.open();
                    }

                    document.add(image);

                } catch (Exception ex) {

                }
            }

            if (document.isOpen()) {
                document.close();
            }
            // Set on UI Thread
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    AlertDialog.Builder builder = new AlertDialog.Builder(PgPaymentReceiptReport.this);
                    builder.setTitle("Success")
                            .setMessage("PDF File Generated Successfully.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType( FileProvider.getUriForFile(PgPaymentReceiptReport.this, BuildConfig.APPLICATION_ID + ".provider",file), "application/pdf");
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    startActivity(intent);
                                }

                            }).show();
                }
            });
        }
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
