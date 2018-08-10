/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
public class BaseActionSupport extends ActionSupport implements ServletRequestAware {
    private static final long serialVersionUID = 2345009477727643843L;

    private HttpServletRequest servletRequest;

    public HttpServletRequest getServletRequest() {
        return servletRequest;
    }

    @Override
    public void setServletRequest(HttpServletRequest servletRequest) {
        this.servletRequest = servletRequest;
    }

    public HttpServletRequest request() {
        return servletRequest;
    }

}
