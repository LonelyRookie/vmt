/*
 * Cognizant Technology Solutions
 */
package com.monitoring.dao;

import com.jolbox.bonecp.BoneCPConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/09
 */
public class ConnectionManager {
    private static final Logger log = LogManager.getLogger(ConnectionManager.class);
    private static com.jolbox.bonecp.BoneCP boneCPConnectionPool = null;

    public static com.jolbox.bonecp.BoneCP getConnectionPool() {
        return boneCPConnectionPool;
    }

    public static void setConnectionPool(com.jolbox.bonecp.BoneCP connectionPool) {
        ConnectionManager.boneCPConnectionPool = connectionPool;
    }

    public static void configureBoneCPConnectionPool(String url, String userName, String password) {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            BoneCPConfig boneCPConfig = new BoneCPConfig();
            boneCPConfig.setJdbcUrl(url);
            boneCPConfig.setUsername(userName);
            boneCPConfig.setPassword(password);
            boneCPConfig.setMinConnectionsPerPartition(5);
            boneCPConfig.setMaxConnectionsPerPartition(10);
            boneCPConfig.setAcquireIncrement(5);
            boneCPConfig.setLazyInit(true);
            boneCPConfig.setAcquireRetryAttempts(1);
            boneCPConfig.setIdleMaxAgeInMinutes(0);
            boneCPConfig.setConnectionTestStatement("select sysdate from dual");
            boneCPConfig.setIdleConnectionTestPeriodInMinutes(2);

            boneCPConnectionPool = new com.jolbox.bonecp.BoneCP(boneCPConfig);
            setConnectionPool(boneCPConnectionPool);
            log.info("context Initialized.....Connection Pooling is configured");
        } catch (Exception e) {
            log.info("Exception encountered on configuring bonecp connection pool.." + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void shutdownBoneCPConnectionPool() {
        try {
            com.jolbox.bonecp.BoneCP connectionPool = ConnectionManager.getConnectionPool();
            if (connectionPool != null) {
                connectionPool.shutdown();
                log.info("context Destroyed.....Connection Pooling shut downed!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}