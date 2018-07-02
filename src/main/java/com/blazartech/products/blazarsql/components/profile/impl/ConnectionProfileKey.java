/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile.impl;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import java.io.Serializable;

/**
 *
 * @author aar1069
 * @version $Id: ConnectionProfileKey.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
class ConnectionProfileKey implements Serializable, Comparable<ConnectionProfileKey> {

    private String userID;
    private String serverName;

    protected ConnectionProfileKey(String userID, String serverName) {
        this.userID = userID;
        this.serverName = serverName;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int compareTo(ConnectionProfileKey o) {
        if (getUserID().compareTo(o.getUserID()) == 0) {
            return getServerName().compareTo(o.getServerName());
        } else {
            return getUserID().compareTo(o.getUserID());
        }
    }

    @Override
    public boolean equals(Object obj) {
        ConnectionProfileKey key = (ConnectionProfileKey) obj;
        return this.compareTo(key) == 0;
    }
    
    public static ConnectionProfileKey makeKey(ConnectionProfile profile) {
        return new ConnectionProfileKey(profile.getUserID(), profile.getServerName());
    }
    
    public static ConnectionProfileKey makeKey(String userID, String serverName) {
        return new ConnectionProfileKey(userID, serverName);
    }
}
