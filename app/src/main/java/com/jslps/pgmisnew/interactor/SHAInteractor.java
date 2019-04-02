package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.Pgmemtbl;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class SHAInteractor {

    public interface shaInteractor {
        void getPgMemList(List<Pgmemtbl> list);

    }

    public void getPgMemList(final shaInteractor listener,String pgCode){
        List<Pgmemtbl> list = Select.from(Pgmemtbl.class)
                .where(Condition.prop("Pgcode").eq(pgCode))
                .list();
        listener.getPgMemList(list);
    }
}
