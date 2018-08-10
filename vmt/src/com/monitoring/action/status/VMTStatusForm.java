/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.status;

import com.monitoring.action.BaseAbstractAction;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/13
 */
public class VMTStatusForm extends BaseAbstractAction {
    private static final long serialVersionUID = -1446864776251951813L;

    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
