package com.jslps.pgmisnew.view;

import com.jslps.pgmisnew.database.PgPaymentHeadModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.PgReceiptTranstbl;

import java.util.List;

public interface PgReceiptView {

    void getHeadList(List<PgPaymentHeadModel> list);

    void setSpinnerHead();

    void setOpenCalender();

    void blankHead();

    void blankDate();

    void blankamount();

    void dataSaved();

    void setRecyclerView();

    void setPgName();

    void clearForm();

    void editRecord(PgReceiptTranstbl item);


    void dataEdited();
}
