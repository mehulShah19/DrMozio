package com.example.mehul.drmozio.bean;

/**
 * Created by Mehul on 03/09/16.
 * Patient class is dao class used throughout the project to perfom Patient operations
 */
public class Patient {

   private String name = "";
   private boolean isMale;
   private boolean hasMigrane;
   private boolean consumesHallucinogenicDrug;
   private int age;

   private long date;

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    private long id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null){
            return;
        }
        this.name = name;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        this.isMale = male;
    }

    public boolean isHasMigrane() {
        return hasMigrane;
    }

    public void setHasMigrane(boolean hasMigrane) {
        this.hasMigrane = hasMigrane;
    }

    public boolean isConsumesHallucinogenicDrug() {
        return consumesHallucinogenicDrug;
    }

    public void setConsumesHallucinogenicDrug(boolean consumesHallucinogenicDrug) {
        this.consumesHallucinogenicDrug = consumesHallucinogenicDrug;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
