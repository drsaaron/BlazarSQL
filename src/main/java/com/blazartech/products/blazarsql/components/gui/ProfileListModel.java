/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileCollectionChangeListener;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileCollectionChangedEvent;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileManager;
import javax.swing.DefaultComboBoxModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author aar1069
 * @version $Id: ProfileListModel.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
@Component
public class ProfileListModel extends DefaultComboBoxModel<ConnectionProfile> implements InitializingBean, ConnectionProfileCollectionChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(ProfileListModel.class);

    @Autowired
    private ConnectionProfileManager profileManager;
    
    /** Creates a new instance of ProfileListModel
     * @throws java.lang.Exception */
    @Override
    public void afterPropertiesSet() throws Exception {
        profileManager.addProfileCollectionChangeListener(this);
        refreshElements();
    }
    
    public void removeProfile(ConnectionProfile profile) {
        profileManager.removeProfile(profile);
    }

    @Override
    public void profileCollectionChanged(ConnectionProfileCollectionChangedEvent event) {
        refreshElements();
        fireContentsChanged(this, 0, profileManager.getConnectionProfiles().size());
    }
    
    private void refreshElements() {
        logger.info("refreshing connection profiles collection");
        
        removeAllElements();
        profileManager.getConnectionProfiles().forEach((p) -> {
            logger.info("adding element " + p.getDatabaseName());
            addElement(p);
        });        
    }
}
