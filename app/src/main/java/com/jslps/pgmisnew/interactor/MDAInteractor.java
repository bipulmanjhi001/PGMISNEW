package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.Pgmemtbl;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.Collections;
import java.util.List;

public class MDAInteractor {

    public interface mdaInteractor {
        void getPgMemList(List<Pgmemtbl> list);

    }

    public void getPgMemList(final mdaInteractor listener,String pgCode){
        List<Pgmemtbl> list = Select.from(Pgmemtbl.class)
                .where(Condition.prop("Pgcode").eq(pgCode))
                .list();
        Collections.reverse(list);
        listener.getPgMemList(list);
    }
}
