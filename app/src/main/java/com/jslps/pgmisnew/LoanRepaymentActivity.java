package com.jslps.pgmisnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.adapter.Pgmisbatchloanadapter;
import com.jslps.pgmisnew.database.PgmisBatchLoantbl;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoanRepaymentActivity extends AppCompatActivity {

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
    @BindView(R.id.imageView14)
    ImageView imageView14;
    @BindView(R.id.textView112)
    TextView textView112;
    @BindView(R.id.textView777)
    TextView textView777;

    String pgname, pgcode, grpcode, grpmemcode, memname;
    List<PgmisBatchLoantbl> pgmisBatchLoantblList;
    Pgmisbatchloanadapter aAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_repayment);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        pgname = PgActivity.pgNameSelected;
        Intent intent = getIntent();
        pgcode = intent.getStringExtra("pgcode");
        grpcode = intent.getStringExtra("grpcode");
        grpmemcode = intent.getStringExtra("grpmemcode");
        memname = intent.getStringExtra("membername");
        getLoanDetailsMember();

        textView112.setText(memname);
        textView23.setText(pgname);
    }

    private void getLoanDetailsMember() {
        pgmisBatchLoantblList = Select.from(PgmisBatchLoantbl.class)
                .where(Condition.prop("pgcode").eq(pgcode))
                .where(Condition.prop("grpcode").eq(grpcode))
                .where(Condition.prop("grpmemcode").eq(grpmemcode))
                .list();

        textView777.setText("Total loans: "+pgmisBatchLoantblList.size());
        aAdapter = new Pgmisbatchloanadapter(this, pgmisBatchLoantblList);
        LinearLayoutManager verticalLayoutmanager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recylerList.setLayoutManager(verticalLayoutmanager);
        recylerList.setAdapter(aAdapter);
    }
}
