package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.PgActivityModel;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.util.SeedDataPgActivity;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class PgActivityInteractor {

    public interface pgActivityInteractor {
        void getPgActivityList(List<PgActivityModel> list);
        void getPgList(List<Pgtbl> list);
    }

    public void getPgActivityList(final pgActivityInteractor listener){
        listener.getPgActivityList(SeedDataPgActivity.getListData());
    }

    public void getPgList(final pgActivityInteractor listener){
        List<Pgtbl> pgtblList = Pgtbl.listAll(Pgtbl.class);
        listener.getPgList(pgtblList);
    }
}
