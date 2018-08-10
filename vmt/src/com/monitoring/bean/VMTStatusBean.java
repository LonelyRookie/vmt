/*
 * Cognizant Technology Solutions
 */
package com.monitoring.bean;

import java.io.Serializable;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/13
 */
public class VMTStatusBean implements Serializable {
    private static final long serialVersionUID = 3829716890347874585L;

    private String status = "";
    private String startDate = "";
    private String endDate = "";
    private String currentDate = "";
    private String country = "";
    private String timeElapsed = "";
    private String timeElapsedSince = "";
    private String durationOfSync = "";
    private String colorCode = "";
    private String str24Hr = "";
    private String avgTime = "";
    private String loadName = "";
    private String display = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTimeElapsed() {
        return timeElapsed;
    }

    public void setTimeElapsed(String timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public String getTimeElapsedSince() {
        return timeElapsedSince;
    }

    public void setTimeElapsedSince(String timeElapsedSince) {
        this.timeElapsedSince = timeElapsedSince;
    }

    public String getDurationOfSync() {
        return durationOfSync;
    }

    public void setDurationOfSync(String durationOfSync) {
        this.durationOfSync = durationOfSync;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getStr24Hr() {
        return str24Hr;
    }

    public void setStr24Hr(String str24Hr) {
        this.str24Hr = str24Hr;
    }

    public String getAvgTime() {
        return avgTime;
    }

    public void setAvgTime(String avgTime) {
        this.avgTime = avgTime;
    }

    public String getLoadName() {
        return loadName;
    }

    public void setLoadName(String loadName) {
        this.loadName = loadName;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
