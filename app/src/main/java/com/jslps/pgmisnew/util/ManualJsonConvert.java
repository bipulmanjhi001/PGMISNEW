package com.jslps.pgmisnew.util;

import com.jslps.pgmisnew.PgActivity;
import com.jslps.pgmisnew.database.Blocktbl;
import com.jslps.pgmisnew.database.Clustertbl;
import com.jslps.pgmisnew.database.Logintbl;
import com.jslps.pgmisnew.database.Pgtbl;
import com.jslps.pgmisnew.database.Villagetbl;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

public class ManualJsonConvert {
    private String tblIdentifier;

    public ManualJsonConvert(String tblIdentifier) {
        this.tblIdentifier = tblIdentifier;
    }

    public String ConvertJson(){
        String JsonString="";

        if(tblIdentifier.equals("tblProducerGroupMembers")){

            StringBuilder lStringBuilder = new StringBuilder();
            lStringBuilder.append("{");
            lStringBuilder.append("\"tblProducerGroupMembers\"");
            lStringBuilder.append(":");
            lStringBuilder.append("[");
            for (int i = 0; i< PgActivity.pgmemtblList.size(); i++){
                String districtCode="",blockcode="",clusterCode="",villageCode="";
                String userId="";
                List<Logintbl> logintblList = Logintbl.listAll(Logintbl.class);
                if(logintblList.size()>0){
                    userId = logintblList.get(0).getUserid();
                }

                List<Pgtbl> pgtblList = Select.from(Pgtbl.class)
                        .where(Condition.prop("Pgcode").eq(PgActivity.pgmemtblList.get(i).getPgcode()))
                        .list();
                if(pgtblList.size()>0){
                    villageCode = pgtblList.get(0).getVillagecode();

                    List<Villagetbl> villagetblList = Select.from(Villagetbl.class)
                            .where(Condition.prop("Villagecode").eq(villageCode))
                            .list();
                    if(villagetblList.size()>0){
                        clusterCode = villagetblList.get(0).getClustercode();
                        List<Clustertbl> clustertblList = Select.from(Clustertbl.class)
                                .where(Condition.prop("Clustercode").eq(clusterCode))
                                .list();
                        blockcode = clustertblList.get(0).getBlockcode();

                        List<Blocktbl> blocktblList = Select.from(Blocktbl.class)
                                .where(Condition.prop("Blockcode").eq(blockcode))
                                .list();
                        if(blocktblList.size()>0){
                            districtCode = blocktblList.get(0).getDistrictcode();
                        }
                    }
                }

                lStringBuilder.append("{");
                lStringBuilder.append("\"Blockcode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+blockcode+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Districtcode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+districtCode+"\"");

                lStringBuilder.append(",");

                lStringBuilder.append("\"ClusterCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+clusterCode+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"VillageCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+villageCode+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"MembershipFee\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getMembershipfee()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"ShareCapital\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getSharecapital()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"PGCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getPgcode()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Guid\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getUid()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"GroupCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getGrpcode()+"\"");
                lStringBuilder.append(",");


                lStringBuilder.append("\"Group_M_Code\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getGrpmemcode()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"FatherName\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getFathername()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"HusbandName\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getHusbandname()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Designation\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getDesignation()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"StateCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+"20"+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"PrimaryActivity\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getPrimaryactivity()+"\"");
                lStringBuilder.append(",");


                lStringBuilder.append("\"Fishery\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getFishery()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"HVA\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getHva()+"\"");
                lStringBuilder.append(",");


                lStringBuilder.append("\"Ntfp\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.pgmemtblList.get(i).getNtfp()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"CreatedDate\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+""+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"createdBy\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+userId+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"IsTablet\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+"1"+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"FlagStatus\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+"1"+"\"");



                lStringBuilder.append("}");
                if(i<PgActivity.pgmemtblList.size()-1) {
                    lStringBuilder.append(",");
                }

            }
            lStringBuilder.append("]");
            lStringBuilder.append("}");
            JsonString = lStringBuilder.toString();
        }


        //next if
        if(tblIdentifier.equals("tblMstGroupMembers_Johar")){
            StringBuilder lStringBuilder = new StringBuilder();
            lStringBuilder.append("{");
            lStringBuilder.append("\"tblMstGroupMembers_Johar\"");
            lStringBuilder.append(":");
            lStringBuilder.append("[");
            for (int i = 0; i< PgActivity.shgmemberslocallyaddedtblList.size(); i++){
                String districtCode="",blockcode="",clusterCode="",villageCode="";
                String userId="";
                List<Logintbl> logintblList = Logintbl.listAll(Logintbl.class);
                if(logintblList.size()>0){
                    userId = logintblList.get(0).getUserid();
                }

                List<Pgtbl> pgtblList = Select.from(Pgtbl.class)
                        .where(Condition.prop("Pgcode").eq(PgActivity.pgmemtblList.get(i).getPgcode()))
                        .list();
                if(pgtblList.size()>0){
                    villageCode = pgtblList.get(0).getVillagecode();

                    List<Villagetbl> villagetblList = Select.from(Villagetbl.class)
                            .where(Condition.prop("Villagecode").eq(villageCode))
                            .list();
                    if(villagetblList.size()>0){
                        clusterCode = villagetblList.get(0).getClustercode();
                        List<Clustertbl> clustertblList = Select.from(Clustertbl.class)
                                .where(Condition.prop("Clustercode").eq(clusterCode))
                                .list();
                        blockcode = clustertblList.get(0).getBlockcode();

                        List<Blocktbl> blocktblList = Select.from(Blocktbl.class)
                                .where(Condition.prop("Blockcode").eq(blockcode))
                                .list();
                        if(blocktblList.size()>0){
                            districtCode = blocktblList.get(0).getDistrictcode();
                        }
                    }
                }
                lStringBuilder.append("{");
                lStringBuilder.append("\"Blockcode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+blockcode+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Districtcode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+districtCode+"\"");

                lStringBuilder.append(",");

                lStringBuilder.append("\"ClusterCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+clusterCode+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"VillageCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+villageCode+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Createdby\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+userId+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Createdon\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+""+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Guid\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getUid()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"GroupCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getGrpcode()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Group_M_Code\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getGrpmemcode()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Gender\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getGender()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"CastCategory\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getCast()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"Status\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+"1"+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"StateCode\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+"20"+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"MemberName\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getMembername()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"FatherHusbandMothNm\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getFathername()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"LiveStock\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+PgActivity.shgmemberslocallyaddedtblList.get(i).getLivestock()+"\"");
                lStringBuilder.append(",");

                lStringBuilder.append("\"IsTablet\"");
                lStringBuilder.append(":");
                lStringBuilder.append("\""+"1"+"\"");


                lStringBuilder.append("}");
                if(i<PgActivity.shgmemberslocallyaddedtblList.size()-1) {
                    lStringBuilder.append(",");
                }
            }

            lStringBuilder.append("]");
            lStringBuilder.append("}");
            JsonString = lStringBuilder.toString();
        }


        return JsonString;
    }
}
