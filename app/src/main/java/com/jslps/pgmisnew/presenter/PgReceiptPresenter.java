package com.jslps.pgmisnew.presenter;

import com.jslps.pgmisnew.database.PgPaymentHeadModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.PgReceiptTranstbl;
import com.jslps.pgmisnew.interactor.PgReceiptInteractor;
import com.jslps.pgmisnew.view.PgReceiptView;

import java.util.List;

public class PgReceiptPresenter implements PgReceiptInteractor.PgreceiptInteractor {
    private PgReceiptInteractor pgReceiptInteractor;
    private PgReceiptView pgReceiptView;
 

    public PgReceiptPresenter(PgReceiptInteractor pgReceiptInteractor, PgReceiptView pgReceiptView) {
        this.pgReceiptInteractor = pgReceiptInteractor;
        this.pgReceiptView = pgReceiptView;
    }

    public void getHeadList(){
        pgReceiptInteractor.getHeadList(this);
    }

    @Override
    public void getHeadList(List<PgPaymentHeadModel> list) {
        pgReceiptView.getHeadList(list);
    }

    @Override
    public void dataSaved() {
        pgReceiptView.dataSaved();
    }

    @Override
    public void dataEdited() {
        pgReceiptView.dataEdited();
    }

    public void setSpinnerHead(){
        pgReceiptView.setSpinnerHead();
    }

    public void openCalender(){
        pgReceiptView.setOpenCalender();
    }

    public void blankHead() {
        pgReceiptView.blankHead();
    }

    public void blankDate() {
        pgReceiptView.blankDate();
    }

    public void blankamount() {
        pgReceiptView.blankamount();
    }

    public String[] getUserDetails() {
        return pgReceiptInteractor.getUserDetails(this);
    }

    public void saveData(String budget_code, String head_name, String date, String amount, String remark, String pgCode, String username, String userid, String isexported) {
        pgReceiptInteractor.saveData(this,budget_code,head_name,date,amount,remark,pgCode,username,userid,isexported);
    }


    public List<PgReceiptTranstbl> getPgReceiptTranstblList(String pgCodeSelected) {
        return  pgReceiptInteractor.getPgReceiptTransList(this,pgCodeSelected);
    }

    public void setRecyclerView() {
        pgReceiptView.setRecyclerView();
    }

    public void setPgName() {
        pgReceiptView.setPgName();
    }

    public void clearForm() {
        pgReceiptView.clearForm();
    }

    public void editRecord(PgReceiptTranstbl item) {
        pgReceiptView.editRecord(item);
    }

    public void saveEditedData(String budget_code, String head_name, String date, String amount, String remark, String pgCode, String username, String userid, String isexported, PgReceiptTranstbl pgReceiptTranstbl) {
        pgReceiptInteractor.saveEditedData(this,budget_code,head_name,date,amount,remark,pgCode,username,userid,isexported,pgReceiptTranstbl);
    }
}
