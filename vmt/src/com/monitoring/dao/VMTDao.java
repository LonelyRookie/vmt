/*
 * Cognizant Technology Solutions
 */
package com.monitoring.dao;

import com.monitoring.bean.*;
import com.monitoring.common.Util;
import com.monitoring.exception.VMTException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/09
 */
public class VMTDao {
    private static final Logger log = LogManager.getLogger(VMTDao.class);

    public Connection initializeConnection() throws VMTException {
        Connection con;
        try {
            com.jolbox.bonecp.BoneCP boneCPConnectionPool = ConnectionManager.getConnectionPool();
            log.info("Total Created connections:" + boneCPConnectionPool.getTotalCreatedConnections());
            log.info("Total Free connections:" + boneCPConnectionPool.getTotalFree());
            con = boneCPConnectionPool.getConnection();
        } catch (SQLException e) {
            log.error(e);
            throw new VMTException("SQLException", e);
        }

        return con;
    }

    public void closeConnection(Connection con) throws VMTException {
        if (con != null) {
            try {
                log.debug("Closing Connection ... ");
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
                String errMsg = "Caught SQLException while closing either Connection, ResultSet or Statement ....";
                throw new VMTException(errMsg, e);
            }
        }
    }

    public UserBean findUserDetails(Connection con, String userId) throws VMTException {
        log.info("Querying to find the user details for the user Id: " + userId);
        UserBean user = null;

        String queryString =
                "SELECT role_name, " +
                        "region_name, " +
                        "country_name, " +
                        "user_status, " +
                        "user_isid " +
                        "FROM VVA_DARC_CNTRL.VVM_ADM_USERS " +
                        "WHERE UPPER(user_isid) = '" + userId.toUpperCase().trim() + "' " +
                        "AND UPPER(user_status) = 'ACTIVE'";

        log.info("findUserDetails Query => " + queryString);
        try (Statement stmt = con.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryString);
            if (resultSet != null) {
                while (resultSet.next()) {
                    user = new UserBean();
                    user.setRoleName(resultSet.getString(1));
                    user.setRegionName(resultSet.getString(2));
                    user.setCountryName(resultSet.getString(3));
                    user.setUserStatus(resultSet.getString(4));
                    user.setUserISID(resultSet.getString(5));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String errMsg = "Caught SQLException while finding the user details from  VVM_ADMIN_USERS table. " +
                    "Rolling back connection ....";
            log.error(errMsg);
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlEx) {
                    errMsg = "Caught SQLException while rolling back connection after failing to find the user " +
                            "details from VVM_ADMIN_USERS table.";
                    log.error(errMsg);
                    throw new VMTException(errMsg, sqlEx);
                }
            }
            throw new VMTException(errMsg, e);
        }

        return user;
    }

    public void upsertUserDB(Connection con, UserBean userBean) throws VMTException {
        log.info("Upserting the records in VVM_ADM_USER table for user : " + userBean.getUserEnrolled());
        try (Statement stmt = con.createStatement()) {
            String querySql = "MERGE INTO VVA_DARC_CNTRL.VVM_ADM_USERS trg USING (SELECT '" + userBean.getUserEnrolled().trim().toUpperCase() + "' user_isid,'" + userBean.getRoleEnrolled() +
                    "' role_name,'" + userBean.getRegionEnrolled() + "' region_name,'" + userBean.getCountriesEnrolled() + "' country_name,'" +
                    userBean.getStatusEnrolled() + "' user_status,'" + userBean.getUserISID().trim().toUpperCase() + "' created_modified_by_id FROM dual) src " +
                    "ON (trg.user_isid=src.user_isid) " +
                    "WHEN MATCHED THEN UPDATE SET " +
                    "trg.role_name=src.role_name, " +
                    "trg.region_name=src.region_name, " +
                    "trg.country_name=src.country_name, " +
                    "trg.user_status=src.user_status, " +
                    "trg.modified_date=sysdate, " +
                    "trg.modified_by_id=src.created_modified_by_id " +
                    "WHEN NOT MATCHED THEN INSERT " +
                    "(trg.user_isid,trg.role_name,trg.region_name,trg.country_name,trg.user_status,trg.created_date,trg.created_by_id,trg.modified_date,trg.modified_by_id) VALUES " +
                    "(src.user_isid,src.role_name,src.region_name,src.country_name,src.user_status,sysdate,src.created_modified_by_id,sysdate,src.created_modified_by_id)";

            log.info("upsertUserDB query sql: " + querySql);
            con.setAutoCommit(false);
            int rows = stmt.executeUpdate(querySql);
            con.commit();
            log.info("Rows modified :" + rows);
            log.info("Details for ISID " + userBean.getUserEnrolled().trim().toUpperCase()
                    + " has been saved successfully");
        } catch (SQLException sqlEx) {
            log.error("SQL Exception encountered while doing operations in VVM_ADMIN_USERS table.");
            log.error("SQLException : " + sqlEx.getMessage());
            throw new VMTException("SQL Exception encountered while doing operations in VVM_ADMIN_USERS table", sqlEx);
        } catch (Exception e) {
            log.error("Generalised Exception encountered while doing operations in VVM_ADMIN_USERS table.");
            log.error("Exception : " + e.getMessage());
            throw new VMTException("Generalised Exception encountered while doing operations in " +
                    "VVM_ADMIN_USERS table", e);
        }
    }

    public List<VMTStatusBean> queryVMTSyncStatus(String country, Connection con, List<VMTStatusBean> vmtStatusBeanList)
            throws VMTException {

        String countries;
        if ("ALL!".equals(country)) {
            countries = queryForCountryRegion(con)
                    .stream()
                    .map(str -> "'" + str + "'")
                    .collect(Collectors.joining(",", "(", ")"));
        } else {
            countries = Arrays.stream(country.split(","))
                    .map(str -> "'" + str + "'")
                    .collect(Collectors.joining(",", "(", ")"));
        }

        String queryString = "SELECT status, to_char(start_date, 'MM/DD/YYYY HH24:MI:SS')\n" +
                "    , to_char(end_date, 'MM/DD/YYYY HH24:MI:SS')\n" +
                "    , to_char(sysdate, 'MM/DD/YYYY HH24:MI:SS'), country, load_name\n" +
                "FROM VVA_DARC_CNTRL.VVM_LOG b\n" +
                "WHERE trunc(start_date) IN (\n" +
                "    SELECT MAX(trunc(start_date)) AS a\n" +
                "    FROM VVA_DARC_CNTRL.VVM_LOG\n" +
                "    where load_type='AUTO'\n" +
                ")\n" +
                "and b.load_type='AUTO' " +
                "and country in " + countries;

        log.info("Query => \n" + queryString);
        try (Statement stmt = con.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryString);

            if (null != resultSet) {
                while (resultSet.next()) {
                    VMTStatusBean vmtStatusBean = new VMTStatusBean();
                    vmtStatusBean.setStatus(resultSet.getString(1));
                    vmtStatusBean.setStartDate(resultSet.getString(2));
                    vmtStatusBean.setEndDate(resultSet.getString(3));
                    vmtStatusBean.setCurrentDate(resultSet.getString(4));
                    vmtStatusBean.setCountry(resultSet.getString(5));
                    vmtStatusBean.setLoadName(resultSet.getString(6));
                    vmtStatusBean.setTimeElapsed(Util.calculateTimeBetweenTwoDates(resultSet.getString(2), resultSet.getString(4)));
                    if (null != resultSet.getString(3)) {
                        vmtStatusBean.setTimeElapsedSince(Util.calculateTimeBetweenTwoDates(resultSet.getString(3), resultSet.getString(4)));
                    } else {
                        vmtStatusBean.setTimeElapsedSince("N.A.");
                    }
                    if (null != resultSet.getString(3)) {
                        vmtStatusBean.setDurationOfSync(Util.calculateTimeBetweenTwoDates(resultSet.getString(2), resultSet.getString(3)));
                    } else {
                        vmtStatusBean.setDurationOfSync(Util.calculateTimeBetweenTwoDates(resultSet.getString(2), resultSet.getString(4)));
                    }
                    vmtStatusBean.setStr24Hr(Util.calculateTimeBetweenTwoDates(resultSet.getString(2), resultSet.getString(4)));
                    vmtStatusBean.setColorCode(Util.determineColorCode(vmtStatusBean));
                    vmtStatusBean.setAvgTime(queryAverageTime(con, resultSet.getString(1)));
                    vmtStatusBean.setDisplay(Util.determineDisplay(vmtStatusBean));
                    vmtStatusBeanList.add(vmtStatusBean);
                }
            }
        } catch (SQLException sqlEx) {
            String errMsg = "Caught SQLException while querying VVM_LOG table. Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errMsg = "Caught SQLException while rolling back connection after failing to query VVM_LOG table.";
                    throw new VMTException(errMsg, sqlExp);
                }
            }
            throw new VMTException(errMsg, sqlEx);
        }

        return vmtStatusBeanList;
    }

    public String queryAverageTime(Connection con, String status) throws VMTException {
        log.info("queryAverageTime: status ----->" + status);
        String averageTime = "";
        String queryStatus = "";
        if (status.contains("_")) {
            String[] strStatusArray = status.split("_");
            queryStatus = strStatusArray[0] + "_COMPLETED";
        } else {
            queryStatus = "COMPLETED";
        }
        String queryString = "SELECT FLOOR(AVG(end_date-start_date)*24)||':'||LPAD(FLOOR(TRUNC(MOD(AVG(end_date-start_date)*24,1),2)*60),2,'0') " +
                "FROM VVA_DARC_CNTRL.VVM_LOG " +
                "WHERE start_date>sysdate-30 " +
                "AND status LIKE '" + queryStatus + "'";

        log.info("Query => \n" + queryString);

        try (Statement stmt = con.createStatement()) {
            ResultSet resultSet = stmt.executeQuery(queryString);

            if (resultSet != null && resultSet.next()) {
                averageTime = resultSet.getString(1);
            }
        } catch (SQLException sqlEx) {
            String errMsg = "Caught SQLException while doing average in VVM_LOG table. Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errMsg = "Caught SQLException while rolling back connection while doing average in VVM_LOG table.";
                    throw new VMTException(errMsg, sqlExp);
                }
            }
            throw new VMTException(errMsg, sqlEx);
        }

        return averageTime;
    }

    public List<RecSummaryBean> getCountsFromRecSummary(String loadName, Connection con)
            throws VMTException {

        List<RecSummaryBean> recSummaryBeanList = new ArrayList<>();

        try (Statement stmt = con.createStatement()) {
            String queryString = "SELECT MODULE_NAME, COUNTRY, to_char(START_DATE, 'MM/DD/YYYY HH24:MI:SS')\n" +
                    "\t, to_char(END_DATE, 'MM/DD/YYYY HH24:MI:SS'), LOAD_NAME\n" +
                    "\t, LOAD_TYPE, WF_NAME, WRKFLW_ORDER_NMBR, STATUS\n" +
                    "FROM VVA_DARC_CNTRL.VVM_LOAD_RECORD_SUMMARY\n" +
                    "WHERE END_DATE BETWEEN sysdate - 1 AND sysdate\n" +
                    "\tAND LOAD_NAME = '" + loadName.trim().toUpperCase() + "'\n" +
                    "ORDER BY START_DATE DESC";

            log.info("Query => \n" + queryString);
            ResultSet resultSet = stmt.executeQuery(queryString);

            if (null != resultSet) {
                while (resultSet.next()) {
                    RecSummaryBean recSummaryBean = new RecSummaryBean();
                    recSummaryBean.setModule(resultSet.getString(1));
                    recSummaryBean.setCountry(resultSet.getString(2));
                    recSummaryBean.setStartDate(resultSet.getString(3));
                    recSummaryBean.setEndDate(resultSet.getString(4));
                    recSummaryBean.setLoadName(resultSet.getString(5));
                    recSummaryBean.setLoadType(resultSet.getString(6));
                    recSummaryBean.setWfName(resultSet.getString(7));
                    recSummaryBean.setWfOrderNum(String.valueOf(resultSet.getInt(8)));
                    recSummaryBean.setStatus(resultSet.getString(9));
                    recSummaryBean.setColorCode(Util.determineColorCode(recSummaryBean));
                    recSummaryBean.setDisplay(Util.determineDisplay(recSummaryBean));
                    recSummaryBeanList.add(recSummaryBean);
                }
            }

        } catch (SQLException sqlEx) {
            String errMsg = "Caught SQLException while quering SYS_LOAD_RECORD_SUMMARY table. " +
                    "Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errMsg = "Caught SQLException while rolling back connection after failing to query " +
                            "SYS_LOAD_RECORD_SUMMARY table.";
                    throw new VMTException(errMsg, sqlExp);
                }
            }
            throw new VMTException(errMsg, sqlEx);
        }

        return recSummaryBeanList;
    }

    public List<RecSummaryBean> getArchivalFromRecSummary(String country, String startDate, String endDate,
                                                          String module, String loadName, Connection con)
            throws VMTException {

        List<RecSummaryBean> recSummaryBeanList = new ArrayList<>();

        String countries = Stream.of(country.split(","))
                .map(s -> "'" + s.trim() + "'")
                .collect(Collectors.joining(",", "(", ")"));

        try (Statement stmt = con.createStatement()) {
            String queryString = "SELECT MODULE_NAME, COUNTRY, to_char(START_DATE, 'MM/DD/YYYY HH24:MI:SS')\n" +
                    "\t, to_char(END_DATE, 'MM/DD/YYYY HH24:MI:SS'), LOAD_NAME\n" +
                    "\t, LOAD_TYPE, WF_NAME, WRKFLW_ORDER_NMBR, STATUS\n" +
                    "FROM VVA_DARC_CNTRL.VVM_LOAD_RECORD_SUMMARY\n" +
                    "WHERE to_char(END_DATE, 'YYYY-MM-DD') BETWEEN '" + startDate + "' AND '" + endDate + "'\n" +
                    " AND COUNTRY IN " + countries + "\n";
            if (module != null && !"".equals(module)) {
                queryString += " AND MODULE_NAME = '" + module + "'\n";
            }
            queryString += " AND LOAD_NAME LIKE '%" + loadName + "%'\n" +
                    "ORDER BY END_DATE DESC";

            log.info("Query => \n" + queryString);
            ResultSet resultSet = stmt.executeQuery(queryString);

            if (null != resultSet) {
                while (resultSet.next()) {
                    RecSummaryBean recSummaryBean = new RecSummaryBean();
                    recSummaryBean.setModule(resultSet.getString(1));
                    recSummaryBean.setCountry(resultSet.getString(2));
                    recSummaryBean.setStartDate(resultSet.getString(3));
                    recSummaryBean.setEndDate(resultSet.getString(4));
                    recSummaryBean.setLoadName(resultSet.getString(5));
                    recSummaryBean.setLoadType(resultSet.getString(6));
                    recSummaryBean.setWfName(resultSet.getString(7));
                    recSummaryBean.setWfOrderNum(String.valueOf(resultSet.getInt(8)));
                    recSummaryBean.setStatus(resultSet.getString(9));
                    recSummaryBean.setColorCode(Util.determineColorCode(recSummaryBean));
                    recSummaryBean.setDisplay(Util.determineDisplay(recSummaryBean));
                    recSummaryBeanList.add(recSummaryBean);
                }
            }

        } catch (SQLException sqlEx) {
            String errMsg = "Caught SQLException while quering SYS_LOAD_RECORD_SUMMARY table. " +
                    "Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errMsg = "Caught SQLException while rolling back connection after failing to query " +
                            "SYS_LOAD_RECORD_SUMMARY table.";
                    throw new VMTException(errMsg, sqlExp);
                }
            }
            throw new VMTException(errMsg, sqlEx);
        }

        return recSummaryBeanList;
    }

    public List<SessionLogBean> getDataFromSessionLog(String loadName, String wfName, Connection con) throws VMTException {
        List<SessionLogBean> sessionLogBeanList = new ArrayList<>();

        try (Statement stmt = con.createStatement()) {
            String queryString = "SELECT LOAD_NAME, WORFLOW_NAME, WORKFLOW_ID, WORKFLOW_RUN_ID, SESSION_NAME, " +
                    "SESSION_ID, STATUS, to_char(START_TIME,'MM/DD/YYYY HH24:MI:SS'), " +
                    "to_char(END_TIME,'MM/DD/YYYY HH24:MI:SS'), SUCCESFULL_ROWS, FAILED_ROWS, " +
                    "ERROR_CODE, ERROR_MESSAGE\n" +
                    "FROM VVA_DARC_CNTRL.VVM_LOAD_SESSION_LOG\n" +
                    "WHERE LOAD_NAME='" + loadName + "' " +
                    "AND WORFLOW_NAME='" + wfName + "'\n" +
                    "ORDER BY END_TIME DESC";

            log.info("Query => \n" + queryString);
            ResultSet resultSet = stmt.executeQuery(queryString);

            if (null != resultSet) {
                while (resultSet.next()) {
                    SessionLogBean sessionLogBean = new SessionLogBean();
                    sessionLogBean.setLoadName(resultSet.getString(1));
                    sessionLogBean.setWfName(resultSet.getString(2));
                    sessionLogBean.setWfId(resultSet.getInt(3));
                    sessionLogBean.setWfRunId(resultSet.getInt(4));
                    sessionLogBean.setSessionName(resultSet.getString(5));
                    sessionLogBean.setSessionId(resultSet.getInt(6));
                    sessionLogBean.setStatus(resultSet.getString(7));
                    sessionLogBean.setStartTime(resultSet.getString(8));
                    sessionLogBean.setEndTime(resultSet.getString(9));
                    sessionLogBean.setColorCode(Util.determineColorCode(sessionLogBean));
                    sessionLogBean.setSuccessfulRows(resultSet.getInt(10));
                    sessionLogBean.setFailedRows(resultSet.getInt(11));
                    sessionLogBean.setErrorCode(resultSet.getInt(12));
                    sessionLogBean.setErrorMessage(resultSet.getString(13));
                    sessionLogBeanList.add(sessionLogBean);
                }
            }

        } catch (SQLException sqlEx) {
            String errorMessage = "Caught SQLException while querying VVM_LOAD_SESSION_LOG table. Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errorMessage = "Caught SQLException while rolling back connection after failing" +
                            " to query VVM_LOAD_SESSION_LOG table.";
                    throw new VMTException(errorMessage, sqlExp);
                }
            }
            throw new VMTException(errorMessage, sqlEx);
        }

        return sessionLogBeanList;
    }

    public List<String> queryForCountryRegion(Connection con) throws VMTException {
        List<String> countryList = new ArrayList<>();
        try (Statement stmt = con.createStatement()) {
            String queryString = "SELECT PARTITION_NAME FROM VVA_DARC_CNTRL.PRTN_CTRY_RLTN";
            log.info("Query => \n" + queryString);
            ResultSet resultSet = stmt.executeQuery(queryString);
            if (resultSet != null) {
                while (resultSet.next()) {
                    countryList.add(resultSet.getString(1));
                }
            }
        } catch (SQLException sqlEx) {
            String errorMessage = "Caught SQLException while quering PRTN_CTRY_RLTN table. Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errorMessage = "Caught SQLException while rolling back connection after failing to query " +
                            "PRTN_CTRY_RLTN table.";

                    throw new VMTException(errorMessage, sqlExp);
                }
            }
            throw new VMTException(errorMessage, sqlEx);
        }

        return countryList;
    }

    public List<String> queryForSfApiName(Connection con, String country) throws VMTException {
        List<String> apiNameList = new ArrayList<>();
        try (Statement stmt = con.createStatement()) {
            String queryString = "select distinct sf_api_name " +
                    "from VVA_DARC_CNTRL.VVM_CNTRY_LOAD_TYPE_RELATION\n" +
                    "where country = '" + country + "' and load_type ='AUTO' and ACTV_IND='Y'\n" +
                    "order by sf_api_name asc";
            log.info("Query => \n" + queryString);
            ResultSet resultSet = stmt.executeQuery(queryString);
            if (resultSet != null) {
                while (resultSet.next()) {
                    apiNameList.add(resultSet.getString(1));
                }
            }
        } catch (SQLException sqlEx) {
            String errorMessage = "Caught SQLException while quering VVM_CNTRY_LOAD_TYPE_RELATION table. " +
                    "Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errorMessage = "Caught SQLException while rolling back connection after failing to query " +
                            "VVM_CNTRY_LOAD_TYPE_RELATION table.";

                    throw new VMTException(errorMessage, sqlExp);
                }
            }
            throw new VMTException(errorMessage, sqlEx);
        }

        return apiNameList;
    }

    public List<AuditSfdcBean> getDataFromAuditSfdc(String objectName, Connection con) throws VMTException {
        List<AuditSfdcBean> auditSfdcBeanList = new ArrayList<>();

        try (Statement stmt = con.createStatement()) {
            String queryString = "select table_name,db_column_name,data_type,sf_column_name," +
                    "to_char(created_date, 'MM/DD/YYYY HH24:MI:SS'),to_char(modified_date, 'MM/DD/YYYY HH24:MI:SS')," +
                    "is_include,sf_field_mod_type,operation " +
                    "from VVA_DARC_CNTRL.audit_sfdc\n" +
                    "where sf_object_name='" + objectName + "'\n" +
                    "and sf_column_name is not null\n" +
                    "and table_name like 'STG_%'";

            log.info("Query => \n" + queryString);
            ResultSet resultSet = stmt.executeQuery(queryString);

            if (null != resultSet) {
                while (resultSet.next()) {
                    AuditSfdcBean auditSfdcBean = new AuditSfdcBean();
                    auditSfdcBean.setTableName(resultSet.getString(1));
                    auditSfdcBean.setDbColumnName(resultSet.getString(2));
                    auditSfdcBean.setDataType(resultSet.getString(3));
                    auditSfdcBean.setSfColumnName(resultSet.getString(4));
                    auditSfdcBean.setCreateDate(resultSet.getString(5));
                    auditSfdcBean.setModifiedDate(resultSet.getString(6));
                    auditSfdcBean.setIsInclude(resultSet.getString(7));
                    auditSfdcBean.setSfFieldModType(resultSet.getString(8));
                    auditSfdcBean.setOperation(resultSet.getString(9));
                    auditSfdcBeanList.add(auditSfdcBean);
                }
            }

        } catch (SQLException sqlEx) {
            String errorMessage = "Caught SQLException while querying audit_sfdc table. Rolling back connection ....";
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException sqlExp) {
                    errorMessage = "Caught SQLException while rolling back connection after failing" +
                            " to query audit_sfdc table.";
                    throw new VMTException(errorMessage, sqlExp);
                }
            }
            throw new VMTException(errorMessage, sqlEx);
        }

        return auditSfdcBeanList;
    }
}
