package com.jslps.pgmisnew.interactor;

import android.text.TextUtils;

import com.jslps.pgmisnew.database.Blocktbl;
import com.jslps.pgmisnew.database.Clustertbl;
import com.jslps.pgmisnew.database.Districttbl;
import com.jslps.pgmisnew.database.Logintbl;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.Villagetbl;
import com.jslps.pgmisnew.util.CheckConnectivity;
import com.jslps.pgmisnew.util.EncryptClass;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class LoginInteractor {

    public interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess();

        void storePassword(boolean yes);

        void callLoginApi();

        void callVersionCheckApi();

        void moveToNextActivity();

    }

    public void login(final String username, final String password,final boolean isCheck, final OnLoginFinishedListener listener) {
            String passwordEncrypted = new EncryptClass(password,"encrypt").Encrypt();

            listener.storePassword(isCheck);

            if (TextUtils.isEmpty(username)) {
                listener.onUsernameError();
            } else if(TextUtils.isEmpty(password)) {
                listener.onPasswordError();
            }else{
                List<Logintbl> logintblList = Select.from(Logintbl.class)
                        .where(Condition.prop("Username").eq(username))
                        .where(Condition.prop("Password").eq(passwordEncrypted))
                        .list();
                if(logintblList.size()>0){
                    listener.onSuccess();
                }else{
                    //deleting the the database of old user if prersent
                    Logintbl.deleteAll(Logintbl.class);
                    Blocktbl.deleteAll(Blocktbl.class);
                    Clustertbl.deleteAll(Clustertbl.class);
                    Districttbl.deleteAll(Districttbl.class);
                    Pgmemtbl.deleteAll(Pgmemtbl.class);
                    Pgtbl.deleteAll(Pgtbl.class);
                    Villagetbl.deleteAll(Villagetbl.class);

                    listener.callLoginApi();
                }

            }


    }

    public void versionCheck(final OnLoginFinishedListener listener){
         listener.callVersionCheckApi();
    }

    public void consumeData(final OnLoginFinishedListener listener,String data,String username,String password){
        try{

            //district
            JSONArray mainArray = new JSONArray(data);
            for(int i=0;i<mainArray.length();i++){
                JSONObject districtObject = mainArray.getJSONObject(i);
                String districtCode = districtObject.optString("DistrictCode");
                String districtName = districtObject.optString("DistrictName");
                String userId = districtObject.optString("UserID");
                //saving data to Logintbl
                String passwordEncrypted = new EncryptClass(password,"encrypt").Encrypt();
                Logintbl logintbl = new Logintbl(userId,passwordEncrypted,username,districtCode,districtName);
                logintbl.save();

                //block
                JSONArray blockArray = districtObject.getJSONArray("listblock");
                for(int j=0;j<blockArray.length();j++){
                    JSONObject blockObject = blockArray.getJSONObject(j);
                    String blockCode = blockObject.optString("BlockCode");
                    String blockName = blockObject.optString("BlockName");
                    //saving data to Blocktbl
                    Blocktbl dataB = new Blocktbl(districtCode,blockCode,blockName);
                    dataB.save();

                    //cluster
                    JSONArray clusterArray = blockObject.getJSONArray("listCluster");
                    for(int k=0;k<clusterArray.length();k++){
                        JSONObject clusterObject = clusterArray.getJSONObject(k);
                        String clusterCode = clusterObject.optString("ClusterCode");
                        String clusterName = clusterObject.optString("ClusterName");
                        //saving data to clustertbl
                        Clustertbl dataC = new Clustertbl(blockCode,clusterCode,clusterName);
                        dataC.save();

                        //village
                        JSONArray villageArray = clusterObject.getJSONArray("listVillPG");
                        for(int l=0;l<villageArray.length();l++){
                            JSONObject villageObject = villageArray.getJSONObject(l);
                            String villageCode = villageObject.optString("VillageCode");
                            String villageName = villageObject.optString("VillageName");
                            //saving data to villagetbl
                            Villagetbl dataV = new Villagetbl(clusterCode,villageCode,villageName);
                            dataV.save();

                            //PG
                            JSONArray pgArray = villageObject.getJSONArray("liPgCode");
                            for(int m=0;m<pgArray.length();m++){
                                JSONObject pgObject = pgArray.getJSONObject(m);
                                String pgCode = pgObject.optString("PGCode");
                                String pgName = pgObject.optString("PGName");
                                //saving to pgtbl
                                Pgtbl dataP = new Pgtbl(villageCode,pgCode,pgName);
                                dataP.save();

                                //Grp or Pgmem
                                JSONArray grpArray = pgObject.getJSONArray("listGroup");
                                for(int n=0;n<grpArray.length();n++){
                                    JSONObject grpObject = grpArray.getJSONObject(n);
                                    String GroupCode = grpObject.optString("GroupCode");
                                    String Group_M_Code = grpObject.optString("Group_M_Code");
                                    String MemberName = grpObject.optString("MemberName");
                                    //saving to PgMemtbl
                                    Pgmemtbl dataPM = new Pgmemtbl(pgCode,Group_M_Code,GroupCode,MemberName);
                                    dataPM.save();
                                }
                            }
                        }
                    }
                }
            }

            //new activity here
            listener.moveToNextActivity();

        }catch (Exception e){

        }
    }
}
