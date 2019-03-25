package com.jslps.pgmisnew;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.objects.AlertAction;
import com.jslps.pgmisnew.adapter.AddPgMembersAdapter;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgmemberslocallyaddedtbl;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;
import com.jslps.pgmisnew.interactor.APMInteractor;
import com.jslps.pgmisnew.presenter.APMPresenter;
import com.jslps.pgmisnew.util.Activitycode;
import com.jslps.pgmisnew.util.AlertView;
import com.jslps.pgmisnew.util.RevealClass;
import com.jslps.pgmisnew.util.SetSpinnerText;
import com.jslps.pgmisnew.view.APMView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddPgMemberActivity extends AppCompatActivity implements APMView {
    @BindView(R.id.spn_shg)
    SearchableSpinner spnShg;
    @BindView(R.id.textView22)
    TextView tvPgName;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;
    @BindView(R.id.imageView2)
    ImageView headerImg;
    @BindView(R.id.floatingActionButton4)
    FloatingActionButton floatingActionBtn;

    /*Defining objects*/
    APMPresenter presenter;
    AddPgMembersAdapter aAdapter;


    /*Class Globals*/
    List<ShgModel> shgModelList;
    private String shgCodeSelected;
    private String shgNameSelected;
    List<Shgmemnonpg> shgmemnonpgList;
    List<Integer> checkboxCheckPositionsList;
    String primaryCodeSelected, primaryNameSelected, designationSelected, genderNameSelected,castNameSelected;
    int designationCodeSelected,genderCodeSelected,castCodeSelected;
    List<String> primary;
    List<String> primaryName;



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
        presenter.setZoomIn();


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
                    shgNameSelected = spnShg.getSelectedItem().toString();
                    presenter.getShgNonPgMemList(shgCodeSelected);

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

    @Override
    public void setViewAdapter(TextView farmername, TextView fatherhusbandshg, int adapterPosition, ImageView imageView) {
        Shgmemnonpg item = shgmemnonpgList.get(adapterPosition);
        farmername.setText(item.getMemname());
        fatherhusbandshg.setText(item.getFatherhusbandname());

//        if(checkboxCheckPositionsList.contains(adapterPosition)){
//            checkBox.setChecked(true);
//        }else{
//            checkBox.setChecked(false);
//        }

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openDilog(imageView, item.getMemname(), item.getGrpmemcode(), item.getShgcode(), item.getFatherhusbandname());
                //this must be implemented through interface
//                boolean isChecked = checkBox.isChecked();
//                if(isChecked) {
//                    checkboxCheckPositionsList.add(adapterPosition);
//                    openDilog(checkBox);
//                }else{
//                    if(checkboxCheckPositionsList.size()>0){
//                        for(int i=0;i<checkboxCheckPositionsList.size();i++){
//                           int value = checkboxCheckPositionsList.get(i);
//                           if(value==adapterPosition){
//                               checkboxCheckPositionsList.remove(i);
//                           }
//                        }
//                    }
//
//                }
            }
        });


    }

    private void openDilog(ImageView imageView, String farmer, String grpMemCode, String grpCode, String fatherHusbandNameShg) {
        final View dialogView = View.inflate(this, R.layout.dialog_member_details, null);
        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        //binding the views
        FloatingActionButton closeDialog = dialog.findViewById(R.id.floatingActionButton2);
        TextView farmerName = dialog.findViewById(R.id.textView50);
        Spinner spinner = dialog.findViewById(R.id.primary_activity);
        Spinner spinner1 = dialog.findViewById(R.id.primary_activity2);
        Spinner spinner2 = dialog.findViewById(R.id.select_gender);
        Spinner spinner3 = dialog.findViewById(R.id.select_cast);
        CheckBox fisheryC = dialog.findViewById(R.id.radioButton);
        CheckBox hvaC = dialog.findViewById(R.id.radioButton2);
        CheckBox livestockC = dialog.findViewById(R.id.radioButton3);
        CheckBox ntfpC = dialog.findViewById(R.id.radioButton4);
        Button save = dialog.findViewById(R.id.button2);
        TextView tvHusbandName = dialog.findViewById(R.id.et_husband_name);
        TextView tvFatherName = dialog.findViewById(R.id.et_father_name);
        TextView tvMemberShipFee = dialog.findViewById(R.id.et_membershipfee);
        TextView tvShareCapital = dialog.findViewById(R.id.et_sharecapital);


        //logics
        List<Pgtbl> list = Select.from(Pgtbl.class)
                .where(Condition.prop("Pgcode").eq(PgActivity.pgCodeSelected))
                .list();
        List<String> primaryCSV;
        primary = new ArrayList<>();
        primary.add("");
        primaryName = new ArrayList<>();
        primaryName.add("प्राथमिक गतिविधि का चयन करें");

        String fishery = "", hva = "", livestock = "", ntfp = "";

        if (list.size() > 0) {
            String primaryActivity = list.get(0).getPrimaryactivity();
            primaryCSV = Arrays.asList(primaryActivity.split(","));
            primary.addAll(primaryCSV);

            fishery = list.get(0).getFishery();
            hva = list.get(0).getHva();
            livestock = list.get(0).getLivestock();
            ntfp = list.get(0).getNtfp();

            System.out.print("");
        }

        if (fishery.equals("0")) {
            fisheryC.setEnabled(false);
        }
        if (hva.equals("0")) {
            hvaC.setEnabled(false);
        }

        if (livestock.equals("0")) {
            livestockC.setEnabled(false);
        }

        if (ntfp.equals("0")) {
            ntfpC.setEnabled(false);
        }

        if (primary.size() > 0) {
            for (int i = 1; i < primary.size(); i++) {
                String name = new Activitycode(this).primaryActivity(Integer.parseInt(primary.get(i)));
                primaryName.add(name);
            }

        }

        List<Integer> designationCode = new ArrayList<>();
        designationCode.add(0);
        List<String> designation = new ArrayList<>();
        designation.add("पदनाम का चयन करें");
        for (int i = 1; i < 5; i++) {
            designationCode.add(i);
            designation.add(new Activitycode(this).designation(i));
        }


        //setting the values
        farmerName.setText(farmer);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, primaryName) {
        };

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                primaryCodeSelected = primary.get(position);
                primaryNameSelected = primaryName.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, designation) {
        };

        spinner1.setAdapter(spinnerAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designationCodeSelected = designationCode.get(position);
                designationSelected = designation.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //save
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (primaryCodeSelected.equals("")) {
                    new AlertView(AddPgMemberActivity.this, getString(R.string.error), getString(R.string.select_primary_activity), getString(R.string.try_again));
                } else if (designationCodeSelected == 0) {
                    new AlertView(AddPgMemberActivity.this, getString(R.string.error), getString(R.string.select_designation), getString(R.string.try_again));
                } else {
                    //saving the data pgmemtbl
                    String fishery = "0";
                    String hva = "0";
                    String ntfp = "0";
                    String livestock = "0";

                    boolean fisheryB = fisheryC.isChecked();
                    boolean hvaB = hvaC.isChecked();
                    boolean ntfpB = ntfpC.isChecked();
                    boolean livestockB = livestockC.isChecked();
                    if (fisheryB) {
                        fishery = "1";
                    }
                    if (hvaB) {
                        hva = "1";
                    }
                    if (ntfpB) {
                        ntfp = "1";
                    }
                    if (livestockB) {
                        livestock = "1";
                    }

                    List<Shgtbl> list = Select.from(Shgtbl.class)
                            .where(Condition.prop("Shgcode").eq(grpCode))
                            .list();
                    String grpName = list.get(0).getShgname();

                    Pgmemtbl data = new Pgmemtbl(
                            PgActivity.pgCodeSelected,
                            grpMemCode,
                            grpCode,
                            farmer,
                            tvMemberShipFee.getText().toString(),
                            tvShareCapital.getText().toString(),
                            tvFatherName.getText().toString(),
                            tvHusbandName.getText().toString(),
                            designationCodeSelected + "",
                            fatherHusbandNameShg,
                            primaryCodeSelected,
                            fishery,
                            hva,
                            ntfp,
                            livestock,
                            grpName,
                            "0");

                    data.save();

                    //updating mermber as added to pg
                    List<Shgmemnonpg> list1 = Select.from(Shgmemnonpg.class)
                            .where(Condition.prop("Shgcode").eq(grpCode))
                            .where(Condition.prop("Grpmemcode").eq(grpMemCode))
                            .list();
                    if (list1.size() > 0) {
                        for (int i = 0; i < list1.size(); i++) {
                            list1.get(i).setAddedtopg("1");
                            list1.get(i).save();
                        }
                    }

                    new StyleableToast
                            .Builder(AddPgMemberActivity.this)
                            .text(getString(R.string.saved))
                            .iconStart(R.drawable.right)
                            .textColor(Color.WHITE)
                            .backgroundColor(getResources().getColor(R.color.colorPrimary))
                            .show();
                    new RevealClass().revealShow(dialogView, false, dialog, imageView);

                    new SetSpinnerText(spnShg, getString(R.string.select_shgg));
                    new CountDownTimer(200, 0) {
                        public void onFinish() {

                            new SetSpinnerText(spnShg, grpName);
                        }

                        public void onTick(long millisUntilFinished) {

                        }
                    }.start();


                }

            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new RevealClass().revealShow(dialogView, false, dialog, imageView);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                new RevealClass().revealShow(dialogView, true, null, imageView);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    new RevealClass().revealShow(dialogView, false, dialog, imageView);
                    return true;
                }

                return false;
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        dialog.getWindow().setLayout(width, height);

        dialog.show();


    }

    @Override
    public void setRecyclerView() {
        aAdapter = new AddPgMembersAdapter(presenter, shgmemnonpgList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);

        //Applying animation to recyclerview
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_fall_down);
        recylerList.setLayoutAnimation(animation);

        recylerList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && floatingActionBtn.getVisibility() == View.VISIBLE) {
                    floatingActionBtn.hide();
                } else if (dy < 0 && floatingActionBtn.getVisibility() != View.VISIBLE) {
                    floatingActionBtn.show();
                }
            }
        });
    }

    @Override
    public void setShgMemNonPg(List<Shgmemnonpg> list) {
        shgmemnonpgList = list;
        presenter.setRecyclerView();


    }

    @Override
    public void setZoomIn() {
        Animation zoom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_in);
        headerImg.startAnimation(zoom);
    }

    @OnClick(R.id.floatingActionButton4)
    public void onViewClicked() {
        if(shgCodeSelected.equals("")){
            openDilog1(getString(R.string.no));
        }else{
            alert(shgNameSelected,"क्या यह सदस्य "+shgNameSelected+" का है?");
        }

    }

    private void alert(String heading,String message){
        com.irozon.alertview.AlertView alert = new com.irozon.alertview.AlertView(heading, message, AlertStyle.DIALOG);
        alert.addAction(new AlertAction(getString(R.string.yes), AlertActionStyle.DEFAULT, action -> {
            openDilog1(getString(R.string.yes));
        }));
        alert.addAction(new AlertAction(getString(R.string.no), AlertActionStyle.DEFAULT, action -> {
            openDilog1(getString(R.string.no));
        }));
        alert.show(AddPgMemberActivity.this);
    }

    private void openDilog1(String userResponse) {
        final View dialogView = View.inflate(this, R.layout.dialog_member_details_not_in_shg, null);
        final Dialog dialog = new Dialog(this, R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        //binding the views
        TextView shgName = dialog.findViewById(R.id.textView50);
        TextView tvMemberName = dialog.findViewById(R.id.et_membername);
        FloatingActionButton closeDialog = dialog.findViewById(R.id.floatingActionButton2);
        Spinner spinner = dialog.findViewById(R.id.primary_activity);
        Spinner spinner1 = dialog.findViewById(R.id.primary_activity2);
        Spinner spinner2 = dialog.findViewById(R.id.select_gender);
        Spinner spinner3 = dialog.findViewById(R.id.select_cast);
        CheckBox fisheryC = dialog.findViewById(R.id.radioButton);
        CheckBox hvaC = dialog.findViewById(R.id.radioButton2);
        CheckBox livestockC = dialog.findViewById(R.id.radioButton3);
        CheckBox ntfpC = dialog.findViewById(R.id.radioButton4);
        Button save = dialog.findViewById(R.id.button2);
        TextView tvHusbandName = dialog.findViewById(R.id.et_husband_name);
        TextView tvFatherName = dialog.findViewById(R.id.et_father_name);
        TextView tvMemberShipFee = dialog.findViewById(R.id.et_membershipfee);
        TextView tvShareCapital = dialog.findViewById(R.id.et_sharecapital);


        //logics
        if(userResponse.equals(getString(R.string.yes))){
            shgName.setText(shgNameSelected);
            shgName.setVisibility(View.VISIBLE);
        }else{
            shgName.setText("");
            shgName.setVisibility(View.GONE);
        }

        List<Pgtbl> list = Select.from(Pgtbl.class)
                .where(Condition.prop("Pgcode").eq(PgActivity.pgCodeSelected))
                .list();
        List<String> primaryCSV;
        primary = new ArrayList<>();
        primary.add("");
        primaryName = new ArrayList<>();
        primaryName.add("प्राथमिक गतिविधि का चयन करें");

        String fishery = "", hva = "", livestock = "", ntfp = "";

        if (list.size() > 0) {
            String primaryActivity = list.get(0).getPrimaryactivity();
            primaryCSV = Arrays.asList(primaryActivity.split(","));
            primary.addAll(primaryCSV);

            fishery = list.get(0).getFishery();
            hva = list.get(0).getHva();
            livestock = list.get(0).getLivestock();
            ntfp = list.get(0).getNtfp();

            System.out.print("");
        }

        if (fishery.equals("0")) {
            fisheryC.setEnabled(false);
        }
        if (hva.equals("0")) {
            hvaC.setEnabled(false);
        }

        if (livestock.equals("0")) {
            livestockC.setEnabled(false);
        }

        if (ntfp.equals("0")) {
            ntfpC.setEnabled(false);
        }

        if (primary.size() > 0) {
            for (int i = 1; i < primary.size(); i++) {
                String name = new Activitycode(this).primaryActivity(Integer.parseInt(primary.get(i)));
                primaryName.add(name);
            }

        }

        List<Integer> designationCode = new ArrayList<>();
        designationCode.add(0);
        List<String> designation = new ArrayList<>();
        designation.add("पदनाम का चयन करें");
        for (int i = 1; i < 5; i++) {
            designationCode.add(i);
            designation.add(new Activitycode(this).designation(i));
        }



        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, primaryName) {
        };

        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                primaryCodeSelected = primary.get(position);
                primaryNameSelected = primaryName.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<String> spinnerAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, designation) {
        };

        spinner1.setAdapter(spinnerAdapter1);

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                designationCodeSelected = designationCode.get(position);
                designationSelected = designation.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        List<Integer> genderCode = new ArrayList<>();
        genderCode.add(0);
        genderCode.add(1);
        genderCode.add(2);
        List<String> genderName = new ArrayList<>();
        genderName.add(getString(R.string.select_gender));
        genderName.add(getString(R.string.male));
        genderName.add(getString(R.string.female));

        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genderName) {
        };

        spinner2.setAdapter(spinnerAdapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                genderCodeSelected = genderCode.get(position);
                genderNameSelected = genderName.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<Integer> castCode = new ArrayList<>();
        castCode.add(0);
        castCode.add(1);
        castCode.add(2);
        castCode.add(3);
        castCode.add(4);

        List<String> castName = new ArrayList<>();
        castName.add(getString(R.string.cast));
        castName.add("SC");
        castName.add("ST");
        castName.add("OBC");
        castName.add("Others");

        ArrayAdapter<String> spinnerAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, castName) {
        };

        spinner3.setAdapter(spinnerAdapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                castCodeSelected = castCode.get(position);
                castNameSelected = castName.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if (primaryCodeSelected.equals("")) {
                    new AlertView(AddPgMemberActivity.this, getString(R.string.error), getString(R.string.select_primary_activity), getString(R.string.try_again));
                } else if (designationCodeSelected == 0) {
                    new AlertView(AddPgMemberActivity.this, getString(R.string.error), getString(R.string.select_designation), getString(R.string.try_again));
                } else if(genderCodeSelected==0){
                    new AlertView(AddPgMemberActivity.this, getString(R.string.error), getString(R.string.select_gender), getString(R.string.try_again));
                }else{
                    //saving the data pgmemtbl
                    String fishery = "0";
                    String hva = "0";
                    String ntfp = "0";
                    String livestock = "0";

                    boolean fisheryB = fisheryC.isChecked();
                    boolean hvaB = hvaC.isChecked();
                    boolean ntfpB = ntfpC.isChecked();
                    boolean livestockB = livestockC.isChecked();
                    if (fisheryB) {
                        fishery = "1";
                    }
                    if (hvaB) {
                        hva = "1";
                    }
                    if (ntfpB) {
                        ntfp = "1";
                    }
                    if (livestockB) {
                        livestock = "1";
                    }

                    String uuid = UUID.randomUUID().toString();
                    Pgmemtbl data = new Pgmemtbl(
                            PgActivity.pgCodeSelected,
                            uuid,
                            shgCodeSelected,
                            tvMemberName.getText().toString(),
                            tvMemberShipFee.getText().toString(),
                            tvShareCapital.getText().toString(),
                            tvFatherName.getText().toString(),
                            tvHusbandName.getText().toString(),
                            designationCodeSelected + "",
                            "",
                            primaryCodeSelected,
                            fishery,
                            hva,
                            ntfp,
                            livestock,
                            shgNameSelected,
                            "0");

                    data.save();

                    Shgmemberslocallyaddedtbl data1 = new Shgmemberslocallyaddedtbl(PgActivity.pgCodeSelected,
                            uuid,
                            shgCodeSelected,
                            tvMemberName.getText().toString(),
                            tvFatherName.getText().toString(),
                            tvHusbandName.getText().toString(),
                            designationCodeSelected + "",
                            primaryCodeSelected,
                            fishery,
                            hva,
                            ntfp,
                            livestock,
                            "1",
                             uuid,
                            "0",
                            genderCodeSelected+"",
                            castCodeSelected+"");
                    data1.save();

                    new StyleableToast
                            .Builder(AddPgMemberActivity.this)
                            .text(getString(R.string.saved))
                            .iconStart(R.drawable.right)
                            .textColor(Color.WHITE)
                            .backgroundColor(getResources().getColor(R.color.colorPrimary))
                            .show();

                    new RevealClass().revealShow(dialogView, false, dialog, headerImg);

                }
            }
        });


        closeDialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new RevealClass().revealShow(dialogView, false, dialog, headerImg);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                new RevealClass().revealShow(dialogView, true, null, headerImg);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    new RevealClass().revealShow(dialogView, false, dialog, headerImg);
                    return true;
                }

                return false;
            }
        });


        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        dialog.getWindow().setLayout(width, height);

        dialog.show();
    }
}
