/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.login;

import com.monitoring.action.BaseAbstractAction;
import com.monitoring.constants.VMTConstants;
import com.opensymphony.xwork2.ActionContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.SessionMap;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
public class LogoutAction extends BaseAbstractAction {
    private static final long serialVersionUID = 7537435728628704427L;
    private static final Logger log = LogManager.getLogger(LogoutAction.class);

    @Override
    @SuppressWarnings("unchecked")
    public String execute() {
        String result = ERROR;
        try {
            log.debug("user: " + getSession().getAttribute(VMTConstants.USER_ID) + " logout");

            // get session
            SessionMap session = (SessionMap) ActionContext.getContext().getSession();

            // invalidate
            session.invalidate();

            // renew servlet session
            session.put("renewServletSession", null);
            session.remove("renewServletSession");

            // populate the struts session
            session.entrySet();

            result = SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
