/*
 * Cognizant Technology Solutions
 */
package com.monitoring.action.login;

import com.monitoring.action.BaseAbstractAction;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
class LoginForm extends BaseAbstractAction {
    private static final long serialVersionUID = 8781645154848602279L;

    private String userName = "";
    private String password = "";

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}