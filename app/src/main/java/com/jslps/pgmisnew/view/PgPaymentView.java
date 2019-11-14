package com.jslps.pgmisnew.view;

import com.jslps.pgmisnew.database.PgPaymentHeadModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.TblMstPgPaymentReceipthead;

import java.util.List;

public interface PgPaymentView {

    void getHeadList(List<TblMstPgPaymentReceipthead> list);

    void setSpinnerHead();

    void setOpenCalender();

    void blankHead();

    void blankDate();

    void blankamount();

    void dataSaved();

    void setRecyclerView();

    void setPgName();

    void clearForm();

    void editRecord(PgPaymentTranstbl item);


    void dataEdited();

    void blankPaymentMode();
}
