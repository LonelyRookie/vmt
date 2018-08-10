/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.user;

import com.monitoring.action.BaseAbstractAction;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
class UserForm extends BaseAbstractAction {
    private static final long serialVersionUID = 9087565386288763296L;

    private String isid = "";
    private String country = "";
    private String region = "";
    private String role = "";
    private String status = "";

    public String getIsid() {
        return isid;
    }

    public void setIsid(String isid) {
        this.isid = isid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}