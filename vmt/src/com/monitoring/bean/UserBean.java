/*
 * Cognizant Technology Solutions
 */
package com.monitoring.bean;

/**
 * @author Carl, Tao @ Cognizant
 * @date 2018/03/08
 */
public class UserBean {
	private String roleName = "";
	private String regionName = "";
	private String countryName = "";
	private String userStatus = "";
	private String userISID = "";

	private String roleEnrolled = "";
	private String countriesEnrolled = "";
	private String regionEnrolled = "";
	private String userEnrolled = "";
	private String statusEnrolled = "";

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserISID() {
		return userISID;
	}

	public void setUserISID(String userISID) {
		this.userISID = userISID;
	}

	public String getRoleEnrolled() {
		return roleEnrolled;
	}

	public void setRoleEnrolled(String roleEnrolled) {
		this.roleEnrolled = roleEnrolled;
	}

	public String getCountriesEnrolled() {
		return countriesEnrolled;
	}

	public void setCountriesEnrolled(String countriesEnrolled) {
		this.countriesEnrolled = countriesEnrolled;
	}

	public String getRegionEnrolled() {
		return regionEnrolled;
	}

	public void setRegionEnrolled(String regionEnrolled) {
		this.regionEnrolled = regionEnrolled;
	}

	public String getUserEnrolled() {
		return userEnrolled;
	}

	public void setUserEnrolled(String userEnrolled) {
		this.userEnrolled = userEnrolled;
	}

	public String getStatusEnrolled() {
		return statusEnrolled;
	}

	public void setStatusEnrolled(String statusEnrolled) {
		this.statusEnrolled = statusEnrolled;
	}

	@Override
	public String toString() {
		return "UserBean [roleName=" + roleName + ", regionName=" + regionName + ", countryName=" + countryName
				+ ", userStatus=" + userStatus + ", userISID=" + userISID + ", roleEnrolled=" + roleEnrolled
				+ ", countriesEnrolled=" + countriesEnrolled + ", regionEnrolled=" + regionEnrolled + ", userEnrolled="
				+ userEnrolled + ", statusEnrolled=" + statusEnrolled + "]";
	}

}
