/*
 * Cognizant Technology Solutions
 */
package com.monitoring.bean;

import java.io.Serializable;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/15
 */
public class RecSummaryBean implements Serializable {
    private static final long serialVersionUID = 4642804100731716963L;

    private String module = "";
    private String country = "";
    private String startDate = "";
    private String endDate = "";
    private String loadName = "";
    private String loadType = "";
    private String wfName = "";
    private String wfOrderNum = "";
    private String status = "";
    private String colorCode = "";
    private String display = "";

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getLoadName() {
        return loadName;
    }

    public void setLoadName(String loadName) {
        this.loadName = loadName;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }

    public String getWfName() {
        return wfName;
    }

    public void setWfName(String wfName) {
        this.wfName = wfName;
    }

    public String getWfOrderNum() {
        return wfOrderNum;
    }

    public void setWfOrderNum(String wfOrderNum) {
        this.wfOrderNum = wfOrderNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
