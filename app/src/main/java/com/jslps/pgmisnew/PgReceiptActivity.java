package com.jslps.pgmisnew;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jslps.pgmisnew.adapter.PgPaymentAdapter;
import com.jslps.pgmisnew.adapter.PgReceiptAdapter;
import com.jslps.pgmisnew.database.PgPaymentHeadModel;
import com.jslps.pgmisnew.database.PgReceiptTranstbl;
import com.jslps.pgmisnew.interactor.PgReceiptInteractor;
import com.jslps.pgmisnew.presenter.PgReceiptPresenter;
import com.jslps.pgmisnew.util.AppConstant;
import com.jslps.pgmisnew.util.SetSpinnerText;
import com.jslps.pgmisnew.view.PgReceiptView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PgReceiptActivity extends AppCompatActivity implements PgReceiptView, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.spinner2)
    Spinner spinner2;
    @BindView(R.id.textView71)
    TextView textView71;
    @BindView(R.id.imageView16)
    ImageView imageView16;
    @BindView(R.id.textInputLayout6)
    TextInputLayout textInputLayout6;
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.parentContainer)
    ConstraintLayout parentContainer;
    @BindView(R.id.et_enter_amount)
    TextInputEditText etEnterAmount;
    @BindView(R.id.et_enter_remark)
    TextInputEditText etEnterRemark;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;

    /*Defining objects*/
    PgReceiptPresenter presenter;
    List<PgPaymentHeadModel> pgPaymentHeadModelList;
    public ArrayAdapter<PgPaymentHeadModel> headSpinAdapter;
    PgPaymentHeadModel headModelSelected;
    PgReceiptAdapter aAdapter;
    List<PgReceiptTranstbl> pgReceiptTranstblList;
    private PgReceiptTranstbl pgReceiptSelectedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_receipt);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        //edit mode should be false at start
        AppConstant.editpgreceiptrecord = false;
        presenter = new PgReceiptPresenter(new PgReceiptInteractor(), this);
        spinner2.setOnItemSelectedListener(this);
        presenter.getHeadList();
        presenter.setSpinnerHead();
        presenter.setRecyclerView();
        presenter.setPgName();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // First item will be gray
        if (position == 0) {
            ((TextView) view).setTextColor(ContextCompat.getColor(this, R.color.colorGrayHint));
        }
        headModelSelected = (PgPaymentHeadModel) parent.getSelectedItem();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void getHeadList(List<PgPaymentHeadModel> list) {
        pgPaymentHeadModelList = list;
    }

    @Override
    public void setSpinnerHead() {
        headSpinAdapter = new ArrayAdapter<PgPaymentHeadModel>(this, android.R.layout.simple_spinner_dropdown_item, pgPaymentHeadModelList) {

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    tv.setTextColor(getResources().getColor(R.color.colorGrayHint));
                } else {
//                        tv.setTextColor(getResources().getColor(R.color.colorGray));
                }
                return view;
            }
        };
        spinner2.setAdapter(headSpinAdapter);
    }

    @Override
    public void setOpenCalender() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PgReceiptActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void blankHead() {
        new StyleableToast
                .Builder(this)
                .text(getString(R.string.select_headd))
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    @Override
    public void blankDate() {
        new StyleableToast
                .Builder(this)
                .text(getString(R.string.blank_date))
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    @Override
    public void blankamount() {
        new StyleableToast
                .Builder(this)
                .text("Amount Can't be Blank or zero")
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    @Override
    public void dataSaved() {
        new StyleableToast
                .Builder(this)
                .text("Data Saved Successfully")
                .iconStart(R.drawable.right)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();

        presenter.setRecyclerView();
        presenter.clearForm();
    }

    @Override
    public void setRecyclerView() {
        pgReceiptTranstblList = presenter.getPgReceiptTranstblList(PgActivity.pgCodeSelected);
        Collections.reverse(pgReceiptTranstblList);
        aAdapter = new PgReceiptAdapter(this,pgReceiptTranstblList,presenter);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
    }

    @Override
    public void setPgName() {
        textView23.setText(PgActivity.pgNameSelected);
    }

    @Override
    public void clearForm() {
        presenter.setSpinnerHead();
        textView71.setText("");
        textView71.setHint("Click Calender to Enter Date");
        etEnterAmount.setText("");
        etEnterRemark.setText("");
    }

    @Override
    public void editRecord(PgReceiptTranstbl item) {
        new SetSpinnerText(spinner2,item.getHeadname());
        textView71.setText(item.getDate());
        textView71.setHint("");
        etEnterAmount.setText(item.getAmount());
        etEnterRemark.setText(item.getRemark());
        AppConstant.editpgreceiptrecord = true;
        pgReceiptSelectedItem = item;
    }

    @Override
    public void dataEdited() {
        new StyleableToast
                .Builder(this)
                .text("Data Edited Successfully")
                .iconStart(R.drawable.right)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();

        presenter.setRecyclerView();
        presenter.clearForm();
        AppConstant.editpgreceiptrecord =false;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

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
            Toast.makeText(PgReceiptActivity.this, "Please Select Valid Date", Toast.LENGTH_LONG).show();
        } else {
            textView71.setText(date);
            textView71.setHint("");

        }
    }

    private boolean validation() {
        boolean result = false;
        if (headModelSelected.getBudgetcode().equals("0")) {
            presenter.blankHead();
        } else if (textView71.getHint().toString().equals("Click Calender to Enter Date")) {
            presenter.blankDate();
        } else if (etEnterAmount.getText().toString().equals("") || etEnterAmount.getText().toString().equals("0")) {
            presenter.blankamount();
        } else {
            result = true;
        }

        return result;
    }


    @OnClick({R.id.imageView16, R.id.button4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageView16:
                presenter.openCalender();
                break;
            case R.id.button4:
                //for saving
                if (validation()) {
                    String budget_code = headModelSelected.getBudgetcode();
                    String head_name = headModelSelected.getHead_name();
                    String date = textView71.getText().toString();
                    String amount = etEnterAmount.getText().toString();
                    String remark = etEnterRemark.getText().toString();
                    String pgCode = PgActivity.pgCodeSelected;
                    String[] userDetails = presenter.getUserDetails();
                    String username = userDetails[0];
                    String userid = userDetails[1];
                    String isexported = "0";
                    if(AppConstant.editpgreceiptrecord){
                        presenter.saveEditedData(budget_code, head_name, date, amount, remark, pgCode, username, userid, isexported,pgReceiptSelectedItem);
                    }else{
                        presenter.saveData(budget_code, head_name, date, amount, remark, pgCode, username, userid, isexported);
                    }

                }

                break;
        }
    }

}
