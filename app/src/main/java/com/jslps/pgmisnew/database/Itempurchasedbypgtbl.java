package com.jslps.pgmisnew.database;

import android.support.annotation.NonNull;

import com.orm.SugarRecord;
import com.orm.dsl.Unique;

public class Itempurchasedbypgtbl extends SugarRecord {
    @Unique
    private String uuid;
    private String itemcode;
    private String itemname;
    private String unit;
    private String rate;
    private String quantity;
    private String budgetname;
    private String budgetcode;
    private String pgcode;
    private String entrydate;
    private String isexported;
    private String paymentmode;


    public Itempurchasedbypgtbl() {
    }

    public Itempurchasedbypgtbl(String uuid, String itemcode, String itemname, String unit, String rate, String quantity, String budgetname, String budgetcode, String pgcode, String entrydate,String isexported,String paymentmode) {
        this.uuid = uuid;
        this.itemcode = itemcode;
        this.itemname = itemname;
        this.unit = unit;
        this.rate = rate;
        this.quantity = quantity;
        this.budgetname = budgetname;
        this.budgetcode = budgetcode;
        this.pgcode = pgcode;
        this.entrydate = entrydate;
        this.isexported = isexported;
        this.paymentmode = paymentmode;

    }

    public String getIsexported() {
        return isexported;
    }

    public void setIsexported(String isexported) {
        this.isexported = isexported;
    }

    public String getPaymentmode() {
        return paymentmode;
    }

    public void setPaymentmode(String paymentmode) {
        this.paymentmode = paymentmode;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getItemcode() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode = itemcode;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getBudgetname() {
        return budgetname;
    }

    public void setBudgetname(String budgetname) {
        this.budgetname = budgetname;
    }

    public String getBudgetcode() {
        return budgetcode;
    }

    public void setBudgetcode(String budgetcode) {
        this.budgetcode = budgetcode;
    }

    public String getPgcode() {
        return pgcode;
    }

    public void setPgcode(String pgcode) {
        this.pgcode = pgcode;
    }

    public String getEntrydate() {
        return entrydate;
    }

    public void setEntrydate(String entrydate) {
        this.entrydate = entrydate;
    }

    @NonNull
    @Override
    public String toString() {
        return itemname;
    }
}
