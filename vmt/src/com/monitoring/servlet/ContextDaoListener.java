/*
 * Cognizant Technology Solutions
 */
package com.monitoring.servlet;

import com.monitoring.dao.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/09
 */
public class ContextDaoListener implements ServletContextListener {
    private static final Logger log = LogManager.getLogger(ContextDaoListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("\n---------------------------------------------");
        log.info("ContextInitialized Method has been Called......");

        ServletContext sc = servletContextEvent.getServletContext();
        String url = sc.getInitParameter("url");
        String userName = sc.getInitParameter("dbUserName");
        String password = sc.getInitParameter("dbPassword");

        ConnectionManager.configureBoneCPConnectionPool(url, userName, password);
        log.info("---------------------------------------------\n");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        log.info("\n---------------------------------------------");
        log.info("contextDestroyed Method has been Called......");
        ConnectionManager.shutdownBoneCPConnectionPool();
        log.info("---------------------------------------------\n");
    }
}
