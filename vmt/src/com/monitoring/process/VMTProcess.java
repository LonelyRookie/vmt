/*
 * Cognizant Technology Solutions
 */
package com.monitoring.process;

import com.monitoring.bean.*;
import com.monitoring.dao.VMTDao;
import com.monitoring.exception.VMTException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/09
 */
public class VMTProcess {
    private static final Logger log = LogManager.getLogger(VMTProcess.class);
    VMTDao vmtDao = new VMTDao();

    public UserBean findUserDetailsProcess(String userId) throws VMTException {
        log.info("Inside findUserDetailsProcess ");

        Connection con = vmtDao.initializeConnection();
        UserBean userBean = vmtDao.findUserDetails(con, userId);
        vmtDao.closeConnection(con);

        return userBean;
    }

    public void performUserDBProcess(UserBean userBean) throws VMTException {
        log.info("Inside performUserDBProcess ");
        Connection con = vmtDao.initializeConnection();
        vmtDao.upsertUserDB(con, userBean);
        vmtDao.closeConnection(con);
    }

    public List<VMTStatusBean> queryForVMTStatusProcess(String country) throws VMTException {
        log.info("Inside query VMT Status Process.");
        Connection con = vmtDao.initializeConnection();
        List<VMTStatusBean> vmtStatusBeanList = new ArrayList<>();
        vmtStatusBeanList = vmtDao.queryVMTSyncStatus(country, con, vmtStatusBeanList);
        vmtDao.closeConnection(con);
        log.info("queryForVMTStatusProcess size: " + vmtStatusBeanList.size());

        return vmtStatusBeanList;
    }

    public List<RecSummaryBean> retrieveRecSummary(String loadName) throws VMTException {
        log.info("inside retrieveRecSummary");
        Connection con = vmtDao.initializeConnection();
        List<RecSummaryBean> recSummaryBeanList = vmtDao.getCountsFromRecSummary(loadName, con);
        vmtDao.closeConnection(con);
        log.info("retrieveRecSummary size: " + recSummaryBeanList.size());
        return recSummaryBeanList;
    }

    public List<RecSummaryBean> retrieveArchivalRecSummary(String country, String startDate, String endDate, String module, String loadName)
            throws VMTException {

        log.info("inside retrieveArchivalRecSummary");
        Connection con = vmtDao.initializeConnection();
        List<RecSummaryBean> recSummaryBeanList = vmtDao.getArchivalFromRecSummary(country, startDate, endDate, module, loadName, con);
        vmtDao.closeConnection(con);
        log.info("retrieveArchivalRecSummary size: " + recSummaryBeanList.size());
        return recSummaryBeanList;
    }

    public List<SessionLogBean> retrieveSessionLogSummary(String loadName, String wfName) throws VMTException {
        log.info("inside retrieveSessionLogSummary");
        Connection con = vmtDao.initializeConnection();
        List<SessionLogBean> sessionLogBeanList = vmtDao.getDataFromSessionLog(loadName, wfName, con);
        vmtDao.closeConnection(con);
        log.info("retrieveSessionLogSummary size: " + sessionLogBeanList.size());
        return sessionLogBeanList;
    }

    public List<String> queryForCountry() throws VMTException {
        log.info("Inside queryForCountry ");
        Connection con = vmtDao.initializeConnection();
        List<String> countryList = vmtDao.queryForCountryRegion(con);
        vmtDao.closeConnection(con);
        return countryList;
    }

    public List<String> queryForSfApiName(String country) throws VMTException {
        log.info("Inside queryForSfApiName ");
        Connection con = vmtDao.initializeConnection();
        List<String> sfApiNameList = vmtDao.queryForSfApiName(con, country);
        vmtDao.closeConnection(con);
        return sfApiNameList;
    }

    public List<AuditSfdcBean> retrieveAuditSfdcInfo(String objectName) throws VMTException {
        log.info("inside retrieveAuditSfdcInfo");
        Connection con = vmtDao.initializeConnection();
        List<AuditSfdcBean> auditSfdcBeanList = vmtDao.getDataFromAuditSfdc(objectName, con);
        vmtDao.closeConnection(con);
        log.info("retrieveAuditSfdcInfo size: " + auditSfdcBeanList.size());
        return auditSfdcBeanList;
    }
}
