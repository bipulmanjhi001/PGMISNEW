package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.Logintbl;
import com.jslps.pgmisnew.database.PgPaymentHeadModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;
import com.jslps.pgmisnew.database.TblMstPgPaymentReceipthead;
import com.jslps.pgmisnew.presenter.PgPaymentPresenter;
import com.jslps.pgmisnew.util.SeedDataPgPaymentHead;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PgPaymentInteractor {




    public interface pgpaymentinteractor {

        void getHeadList(List<TblMstPgPaymentReceipthead> list);
        void dataSaved();
        void dataEdited();
    }

    public void getHeadList(pgpaymentinteractor listner){
        List<TblMstPgPaymentReceipthead> list = Select.from(TblMstPgPaymentReceipthead.class)
                .whereOr(Condition.prop("showin").eq("B"),Condition.prop("showin").eq("P"))
                .list();
        listner.getHeadList(list);


    }

    public String[] getUserDetails(pgpaymentinteractor listner) {
        List<Logintbl> users = Logintbl.listAll(Logintbl.class);

        String username = users.get(0).getUsername();
        String userid = users.get(0).getUserid();

        return new String[]{username,userid};
    }

    public List<PgPaymentTranstbl> getPgPaymentTransList(pgpaymentinteractor listner,String pgCode) {

        return Select.from(PgPaymentTranstbl.class)
                .where(Condition.prop("pgcode").eq(pgCode))
                .list();
    }

    public void saveData(pgpaymentinteractor listner,String budget_code, String head_name, String date, String amount, String remark, String pgCode, String username, String userid, String isexported,String Paymentmode) {
        PgPaymentTranstbl data = new PgPaymentTranstbl(UUID.randomUUID().toString(),budget_code,head_name,date,amount,remark,pgCode,username,userid,isexported,Paymentmode);
        data.save();
        listner.dataSaved();
    }

    public void saveEditedData(pgpaymentinteractor listner,String budget_code, String head_name, String date, String amount, String remark, String pgCode, String username, String userid, String isexported,PgPaymentTranstbl pgPaymentSelectedItem) {
        pgPaymentSelectedItem.setBudgetcode(budget_code);
        pgPaymentSelectedItem.setHeadname(head_name);
        pgPaymentSelectedItem.setDate(date);
        pgPaymentSelectedItem.setAmount(amount);
        pgPaymentSelectedItem.setRemark(remark);
        pgPaymentSelectedItem.save();
        listner.dataEdited();
    }

}
