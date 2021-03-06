/*
 * ConnectionParametersPanel.java
 *
 * Created on April 2, 2004, 10:09 AM
 */
package com.blazartech.products.blazarsql.components.gui;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileCollectionChangeListener;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileCollectionChangedEvent;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileManager;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * A panel whence to gather database login parameters.
 *
 * @author Dr. Scott E. Aaron
 * @version $Id: ConnectionParametersPanel.java 30 2015-04-23 19:52:54Z aar1069
 * $
 */

/* $Log: ConnectionParametersPanel.java,v $
/* Revision 1.4  2008/10/03 18:57:16  aar1069
/* code cleanup
/*
/* Revision 1.3  2006/11/01 16:51:38  aar1069
/* Added some javadoc.
/*
/* Revision 1.2  2006/11/01 16:49:47  aar1069
/* Only set the default connection parameters if there is a default connection.
/*
 *******************************************************************************/
@Component
@Scope("prototype")
public final class ConnectionParametersPanel extends JPanel implements InitializingBean, ConnectionProfileCollectionChangeListener {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionParametersPanel.class);

    private void populateFromDefaultProfile() {
        // if there is a default connection profile, select it.
        logger.info("populating from default profile");
        ConnectionProfile defProf = getProfileManager().getDefaultProfile();
        if (defProf != null) {
            logger.info("default profile is not null");
            _profileComboBox.setSelectedItem(defProf);
            setConnectionParameters(defProf);
        } else {
            logger.info("default profile is null");
        }
    }

    @Override
    public void profileCollectionChanged(ConnectionProfileCollectionChangedEvent event) {
        populateFromDefaultProfile();
    }

    /**
     * Creates new form ConnectionParametersPanel
     *
     * @throws java.lang.Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        initComponents();

        // if there is a default connection profile, select it.
        populateFromDefaultProfile();

        /* fix the size of the server type combo box to be the same width as
         * the text boxes.  There's probably a better way to do this.  A different
         * layout manager?
         */
        Dimension serverNameSize = _serverName.getPreferredSize();
        Dimension serverTypeSize = _supportedTypesComboBox.getPreferredSize();
        serverTypeSize.width = serverNameSize.width;
        _supportedTypesComboBox.setPreferredSize(serverTypeSize);

        // add this as a listener for profile collection changes
        getProfileManager().addProfileCollectionChangeListener(this);
    }

    @Autowired
    private SupportedDatabaseTypesModel supportedDatabaseTypesModel;

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        _userIdPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        _userId = new javax.swing.JTextField();
        _serverPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        _serverName = new javax.swing.JTextField();
        _databasePanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        _database = new javax.swing.JTextField();
        serverTypePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        _supportedTypesComboBox = new javax.swing.JComboBox();
        profilePanel = new javax.swing.JPanel();
        _profileComboBox = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        _saveRequest = new javax.swing.JCheckBox();
        _defaultProfile = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Connection"));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        _userIdPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel1.setText("User ID");
        _userIdPanel.add(jLabel1);

        _userId.setColumns(getTextboxWidth());
        _userId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _userIdKeyReleased(evt);
            }
        });
        _userIdPanel.add(_userId);

        jPanel1.add(_userIdPanel);

        _serverPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel3.setText("Server");
        _serverPanel.add(jLabel3);

        _serverName.setColumns(getTextboxWidth());
        _serverName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _serverNameKeyReleased(evt);
            }
        });
        _serverPanel.add(_serverName);

        jPanel1.add(_serverPanel);

        _databasePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel4.setText("Database");
        _databasePanel.add(jLabel4);

        _database.setColumns(getTextboxWidth());
        _database.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                _databaseKeyReleased(evt);
            }
        });
        _databasePanel.add(_database);

        jPanel1.add(_databasePanel);

        serverTypePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jLabel2.setText("Server Type");
        serverTypePanel.add(jLabel2);

        _supportedTypesComboBox.setBackground(getBackground());
        _supportedTypesComboBox.setModel(supportedDatabaseTypesModel);
        _supportedTypesComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                _supportedTypesComboBoxItemStateChanged(evt);
            }
        });
        serverTypePanel.add(_supportedTypesComboBox);

        jPanel1.add(serverTypePanel);

        add(jPanel1, java.awt.BorderLayout.CENTER);

        profilePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Profiles"));

        _profileComboBox.setBackground(getBackground());
        _profileComboBox.setModel(profileListModel
        );
        _profileComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                _profileComboBoxItemStateChanged(evt);
            }
        });
        profilePanel.add(_profileComboBox);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        _saveRequest.setFont(new java.awt.Font("Dialog", 0, 8)); // NOI18N
        _saveRequest.setText("Save");
        _saveRequest.setToolTipText("Save connection parameters to profile");
        _saveRequest.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                _saveRequestItemStateChanged(evt);
            }
        });
        jPanel2.add(_saveRequest);

        _defaultProfile.setFont(_saveRequest.getFont());
        _defaultProfile.setText("Default");
        _defaultProfile.setToolTipText("The default profile");
        _defaultProfile.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                _defaultProfileItemStateChanged(evt);
            }
        });
        jPanel2.add(_defaultProfile);

        profilePanel.add(jPanel2);

        add(profilePanel, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents

    private void _databaseKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__databaseKeyReleased
        // Add your handling code here:
        handleUserType();
    }//GEN-LAST:event__databaseKeyReleased

    private void _serverNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__serverNameKeyReleased
        // Add your handling code here:
        handleUserType();
    }//GEN-LAST:event__serverNameKeyReleased

    private void _userIdKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event__userIdKeyReleased
        // Add your handling code here:
        handleUserType();
    }//GEN-LAST:event__userIdKeyReleased

    private void _supportedTypesComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event__supportedTypesComboBoxItemStateChanged
        // Add your handling code here:
        handleUserType();
    }//GEN-LAST:event__supportedTypesComboBoxItemStateChanged

    private void handleUserType() {
        setDefaultProfile(false);
    }

    private void _defaultProfileItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event__defaultProfileItemStateChanged
        // Add your handling code here:
        if (_defaultProfile.isSelected() && !_saveRequest.isSelected()) {
            _saveRequest.setSelected(true);
        } else if (!_defaultProfile.isSelected()) {
            ConnectionProfile p = (ConnectionProfile) _profileComboBox.getSelectedItem();
            if (p.isDefaultProfile() && p.getUserID().equals(getUserId())
                    && p.getServerName().equals(getServerName())
                    && p.getDatabaseName().equals(getDatabaseName())
                    && p.getServerType().equals(getDatabaseType())) {
                // was default, now is not.  Update the profile and mark for save.
                p.setDefaultProfile(false);
                setSaveRequested(true);
            }
        }
    }//GEN-LAST:event__defaultProfileItemStateChanged

    private void _saveRequestItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event__saveRequestItemStateChanged
        // Add your handling code here:
        setSaveRequested(_saveRequest.isSelected());
    }//GEN-LAST:event__saveRequestItemStateChanged

    private void _profileComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event__profileComboBoxItemStateChanged
        // Add your handling code here:
        ConnectionProfile p = (ConnectionProfile) _profileComboBox.getSelectedItem();
        setConnectionParameters(p);
    }//GEN-LAST:event__profileComboBoxItemStateChanged

    /**
     * Getter for property textboxWidth.
     *
     * @return Value of property textboxWidth.
     *
     */
    public int getTextboxWidth() {
        return this.textboxWidth;
    }

    /**
     * Setter for property textboxWidth.
     *
     * @param textboxWidth New value of property textboxWidth.
     *
     */
    public void setTextboxWidth(int textboxWidth) {
        int oldTextboxWidth = this.textboxWidth;
        this.textboxWidth = textboxWidth;
        firePropertyChange("textboxWidth", oldTextboxWidth, textboxWidth);
    }

    /**
     * Getter for property userId.
     *
     * @return Value of property userId.
     *
     */
    public String getUserId() {
        return _userId.getText();
    }

    /**
     * Setter for property userId.
     *
     * @param userId New value of property userId.
     *
     */
    public void setUserId(String userId) {
        _userId.setText(userId);
    }

    /**
     * Getter for property serverName.
     *
     * @return Value of property serverName.
     *
     */
    public String getServerName() {
        return _serverName.getText();
    }

    /**
     * Setter for property serverName.
     *
     * @param serverName New value of property serverName.
     *
     */
    public void setServerName(String serverName) {
        _serverName.setText(serverName);
    }

    /**
     * Getter for property database.
     *
     * @return Value of property database.
     *
     */
    public String getDatabaseName() {
        return _database.getText();
    }

    /**
     * Setter for property database.
     *
     * @param database New value of property database.
     *
     */
    public void setDatabaseName(String database) {
        _database.setText(database);
    }

    /**
     * Getter for property databaseType.
     *
     * @return Value of property databaseType.
     *
     */
    public String getDatabaseType() {
        return (String) _supportedTypesComboBox.getSelectedItem();
    }

    /**
     * Setter for property databaseType.
     *
     * @param databaseType New value of property databaseType.
     *
     */
    public void setDatabaseType(String databaseType) {
        _supportedTypesComboBox.setSelectedItem(databaseType);
    }

    /**
     * Set the parameters according to a selected profile.
     *
     * @param profile the profile
     */
    public void setConnectionParameters(ConnectionProfile profile) {
        if (profile != null) {
            setUserId(profile.getUserID());
            setServerName(profile.getServerName());
            setDatabaseName(profile.getDatabaseName());
            setDatabaseType(profile.getServerType());

            // if this is the default connection, so indicate.
            setDefaultProfile(profile.isDefaultProfile());
        }

        // Since this is from a profile, we don't need to save it.
        setSaveRequested(false);
        _saveRequest.setSelected(false);
    }

    /**
     * Getter for property saveRequested.
     *
     * @return Value of property saveRequested.
     *
     */
    public boolean isSaveRequested() {
        return _saveRequest.isSelected();
    }

    /**
     * Setter for property saveRequested.
     *
     * @param saveRequested New value of property saveRequested.
     *
     */
    public void setSaveRequested(boolean saveRequested) {
        _saveRequest.setSelected(saveRequested);
    }

    /**
     * Getter for property profileManager.
     *
     * @return Value of property profileManager.
     *
     */
    public ConnectionProfileManager getProfileManager() {
        return this.profileManager;
    }

    /**
     * Getter for property defaultProfile.
     *
     * @return Value of property defaultProfile.
     *
     */
    public boolean isDefaultProfile() {
        return _defaultProfile.isSelected();
    }

    /**
     * Setter for property defaultProfile.
     *
     * @param defaultProfile New value of property defaultProfile.
     *
     */
    public void setDefaultProfile(boolean defaultProfile) {
        _defaultProfile.setSelected(defaultProfile);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField _database;
    private javax.swing.JPanel _databasePanel;
    private javax.swing.JCheckBox _defaultProfile;
    private javax.swing.JComboBox _profileComboBox;
    private javax.swing.JCheckBox _saveRequest;
    private javax.swing.JTextField _serverName;
    private javax.swing.JPanel _serverPanel;
    private javax.swing.JComboBox _supportedTypesComboBox;
    private javax.swing.JTextField _userId;
    private javax.swing.JPanel _userIdPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JPanel serverTypePanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Holds value of property textboxWidth.
     */
    private int textboxWidth = 13;

    /**
     * Holds value of property profileManager.
     */
    @Autowired
    private ConnectionProfileManager profileManager;

    @Autowired
    private ProfileListModel profileListModel;
}
