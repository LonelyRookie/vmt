/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.mapping;

import com.monitoring.bean.AuditSfdcBean;
import com.monitoring.constants.VMTConstants;
import com.monitoring.process.VMTProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/04/17
 */
public class MappingDetailsAction extends MappingDetailsForm {
    private static final long serialVersionUID = -2552993967322459443L;
    private static final Logger log = LogManager.getLogger(MappingDetailsAction.class);

    private int noOfRecords = -1;
    private List<AuditSfdcBean> auditSfdcBeanList;

    @Override
    public String execute() throws Exception {
        String result = SUCCESS;
        VMTProcess vmtProcess = new VMTProcess();

        String action = getAction() == null ? "" : getAction();

        if (getSession().isNew() || getSession().getAttribute(VMTConstants.USER_ID) == null) {
            request().setAttribute("errMsg", "Session expired, please try to log in again!");
            return ERROR;
        }

        String userCountry = (String)getSession().getAttribute(VMTConstants.COUNTRY_DETAILS);
        List<String> countryList = Collections.emptyList();
        if (userCountry != null) {
            if ("ALL!".equals(userCountry)) {
                countryList = vmtProcess.queryForCountry();
            } else {
                countryList = Stream.of(userCountry.split(",")).map(String::trim).collect(Collectors.toList());
            }
        }
        Collections.sort(countryList);
        request().setAttribute(VMTConstants.COUNTRY_LIST, countryList);

        if ("search".equals(action)) {
            String country = request().getParameter("country");
            String module = request().getParameter("module");
            request().setAttribute("country", country);
            request().setAttribute("module", module);

            auditSfdcBeanList = vmtProcess.retrieveAuditSfdcInfo(module);
            noOfRecords = auditSfdcBeanList.size();
        }

        return result;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public List<AuditSfdcBean> getAuditSfdcBeanList() {
        return auditSfdcBeanList;
    }
}
