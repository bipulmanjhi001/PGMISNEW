package com.jslps.pgmisnew.view;

public interface PaymentReceiptReport {
    void setPgName();

    void openCalender(String from);

    void selectAtLeastOneCalender();

    void getReport();

    void clearDates();
}
