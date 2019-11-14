package com.jslps.pgmisnew;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jslps.pgmisnew.adapter.PgPaymentAdapter;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.TblMstPgPaymentReceipthead;
import com.jslps.pgmisnew.interactor.PgPaymentInteractor;
import com.jslps.pgmisnew.presenter.PgPaymentPresenter;
import com.jslps.pgmisnew.util.AppConstant;
import com.jslps.pgmisnew.util.SetSpinnerText;
import com.jslps.pgmisnew.view.PgPaymentView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
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

public class PgpaymentActivity extends AppCompatActivity implements PgPaymentView, AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.spinner2)
    SearchableSpinner spinner2;
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
    @BindView(R.id.spinner3)
    Spinner spinner3;

    /*Defining objects*/
    PgPaymentPresenter presenter;
    List<TblMstPgPaymentReceipthead> pgPaymentHeadModelList;
    public ArrayAdapter<TblMstPgPaymentReceipthead> headSpinAdapter;
    TblMstPgPaymentReceipthead headModelSelected;
    String selectedPaymentMode;
    PgPaymentAdapter aAdapter;
    List<PgPaymentTranstbl> pgPaymentTranstblList;

    private PgPaymentTranstbl pgPaymentSelectedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pgpayment);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        //edit mode should be false at start
        AppConstant.editpgpaymentrecord = false;
        presenter = new PgPaymentPresenter(new PgPaymentInteractor(), this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        presenter.getHeadList();
        presenter.setSpinnerHead();
        presenter.setRecyclerView();
        presenter.setPgName();
    }


    @Override
    public void getHeadList(List<TblMstPgPaymentReceipthead> list) {
        pgPaymentHeadModelList = list;

    }

    @Override
    public void setSpinnerHead() {
        headSpinAdapter = new ArrayAdapter<TblMstPgPaymentReceipthead>(this, android.R.layout.simple_spinner_dropdown_item, pgPaymentHeadModelList) {

        };
        spinner2.setAdapter(headSpinAdapter);
    }

    @Override
    public void setOpenCalender() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                PgpaymentActivity.this,
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
        pgPaymentTranstblList = presenter.getPgPaymentTranstblList(PgActivity.pgCodeSelected);
        Collections.reverse(pgPaymentTranstblList);
        aAdapter = new PgPaymentAdapter(this, pgPaymentTranstblList, presenter);
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
        spinner3.setSelection(0);

    }

    @Override
    public void editRecord(PgPaymentTranstbl item) {
        new SetSpinnerText(spinner2, item.getHeadname());
        textView71.setText(item.getDate());
        textView71.setHint("");
        etEnterAmount.setText(item.getAmount());
        etEnterRemark.setText(item.getRemark());
        AppConstant.editpgpaymentrecord = true;
        pgPaymentSelectedItem = item;
        if(item.getPaymentmode().equals("Cash")){
            spinner3.setSelection(1);
        }else{
            spinner3.setSelection(2);
        }

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
        AppConstant.editpgpaymentrecord = false;
    }

    @Override
    public void blankPaymentMode() {
        new StyleableToast
                .Builder(this)
                .text("Please Select Payment Mode")
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch (parent.getId()){
            case R.id.spinner2:
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(this, R.color.colorGrayHint));
                }
                headModelSelected = (TblMstPgPaymentReceipthead) parent.getSelectedItem();
                break;

            case R.id.spinner3:
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(this, R.color.colorGrayHint));
                }

                selectedPaymentMode =  parent.getSelectedItem().toString();
                break;

        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                    String budget_code = headModelSelected.getBudgetid();
                    String head_name = headModelSelected.getHeadname();
                    String date = textView71.getText().toString();
                    String amount = etEnterAmount.getText().toString();
                    String remark = etEnterRemark.getText().toString();
                    String pgCode = PgActivity.pgCodeSelected;
                    String[] userDetails = presenter.getUserDetails();
                    String username = userDetails[0];
                    String userid = userDetails[1];
                    String isexported = "0";
                    if (AppConstant.editpgpaymentrecord) {
                        presenter.saveEditedData(budget_code, head_name, date, amount, remark, pgCode, username, userid, isexported, pgPaymentSelectedItem);
                    } else {
                        presenter.saveData(budget_code, head_name, date, amount, remark, pgCode, username, userid, isexported,selectedPaymentMode);
                    }

                }

                break;
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
        }else if (selectedPaymentMode.equals("Select Payment")) {
            presenter.blankPaymentMode();
        } else {
            result = true;
        }

        return result;
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
            Toast.makeText(PgpaymentActivity.this, "Please Select Valid Date", Toast.LENGTH_LONG).show();
        } else {
            textView71.setText(newDate);
            textView71.setHint("");

        }
    }
}
