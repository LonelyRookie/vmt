/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.user;

import com.monitoring.bean.UserBean;
import com.monitoring.constants.VMTConstants;
import com.monitoring.exception.VMTException;
import com.monitoring.process.VMTProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/12
 */
public class AddUserAction extends UserForm {
    private static final long serialVersionUID = 8609872059545594337L;
    private static final Logger log = LogManager.getLogger(AddUserAction.class);

    @Override
    public String execute() throws Exception {
        VMTProcess vmtProcess = new VMTProcess();
        String result = ERROR;
        getSession().removeAttribute("message");

        if (getSession().isNew() || getSession().getAttribute(VMTConstants.USER_ID) == null) {
            request().setAttribute("errMsg", "Session expired, please try to log in again!");
            return ERROR;
        }

        if ("update".equals(request().getParameter("action"))) {
            UserBean user = new UserBean();
            user.setUserISID(String.valueOf(getSession().getAttribute(VMTConstants.USER_ID)).trim());
            user.setUserEnrolled(getIsid().trim());
            user.setRegionEnrolled(getRegion().trim());
            user.setCountriesEnrolled(getCountry().trim());
            user.setRoleEnrolled(getRole().trim());
            user.setStatusEnrolled(getStatus().trim());
            log.debug("carl test: " + getIsid());
            if (getIsid() == null || "".equals(getIsid().trim())) {
                result = SUCCESS;
                getSession().setAttribute("message", "User ISID is required!");
                return result;
            }
            try {
                vmtProcess.performUserDBProcess(user);
                if (user.getUserISID().toUpperCase().equals(user.getUserEnrolled().toUpperCase())) {
                    request().setAttribute("errMsg", "User ISID details changed... Please login again to continue...");
                    getSession().setAttribute(VMTConstants.USER_ID, null);
                    result = ERROR;
                } else {
                    result = SUCCESS;
                    getSession().setAttribute("message", "User update success!");
                }
            } catch (VMTException e) {
                e.printStackTrace();
                log.error("AddUserAction occurs error: " + e.getMessage());
                request().setAttribute("errMsg", e.getMessage());
            }
        } else {
            log.info("<-----Entering into AddUserAction class -------->");
            String country = (String)getSession().getAttribute(VMTConstants.COUNTRY_DETAILS);
            List<String> countryList = Collections.emptyList();
            if (country != null) {
                if (VMTConstants.ALL.equals(country) || (boolean)getSession().getAttribute(VMTConstants.IS_ADMIN) ) {
                    countryList = vmtProcess.queryForCountry();
                } else {
                    countryList = Stream.of(country.split(",")).map(String::trim).collect(Collectors.toList());
                }
            }
            Collections.sort(countryList);
            getSession().setAttribute(VMTConstants.COUNTRY_LIST, countryList);
            getSession().setAttribute("noOfRecords", countryList.size());

            result = SUCCESS;
        }

        return result;
    }
}
