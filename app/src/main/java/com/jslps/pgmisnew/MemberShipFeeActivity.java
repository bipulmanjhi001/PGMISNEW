package com.jslps.pgmisnew;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.MemberShipFeeActivityAdapter;
import com.jslps.pgmisnew.database.MembershipFeeModel;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.MFAInteractor;
import com.jslps.pgmisnew.presenter.MFAPresenter;
import com.jslps.pgmisnew.util.AppConstant;
import com.jslps.pgmisnew.view.MFAView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MemberShipFeeActivity extends AppCompatActivity implements MFAView {
    @BindView(R.id.imageView2)
    ImageView headerImg;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton floatingActionButton;
    @BindView(R.id.textView23)
    TextView tvPgName;
    @BindView(R.id.save)
    Button btnSave;

    /*Defining objects*/
    MFAPresenter presenter;
    MemberShipFeeActivityAdapter aAdapter;


    /*Class Globals*/
    List<Pgmemtbl> pgmemtblList;
    List<MembershipFeeModel> membershipFeeModelList;
    boolean setEditTextByCode = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_ship_fee);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        /*initialiazation*/
        presenter = new MFAPresenter(this, new MFAInteractor());

        /*calling presenter methods*/
        presenter.getPgMem(PgActivity.pgCodeSelected);
        presenter.setZoomIn();
        presenter.setPgname();
        presenter.setRecyclerView();
    }

    @Override
    public void setPgMemList(List<Pgmemtbl> list) {
        pgmemtblList = list;
    }

    @Override
    public void setZoomIn() {
        Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        headerImg.startAnimation(zoom);
    }

    @Override
    public void setPgName() {
        tvPgName.setText(PgActivity.pgNameSelected);
    }

    @Override
    public void setViewAdapter(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox) {
        Pgmemtbl item = pgmemtblList.get(adapterPosition);
        if (adapterPosition == 0) {
            viewLayout.setVisibility(View.VISIBLE);
        } else {
            viewLayout.setVisibility(View.GONE);
        }

        farmer.setText(String.format("%s(%s)", item.getMembername(), item.getGrpname()));
        total.setText(AppConstant.MEMBERSHIPFEE);
        String paidS;
        if (item.getMembershipfee() != null) {
            paidS = item.getMembershipfee();
            if (item.getMembershipfee().equals("") || item.getMembershipfee().equals("null")) {
                paidS = "0";
            }
            paid.setText(paidS);
        } else {
            paidS = "0";
            paid.setText(paidS);
        }

        float remainingAmount = Float.parseFloat(AppConstant.MEMBERSHIPFEE) - Float.parseFloat(paidS);
        remaining.setText(String.format("%s", remainingAmount));
        if(remainingAmount==0){
            firstLayout.setBackgroundResource(R.drawable.item_border_light_green);
        }else{
            firstLayout.setBackgroundResource(R.drawable.item_border_view_pg_activity);
        }

        //clearing enterAmount
        enterAmount.setText("");
        checkBox.setChecked(false);

        if (membershipFeeModelList.size() > 0) {
            for (int i = 0; i < membershipFeeModelList.size(); i++) {
                String adapterPositionM = membershipFeeModelList.get(i).getAdapterposition();
                if (adapterPositionM.equals(adapterPosition + "")) {
                    enterAmount.setText(membershipFeeModelList.get(i).getAmount());
                    checkBox.setChecked(true);
                }

            }
        }


        enterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!enterAmount.getText().toString().equals("")) {
                    float userEnteredAmount = Float.parseFloat(enterAmount.getText().toString());
                    if (userEnteredAmount > remainingAmount) {
                        new StyleableToast
                                .Builder(MemberShipFeeActivity.this)
                                .text(getString(R.string.cant_be_greater))
                                .iconStart(R.drawable.wrong_icon_white)
                                .textColor(Color.WHITE)
                                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                                .show();
                        enterAmount.setText("");
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        String finalPaidS = paidS;
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = checkBox.isChecked();
                if (isChecked) {
                    if (enterAmount.getText().toString().equals("")) {
                        checkBox.setChecked(false);
                        new StyleableToast
                                .Builder(MemberShipFeeActivity.this)
                                .text(getString(R.string.enter_mebership_fee))
                                .iconStart(R.drawable.wrong_icon_white)
                                .textColor(Color.WHITE)
                                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                                .show();
                    } else {
                        MembershipFeeModel model = new MembershipFeeModel();
                        model.setPgcode(PgActivity.pgCodeSelected);
                        model.setPgmemcode(item.getGrpmemcode());
                        model.setGrpcode(item.getGrpcode());
                        model.setAdapterposition(adapterPosition + "");
                        model.setAmount(enterAmount.getText().toString());
                        float calculatedAmount = Float.parseFloat(enterAmount.getText().toString())+Float.parseFloat(finalPaidS);
                        model.setUpdateamount(calculatedAmount+"");
                        membershipFeeModelList.add(model);
                    }

                } else {
                    if (membershipFeeModelList.size() > 0) {
                        for (int i = 0; i < membershipFeeModelList.size(); i++) {
                            int value = Integer.parseInt(membershipFeeModelList.get(i).getAdapterposition());
                            if (value == adapterPosition) {
                                membershipFeeModelList.remove(i);
                            }
                        }
                    }

                }
            }
        });

    }

    @Override
    public void setRecyclerView() {
        aAdapter = new MemberShipFeeActivityAdapter(presenter, pgmemtblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
        membershipFeeModelList = new ArrayList<>();

        //Applying animation to recyclerview
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recylerList.setLayoutAnimation(animation);
    }


    @OnClick(R.id.save)
    public void onViewClicked() {
        if(membershipFeeModelList.size()>0){
            for(int i =0;i<membershipFeeModelList.size();i++){
               String pgCode = membershipFeeModelList.get(i).getPgcode();
               String grpMemCode = membershipFeeModelList.get(i).getPgmemcode();
               String grpCode = membershipFeeModelList.get(i).getGrpcode();
               String calculatedAmount = membershipFeeModelList.get(i).getUpdateamount();

               List<Pgmemtbl> list = Select.from(Pgmemtbl.class)
                        .where(Condition.prop("Pgcode").eq(pgCode))
                        .where(Condition.prop("Grpmemcode").eq(grpMemCode))
                        .where(Condition.prop("Grpcode").eq(grpCode))
                        .list();
               list.get(0).setMembershipfee(calculatedAmount);
               list.get(0).setIsupdated("1");
               list.get(0).save();
            }
            presenter.getPgMem(PgActivity.pgCodeSelected);
            presenter.setRecyclerView();
            new StyleableToast
                    .Builder(MemberShipFeeActivity.this)
                    .text(getString(R.string.saved))
                    .iconStart(R.drawable.right)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }else{
            new StyleableToast
                    .Builder(MemberShipFeeActivity.this)
                    .text(getString(R.string.at_least_one_amount))
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }
    }
}
