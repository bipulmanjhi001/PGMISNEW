package com.jslps.pgmisnew;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.PgItemPurcahseAdapter;
import com.jslps.pgmisnew.database.Itempurchasedbypgtbl;
import com.jslps.pgmisnew.database.Pgmisstockmsttbl;
import com.jslps.pgmisnew.database.TblMstPgPaymentReceipthead;
import com.jslps.pgmisnew.util.GetCurrentDate;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StockPurchase extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner2)
    SearchableSpinner spinnerItem;
    @BindView(R.id.spinner4)
    Spinner spinnerUnit;
    @BindView(R.id.et_rate)
    TextInputEditText etRate;
    @BindView(R.id.et_quantity)
    TextInputEditText etQuantity;
    @BindView(R.id.button4)
    Button btnSave;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.spinner5)
    SearchableSpinner spinnnerHead;
    @BindView(R.id.spinnermodeofpayment)
    Spinner spinnermodeofpayment;

    List<Pgmisstockmsttbl> pgmisstockmsttblList;
    List<TblMstPgPaymentReceipthead> pgPaymentHeadModelList;
    TblMstPgPaymentReceipthead headModelSelected;
    Pgmisstockmsttbl itemModelSelected;
    String unitSelected,paymentmode;
    List<Itempurchasedbypgtbl> itempurchasedbypgtblList;

    public ArrayAdapter<TblMstPgPaymentReceipthead> headSpinAdapter;
    public ArrayAdapter<Pgmisstockmsttbl> itemSpinAdapter;
    @BindView(R.id.textView23)
    TextView pgName;
    PgItemPurcahseAdapter aAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_purchase);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        spinnerItem.setOnItemSelectedListener(this);
        spinnerUnit.setOnItemSelectedListener(this);
        spinnnerHead.setOnItemSelectedListener(this);
        spinnermodeofpayment.setOnItemSelectedListener(this);
        pgName.setText(PgActivity.pgNameSelected);

        pgPaymentHeadModelList = Select.from(TblMstPgPaymentReceipthead.class)
                .whereOr(Condition.prop("showin").eq("B"), Condition.prop("showin").eq("P"))
                .list();
        pgmisstockmsttblList = Pgmisstockmsttbl.listAll(Pgmisstockmsttbl.class);

        setSpinners();
        setRecyclerView();


    }

    private void setRecyclerView() {
        itempurchasedbypgtblList = Select.from(Itempurchasedbypgtbl.class)
                .where(Condition.prop("pgcode").eq(PgActivity.pgCodeSelected))
                .list();
        Collections.reverse(itempurchasedbypgtblList);

        aAdapter = new PgItemPurcahseAdapter(this, itempurchasedbypgtblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
    }


    private void setSpinners() {
        headSpinAdapter = new ArrayAdapter<TblMstPgPaymentReceipthead>(this, android.R.layout.simple_spinner_dropdown_item, pgPaymentHeadModelList) {

        };
        spinnnerHead.setAdapter(headSpinAdapter);

        itemSpinAdapter = new ArrayAdapter<Pgmisstockmsttbl>(this, android.R.layout.simple_spinner_dropdown_item, pgmisstockmsttblList) {

        };
        spinnerItem.setAdapter(itemSpinAdapter);
    }

    @OnClick(R.id.button4)
    public void onViewClicked() {
        if (validation()) {
            Itempurchasedbypgtbl data = new Itempurchasedbypgtbl(UUID.randomUUID().toString(),
                    itemModelSelected.getItemcode(),
                    itemModelSelected.getItemname(),
                    unitSelected,
                    etRate.getText().toString(),
                    etQuantity.getText().toString(),
                    headModelSelected.getHeadname(),
                    headModelSelected.getBudgetcode(),
                    PgActivity.pgCodeSelected,
                    new GetCurrentDate().getDate(),
                    "0",paymentmode);
            data.save();

            setRecyclerView();

            new StyleableToast
                    .Builder(this)
                    .text("Data Saved Successfully")
                    .iconStart(R.drawable.right)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
            clearForm();

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner5:
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(this, R.color.colorGrayHint));
                }
                headModelSelected = (TblMstPgPaymentReceipthead) adapterView.getSelectedItem();
                break;

            case R.id.spinner2:
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(this, R.color.colorGrayHint));
                }

                itemModelSelected = (Pgmisstockmsttbl) adapterView.getSelectedItem();
                break;

            case R.id.spinner4:

                unitSelected = adapterView.getSelectedItem().toString();
                break;

            case R.id.spinnermodeofpayment:

                paymentmode = adapterView.getSelectedItem().toString();
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validation() {
        boolean result = false;
        if (headModelSelected.getBudgetcode().equals("0")) {
            new StyleableToast
                    .Builder(this)
                    .text("Please Select Head Name")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        } else if (itemModelSelected.getItemcode().equals("0")) {
            new StyleableToast
                    .Builder(this)
                    .text("Please Select Item name")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        } else if (unitSelected.equals("Select Unit")) {
            new StyleableToast
                    .Builder(this)
                    .text("Please Select Unit")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        } else if (etRate.getText().toString().equals("")) {
            new StyleableToast
                    .Builder(this)
                    .text("Rate Can't be Empty")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        } else if (etQuantity.getText().toString().equals("")) {
            new StyleableToast
                    .Builder(this)
                    .text("Quantity Can't be Empty")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        } else if (paymentmode.equals("Select Payment")) {
            new StyleableToast
                    .Builder(this)
                    .text("Please Select Payment Mode")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }else {
            result = true;
        }

        return result;
    }

    public void clearForm() {
        setSpinners();
        spinnerUnit.setSelection(0);
        spinnermodeofpayment.setSelection(0);
        etRate.setText("");
        etQuantity.setText("");

    }
}
