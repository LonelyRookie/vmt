/*
 * Cognizant Technology Solutions
 */
package com.monitoring.interceptors;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/05/08
 */
public class HttpHeaderInterceptor implements Interceptor {
    private static final long serialVersionUID = -7728855841643744931L;

    @Override
    public void destroy() {
    }

    @Override
    public void init() {
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        final ActionContext ac = actionInvocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) ac.get(StrutsStatics.HTTP_RESPONSE);
        response.addHeader("X-Frame-Options", "DENY");
        return actionInvocation.invoke();
    }
}
