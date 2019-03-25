package com.jslps.pgmisnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.MemberDetailsActivityAdapter;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.MDAInteractor;
import com.jslps.pgmisnew.presenter.MDAPresenter;
import com.jslps.pgmisnew.util.Activitycode;
import com.jslps.pgmisnew.view.MDAView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MemberDetailsActivity extends AppCompatActivity implements MDAView {

    @BindView(R.id.imageView2)
    ImageView headerImage;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.recyler_list)
    RecyclerView recyclerView;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatActionBtnAdd;
    @BindView(R.id.textView23)
    TextView tvPgName;

    /*Defining objects*/
    MDAPresenter presenter;
    MemberDetailsActivityAdapter aAdapter;


    /*Class Globals*/
    List<Pgmemtbl> pgmemtblList;


    private boolean mActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_details);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        /*initialiazation*/
        presenter = new MDAPresenter(this, new MDAInteractor());

        /*calling presenter methods*/
        presenter.getPgMem(PgActivity.pgCodeSelected);
        presenter.setZoomIn();
        presenter.setRecyclerView();
        presenter.setPgname();

        /*onclick*/
        floatActionBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveToNextActivity();
            }
        });


    }

    @Override
    public void setPgMemList(List<Pgmemtbl> list) {
        pgmemtblList = list;
    }

    @Override
    public void setZoomIn() {
        Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        headerImage.startAnimation(zoom);
    }

    @Override
    public void setRecyclerView() {
        aAdapter = new MemberDetailsActivityAdapter(presenter, pgmemtblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(verticalLayoutmanager);
        recyclerView.setAdapter(aAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatActionBtnAdd.getVisibility() == View.VISIBLE) {
                    floatActionBtnAdd.hide();
                } else if (dy < 0 && floatActionBtnAdd.getVisibility() != View.VISIBLE) {
                    floatActionBtnAdd.show();
                }
            }
        });
    }

    @Override
    public void moveToNext() {
        Intent intent = new Intent(this, AddPgMemberActivity.class);
        startActivity(intent);
    }

    @Override
    public void setPgName() {
        tvPgName.setText(PgActivity.pgNameSelected);
    }

    @Override
    public void setViewAdapter(ConstraintLayout firstLayout,ImageView edit,ImageView delete,ImageView dropDown, TextView farmername, TextView fatherhusbandshg, TextView shg, TextView fathername, TextView husbandname, TextView designation, TextView primaryactivity, TextView fishery, TextView hva, TextView livestock, TextView ntfp, TextView memfee, TextView sharecapital, View viewLayout, ConstraintLayout layout, int adapterPostion) {
        Pgmemtbl item = pgmemtblList.get(adapterPostion);

        //visbility of edit and delete icons
        if(item.getIsexported().equals("1")){
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            firstLayout.setBackgroundResource(R.drawable.item_border_view_pg_activity);

        }else{
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
            firstLayout.setBackgroundResource(R.drawable.item_border_light_green);
        }

        if (adapterPostion == 0) {
            viewLayout.setVisibility(View.VISIBLE);
        } else {
            viewLayout.setVisibility(View.GONE);
        }

        if (layout.getVisibility() == View.VISIBLE) {
            layout.setVisibility(View.GONE);
            dropDown.setRotation(0);
        }

        dropDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActive) {
                    Animation slideup = AnimationUtils.loadAnimation(MemberDetailsActivity.this, R.anim.slide_up);
                    layout.startAnimation(slideup);
                    dropDown.setRotation(0);
                    slideup.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            layout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    mActive = false;
                } else {
                    Animation slidedown = AnimationUtils.loadAnimation(MemberDetailsActivity.this, R.anim.slide_down);
                    layout.startAnimation(slidedown);
                    layout.setVisibility(View.VISIBLE);
                    dropDown.setRotation(180);
                    mActive = true;

                }
            }
        });


        //setting values
        farmername.setText(item.getMembername());
        fatherhusbandshg.setText(item.getFatherhusbandnameshg());
        shg.setText(item.getGrpname());
        fathername.setText(item.getFathername());
        husbandname.setText(item.getHusbandname());
        designation.setText(new Activitycode(this).designation(Integer.parseInt(item.getDesignation())));
        primaryactivity.setText(new Activitycode(this).primaryActivity(Integer.parseInt(item.getPrimaryactivity())));
        if (item.getFishery().equals("1")) {
            fishery.setText("Yes");
        } else {
            fishery.setText("No");
        }

        if (item.getHva().equals("1")) {
            hva.setText("Yes");
        } else {
            hva.setText("No");
        }

        if (item.getLivestock().equals("1")) {
            livestock.setText("Yes");
        } else {
            livestock.setText("No");
        }

        if (item.getNtfp().equals("1")) {
            ntfp.setText("Yes");
        } else {
            ntfp.setText("No");
        }

        memfee.setText(item.getMembershipfee());
        sharecapital.setText(item.getSharecapital());


    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPgMem(PgActivity.pgCodeSelected);
        presenter.setRecyclerView();
    }
}
