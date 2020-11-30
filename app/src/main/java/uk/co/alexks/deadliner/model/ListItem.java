package uk.co.alexks.deadliner.model;

/**
 * Created by ashaw on 08/03/2017.
 */

public class ListItem {

    private String name;
    private String type;
    private String note;
    private String date;
    private String dbId;

    public String getDbId() {
        return dbId;
    }

    public void setDbId(String dbId) {
        this.dbId = dbId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public String getNotifyUnit() {
        return notifyUnit;
    }

    public void setNotifyUnit(String notifyUnit) {
        this.notifyUnit = notifyUnit;
    }

    private String time;
    private String notifyTime;
    private String notifyUnit;

}
