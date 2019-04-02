package com.jslps.pgmisnew;

import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.jslps.pgmisnew.adapter.ShareCapitalActivityAdapter;
import com.jslps.pgmisnew.database.MembershipFeeModel;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.SHAInteractor;
import com.jslps.pgmisnew.presenter.SHAPresenter;
import com.jslps.pgmisnew.util.AppConstant;
import com.jslps.pgmisnew.view.SHAView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShareCapitalActivity extends AppCompatActivity implements SHAView {
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
    @BindView(R.id.parentContainer)
    ConstraintLayout parentContainer;


    /*Defining objects*/
    SHAPresenter presenter;
    ShareCapitalActivityAdapter aAdapter;

    /*Class Globals*/
    List<Pgmemtbl> pgmemtblList;
    List<MembershipFeeModel> membershipFeeModelList;
    boolean setEditTextByCode = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_capital);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        /*initialiazation*/
        presenter = new SHAPresenter(this, new SHAInteractor());

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
    public void setViewAdapter(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox, TextView pos) {
        pos.setText(adapterPosition + "");
        Pgmemtbl item = pgmemtblList.get(Integer.parseInt(pos.getText().toString()));
        if (adapterPosition == 0) {
            viewLayout.setVisibility(View.VISIBLE);
        } else {
            viewLayout.setVisibility(View.GONE);
        }

        farmer.setText(String.format("%s(%s)", item.getMembername(), item.getGrpname()));
        total.setText(AppConstant.SHARECAPITAL);
        String paidS;
        if (item.getSharecapital() != null) {
            paidS = item.getSharecapital();
            if (item.getSharecapital().equals("") || item.getSharecapital().equals("null")) {
                paidS = "0";
            }
            paid.setText(paidS);
        } else {
            paidS = "0";
            paid.setText(paidS);
        }

        float remainingAmount = Float.parseFloat(AppConstant.SHARECAPITAL) - Float.parseFloat(paidS);
        remaining.setText(String.format("%s", remainingAmount));
        if (remainingAmount == 0) {
            firstLayout.setBackgroundResource(R.drawable.item_border_light_green);
        } else {
            firstLayout.setBackgroundResource(R.drawable.item_border_view_pg_activity);
        }


        if (membershipFeeModelList.size() > 0) {

            String amount = membershipFeeModelList.get(adapterPosition).getAmount();
            if (amount != null) {
                setEditTextByCode = true;
                enterAmount.setText(membershipFeeModelList.get(adapterPosition).getAmount());


            } else {
                //clearing enterAmount
                //setEditTextByCode = true;
                setEditTextByCode = true;
                enterAmount.setText("");


            }

        }



    }

    private void timeDelay() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                setEditTextByCode = false;
            }
        }, 500);
    }

    @Override
    public void addTextChangeListner(ConstraintLayout firstLayout, TextView farmer, TextView total, TextView paid, TextView remaining, TextInputEditText enterAmount, View viewLayout, int adapterPosition, CheckBox checkBox, TextView pos) {
        enterAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!enterAmount.getText().equals("") && !setEditTextByCode) {
                    Pgmemtbl item = pgmemtblList.get(Integer.parseInt(pos.getText().toString()));

                    String paidS;
                    if (item.getSharecapital() != null) {
                        paidS = item.getSharecapital();
                        if (item.getSharecapital().equals("") || item.getSharecapital().equals("null")) {
                            paidS = "0";
                        }
                    } else {
                        paidS = "0";
                    }
                    String finalPaidS = paidS;
                    float remainingAmount = Float.parseFloat(AppConstant.SHARECAPITAL) - Float.parseFloat(paidS);
                    String ss = enterAmount.getText().toString();
                    if (ss.equals("")) {
                        ss = "0";
                    }


                    if (Float.parseFloat(ss) > remainingAmount) {
                        new StyleableToast
                                .Builder(ShareCapitalActivity.this)
                                .text(getString(R.string.cant_be_greater))
                                .iconStart(R.drawable.wrong_icon_white)
                                .textColor(Color.WHITE)
                                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                                .show();
                        setEditTextByCode = true;
                        enterAmount.setText("");
                        timeDelay();
                        if (membershipFeeModelList.size() > 0) {
                            add(item, enterAmount, Integer.parseInt(pos.getText().toString()), finalPaidS);

                        }

                    } else {

                        if (membershipFeeModelList.size() > 0) {
                            add(item, enterAmount, Integer.parseInt(pos.getText().toString()), finalPaidS);
                        }


                    }


                }else{
                    setEditTextByCode=false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void add(Pgmemtbl item, TextInputEditText enterAmount, int adapterPosition, String finalPaidS) {
        MembershipFeeModel model = new MembershipFeeModel();
        model.setPgcode(PgActivity.pgCodeSelected);
        model.setPgmemcode(item.getGrpmemcode());
        model.setGrpcode(item.getGrpcode());
        model.setAdapterposition(adapterPosition + "");
        model.setAmount(enterAmount.getText().toString());
        String amt = enterAmount.getText().toString();
        if (amt.equals("")) {
            amt = "0";
        }
        float calculatedAmount = Float.parseFloat(amt) + Float.parseFloat(finalPaidS);
        model.setUpdateamount(calculatedAmount + "");
        membershipFeeModelList.set(adapterPosition, model);
        System.out.print("");
    }

    @Override
    public void setRecyclerView() {
        aAdapter = new ShareCapitalActivityAdapter(presenter, pgmemtblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
        membershipFeeModelList = new ArrayList<>();
        if (pgmemtblList.size() > 0) {
            for (int i = 0; i < pgmemtblList.size(); i++) {
                MembershipFeeModel model = new MembershipFeeModel();
                model.setAdapterposition("");
                membershipFeeModelList.add(model);

            }
        }


        //Applying animation to recyclerview
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recylerList.setLayoutAnimation(animation);


    }


    @OnClick(R.id.save)
    public void onViewClicked() {
        int count = 0;
        if (membershipFeeModelList.size() > 0) {
            for (int i = 0; i < membershipFeeModelList.size(); i++) {
                String pgCode = membershipFeeModelList.get(i).getPgcode();
                String grpMemCode = membershipFeeModelList.get(i).getPgmemcode();
                String grpCode = membershipFeeModelList.get(i).getGrpcode();
                String calculatedAmount = membershipFeeModelList.get(i).getUpdateamount();
                String amount = membershipFeeModelList.get(i).getAmount();

                if (amount == null) {
                    count++;
                }

                if (amount != null) {
                    if (!amount.equals("")) {
                        List<Pgmemtbl> list = Select.from(Pgmemtbl.class)
                                .where(Condition.prop("Pgcode").eq(pgCode))
                                .where(Condition.prop("Grpmemcode").eq(grpMemCode))
                                .where(Condition.prop("Grpcode").eq(grpCode))
                                .list();
                        list.get(0).setSharecapital(calculatedAmount);
                        list.get(0).setIsupdated("1");
                        list.get(0).save();
                    }

                }

            }
            if (count == membershipFeeModelList.size()) {
                new StyleableToast
                        .Builder(ShareCapitalActivity.this)
                        .text(getString(R.string.at_least_one_amount))
                        .iconStart(R.drawable.wrong_icon_white)
                        .textColor(Color.WHITE)
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            } else {
                presenter.getPgMem(PgActivity.pgCodeSelected);
                presenter.setRecyclerView();
                new StyleableToast
                        .Builder(ShareCapitalActivity.this)
                        .text(getString(R.string.saved))
                        .iconStart(R.drawable.right)
                        .textColor(Color.WHITE)
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }

        }
    }
}
