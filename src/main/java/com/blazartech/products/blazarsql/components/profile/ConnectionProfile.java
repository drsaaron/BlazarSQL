/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile;

import java.io.Serializable;

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
        if (getServerType().compareTo(o.getServerType()) == 0) {
            if (getUserID().compareTo(o.getUserID()) == 0) {
                if (getServerName().compareTo(o.getServerName()) == 0) {
                    return getDatabaseName().compareTo(o.getDatabaseName());
                } else {
                    return getServerName().compareTo(o.getServerName());
                }
            } else {
                return getUserID().compareTo(o.getUserID());
            }
        } else {
            return getServerType().compareTo(o.getServerType());
        }
    }
    
    @Override
    public String toString() {
        return getUserID() + "/" + getServerName();
    }
}
