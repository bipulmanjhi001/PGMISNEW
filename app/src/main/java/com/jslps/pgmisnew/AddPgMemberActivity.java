package com.jslps.pgmisnew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.interactor.APMInteractor;
import com.jslps.pgmisnew.presenter.APMPresenter;
import com.jslps.pgmisnew.view.APMView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPgMemberActivity extends AppCompatActivity implements APMView {
    @BindView(R.id.spn_shg)
    SearchableSpinner spnShg;
    @BindView(R.id.textView22)
    TextView tvPgName;

    /*Defining objects*/
    APMPresenter presenter;


    /*Class Globals*/
    List<ShgModel> shgModelList;

    private String shgCodeSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pg_member);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        /*initialiazation*/
        presenter = new APMPresenter(this, new APMInteractor());

        /*calling presenter methods*/
        presenter.getShg(PgActivity.pgCodeSelected);
        presenter.setShgSpinner();
        presenter.setPgname();

    }


    @Override
    public void setShgList(List<ShgModel> list) {
        shgModelList = list;
    }

    @Override
    public void setShgSpinner() {
        List<String> shgCodeList, shgNameList;
        ArrayAdapter<String> spinnerAdapter;
        spnShg.setTitle(getString(R.string.select_shgg));
        spnShg.setPositiveButton(getString(R.string.close));

        if (shgModelList.size() > 0) {
            shgCodeList = new ArrayList<>();
            shgNameList = new ArrayList<>();
            shgCodeList.add("");
            shgNameList.add(getString(R.string.select_shgg));
            for (int i = 0; i < shgModelList.size(); i++) {
                String shgCode = shgModelList.get(i).getShgcode();
                String shgName = shgModelList.get(i).getShgname();
                shgCodeList.add(shgCode);
                shgNameList.add(shgName);
            }


            spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, shgNameList) {
            };
            spnShg.setAdapter(spinnerAdapter);

            spnShg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextSize(16);
                    shgCodeSelected = shgCodeList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

    }

    @Override
    public void setPgName() {
        tvPgName.setText(PgActivity.pgNameSelected);
    }
}
