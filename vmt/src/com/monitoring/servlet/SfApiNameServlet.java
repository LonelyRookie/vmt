/*
 * Cognizant Technology Solutions
 */
package com.monitoring.servlet;

import com.google.gson.Gson;
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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/04/17
 */
public class SfApiNameServlet extends HttpServlet {
    private static final long serialVersionUID = 1599023691020060319L;
    private static final Logger log = LogManager.getLogger(SfApiNameServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        VMTProcess vmtProcess = new VMTProcess();
        Gson gson = new Gson();
        JsonResponse jsonResponse = new JsonResponse();
        PrintWriter pw = resp.getWriter();

        String queryCountry = req.getParameter("country");
        String country = String.valueOf(req.getSession().getAttribute(VMTConstants.COUNTRY_DETAILS));
        List<String> havingAccessCountryList = Stream.of(country.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        if (queryCountry == null) {
            jsonResponse.setStatus(-1);
            jsonResponse.setMessage("failed, no parameter found!");
        } else if (!havingAccessCountryList.contains(queryCountry) && !VMTConstants.ALL.equals(country)) {
            jsonResponse.setStatus(-2);
            jsonResponse.setMessage("failed, no access to this country!");
        } else {
            jsonResponse.setStatus(0);
            jsonResponse.setMessage("success");

            try {
                List<String> sfApiNameList = vmtProcess.queryForSfApiName(queryCountry);
                jsonResponse.setLength(sfApiNameList.size());
                jsonResponse.setNames(sfApiNameList);
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
        private List<String> names;

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

        public List<String> getNames() {
            return names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }
    }
}
