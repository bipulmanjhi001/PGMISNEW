package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class TblMstPgPaymentReceipthead extends SugarRecord {
    private String budgetid;
    private String budgetcode;
    private String headname;
    private String showin;
    private String headnamehindi;

    public TblMstPgPaymentReceipthead() {
    }

    public TblMstPgPaymentReceipthead(String budgetid, String budgetcode, String headname, String showin, String headnamehindi) {
        this.budgetid = budgetid;
        this.budgetcode = budgetcode;
        this.headname = headname;
        this.showin = showin;
        this.headnamehindi = headnamehindi;
    }

    public String getBudgetid() {
        return budgetid;
    }

    public void setBudgetid(String budgetid) {
        this.budgetid = budgetid;
    }

    public String getBudgetcode() {
        return budgetcode;
    }

    public void setBudgetcode(String budgetcode) {
        this.budgetcode = budgetcode;
    }

    public String getHeadname() {
        return headname;
    }

    public void setHeadname(String headname) {
        this.headname = headname;
    }

    public String getShowin() {
        return showin;
    }

    public void setShowin(String showin) {
        this.showin = showin;
    }

    public String getHeadnamehindi() {
        return headnamehindi;
    }

    public void setHeadnamehindi(String headnamehindi) {
        this.headnamehindi = headnamehindi;
    }

    @Override
    public String toString() {
        return headname;
    }
}
