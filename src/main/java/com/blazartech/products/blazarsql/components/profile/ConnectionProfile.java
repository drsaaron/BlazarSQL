/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile;

import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author aar1069
 * @version $Id: ConnectionProfile.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public class ConnectionProfile implements Comparable<ConnectionProfile>, Serializable {

    private String userID;
    private String serverName;
    private String databaseName;
    private String serverType;
    private boolean defaultProfile;

    public boolean isDefaultProfile() {
        return defaultProfile;
    }

    public void setDefaultProfile(boolean defaultProfile) {
        this.defaultProfile = defaultProfile;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerType() {
        return serverType;
    }

    public void setServerType(String serverType) {
        this.serverType = serverType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    @Override
    public int compareTo(ConnectionProfile o) {
        return Comparator.comparing(ConnectionProfile::getServerType)
                .thenComparing(ConnectionProfile::getUserID)
                .thenComparing(ConnectionProfile::getServerName)
                .thenComparing(ConnectionProfile::getDatabaseName)
                .compare(this, o);
    }
    
    @Override
    public String toString() {
	String s = getUserID() + "/" + getServerName();
	if (getDatabaseName() != null) {
	    s += " (" + getDatabaseName() + ")";
	}
	return s;
    }
}
