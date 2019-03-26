package com.jslps.pgmisnew;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.PgActivityAdapter;
import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.interactor.PgActivityInteractor;
import com.jslps.pgmisnew.presenter.PgActivityPresenter;
import com.jslps.pgmisnew.view.PgActivityView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PgActivity extends AppCompatActivity implements PgActivityView {
    @BindView(R.id.spinner)
    SearchableSpinner spinner;
    @BindView(R.id.recyler_list)
    RecyclerView recylerView;
    @BindView(R.id.imageView2)
    ImageView imgHeaderLogo;


    /*Defining objects*/
    PgActivityPresenter presenter;
    PgActivityAdapter aAdapter;


    /*Class Globals*/
    List<PgActivityModel> listPgActivity;
    public static String pgCodeSelected="";
    public static String pgNameSelected="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {

        /*initialiazation*/
        presenter = new PgActivityPresenter(this, new PgActivityInteractor());

        /*calling presenter methods*/
        presenter.getList();
        presenter.setRecyclerView();
        presenter.setZoomIn();
        presenter.getSpinnerList();

    }


    @Override
    public void setPgActivityList(List<PgActivityModel> list) {
        listPgActivity = list;
    }

    @Override
    public void setRecyclerView() {
        aAdapter = new PgActivityAdapter(presenter, listPgActivity);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerView.setLayoutManager(verticalLayoutmanager);
        recylerView.setAdapter(aAdapter);

    }

    @Override
    public void setZoomIn() {
        Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        imgHeaderLogo.startAnimation(zoom);
    }

    @Override
    public void setViewAdapter(TextView text1, TextView text2, ImageView icon1, ImageView icon2, ConstraintLayout layout1, ConstraintLayout layout2, int adapterPostion, View viewLayout) {
        PgActivityModel item = listPgActivity.get(adapterPostion);
        if (item.getId1() == 1) {
            viewLayout.setVisibility(View.VISIBLE);
        } else {
            viewLayout.setVisibility(View.GONE);
        }

        text1.setText(item.getName1());
        text2.setText(item.getName2());
        icon1.setImageResource(item.getImageIcon1());
        icon2.setImageResource(item.getImageIcon2());
        if (item.getId2() == 8) {
            layout2.setVisibility(View.GONE);
        }


        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_layout);
                icon1.startAnimation(zoom);
                zoom.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(item.getId1()==1){
                            Intent intent = new Intent(PgActivity.this, MemberDetailsActivity.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in_layout);
                icon2.startAnimation(zoom);
                zoom.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        if(item.getId2()==2){
                            Intent intent = new Intent(PgActivity.this, MemberShipFeeActivity.class);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(PgActivity.this, Test.class);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });


    }

    @Override
    public void setPgSpinner(List<Pgtbl> list) {
        List<String> pgCodeList, pgNameList;
        ArrayAdapter<String> spinnerAdapter;
        spinner.setTitle(getString(R.string.select_pg));
        spinner.setPositiveButton(getString(R.string.close));
        if(list.size()>0){
            pgCodeList = new ArrayList<>();
            pgNameList = new ArrayList<>();
            for(int i=0;i<list.size();i++){
                String pgCode = list.get(i).getPgcode();
                String pgName = list .get(i).getPgname();
                pgCodeList.add(pgCode);
                pgNameList.add(pgName);
            }

            spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, pgNameList) {
            };
            spinner.setAdapter(spinnerAdapter);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                    ((TextView) parent.getChildAt(0)).setTextSize(20);
                     pgCodeSelected = pgCodeList.get(position);
                     pgNameSelected = pgNameList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @OnClick(R.id.imageView2)
    public void onViewClicked() {
    }
}
