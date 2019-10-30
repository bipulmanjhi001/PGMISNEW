package com.jslps.pgmisnew.database;

public class PaymentReceiptReportModel {
    private String headname;
    private String receivedamount;
    private String paymentamount;
    private String balance;
    private String budgetid;

    public String getBudgetid() {
        return budgetid;
    }

    public void setBudgetid(String budgetid) {
        this.budgetid = budgetid;
    }

    public String getHeadname() {
        return headname;
    }

    public void setHeadname(String headname) {
        this.headname = headname;
    }

    public String getReceivedamount() {
        return receivedamount;
    }

    public void setReceivedamount(String receivedamount) {
        this.receivedamount = receivedamount;
    }

    public String getPaymentamount() {
        return paymentamount;
    }

    public void setPaymentamount(String paymentamount) {
        this.paymentamount = paymentamount;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
