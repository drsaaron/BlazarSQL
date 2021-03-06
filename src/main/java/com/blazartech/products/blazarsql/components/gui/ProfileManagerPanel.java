/*
 * ProfileManagerPanel.java
 *
 * Created on April 12, 2004, 11:05 AM
 */

package com.blazartech.products.blazarsql.components.gui;

import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import javax.swing.JPanel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author  aar1069
 */
@Component
@Scope("prototype") // make a prototype to ensure L&F updates get propogated.
public class ProfileManagerPanel extends JPanel implements InitializingBean {
    
    /** Creates new form ProfileManagerPanel
     * @throws java.lang.Exception */
    @Override
    public void afterPropertiesSet() throws Exception {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        _profileList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        _deleteProfileButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        _profileList.setModel(profileListModel);
        _profileList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        _profileList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                _profileListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(_profileList);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        _deleteProfileButton.setText("Delete");
        _deleteProfileButton.setEnabled(false);
        _deleteProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _deleteProfileButtonActionPerformed(evt);
            }
        });
        jPanel1.add(_deleteProfileButton);

        add(jPanel1, java.awt.BorderLayout.EAST);
    }// </editor-fold>//GEN-END:initComponents

    private void _deleteProfileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__deleteProfileButtonActionPerformed
        // Add your handling code here:
        ProfileListModel model = (ProfileListModel) _profileList.getModel();
        ConnectionProfile p = (ConnectionProfile) _profileList.getSelectedValue();
        model.removeProfile(p);
    }//GEN-LAST:event__deleteProfileButtonActionPerformed

    private void _profileListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event__profileListValueChanged
        // Add your handling code here:
        _deleteProfileButton.setEnabled(true);
    }//GEN-LAST:event__profileListValueChanged
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton _deleteProfileButton;
    private javax.swing.JList _profileList;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    @Autowired
    private ProfileListModel profileListModel;
}
