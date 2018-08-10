/*
 * Cognizant Technology Solutions
 */
package com.monitoring.bean;

import java.io.Serializable;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/04/17
 */
public class AuditSfdcBean implements Serializable {
    private static final long serialVersionUID = -7577128067416369044L;

    private String tableName;
    private String dbColumnName;
    private String dataType;
    private String sfColumnName;
    private String createDate;
    private String modifiedDate;
    private String isInclude;
    private String sfFieldModType;
    private String operation;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDbColumnName() {
        return dbColumnName;
    }

    public void setDbColumnName(String dbColumnName) {
        this.dbColumnName = dbColumnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getSfColumnName() {
        return sfColumnName;
    }

    public void setSfColumnName(String sfColumnName) {
        this.sfColumnName = sfColumnName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getIsInclude() {
        return isInclude;
    }

    public void setIsInclude(String isInclude) {
        this.isInclude = isInclude;
    }

    public String getSfFieldModType() {
        return sfFieldModType;
    }

    public void setSfFieldModType(String sfFieldModType) {
        this.sfFieldModType = sfFieldModType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
