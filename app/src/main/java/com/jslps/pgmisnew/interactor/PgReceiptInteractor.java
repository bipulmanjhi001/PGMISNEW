package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.Logintbl;
import com.jslps.pgmisnew.database.PgPaymentHeadModel;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.PgReceiptDisData;
import com.jslps.pgmisnew.database.PgReceiptTranstbl;
import com.jslps.pgmisnew.database.TblMstPgPaymentReceipthead;
import com.jslps.pgmisnew.util.SeedDataPgPaymentHead;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.UUID;

public class PgReceiptInteractor {


    public interface PgreceiptInteractor {

        void getHeadList(List<TblMstPgPaymentReceipthead> list);
        void dataSaved();
        void dataEdited();
    }

    public void getHeadList(PgreceiptInteractor listner){
        List<TblMstPgPaymentReceipthead> list = Select.from(TblMstPgPaymentReceipthead.class)
                .whereOr(Condition.prop("showin").eq("B"),Condition.prop("showin").eq("R"))
                .list();
        listner.getHeadList(list);
    }

    public String[] getUserDetails(PgreceiptInteractor listner) {
        List<Logintbl> users = Logintbl.listAll(Logintbl.class);

        String username = users.get(0).getUsername();
        String userid = users.get(0).getUserid();

        return new String[]{username,userid};
    }

    public List<PgReceiptTranstbl> getPgReceiptTransList(PgreceiptInteractor listner, String pgCode) {

        return Select.from(PgReceiptTranstbl.class)
                .where(Condition.prop("pgcode").eq(pgCode))
                .list();
    }

    public List<PgReceiptDisData> getPgReceiptDisList(PgreceiptInteractor listner, String pgCode) {

        return Select.from(PgReceiptDisData.class)
                .where(Condition.prop("pgcode").eq(pgCode))
                .list();
    }

    public void saveData(PgreceiptInteractor listner,String budget_code, String head_name, String date, String amount, String remark, String pgCode, String username, String userid, String isexported) {
        PgReceiptTranstbl data = new PgReceiptTranstbl(UUID.randomUUID().toString(),budget_code,head_name,date,amount,remark,pgCode,username,userid,isexported);
        data.save();
        listner.dataSaved();
    }

    public void saveEditedData(PgreceiptInteractor listner,String budget_code, String head_name, String date, String amount, String remark, String pgCode, String username, String userid, String isexported,PgReceiptTranstbl pgReceiptTranstbl) {
        pgReceiptTranstbl.setBudgetcode(budget_code);
        pgReceiptTranstbl.setHeadname(head_name);
        pgReceiptTranstbl.setDate(date);
        pgReceiptTranstbl.setAmount(amount);
        pgReceiptTranstbl.setRemark(remark);
        pgReceiptTranstbl.save();
        listner.dataEdited();
    }

}
