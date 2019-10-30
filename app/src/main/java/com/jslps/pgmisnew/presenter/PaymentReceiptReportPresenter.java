package com.jslps.pgmisnew.presenter;

import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.PgReceiptDisData;
import com.jslps.pgmisnew.interactor.PaymentReceiptRepotInteractor;
import com.jslps.pgmisnew.view.PaymentReceiptReport;

import java.util.List;

public class PaymentReceiptReportPresenter implements PaymentReceiptRepotInteractor.paymentReceiptReport {

    private PaymentReceiptRepotInteractor interactor;
    private PaymentReceiptReport view;

    public PaymentReceiptReportPresenter(PaymentReceiptRepotInteractor interactor, PaymentReceiptReport view) {
        this.interactor = interactor;
        this.view = view;
    }

    public void setPgName() {
        view.setPgName();
    }

    public void openCalender(String from) {
        view.openCalender(from);
    }

    public List<PgReceiptDisData> getReceiptAmountData(String pgcode) {
        return interactor.getPgReceiptList(this,pgcode);
    }

    public void selectAtLeastOneCalender() {
        view.selectAtLeastOneCalender();
    }

    public List<PgPaymentTranstbl> getListPaymentTranstableDateWise(String fromDate, String toDate,String pgcode) {
        return interactor.getPgPaymentTransList(fromDate,toDate,pgcode);
    }

    public void getReport() {
        view.getReport();
    }

    public void clearDates() {
        view.clearDates();
    }
}
