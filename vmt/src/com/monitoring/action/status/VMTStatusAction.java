/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.status;

import com.monitoring.bean.RecSummaryBean;
import com.monitoring.bean.SessionLogBean;
import com.monitoring.bean.VMTStatusBean;
import com.monitoring.constants.VMTConstants;
import com.monitoring.process.VMTProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/13
 */
public class VMTStatusAction extends VMTStatusForm {
    private static final long serialVersionUID = -3852502525945409978L;
    private static final Logger log = LogManager.getLogger(VMTStatusAction.class);
    private int noOfRecords = -1;
    private List<VMTStatusBean> statusList;
    private List<RecSummaryBean> recSummaryList;
    private List<RecSummaryBean> archRecSummaryList;
    private List<SessionLogBean> sessionLogTableList;

    @Override
    public String execute() throws Exception {
        log.info("<-----Entering into VVM Status Action class -------->");

        String result = ERROR;
        String action = getAction() == null ? "" : getAction();
        VMTProcess vmtProcess = new VMTProcess();
        if (getSession().isNew() || getSession().getAttribute(VMTConstants.USER_ID) == null) {
            request().setAttribute("errMsg", "Session expired, please try to log in again!");
            return ERROR;
        }

        if ("ALL".equals(action)) {
            String country = String.valueOf(getSession().getAttribute(VMTConstants.COUNTRY_DETAILS));
            statusList = vmtProcess.queryForVMTStatusProcess(country);
            noOfRecords = statusList.size();
            result = SUCCESS;
        } else if ("recSum".equals(action)) {
            String loadName = request().getParameter("loadName") == null ? "" : request().getParameter("loadName");
            recSummaryList = vmtProcess.retrieveRecSummary(loadName);
            noOfRecords = recSummaryList.size();
            String country = loadName.indexOf('_') > 0 ? loadName.substring(0, loadName.indexOf('_')) : "";
            request().setAttribute("message", "Individual workflow status for country " + country);
            result = action;
        } else if ("sessionLog".equals(action)) {
            String loadName = request().getParameter("loadName") == null ? "" : request().getParameter("loadName");
            sessionLogTableList = vmtProcess.retrieveSessionLogSummary(loadName, request().getParameter("wfName"));
            noOfRecords = sessionLogTableList.size();
            String country = loadName.indexOf('_') > 0 ? loadName.substring(0, loadName.indexOf('_')) : "";
            request().setAttribute("message", "Session level details for country " + country);
            request().setAttribute("loadName", loadName);
            request().setAttribute("isSearch", request().getParameter("isSearch"));
            request().setAttribute("selectedLoadName", request().getParameter("selectedLoadName"));
            request().setAttribute("startDate", request().getParameter("startDate"));
            request().setAttribute("endDate", request().getParameter("endDate"));
            request().setAttribute("country", request().getParameter("country"));
            request().setAttribute("module", request().getParameter("module"));
            result = action;
        } else if ("search".equals(action)) {
            LocalDate currentDate = LocalDate.now();
            LocalDate lastMonth = currentDate.minusMonths(1);
            request().setAttribute("maxDate", currentDate);
            request().setAttribute("minDate", lastMonth);

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

            String startDate = request().getParameter("startDate");
            String endDate = request().getParameter("endDate");
            String country = request().getParameter("country");
            String module = request().getParameter("module");
            String selectedLoadName = request().getParameter("selectedLoadName");
            if (country == null) {
                country = countryList.stream().collect(Collectors.joining(","));
            }

            if (startDate != null && endDate != null) {
                archRecSummaryList = vmtProcess.retrieveArchivalRecSummary(country, startDate, endDate, module,
                        selectedLoadName);
                noOfRecords = archRecSummaryList.size();
                request().setAttribute("startDate", startDate);
                request().setAttribute("endDate", endDate);
                request().setAttribute("country", request().getParameter("country"));
                request().setAttribute("module", module);
                request().setAttribute("selectedLoadName", selectedLoadName);
            }

            result = action;
        }

        return result;
    }

    public int getNoOfRecords() {
        return noOfRecords;
    }

    public List<VMTStatusBean> getStatusList() {
        return statusList;
    }

    public List<RecSummaryBean> getRecSummaryList() {
        return recSummaryList;
    }

    public List<RecSummaryBean> getArchRecSummaryList() {
        return archRecSummaryList;
    }

    public List<SessionLogBean> getSessionLogTableList() {
        return sessionLogTableList;
    }
}
