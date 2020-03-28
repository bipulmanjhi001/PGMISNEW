package com.jslps.pgmisnew;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.MemberDetailsActivityAdapter;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.MDAInteractor;
import com.jslps.pgmisnew.presenter.MDAPresenter;
import com.jslps.pgmisnew.util.Activitycode;
import com.jslps.pgmisnew.view.MDAView;

import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanMembersDetail extends AppCompatActivity implements MDAView {

    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.textView23)
    TextView textView23;
    @BindView(R.id.textView70)
    TextView textView70;
    @BindView(R.id.imageView14)
    ImageView imageView14;

    /*Defining objects*/
    MDAPresenter presenter;
    MemberDetailsActivityAdapter aAdapter;

    /*Class Globals*/
    List<Pgmemtbl> pgmemtblList;
    String primaryCodeSelected, primaryNameSelected, designationSelected, genderNameSelected,castNameSelected;
    int designationCodeSelected,genderCodeSelected,castCodeSelected;
    List<String> primary;
    List<String> primaryName;

    private boolean mActive = false;

    String fromWhichActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_members_detail);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);

        init();
    }

    private void init() {

        Intent intent = getIntent();
        fromWhichActivity = intent.getStringExtra("fromactivity");

        /*initialiazation*/
        presenter = new MDAPresenter(this, new MDAInteractor());

        /*calling presenter methods*/
        presenter.getPgMem(PgActivity.pgCodeSelected);
        presenter.setZoomIn();
        presenter.setRecyclerView();
        presenter.setPgname();
        presenter.setPgItemNo();

        imageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.search();
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
        imageView2.startAnimation(zoom);
    }

    @Override
    public void setRecyclerView() {
        aAdapter = new MemberDetailsActivityAdapter(presenter, pgmemtblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
    }

    @Override
    public void moveToNext() {

    }

    @Override
    public void setPgName() {
        textView23.setText(PgActivity.pgNameSelected);
    }

    @Override
    public void setPgItems() {
        if (pgmemtblList != null) {
            textView70.setText("Members: " + pgmemtblList.size());
        }
    }

    @Override
    public void search() {
        openDilog();
        InputMethodManager imm =
                (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }

    @Override
    public void setViewAdapter(ConstraintLayout firstLayout, ImageView edit, ImageView delete, ImageView dropDown, TextView farmername, TextView fatherhusbandshg, TextView shg, TextView fathername, TextView husbandname, TextView designation, TextView primaryactivity, TextView fishery, TextView hva, TextView livestock, TextView ntfp, TextView memfee, TextView sharecapital, View viewLayout, ConstraintLayout layout, int adapterPosition) {
        Pgmemtbl item = pgmemtblList.get(adapterPosition);

        edit.setVisibility(View.GONE);
        delete.setVisibility(View.GONE);

        if (adapterPosition == 0) {
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
                    Animation slideup = AnimationUtils.loadAnimation(LoanMembersDetail.this, R.anim.slide_up);
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
                    Animation slidedown = AnimationUtils.loadAnimation(LoanMembersDetail.this, R.anim.slide_down);
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


        //moving to next activity
        firstLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fromWhichActivity.equals("repayment")){
                    Intent intent = new Intent(LoanMembersDetail.this,LoanRepaymentActivity.class);
                    intent.putExtra("pgcode",item.getPgcode());
                    intent.putExtra("grpcode",item.getGrpcode());
                    intent.putExtra("grpmemcode",item.getGrpmemcode());
                    intent.putExtra("membername",item.getMembername());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(LoanMembersDetail.this,Loan.class);
                    intent.putExtra("pgcode",item.getPgcode());
                    intent.putExtra("grpcode",item.getGrpcode());
                    intent.putExtra("grpmemcode",item.getGrpmemcode());
                    intent.putExtra("membername",item.getMembername());
                    startActivity(intent);
                }

            }
        });


    }

    private void openDilog() {
        final View dialogView = View.inflate(this, R.layout.view_search, null);
        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        TextInputEditText etSearch = dialog.findViewById(R.id.et_search);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = etSearch.getText().toString().toLowerCase(Locale.getDefault());
                aAdapter.filter(text);
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        dialog.getWindow().setLayout(width, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.show();
        dialog.setCancelable(true);

    }
}
