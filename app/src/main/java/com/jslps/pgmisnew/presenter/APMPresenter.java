package com.jslps.pgmisnew.presenter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Districttbl;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.ShgModel;
import com.jslps.pgmisnew.database.Shgtbl;
import com.jslps.pgmisnew.interactor.APMInteractor;
import com.jslps.pgmisnew.interactor.MDAInteractor;
import com.jslps.pgmisnew.view.APMView;
import com.jslps.pgmisnew.view.MDAView;

import java.util.List;

public class APMPresenter implements APMInteractor.apmInteractor {
    private APMView apmView;
    private APMInteractor apmInteractor;

    public APMPresenter(APMView apmView, APMInteractor apmInteractor) {
        this.apmView = apmView;
        this.apmInteractor = apmInteractor;
    }

    public void getShg(String pgCode){
        apmInteractor.getShgList(this,pgCode);
    }


    @Override
    public void getShg(List<ShgModel> list) {
        apmView.setShgList(list);
    }

    public void setShgSpinner(){
        apmView.setShgSpinner();
    }

    public void setPgname(){
        apmView.setPgName();
    }
}
