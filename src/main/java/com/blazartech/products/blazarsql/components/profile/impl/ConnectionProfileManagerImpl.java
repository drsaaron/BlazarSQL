/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile.impl;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileCollectionChangeListener;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileCollectionChangedEvent;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * base class for an implementation of the connection profile that persists the
 * data; derived classes will implement the persisting functionality.
 *
 * @author aar1069
 * @version $Id: ConnectionProfileManagerImpl.java 30 2015-04-23 19:52:54Z
 * aar1069 $
 */

/* $Log$
 *******************************************************************************/
public abstract class ConnectionProfileManagerImpl implements ConnectionProfileManager {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionProfileManagerImpl.class);

    abstract protected void persistProfiles();

    abstract protected void loadProfiles();

    private final Map<ConnectionProfileKey, ConnectionProfile> indexedProfileCollection = new TreeMap<>();

    private Collection<ConnectionProfile> profiles = new ArrayList<>();

    public Collection<ConnectionProfile> getProfiles() {
        return profiles;
    }

    private void sortProfiles() {
        List<ConnectionProfile> profileList = new ArrayList<>();
        profileList.addAll(profiles);
        Collections.sort(profileList);
        profiles.clear();
        profiles.addAll(profileList);
    }

    public void setProfiles(Collection<ConnectionProfile> profiles) {
        this.profiles = profiles;
        sortProfiles();

        // populate the indexed collection.
        indexedProfileCollection.clear();
        profiles.stream().forEach((p) -> {
            indexedProfileCollection.put(ConnectionProfileKey.makeKey(p), p);
        });
    }

    @Override
    public synchronized void addProfile(ConnectionProfile profile) {
        // do we already have a profile of the same key?
        ConnectionProfileKey key = ConnectionProfileKey.makeKey(profile);
        ConnectionProfile p = indexedProfileCollection.get(key);
        if (p != null) {
            profiles.remove(p);
        }

        // update default profile if necessary
        if (profile.isDefaultProfile()) {
            ConnectionProfile currentDefault = getDefaultProfile();
            if (currentDefault != null) {
                currentDefault.setDefaultProfile(false);
            }
        }

        // add to both sets.
        profiles.add(profile);
        sortProfiles();
        indexedProfileCollection.put(ConnectionProfileKey.makeKey(profile), profile);

        // persist.
        persistProfiles();

        // notify listeners
        fireProfileCollectionChangeEvent();
    }

    @Override
    public synchronized void removeProfile(ConnectionProfile profile) {
        ConnectionProfileKey key = ConnectionProfileKey.makeKey(profile);
        indexedProfileCollection.remove(key);
        profiles.remove(profile);
        persistProfiles();

        // notify listeners
        fireProfileCollectionChangeEvent();
    }

    @Override
    public ConnectionProfile getProfile(String userID, String serverName) {
        ConnectionProfileKey key = ConnectionProfileKey.makeKey(userID, serverName);
        return indexedProfileCollection.get(key);
    }

    @Override
    public Collection<ConnectionProfile> getConnectionProfiles() {
        return getProfiles();
    }

    @Override
    public ConnectionProfile getDefaultProfile() {
        return getProfiles().stream().filter(p -> p.isDefaultProfile()).findFirst().orElse(null);
    }

    private final Collection<ConnectionProfileCollectionChangeListener> changeListeners = new ArrayList<>();

    @Override
    public void addProfileCollectionChangeListener(ConnectionProfileCollectionChangeListener listener) {
        changeListeners.add(listener);
    }

    protected void fireProfileCollectionChangeEvent() {
        ConnectionProfileCollectionChangedEvent event = new ConnectionProfileCollectionChangedEvent(this);
        changeListeners.forEach((l) -> {
            l.profileCollectionChanged(event);
        });
    }
}
