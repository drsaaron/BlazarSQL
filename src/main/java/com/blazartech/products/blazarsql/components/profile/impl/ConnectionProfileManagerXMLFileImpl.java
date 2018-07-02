/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.profile.impl;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * implement a connection profile manager that persists the data to an XML
 * file via XMLEncoder.
 * 
 * @author aar1069
 * @version $Id: ConnectionProfileManagerXMLFileImpl.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
@Component
public class ConnectionProfileManagerXMLFileImpl extends ConnectionProfileManagerImpl implements InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionProfileManagerXMLFileImpl.class);
    
    @Autowired
    @Qualifier("ConnectionProfileSaveFile")
    private File saveFile;

    public void afterPropertiesSet() throws Exception {
        loadProfiles();
    }
    
    public File getSaveFile() {
        return saveFile;
    }

    public void setSaveFile(File saveFile) {
        this.saveFile = saveFile;
        loadProfiles();
    }

    @Override
    protected void persistProfiles() {
        try (FileOutputStream os = new FileOutputStream(getSaveFile()); XMLEncoder e = new XMLEncoder(os)) {
            e.setPersistenceDelegate(ArrayList.class, e.getPersistenceDelegate(List.class));

            // copy the profile to an array list, to ensure we know the type.
            List<ConnectionProfile> profileArrayList = new ArrayList<>();
            profileArrayList.addAll(getConnectionProfiles());

            // write it out
            e.writeObject(profileArrayList);

        } catch (IOException e) {
            throw new RuntimeException("error saving profiles: " + e.getMessage(), e);
        }
    }

    @Override
    protected void loadProfiles() {
        File f = getSaveFile();
        logger.info("reading profile file at " + f.getAbsolutePath());
        
        if (f.exists() && f.length() > 0) {
            try (FileInputStream is = new FileInputStream(f)) {
                XMLDecoder dec = new XMLDecoder(is);
                List<ConnectionProfile> profileList = (List<ConnectionProfile>) dec.readObject();
                setProfiles(profileList);
            } catch (IOException e) {
                throw new RuntimeException("error reading input file: " + e.getMessage(), e);
            }
        }
    }
}
