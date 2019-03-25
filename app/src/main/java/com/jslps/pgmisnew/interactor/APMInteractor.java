package com.jslps.pgmisnew.interactor;

import com.jslps.pgmisnew.database.Districttbl;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.ArrayList;
import java.util.List;

public class APMInteractor {

    public interface apmInteractor {
        void getShg(List<ShgModel> list);
        void getShgNonPgMem(List<Shgmemnonpg> list);

    }

    public void getShgList(final apmInteractor listener,String pgCode){
        List<ShgModel> shgModelList = new ArrayList<>();
        List<Pgtbl> list = Select.from(Pgtbl.class)
                .where(Condition.prop("Pgcode").eq(pgCode))
                .list();
        if(list.size()>0){
            for(int i=0;i<list.size();i++){
                String villageCode = list.get(i).getVillagecode();

                List<Shgtbl> list1 = Select.from(Shgtbl.class)
                        .where(Condition.prop("Villagecode").eq(villageCode))
                        .list();

                for(int j=0;j<list1.size();j++){
                    ShgModel model = new ShgModel();
                    model.setShgcode(list1.get(j).getShgcode());
                    model.setShgname(list1.get(j).getShgname());
                    shgModelList.add(model);
                }
            }
        }
       listener.getShg(shgModelList);
    }

    public void getShgNonPgMem(final  apmInteractor listner,String shgCode){
        List<Shgmemnonpg> list = Select.from(Shgmemnonpg.class)
                .where(Condition.prop("Shgcode").eq(shgCode))
                .where(Condition.prop("Addedtopg").eq("0"))
                .list();

        listner.getShgNonPgMem(list);
    }
}
