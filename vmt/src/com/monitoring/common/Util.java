/*
 * Cognizant Technology Solutions
 */
package com.monitoring.common;

import com.monitoring.bean.RecSummaryBean;
import com.monitoring.bean.SessionLogBean;
import com.monitoring.bean.VMTStatusBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/13
 */
public class Util {
    private static final Logger log = LogManager.getLogger(Util.class);

    public static String calculateTimeBetweenTwoDates(String strStartDate, String strEndDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        LocalDateTime startTime = LocalDateTime.parse(strStartDate, formatter);
        LocalDateTime endTime = LocalDateTime.parse(strEndDate, formatter);
        Duration duration = Duration.between(startTime, endTime);
        String timeDiff = duration.toHours() + ":" + (duration.toMinutes() - duration.toHours() * 60);
        return timeDiff;
    }

    public static String determineColorCode(VMTStatusBean vmtStatusBean) {
        String colorCode = "";
        String status = vmtStatusBean.getStatus().toUpperCase();

        if (validateDuration(vmtStatusBean.getTimeElapsedSince())) {
            colorCode = "red";
        } else {
            if (status.contains("COMPLETED")) {
                colorCode = "green";
            } else if (status.contains("RUNNING")) {
                colorCode = "orange";
            }
        }

        if (status.contains("FAILED")) {
            colorCode = "red";
        }

        return colorCode;
    }

    public static String determineColorCode(RecSummaryBean recSummaryBean) {
        String colorCode = "";
        String status = recSummaryBean.getStatus().toUpperCase();

        if (status.contains("SUCCEED")) {
            colorCode = "blue";
        } else if (status.contains("RUNNING")) {
            colorCode = "orange";
        } else if (status.contains("COMPLETED")) {
            colorCode = "green";
        } else if (status.contains("FAILED")) {
            colorCode = "red";
        }

        return colorCode;
    }

    public static String determineColorCode(SessionLogBean sessionLogBean) {
        String colorCode = "";
        String status = sessionLogBean.getStatus().toUpperCase();

        if (status.contains("SUCCEED")) {
            colorCode = "green";
        } else if (status.contains("FAILED")) {
            colorCode = "red";
        }

        return colorCode;
    }

    public static String determineDisplay(VMTStatusBean vmtStatusBean) {
        String display = "";
        String status = vmtStatusBean.getStatus().toUpperCase();

        // The view link always visible
        /*if (status.contains("RUNNING") || status.contains("QUEUED")) {
            display = "none";
        }*/

        return display;
    }

    public static String determineDisplay(RecSummaryBean recSummaryBean) {
        String display = "";
        String status = recSummaryBean.getStatus().toUpperCase();
        
        if (status.contains("RUNNING") || status.contains("QUEUED")) {
            display = "none";
        }
        
        return display;
    }

    private static boolean validateDuration(String duration) {
        boolean status = false;
        if (!"N.A.".equals(duration)) {
            String[] durationArray = duration.split(":");
            if (durationArray.length > 0 && Integer.parseInt(durationArray[0]) > 23) {
                status = true;
            }
        }
        return status;
    }
}
