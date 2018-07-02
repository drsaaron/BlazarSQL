/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile;

import java.util.Collection;

/**
 * interface for a component to manage connection profiles.
 * 
 * @author aar1069
 * @version $Id: ConnectionProfileManager.java 30 2015-04-23 19:52:54Z aar1069 $
 */
public interface ConnectionProfileManager {
    
    /**
     * add a new profile.
     * 
     * @param profile 
     */
    void addProfile(ConnectionProfile profile);
    
    /**
     * remove a given profile.
     * 
     * @param profile 
     */
    void removeProfile(ConnectionProfile profile);
    
    /**
     * get a connection profile for a given user and server.
     * 
     * @param userID
     * @param serverName
     * @return 
     */
    ConnectionProfile getProfile(String userID, String serverName);
    
    /**
     * get the default profile, if any.
     * 
     * @return 
     */
    ConnectionProfile getDefaultProfile();
    
    /**
     * get all the connection profiles.
     * @return a
     */
    Collection<ConnectionProfile> getConnectionProfiles();

    /**
     * add a change listener
     * @param listener 
     */
    void addProfileCollectionChangeListener(ConnectionProfileCollectionChangeListener listener);
}
