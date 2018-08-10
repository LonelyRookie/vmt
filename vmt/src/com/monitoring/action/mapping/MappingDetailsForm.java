/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.mapping;

import com.monitoring.action.BaseAbstractAction;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/04/17
 */
public class MappingDetailsForm extends BaseAbstractAction {
    private static final long serialVersionUID = -246785876194874321L;

    private String action;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
