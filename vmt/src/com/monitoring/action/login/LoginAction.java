/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.login;

import com.merck.is.arch.Authenticate;
import com.monitoring.bean.UserBean;
import com.monitoring.constants.VMTConstants;
import com.monitoring.process.VMTProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
public class LoginAction extends LoginForm {
    private static final long serialVersionUID = -2129292121550128841L;
    private static final Logger log = LogManager.getLogger(LoginAction.class);

    @Override
    public String execute() throws Exception {
        String result = ERROR;
        VMTProcess vmtProcess = new VMTProcess();

        try {
            try {
                System.setProperty("PHYSICAL_LOCATION", "pr");
                Authenticate.NTAuthenticate("usueapp820027.merck.com", VMTConstants.LOGIN_DOMAIN,
                        getUserName(), getPassword());

                UserBean user = vmtProcess.findUserDetailsProcess(getUserName().trim().toUpperCase());
                if (user != null) {
                    log.debug("user: " + getUserName() + " login");
                    getSession().setAttribute(VMTConstants.USER_ID, user.getUserISID());
                    getSession().setAttribute(VMTConstants.COUNTRY_DETAILS, user.getCountryName());
                    getSession().setAttribute(VMTConstants.IS_ADMIN, user.getRoleName().equalsIgnoreCase("ADMINISTRATOR"));
                    result = SUCCESS;
                } else {
                    request().setAttribute("errMsg", "Cannot find user: " + getUserName());
                }
            } catch (SecurityException e) {
                log.error("Authentication issue...", e);
                e.printStackTrace();
                request().setAttribute("errMsg", getUserName() + " is NOT valid. Cannot authenticate.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
