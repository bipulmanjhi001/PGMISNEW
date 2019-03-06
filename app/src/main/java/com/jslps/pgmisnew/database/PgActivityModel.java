package com.jslps.pgmisnew.database;

public class PgActivityModel {
    private String name1;
    private String name2;
    private int imageIcon1;
    private int imageIcon2;
    private int id1;
    private int id2;

    public PgActivityModel(String name1, String name2, int imageIcon1, int imageIcon2, int id1, int id2) {
        this.name1 = name1;
        this.name2 = name2;
        this.imageIcon1 = imageIcon1;
        this.imageIcon2 = imageIcon2;
        this.id1 = id1;
        this.id2 = id2;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public int getImageIcon1() {
        return imageIcon1;
    }

    public void setImageIcon1(int imageIcon1) {
        this.imageIcon1 = imageIcon1;
    }

    public int getImageIcon2() {
        return imageIcon2;
    }

    public void setImageIcon2(int imageIcon2) {
        this.imageIcon2 = imageIcon2;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }
}
