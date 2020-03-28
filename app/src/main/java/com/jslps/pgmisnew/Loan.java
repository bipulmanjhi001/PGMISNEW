package com.jslps.pgmisnew;

import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.PgPreLoanAdapter;
import com.jslps.pgmisnew.database.Itempurchasedbypgtbl;
import com.jslps.pgmisnew.database.PgmisBatchLoantbl;
import com.jslps.pgmisnew.database.PgmisLoantbl;
import com.jslps.pgmisnew.util.GetCurrentDate;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Loan extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
    @BindView(R.id.constraintLayout2)
    ConstraintLayout constraintLayout2;
    @BindView(R.id.button4)
    ImageView button4;
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
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.parentContainer)
    ConstraintLayout parentContainer;
    @BindView(R.id.textView24)
    TextView textView24;
    @BindView(R.id.loanbatchbutton)
    ImageView loanbatchbutton;
    @BindView(R.id.textView101)
    TextView textView101;
    @BindView(R.id.textView102)
    TextView textView102;
    @BindView(R.id.textView104)
    TextView textView104;

    List<Itempurchasedbypgtbl> itempurchasedbypgtblList;
    List<Itempurchasedbypgtbl> itempurchasedbypgtblList_new;
    List<String> itemcodeString;
    public ArrayAdapter<Itempurchasedbypgtbl> itemSpinAdapter;

    String pgname, pgcode, grpcode, grpmemcode, memname;

    Itempurchasedbypgtbl itempurchasedbypgtblSelected;
    String unitSelected;
    PgPreLoanAdapter aAdapter;

    List<PgmisLoantbl> pgmisLoantblList;
    private static double thresholdloanamount = 3300;
    double quantityFinal;
    List<PgmisBatchLoantbl> pgmisBatchLoantblList;
    double loantakenbymem=0;
    double remainingloanamount=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        pgname = PgActivity.pgNameSelected;
        Intent intent = getIntent();
        pgcode = intent.getStringExtra("pgcode");
        grpcode = intent.getStringExtra("grpcode");
        grpmemcode = intent.getStringExtra("grpmemcode");
        memname = intent.getStringExtra("membername");
        getLoanDetailsMember();

        textView24.setText(memname);
        textView23.setText(pgname);

        spinner2.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);


        itempurchasedbypgtblList = Select.from(Itempurchasedbypgtbl.class)
                .where(Condition.prop("pgcode").eq(PgActivity.pgCodeSelected))
                .list();
        itemcodeString = new ArrayList<>();
        itempurchasedbypgtblList_new = new ArrayList<>();

        //adding quantity of same item
        for (int i = 0; i < itempurchasedbypgtblList.size(); i++) {
            String itemcode = itempurchasedbypgtblList.get(i).getItemcode();
            if (!itemcodeString.contains(itemcode)) {
                Itempurchasedbypgtbl itempurchasedbypgtbl = new Itempurchasedbypgtbl();
                itempurchasedbypgtbl.setItemname(itempurchasedbypgtblList.get(i).getItemname());
                itempurchasedbypgtbl.setItemcode(itempurchasedbypgtblList.get(i).getItemcode());
                itempurchasedbypgtbl.setBudgetcode(itempurchasedbypgtblList.get(i).getBudgetcode());
                itempurchasedbypgtbl.setBudgetname(itempurchasedbypgtblList.get(i).getBudgetname());
                itempurchasedbypgtbl.setPgcode(itempurchasedbypgtblList.get(i).getPgcode());
                itempurchasedbypgtbl.setEntrydate(itempurchasedbypgtblList.get(i).getEntrydate());

                itempurchasedbypgtbl.setRate(itempurchasedbypgtblList.get(i).getRate());

                if (itempurchasedbypgtblList.get(i).getUnit().equals("Kg")) {
                    double quantity = Double.parseDouble(itempurchasedbypgtblList.get(i).getQuantity()) * 1000; //changing to gram
                    itempurchasedbypgtbl.setQuantity(quantity + "");

                } else {
                    itempurchasedbypgtbl.setQuantity(itempurchasedbypgtblList.get(i).getQuantity());
                }

                itempurchasedbypgtbl.setUnit("gram");
                itempurchasedbypgtbl.setUuid(itempurchasedbypgtblList.get(i).getUuid());
                itemcodeString.add(itempurchasedbypgtblList.get(i).getItemcode());
                itempurchasedbypgtblList_new.add(itempurchasedbypgtbl);
            } else {
                for (int j = 0; j < itempurchasedbypgtblList_new.size(); j++) {
                    String itemcode_new = itempurchasedbypgtblList_new.get(j).getItemcode();
                    if (itemcode_new.equals(itemcode)) {
                        String quantity_new = itempurchasedbypgtblList_new.get(j).getQuantity();
                        String quantity = itempurchasedbypgtblList.get(i).getQuantity();
                        String unit = itempurchasedbypgtblList.get(i).getUnit();
                        if (unit.equals("Kg")) {
                            quantity = Double.parseDouble(quantity) * 1000 + "";
                        }

                        double new_quantity = Double.parseDouble(quantity_new) + Double.parseDouble(quantity);
                        Itempurchasedbypgtbl model_new = new Itempurchasedbypgtbl();
                        model_new.setItemname(itempurchasedbypgtblList.get(i).getItemname());
                        model_new.setItemcode(itempurchasedbypgtblList.get(i).getItemcode());
                        model_new.setQuantity(new_quantity + "");
                        model_new.setUnit("gram");
                        itempurchasedbypgtblList_new.set(j, model_new);

                        //changing j to size of new list to stop for loop second
                        j = itempurchasedbypgtblList_new.size();
                    }
                }
            }
        }

        Itempurchasedbypgtbl model1 = new Itempurchasedbypgtbl();
        model1.setItemname("Select Item Name");
        itempurchasedbypgtblList_new.add(model1);
        Collections.reverse(itempurchasedbypgtblList_new);


        setSpinners();
        setRecyclerView();
    }

    private void getLoanDetailsMember() {
        loantakenbymem=0;
        textView101.setText(thresholdloanamount+"");
        pgmisBatchLoantblList = Select.from(PgmisBatchLoantbl.class)
                .where(Condition.prop("pgcode").eq(pgcode))
                .where(Condition.prop("grpcode").eq(grpcode))
                .where(Condition.prop("grpmemcode").eq(grpmemcode))
                .list();
        for(int i=0;i<pgmisBatchLoantblList.size();i++){
            double amount = Double.parseDouble(pgmisBatchLoantblList.get(i).getAmount());
            loantakenbymem =loantakenbymem+amount;
        }

        textView102.setText("-"+loantakenbymem);
        remainingloanamount = thresholdloanamount - loantakenbymem;
        textView104.setText(""+remainingloanamount);
    }

    private void setRecyclerView() {
        pgmisLoantblList = Select.from(PgmisLoantbl.class)
                .where(Condition.prop("pgcode").eq(pgcode))
                .where(Condition.prop("grpcode").eq(grpcode))
                .where(Condition.prop("grpmemcode").eq(grpmemcode))
                .where(Condition.prop("appliedforloan").eq("0"))
                .list();
        Collections.reverse(pgmisLoantblList);

        aAdapter = new PgPreLoanAdapter(this, pgmisLoantblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
    }

    private void setSpinners() {

        itemSpinAdapter = new ArrayAdapter<Itempurchasedbypgtbl>(this, android.R.layout.simple_spinner_dropdown_item, itempurchasedbypgtblList_new) {

        };
        spinner2.setAdapter(itemSpinAdapter);

    }

    @OnClick(R.id.button4)
    public void onViewClicked() {
        if (validation()) {
            PgmisLoantbl data = new PgmisLoantbl(UUID.randomUUID().toString(),
                    pgcode,
                    grpcode,
                    grpmemcode,
                    itempurchasedbypgtblSelected.getItemcode(),
                    itempurchasedbypgtblSelected.getItemname(),
                    etRate.getText().toString(),
                    unitSelected,
                    "0",
                    new GetCurrentDate().getDate(),
                    "",
                    "0",
                    etQuantity.getText().toString());
            data.save();
            clearForm();
            setRecyclerView();
            new StyleableToast
                    .Builder(this)
                    .text("Data Saved Successfully")
                    .iconStart(R.drawable.right)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();

            //updating item purchase table

        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        switch (adapterView.getId()) {
            case R.id.spinner2:
                // First item will be gray
                if (position == 0) {
                    ((TextView) view).setTextColor(ContextCompat.getColor(this, R.color.colorGrayHint));
                }

                itempurchasedbypgtblSelected = (Itempurchasedbypgtbl) adapterView.getSelectedItem();
                if (!itempurchasedbypgtblSelected.getItemname().equals("Select Item Name")) {
                    String todisplayquantity;


                    //calculating the quantity distributed by pg of this item to find remaining quantity
                    List<PgmisLoantbl> list = Select.from(PgmisLoantbl.class)
                            .where(Condition.prop("pgcode").eq(PgActivity.pgCodeSelected))
                            .where(Condition.prop("itemcode").eq(itempurchasedbypgtblSelected.getItemcode()))
                            .list();
                    double quantitygivenasloan = 0;

                    for (int i = 0; i < list.size(); i++) {
                        String unitthis = list.get(i).getUnit();
                        double quantitythis;
                        if (unitthis.equals("Kg")) {
                            quantitythis = Double.parseDouble(list.get(i).getQuantity()) * 1000;
                        } else {
                            quantitythis = Double.parseDouble(list.get(i).getQuantity());
                        }
                        quantitygivenasloan = quantitygivenasloan + quantitythis;
                    }

                    quantityFinal = Double.parseDouble(itempurchasedbypgtblSelected.getQuantity()) - quantitygivenasloan;

                    if ((quantityFinal / 1000) < 1) {
                        todisplayquantity = quantityFinal + " gram";
                    } else {
                        todisplayquantity = quantityFinal / 1000 + " Kg";
                    }

                    textInputLayout7.setHint("Enter Quantity (Available Quantity:" + todisplayquantity + ")");

                } else {
                    textInputLayout7.setHint("Enter Quantity");
                }
                break;

            case R.id.spinner4:

                unitSelected = adapterView.getSelectedItem().toString();
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private boolean validation() {
        boolean result = false;
        if (itempurchasedbypgtblSelected.getItemname().equals("Select Item Name")) {
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
        } else if (!etQuantity.getText().toString().equals("")) {
            double quantity = Double.parseDouble(etQuantity.getText().toString());
            if (unitSelected.equals("Kg")) {
                quantity = quantity * 1000;
            }

            if (quantity > quantityFinal) {
                new StyleableToast
                        .Builder(this)
                        .text("Quantity is higher than Available Stock")
                        .iconStart(R.drawable.wrong_icon_white)
                        .textColor(Color.WHITE)
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            } else {
                //checking for threshold loan amount
                double amount = Double.parseDouble(etRate.getText().toString()) * Double.parseDouble(etQuantity.getText().toString());

                double amountsum=0;
                for (int i = 0; i < pgmisLoantblList.size(); i++) {
                    double ratethis = Double.parseDouble(pgmisLoantblList.get(i).getRate());
                    double quantitythis = Double.parseDouble(pgmisLoantblList.get(i).getQuantity());
                    double amountthis = ratethis * quantitythis;
                    amountsum = amountsum + amountthis;
                }


                if (amount > (remainingloanamount-amountsum)) {
                    new StyleableToast
                            .Builder(this)
                            .text("Loan amount can't be greater than " + (remainingloanamount-amountsum))
                            .iconStart(R.drawable.wrong_icon_white)
                            .textColor(Color.WHITE)
                            .backgroundColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                } else {
                    result = true;
                }

            }
        }

        return result;
    }

    public void clearForm() {
        setSpinners();
        spinner4.setSelection(0);
        etRate.setText("");
        etQuantity.setText("");

    }

    @OnClick(R.id.loanbatchbutton)
    public void onViewClicked1() {
        StringBuilder uuids = new StringBuilder();
        double amount = 0;
        if (pgmisLoantblList.size() > 0) {
            for (int i = 0; i < pgmisLoantblList.size(); i++) {
                String uuid = pgmisLoantblList.get(i).getUuid();
                uuids.append(uuid);
                double rate = Double.parseDouble(pgmisLoantblList.get(i).getRate());
                double quantity = Double.parseDouble(pgmisLoantblList.get(i).getQuantity());
                double amountthis = rate * quantity;
                amount = amount + amountthis;
                if (i < pgmisLoantblList.size() - 1) {
                    uuids.append(",");
                }

                pgmisLoantblList.get(i).setAppliedforloan("1");
                pgmisLoantblList.get(i).save();
            }
            PgmisBatchLoantbl data = new PgmisBatchLoantbl(UUID.randomUUID().toString(), uuids.toString(), pgcode, grpcode, grpmemcode, new GetCurrentDate().getDate(), "0", amount + "");
            data.save();

            new StyleableToast
                    .Builder(this)
                    .text("Successfully applied for loan")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
            getLoanDetailsMember();
            setRecyclerView();
        } else {
            new StyleableToast
                    .Builder(this)
                    .text("Please add item first")
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }


    }
}
