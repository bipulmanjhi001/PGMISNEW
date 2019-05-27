package com.jslps.pgmisnew;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.jslps.pgmisnew.adapter.PgActivityAdapter;
import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.Shgmemberslocallyaddedtbl;
import com.jslps.pgmisnew.interactor.PgActivityInteractor;
import com.jslps.pgmisnew.presenter.PgActivityPresenter;
import com.jslps.pgmisnew.util.AppConstant;
import com.jslps.pgmisnew.util.CheckConnectivity;
import com.jslps.pgmisnew.util.GetUrlUploadSHGANDPG;
import com.jslps.pgmisnew.util.ManualJsonConvert;
import com.jslps.pgmisnew.util.VolleyString;
import com.jslps.pgmisnew.view.PgActivityView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PgActivity extends AppCompatActivity implements PgActivityView, VolleyString.VolleyListner {
    @BindView(R.id.spinner)
    SearchableSpinner spinner;
    @BindView(R.id.recyler_list)
    RecyclerView recylerView;
    @BindView(R.id.imageView2)
    ImageView imgHeaderLogo;
    @BindView(R.id.upload)
    ImageView upload;


    /*Defining objects*/
    PgActivityPresenter presenter;
    PgActivityAdapter aAdapter;


    /*Class Globals*/
    List<PgActivityModel> listPgActivity;
    public static String pgCodeSelected = "";
    public static String pgNameSelected = "";
    public static List<Pgmemtbl> pgmemtblList;
    public static List<Shgmemberslocallyaddedtbl> shgmemberslocallyaddedtblList;
    ProgressDialog progress;


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
        presenter.getPgMemberstblandShgMemrtbl();

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
                        if (item.getId1() == 1) {
                            Intent intent = new Intent(PgActivity.this, MemberDetailsActivity.class);
                            startActivity(intent);
                        } else if (item.getId1() == 3) {
                            Intent intent = new Intent(PgActivity.this, ShareCapitalActivity.class);
                            startActivity(intent);
                        } else {
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
                        if (item.getId2() == 2) {
                            Intent intent = new Intent(PgActivity.this, MemberShipFeeActivity.class);
                            startActivity(intent);
                        } else if (item.getId2() == 4) {
                            Intent intent = new Intent(PgActivity.this, MeetingDetailsOfPg.class);
                            startActivity(intent);
                        } else {
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
        if (list.size() > 0) {
            pgCodeList = new ArrayList<>();
            pgNameList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String pgCode = list.get(i).getPgcode();
                String pgName = list.get(i).getPgname();
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

    @Override
    public void uploadHide() {
        upload.setVisibility(View.GONE);
    }

    @Override
    public void uploadUnhide() {
        upload.setVisibility(View.VISIBLE);
    }


    @Override
    public void pgMemsShgMems() {
        pgmemtblList = Select.from(Pgmemtbl.class)
                .whereOr(Condition.prop("Isexported").eq(0),Condition.prop("Isupdated").eq(1))
                .list();

        shgmemberslocallyaddedtblList =Select.from(Shgmemberslocallyaddedtbl.class)
                .where(Condition.prop("Isexported").eq(0))
                .list();

        if(pgmemtblList.size()>0||shgmemberslocallyaddedtblList.size()>0){
          presenter.uploadButtonUnhide();
        }else{
          presenter.uploadButtonHide();
        }
    }

    @Override
    public void callUploadApi(String sData,String sData1) {
        CheckConnectivity checkConnectivity = new CheckConnectivity();
        if(checkConnectivity.CheckConnection(this)){
            RequestQueue mRequestQueue;
            StringRequest mStringRequest;
            mRequestQueue = Volley.newRequestQueue(this);
            mStringRequest = new VolleyString(new GetUrlUploadSHGANDPG(AppConstant.domain,AppConstant.Upload_tblMstGroupMembers_Johar,sData,sData1).getUrl(),AppConstant.tblMstGroupMembers_Johar,this).getString();
            mRequestQueue.add(mStringRequest);
        }else{
            new StyleableToast
                    .Builder(this)
                    .text(getString(R.string.internet_error))
                    .iconStart(R.drawable.wrong_icon_white)
                    .textColor(Color.WHITE)
                    .backgroundColor(getResources().getColor(R.color.colorPrimary))
                    .show();
        }
    }

    @OnClick(R.id.imageView2)
    public void onViewClicked() {
    }

    @OnClick(R.id.upload)
    public void onViewClicked1() {

        String json1="",json2="";

        if(pgmemtblList.size()>0){
            String jsonString = new ManualJsonConvert("tblProducerGroupMembers").ConvertJson();
            json1 = jsonString;
        }

        if(shgmemberslocallyaddedtblList.size()>0){
            String jsonString = new ManualJsonConvert("tblMstGroupMembers_Johar").ConvertJson();
            json2 = jsonString;
        }

        DialogShow();
        presenter.callUploadApi(json2,json1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPgMemberstblandShgMemrtbl();
    }

    @Override
    public void onResponseSuccess(String tableIndentifier, String result) {

        if(tableIndentifier.equals(AppConstant.tblMstGroupMembers_Johar)){
            if(result.equals("[]")){
                new StyleableToast
                        .Builder(this)
                        .text("Upload Failed Due to no Response from server")
                        .iconStart(R.drawable.wrong_icon_white)
                        .textColor(Color.WHITE)
                        .backgroundColor(getResources().getColor(R.color.colorPrimary))
                        .show();
            }else{

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONArray jsonArray = jsonObject.getJSONArray("Table");
                    JSONObject object = jsonArray.getJSONObject(0);
                    String value = object.optString("RetValue");
                    if(value.equals("1")){
                        if(pgmemtblList.size()>0){
                            for(int i=0;i<pgmemtblList.size();i++){
                                pgmemtblList.get(i).setIsexported("1");
                                pgmemtblList.get(i).setIsupdated("0");
                                pgmemtblList.get(i).save();
                            }
                        }

                        if(shgmemberslocallyaddedtblList.size()>0){
                            for(int i=0;i<shgmemberslocallyaddedtblList.size();i++){
                                shgmemberslocallyaddedtblList.get(i).setIsexported("1");
                                shgmemberslocallyaddedtblList.get(i).save();
                            }
                        }
                        DialogClose();

                        new StyleableToast
                                .Builder(this)
                                .text("Successfully Uploaded")
                                .iconStart(R.drawable.right)
                                .textColor(Color.WHITE)
                                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                                .show();


                        //
                        presenter.getPgMemberstblandShgMemrtbl();


                    }else{
                        DialogClose();
                        new StyleableToast
                                .Builder(this)
                                .text("Upload Failed Response is Other than 1")
                                .iconStart(R.drawable.wrong_icon_white)
                                .textColor(Color.WHITE)
                                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                                .show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onResponseFailure(String tableIdentifier) {
        DialogClose();
        new StyleableToast
                .Builder(this)
                .text("UploaFd ailed Due to server error")
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    private void DialogShow() {
        progress= new ProgressDialog(this);
        progress = new ProgressDialog(this);
        progress.setMessage("अपलोड जारी है, कृपया प्रतीक्षा करें");
        progress.setCancelable(false);
        progress.show();
    }

    private void DialogClose(){
        if(progress!=null){
            progress.dismiss();
        }
    }
}
