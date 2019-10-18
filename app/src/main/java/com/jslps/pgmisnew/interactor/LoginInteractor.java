package com.jslps.pgmisnew.interactor;

import android.text.TextUtils;

import com.jslps.pgmisnew.database.Blocktbl;
import com.jslps.pgmisnew.database.Clustertbl;
import com.jslps.pgmisnew.database.Districttbl;
import com.jslps.pgmisnew.database.Logintbl;
import com.jslps.pgmisnew.database.PgMeetingtbl;
import com.jslps.pgmisnew.database.PgPaymentTranstbl;
import com.jslps.pgmisnew.database.Pgmemtbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.Shgmemberslocallyaddedtbl;
import com.jslps.pgmisnew.database.Shgmemnonpg;
import com.jslps.pgmisnew.database.Shgtbl;
import com.jslps.pgmisnew.database.Villagetbl;
import com.jslps.pgmisnew.util.CheckConnectivity;
import com.jslps.pgmisnew.util.EncryptClass;
import com.orm.query.Condition;
import com.orm.query.Select;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LoginInteractor {

    public interface OnLoginFinishedListener {
        void onUsernameError();

        void onPasswordError();

        void onSuccess(String when);

        void storePassword(boolean yes);

        void callLoginApi();

        void callVersionCheckApi();

        void moveToNextActivity(String when);

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
                    listener.onSuccess("second");
                }else{


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

            //deleting the the database of old user if prersent
            Logintbl.deleteAll(Logintbl.class);
            Blocktbl.deleteAll(Blocktbl.class);
            Clustertbl.deleteAll(Clustertbl.class);
            Districttbl.deleteAll(Districttbl.class);
            Pgmemtbl.deleteAll(Pgmemtbl.class);
            Pgtbl.deleteAll(Pgtbl.class);
            Villagetbl.deleteAll(Villagetbl.class);
            Shgmemnonpg.deleteAll(Shgmemnonpg.class);
            Shgtbl.deleteAll(Shgtbl.class);
            PgMeetingtbl.deleteAll(PgMeetingtbl.class);
            Shgmemberslocallyaddedtbl.deleteAll(Shgmemberslocallyaddedtbl.class);
            PgPaymentTranstbl.deleteAll(PgPaymentTranstbl.class);

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
                                String primaryActivity = pgObject.optString("PrimaryActivity");
                                String secondaryActivity = pgObject.optString("secondryActivity");
                                List<String> secondary = Arrays.asList(secondaryActivity.split(","));
                                String fishery="0",
                                        hva="0",
                                        livestock="0",
                                        ntfp="0";
                                if(secondary.size()>0){
                                    for(int z=0;z<secondary.size();z++){
                                        String code = secondary.get(z);
                                        if(code.equals("1")){
                                            fishery="1";
                                        }
                                        if(code.equals("2")){
                                            hva="1";
                                        }
                                        if(code.equals("3")){
                                            livestock="1";
                                        }
                                        if(code.equals("4")){
                                            ntfp="1";
                                        }
                                    }
                                }


                                //saving to pgtbl
                                        Pgtbl dataP = new Pgtbl(villageCode,pgCode,pgName,primaryActivity,fishery,hva,livestock,ntfp);
                                dataP.save();

                                //Grp or Pgmem
                                JSONArray grpArray = pgObject.getJSONArray("listGroup");
                                for(int n=0;n<grpArray.length();n++){
                                    JSONObject grpObject = grpArray.getJSONObject(n);
                                    String GroupCode = grpObject.optString("GroupCode");
                                    String Group_M_Code = grpObject.optString("Group_M_Code");
                                    String MemberName = grpObject.optString("MemberName");
                                    String primaryActivityM = grpObject.optString("PActivity");
                                    String secondaryActivityM = grpObject.optString("SActivity");
                                    String MembershipFee = grpObject.optString("MembershipFee");
                                    String ShareCapital = grpObject.optString("ShareCapital");
                                    String FatherName = grpObject.optString("FatherName");
                                    String HusbandName = grpObject.optString("HusbandName");
                                    String FatherHusbandNameinSHG = grpObject.optString("FatherHusbandNameinSHG");
                                    String Designation = grpObject.optString("Designation");
                                    String groupName= grpObject.optString("GroupName");
                                    String Isexported ="1";
                                    String Isupdated ="0";

                                    String fisheryM="",
                                            hvaM="",
                                            livestockM="",
                                            ntfpM="";
                                    if(!secondaryActivityM.equals("")){
                                        String result = secondaryActivity.replaceAll("Fishery:","");
                                        fisheryM = result.replaceAll(",HVA:0,Livestock:0,Ntfp:0","");

                                        String result1 = secondaryActivity.replaceAll("Fishery:0,HVA:","");
                                        hvaM = result1.replaceAll(",Livestock:0,Ntfp:0","");

                                        String result2 = secondaryActivity.replaceAll("Fishery:0,HVA:0,Livestock:","");
                                        livestockM = result2.replaceAll(",Ntfp:0","");

                                        ntfpM = secondaryActivity.replaceAll("Fishery:0,HVA:0,Livestock:0,Ntfp:","");

                                    }

                                    //saving to PgMemtbl
                                    Pgmemtbl dataPM = new Pgmemtbl(pgCode,Group_M_Code,GroupCode,MemberName,MembershipFee,ShareCapital,FatherName,HusbandName,Designation,FatherHusbandNameinSHG,primaryActivityM,fisheryM,hvaM,ntfpM,livestockM,groupName,Isexported,Isupdated,"");
                                    dataPM.save();
                                }
                            }


                            //shg
                            JSONArray shgArray = villageObject.getJSONArray("liShg");
                            for(int p=0;p<shgArray.length();p++){
                                JSONObject shgObject = shgArray.getJSONObject(p);
                                String ShgCode = shgObject.optString("ShgCode");
                                String ShgName = shgObject.optString("ShgName");

                                //saving to shgtbl

                                Shgtbl datashg = new Shgtbl(villageCode,ShgCode,ShgName);
                                datashg.save();


                                //shgmem
                                JSONArray shgMemArray = shgObject.getJSONArray("lisNonShg");
                                for(int q=0;q<shgMemArray.length();q++){
                                    JSONObject shgMemObject = shgMemArray.getJSONObject(q);
                                    String Group_M_Code = shgMemObject.optString("Group_M_Code");
                                    String MemberName = shgMemObject.optString("MemberName");
                                    String FatherHusbandNameinSHG = shgMemObject.optString("FatherHusbandNameinSHG");

                                    //saving to shgmemtbl

                                    Shgmemnonpg dataNonpg = new Shgmemnonpg(Group_M_Code,MemberName,FatherHusbandNameinSHG,ShgCode,"0");
                                    dataNonpg.save();
                                }
                            }
                        }
                    }
                }
            }

            //new activity here
            listener.moveToNextActivity("first");

        }catch (Exception e){

        }
    }
}
