package com.jslps.pgmisnew.database;

import com.orm.SugarRecord;

public class PgReceiptDisData extends SugarRecord {
    private String budgethead;
    private String budgetcode;
    private String accountno;
    private String ekoshamount;
    private String pgcode;
    private String approveddate;
    private String budgetid;

    public PgReceiptDisData() {
    }

    public PgReceiptDisData(String budgethead, String budgetcode, String accountno, String ekoshamount,String pgcode,String approveddate,String budgetid) {
        this.budgethead = budgethead;
        this.budgetcode = budgetcode;
        this.accountno = accountno;
        this.ekoshamount = ekoshamount;
        this.pgcode = pgcode;
        this.approveddate = approveddate;
        this.budgetid = budgetid;
    }

    public String getBudgetid() {
        return budgetid;
    }

    public void setBudgetid(String budgetid) {
        this.budgetid = budgetid;
    }

    public String getApproveddate() {
        return approveddate;
    }

    public void setApproveddate(String approveddate) {
        this.approveddate = approveddate;
    }

    public String getPgcode() {
        return pgcode;
    }

    public void setPgcode(String pgcode) {
        this.pgcode = pgcode;
    }

    public String getBudgethead() {
        return budgethead;
    }

    public void setBudgethead(String budgethead) {
        this.budgethead = budgethead;
    }

    public String getBudgetcode() {
        return budgetcode;
    }

    public void setBudgetcode(String budgetcode) {
        this.budgetcode = budgetcode;
    }

    public String getAccountno() {
        return accountno;
    }

    public void setAccountno(String accountno) {
        this.accountno = accountno;
    }

    public String getEkoshamount() {
        return ekoshamount;
    }

    public void setEkoshamount(String ekoshamount) {
        this.ekoshamount = ekoshamount;
    }
}
