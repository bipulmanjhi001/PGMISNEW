package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.PgReceiptDisData;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class PaymentReceiptRepotInteractor {

    public interface paymentReceiptReport{

    }


    public List<PgReceiptDisData> getPgReceiptList(paymentReceiptReport listner, String pgCode) {

        return Select.from(PgReceiptDisData.class)
                .where(Condition.prop("pgcode").eq(pgCode))
                .list();
    }

    public List<PgPaymentTranstbl> getPgPaymentTransList(String fromDate,String toDate,String pgcode){
        if(!fromDate.equals("Select Date") && toDate.equals("Select Date")){
            //only from date present
            return Select.from(PgPaymentTranstbl.class)
                    .where(Condition.prop("pgcode").eq(pgcode))
                    .where(Condition.prop("date").gt(fromDate)).or(Condition.prop("date").eq(fromDate))
                    .list();


        }else if(fromDate.equals("Select Date") && !toDate.equals("Select Date")){
            //only to date present
            return Select.from(PgPaymentTranstbl.class)
                    .where(Condition.prop("pgcode").eq(pgcode))
                    .where(Condition.prop("date").lt(toDate)).or(Condition.prop("date").eq(toDate))
                    .list();

        }else{
            //both date prsent
            return Select.from(PgPaymentTranstbl.class)
                    .where(Condition.prop("pgcode").eq(pgcode))
                    .where(Condition.prop("date").gt(fromDate),Condition.prop("date").lt(toDate)).or(Condition.prop("date").eq(fromDate)).or(Condition.prop("date").eq(toDate))
                    .list();
        }

    }
}
