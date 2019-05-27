package com.jslps.pgmisnew;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.MeetingAdapter;
import com.jslps.pgmisnew.database.PgMeetingtbl;
import com.jslps.pgmisnew.interactor.MeetingInteractor;
import com.jslps.pgmisnew.presenter.MeetingPresenter;
import com.jslps.pgmisnew.view.MeetingView;
import com.muddzdev.styleabletoastlibrary.StyleableToast;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MeetingDetailsOfPg extends AppCompatActivity implements MeetingView, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.imageView2)
    ImageView headerImg;
    @BindView(R.id.textView23)
    TextView tvPgname;
    @BindView(R.id.textView65)
    TextView tvDate;
    @BindView(R.id.imageView11)
    ImageView imgCalender;
    @BindView(R.id.et_no_of_mem)
    TextInputEditText etNoOfMem;
    @BindView(R.id.checkBox3)
    CheckBox chAKM;
    @BindView(R.id.checkBox4)
    CheckBox chAPS;
    @BindView(R.id.checkBox5)
    CheckBox chAMM;
    @BindView(R.id.checkBox6)
    CheckBox chMBK;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.textInputLayout3)
    TextInputLayout textInputLayout3;
    @BindView(R.id.textView66)
    TextView textView66;
    @BindView(R.id.parentContainer)
    ConstraintLayout parentContainer;
    @BindView(R.id.button4)
    Button btnSave;
    @BindView(R.id.recyler_list)
    RecyclerView recylerList;

    /*Defining objects*/
    MeetingAdapter aAdapter;
    MeetingPresenter presenter;


    /*Class Globals*/
    List<PgMeetingtbl> pgMeetingtblList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details_of_pg);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        init();
    }

    private void init() {
        /*initialiazation*/
        presenter = new MeetingPresenter(this, new MeetingInteractor());
        pgMeetingtblList  =new ArrayList<>();


        //callling presenter method
        presenter.pgName();
        presenter.getMeetingData(PgActivity.pgCodeSelected);
        presenter.callRecyclerView();
    }

    @Override
    public void setPgName() {
        tvPgname.setText(PgActivity.pgNameSelected);
    }

    @Override
    public void setOpenCalender() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                MeetingDetailsOfPg.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void setAdapter(ImageView edit, ImageView delete, TextView date, TextView no, TextView cadre, ConstraintLayout layout1, ConstraintLayout layout2,int position) {
        PgMeetingtbl item = pgMeetingtblList.get(position);

        date.setText(item.getMeetingdate());
        no.setText(item.getNoofpeople());
        cadre.setText(item.getCadres());
    }

    @Override
    public void dateNoEmpty() {
        new StyleableToast
                .Builder(this)
                .text(getString(R.string.date_cant_be_empty))
                .iconStart(R.drawable.wrong_icon_white)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
    }

    @Override
    public void saveData() {
        String meetingId = UUID.randomUUID().toString();
        StringBuilder builder = new StringBuilder();
        if (chAKM.isChecked()) {
            builder.append("AKM");
            builder.append(",");
        }
        if (chAMM.isChecked()) {
            builder.append("AMM");
            builder.append(",");
        }
        if (chAPS.isChecked()) {
            builder.append("APS");
            builder.append(",");
        }
        if (chMBK.isChecked()) {
            builder.append("MBK");
            builder.append(",");
        }
        PgMeetingtbl data = new PgMeetingtbl(meetingId, tvDate.getText().toString(), etNoOfMem.getText().toString(), builder.toString(), PgActivity.pgCodeSelected,"0");
        data.save();

        new StyleableToast
                .Builder(this)
                .text(getString(R.string.meeting_saved_successfully))
                .iconStart(R.drawable.right)
                .textColor(Color.WHITE)
                .backgroundColor(getResources().getColor(R.color.colorPrimary))
                .show();
        clearForm();

        presenter.callRecyclerView();
    }

    private void clearForm() {
        tvDate.setText("");
        etNoOfMem.setText("");
        chMBK.setChecked(false);
        chAKM.setChecked(false);
        chAMM.setChecked(false);
        chAPS.setChecked(false);
    }

    @Override
    public void setRecyclerView() {
        presenter.getMeetingData(PgActivity.pgCodeSelected);
        aAdapter = new MeetingAdapter(presenter, pgMeetingtblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
    }

    @Override
    public void meetingData(List<PgMeetingtbl> list) {
        pgMeetingtblList = list;
    }

    @OnClick(R.id.imageView11)
    public void onViewClicked() {
        presenter.openCalender();
    }

    @OnClick(R.id.textView65)
    public void onViewClicked1() {
        presenter.openCalender();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        tvDate.setText(date);
    }

    @OnClick(R.id.button4)
    public void onViewClickedsave() {
        presenter.validation(tvDate.getText().toString(), etNoOfMem.getText().toString(), chAKM.isChecked(), chAPS.isChecked(), chAMM.isChecked(), chMBK.isChecked());
    }
}
