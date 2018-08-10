/*
 * Cognizant Technology Solutions
 */
package com.monitoring.servlet;

import com.google.gson.Gson;
import com.monitoring.bean.UserBean;
import com.monitoring.constants.VMTConstants;
import com.monitoring.exception.VMTException;
import com.monitoring.process.VMTProcess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/04/27
 */
public class CountryListServlet extends HttpServlet {
    private static final long serialVersionUID = 1599023691020060319L;
    private static final Logger log = LogManager.getLogger(CountryListServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VMTProcess vmtProcess = new VMTProcess();
        Gson gson = new Gson();
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter pw = resp.getWriter();

        String queryISID = req.getParameter("ISID");

        if (req.getSession().getAttribute(VMTConstants.USER_ID) == null) {
            jsonResponse.setStatus(-1);
            jsonResponse.setMessage("Session expired!");
        } else if (queryISID == null) {
            jsonResponse.setStatus(-2);
            jsonResponse.setMessage("failed, no parameter found!");
        } else {
            jsonResponse.setStatus(0);
            jsonResponse.setMessage("success");

            try {
                List<String> countryList = vmtProcess.queryForCountry();
                UserBean user = vmtProcess.findUserDetailsProcess(queryISID);
                String userCountry = user != null ? user.getCountryName() : "";
                List<String> selectedCountryList;
                if (VMTConstants.ALL.equals(userCountry)) {
                    selectedCountryList = countryList;
                } else {
                    selectedCountryList = Arrays.stream(userCountry.split(","))
                            .map(String::trim)
                            .collect(Collectors.toList());
                }

                List<Option> options = new ArrayList<>();
                for (String country : countryList) {
                    Option option = new Option();
                    option.setCountry(country);
                    option.setSelected(selectedCountryList.contains(country));

                    options.add(option);
                }

                jsonResponse.setOptions(options);
                jsonResponse.setLength(options.size());
                jsonResponse.setStatus(0);
                jsonResponse.setMessage("success");
            } catch (VMTException e) {
                e.printStackTrace();
            }
        }

        pw.write(gson.toJson(jsonResponse));
        pw.flush();
    }

    private class JsonResponse {
        private int status;
        private String message;
        private int length;
        private List<Option> options;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public List<Option> getOptions() {
            return options;
        }

        public void setOptions(List<Option> options) {
            this.options = options;
        }
    }

    private class Option {
        private String country;
        private boolean isSelected;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }
    }
}
