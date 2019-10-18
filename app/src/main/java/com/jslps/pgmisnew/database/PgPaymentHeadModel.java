package com.jslps.pgmisnew.database;

public class PgPaymentHeadModel {
    private String budgetcode;
    private String head_name;

    public PgPaymentHeadModel(String budgetcode, String head_name) {
        this.budgetcode = budgetcode;
        this.head_name = head_name;
    }

    public String getBudgetcode() {
        return budgetcode;
    }

    public void setBudgetcode(String budgetcode) {
        this.budgetcode = budgetcode;
    }

    public String getHead_name() {
        return head_name;
    }

    public void setHead_name(String head_name) {
        this.head_name = head_name;
    }

    @Override
    public String toString() {
        return head_name;
    }
}
