/*
 * SupportDatabaseTypesModel.java
 *
 * Created on April 8, 2004, 12:48 PM
 */

package com.blazartech.products.blazarsql.components.gui;

import com.blazartech.products.blazarsql.components.dataobjects.ConnectionManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author  Dr. Scott E. Aaron
 * @version $Id: SupportedDatabaseTypesModel.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log: SupportedDatabaseTypesModel.java,v $
/* Revision 1.2  2008/08/27 19:30:46  AAR1069
/* use more imports
/*
 *******************************************************************************/

@Component
public class SupportedDatabaseTypesModel extends DefaultComboBoxModel implements InitializingBean {
    
    private static final Logger logger = LoggerFactory.getLogger(SupportedDatabaseTypesModel.class);
    
    /** Creates a new instance of SupportDatabaseTypesModel
     * @throws java.lang.Exception */
    @Override
    public void afterPropertiesSet() throws Exception {
        getSupportedTypes().forEach((s) -> {
            addElement(s);
        });
    }

    @Autowired
    private ConnectionManager connectionManager;

    private List<String> getSupportedTypes() {
        try {
            List<String> v = new ArrayList<>();

            Collection<String> supportedTypes = connectionManager.getSupportedServerTypes();
            v.addAll(supportedTypes);
            
            Collections.sort(v);
            return v;
        } catch (Exception e) {
            logger.error("error getting supported data types: " + e.getMessage(), e);
            return null;
        }
    }
    
}
