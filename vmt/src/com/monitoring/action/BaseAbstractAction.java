/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
public abstract class BaseAbstractAction extends BaseActionSupport {
    private static final long serialVersionUID = 5680397303861572401L;

    public HttpSession getSession() {
        return (HttpSession) this.getServletRequest().getSession();
    }

    public ServletContext getContext() {
        return getSession().getServletContext();
    }
}
