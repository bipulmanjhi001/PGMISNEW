package com.jslps.pgmisnew;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.irozon.alertview.AlertActionStyle;
import com.irozon.alertview.AlertStyle;
import com.irozon.alertview.objects.AlertAction;
import com.jslps.pgmisnew.adapter.MemberDetailsActivityAdapter;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.Shgmemberslocallyaddedtbl;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;
import com.jslps.pgmisnew.interactor.MDAInteractor;
import com.jslps.pgmisnew.presenter.MDAPresenter;
import com.jslps.pgmisnew.util.Activitycode;
import com.jslps.pgmisnew.util.AlertView;
import com.jslps.pgmisnew.util.RevealClass;
import com.jslps.pgmisnew.util.SetSpinnerText;
import com.jslps.pgmisnew.view.MDAView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    @BindView(R.id.textView70)
    TextView tvItems;
    @BindView(R.id.imageView14)
    ImageView btnSearch;

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
        presenter.setPgItemNo();


        /*onclick*/
        floatActionBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.moveToNextActivity();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
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
    public void setPgItems() {
        if (pgmemtblList != null) {
            tvItems.setText("Members: " + pgmemtblList.size());
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

    @Override
    public void setViewAdapter(ConstraintLayout firstLayout, ImageView edit, ImageView delete, ImageView dropDown, TextView farmername, TextView fatherhusbandshg, TextView shg, TextView fathername, TextView husbandname, TextView designation, TextView primaryactivity, TextView fishery, TextView hva, TextView livestock, TextView ntfp, TextView memfee, TextView sharecapital, View viewLayout, ConstraintLayout layout, int adapterPostion) {
        Pgmemtbl item = pgmemtblList.get(adapterPostion);

        //visbility of edit and delete icons
        if (item.getIsexported().equals("1")) {
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);
            firstLayout.setBackgroundResource(R.drawable.item_border_view_pg_activity);

        } else {
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


        //delete and edit logics
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert(MemberDetailsActivity.this.getString(R.string.delete_sure),"",item.getId());
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pgmemtbl data =Pgmemtbl.findById(Pgmemtbl.class, item.getId());
                String pgCode = data.getPgcode();
                String grpMemCode = data.getGrpmemcode();
                String grpCode = data.getGrpcode();

                List<Shgmemberslocallyaddedtbl> list = Select.from(Shgmemberslocallyaddedtbl.class)
                        .where(Condition.prop("Pgcode").eq(pgCode))
                        .where(Condition.prop("Grpmemcode").eq(grpMemCode))
                        .where(Condition.prop("Grpcode").eq(grpCode))
                        .list();

                if(list.size()>0){
                   dialog("yes",item,list);
                }else{
                   dialog("no",item,list);
                }
            }
        });



    }

    private void dialog(String presentShgTbl,Pgmemtbl item,List<Shgmemberslocallyaddedtbl> list) {
        if(presentShgTbl.equals("yes")){
            dialog1(item,list);
        }else{
            dialog2(item);
        }
    }

    private void dialog2(Pgmemtbl Item) {
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
        farmerName.setText(Item.getMembername());
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

        String primaryActivity = Item.getPrimaryactivity();
        String designationCodee= Item.getDesignation();

        spinner.setSelection(Integer.parseInt(primaryActivity));
        spinner1.setSelection(Integer.parseInt(designationCodee));


        //save
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Pgmemtbl data =Pgmemtbl.findById(Pgmemtbl.class, Item.getId());

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
                if (primaryCodeSelected.equals("")) {
                    new AlertView(MemberDetailsActivity.this, getString(R.string.error), getString(R.string.select_primary_activity), getString(R.string.try_again));
                } else if (designationCodeSelected == 0) {
                    new AlertView(MemberDetailsActivity.this, getString(R.string.error), getString(R.string.select_designation), getString(R.string.try_again));
                } else{
                    data.setMembername(farmerName.getText().toString());
                    data.setMembershipfee(tvMemberShipFee.getText().toString());
                    data.setSharecapital(tvShareCapital.getText().toString());
                    data.setFathername(tvFatherName.getText().toString());
                    data.setHusbandname(tvHusbandName.getText().toString());
                    data.setDesignation(designationCodeSelected+"");
                    data.setPrimaryactivity(primaryCodeSelected);
                    data.setFishery(fishery);
                    data.setHva(hva);
                    data.setNtfp(ntfp);
                    data.setLivestock(livestock);
                    data.save();


                    new StyleableToast
                            .Builder(MemberDetailsActivity.this)
                            .text(getString(R.string.update_msg))
                            .iconStart(R.drawable.right)
                            .textColor(Color.WHITE)
                            .backgroundColor(getResources().getColor(R.color.colorPrimary))
                            .show();

                    new RevealClass().revealShow(dialogView, false, dialog, headerImage);

                    //refreshing
                    presenter.getPgMem(PgActivity.pgCodeSelected);
                    presenter.setRecyclerView();

                }

            }
        });

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new RevealClass().revealShow(dialogView, false, dialog, headerImage);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                new RevealClass().revealShow(dialogView, true, null, headerImage);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    new RevealClass().revealShow(dialogView, false, dialog, headerImage);
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

    private void dialog1(Pgmemtbl Item,List<Shgmemberslocallyaddedtbl> listt) {
        //present in shglocal table
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

        shgName.setText(Item.getGrpname());
        tvMemberName.setText(Item.getMembername());


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


        String primaryActivity = Item.getPrimaryactivity();
        String designationCodee= Item.getDesignation();
        String genderr = listt.get(0).getGender();
        String castt = listt.get(0).getCast();

        spinner.setSelection(Integer.parseInt(primaryActivity));
        spinner1.setSelection(Integer.parseInt(designationCodee));
        spinner2.setSelection(Integer.parseInt(genderr));
        spinner3.setSelection(Integer.parseInt(castt));


        tvFatherName.setText(Item.getFathername());
        tvHusbandName.setText(Item.getHusbandname());
        tvMemberShipFee.setText(Item.getMembershipfee());
        tvShareCapital.setText(Item.getSharecapital());

        String fisheryCS= Item.getFishery();
        String hvaCS = Item.getHva();
        String liveStockS = Item.getLivestock();
        String ntfsS = Item.getNtfp();

        if(fisheryCS.equals("0")){
            fisheryC.setChecked(false);
        }else{
            fisheryC.setChecked(true);
        }
        if(hvaCS.equals("0")){
            hvaC.setChecked(false);
        }else{
            hvaC.setChecked(true);
        }

        if(liveStockS.equals("0")){
            livestockC.setChecked(false);
        }else{
            livestockC.setChecked(true);
        }

        if(ntfsS.equals("0")){
            ntfpC.setChecked(false);
        }else{
            ntfpC.setChecked(true);
        }

        closeDialog.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                new RevealClass().revealShow(dialogView, false, dialog, headerImage);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onShow(DialogInterface dialogInterface) {
                new RevealClass().revealShow(dialogView, true, null, headerImage);
            }
        });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {

                    new RevealClass().revealShow(dialogView, false, dialog, headerImage);
                    return true;
                }

                return false;
            }
        });

        //save the updated data
        save.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Pgmemtbl data =Pgmemtbl.findById(Pgmemtbl.class, Item.getId());

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
                if (primaryCodeSelected.equals("")) {
                    new AlertView(MemberDetailsActivity.this, getString(R.string.error), getString(R.string.select_primary_activity), getString(R.string.try_again));
                } else if (designationCodeSelected == 0) {
                    new AlertView(MemberDetailsActivity.this, getString(R.string.error), getString(R.string.select_designation), getString(R.string.try_again));
                } else if(genderCodeSelected==0){
                    new AlertView(MemberDetailsActivity.this, getString(R.string.error), getString(R.string.select_gender), getString(R.string.try_again));
                }else{
                     data.setMembername(tvMemberName.getText().toString());
                     data.setMembershipfee(tvMemberShipFee.getText().toString());
                     data.setSharecapital(tvShareCapital.getText().toString());
                     data.setFathername(tvFatherName.getText().toString());
                     data.setHusbandname(tvHusbandName.getText().toString());
                     data.setDesignation(designationCodeSelected+"");
                     data.setPrimaryactivity(primaryCodeSelected);
                     data.setFishery(fishery);
                     data.setHva(hva);
                     data.setNtfp(ntfp);
                     data.setLivestock(livestock);
                     data.save();

                    listt.get(0).setMembername(tvMemberName.getText().toString());
                    listt.get(0).setFathername(tvFatherName.getText().toString());
                    listt.get(0).setHusbandname(tvHusbandName.getText().toString());
                    listt.get(0).setDesignation(designationCodeSelected+"");
                    listt.get(0).setPrimaryactivity(primaryCodeSelected);
                    listt.get(0).setFishery(fishery);
                    listt.get(0).setHva(hva);
                    listt.get(0).setNtfp(ntfp);
                    listt.get(0).setLivestock(livestock);
                    listt.get(0).setGender(genderCodeSelected+"");
                    listt.get(0).setCast(castCodeSelected+"");
                    listt.get(0).save();

                    new StyleableToast
                            .Builder(MemberDetailsActivity.this)
                            .text(getString(R.string.update_msg))
                            .iconStart(R.drawable.right)
                            .textColor(Color.WHITE)
                            .backgroundColor(getResources().getColor(R.color.colorPrimary))
                            .show();

                    new RevealClass().revealShow(dialogView, false, dialog, headerImage);

                    //refreshing
                    presenter.getPgMem(PgActivity.pgCodeSelected);
                    presenter.setRecyclerView();

                }
            }
        });


        //show dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int width = (int) (getResources().getDisplayMetrics().widthPixels * 0.90);
        int height = (int) (getResources().getDisplayMetrics().heightPixels * 0.90);
        dialog.getWindow().setLayout(width, height);

        dialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.getPgMem(PgActivity.pgCodeSelected);
        presenter.setRecyclerView();
    }

    private void alert(String heading,String message,long id){
        com.irozon.alertview.AlertView alert = new com.irozon.alertview.AlertView(heading, message, AlertStyle.DIALOG);
        alert.addAction(new AlertAction(getString(R.string.yes), AlertActionStyle.DEFAULT, action -> {
            //delete logic here
            Pgmemtbl data =Pgmemtbl.findById(Pgmemtbl.class, id);

            String pgCode = data.getPgcode();
            String grpMemCode = data.getGrpmemcode();
            String grpCode = data.getGrpcode();

            List<Shgmemberslocallyaddedtbl> list = Select.from(Shgmemberslocallyaddedtbl.class)
                    .where(Condition.prop("Pgcode").eq(pgCode))
                    .where(Condition.prop("Grpmemcode").eq(grpMemCode))
                    .where(Condition.prop("Grpcode").eq(grpCode))
                    .list();

            if(list.size()>0){
                list.get(0).delete();
            }

            boolean delete =data.delete();
            Toast.makeText(MemberDetailsActivity.this, "Date Deleted Successfully", Toast.LENGTH_LONG).show();

            //refreshing
            presenter.getPgMem(PgActivity.pgCodeSelected);
            presenter.setRecyclerView();
        }));
        alert.addAction(new AlertAction(getString(R.string.no), AlertActionStyle.DEFAULT, action -> {

        }));
        alert.show(MemberDetailsActivity.this);
    }



}
