package com.jslps.pgmisnew.presenter;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.interactor.MDAInteractor;
import com.jslps.pgmisnew.view.MDAView;

import java.util.List;

public class MDAPresenter implements MDAInteractor.mdaInteractor {
    private MDAView mdaView;
    private MDAInteractor mdaInteractor;

    public MDAPresenter(MDAView mdaView, MDAInteractor mdaInteractor) {
        this.mdaView = mdaView;
        this.mdaInteractor = mdaInteractor;
    }

    public void getPgMem(String pgCode){
        mdaInteractor.getPgMemList(this,pgCode);
    }

    @Override
    public void getPgMemList(List<Pgmemtbl> list) {
        mdaView.setPgMemList(list);
    }

    public void setZoomIn(){
        mdaView.setZoomIn();
    }

    public void setRecyclerView(){
        mdaView.setRecyclerView();
    }

    public void setViewAdapter(ConstraintLayout firstLayout,ImageView edit,ImageView delete,ImageView dropDown,TextView farmername, TextView fatherhusbandshg, TextView shg, TextView fathername, TextView husbandname, TextView designation, TextView primaryactivity, TextView fishery, TextView hva, TextView livestock, TextView ntfp, TextView memfee, TextView sharecapital, View viewLayout, ConstraintLayout layout, int adapterPosition) {
        mdaView.setViewAdapter(firstLayout,edit,delete,dropDown,farmername,fatherhusbandshg,shg,fathername,husbandname,designation,primaryactivity,fishery,hva,livestock, ntfp,memfee,sharecapital,viewLayout,layout,adapterPosition);
    }

    public void moveToNextActivity(){
        mdaView.moveToNext();
    }

    public void setPgname(){
        mdaView.setPgName();
    }
}
