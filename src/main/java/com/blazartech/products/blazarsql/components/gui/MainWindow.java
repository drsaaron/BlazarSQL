/*
 * MainWindow.java
 *
 * Created on April 9, 2004, 3:34 PM
 */

package com.blazartech.products.blazarsql.components.gui;

import com.blazartech.util.gui.exception.ExceptionDisplayPanel;
import com.blazartech.products.blazarsql.Constants;
import com.blazartech.products.blazarsql.components.gui.editor.SQLEditor;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfile;
import com.blazartech.products.blazarsql.components.profile.ConnectionProfileManager;
import com.blazartech.products.blazarsql.components.writer.ResultSetWriter;
import com.blazartech.products.blazarsql.components.writer.ResultSetWriterFactory;
import com.blazartech.products.lafman.UILookAndFeelManager;
import java.awt.*;
import java.awt.Cursor;
import java.awt.event.ContainerAdapter;
import java.awt.event.ContainerEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.Preferences;
import jakarta.inject.Provider;
import javax.swing.*;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Main window for the BlazarSQL application.  This application provides a
 * graphical interface for interactive database queries as well as access to
 * database object definitions.
 * @author Dr. Scott E. Aaron
 * @version $Id: MainWindow.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log: MainWindow.java,v $
/* Revision 1.11  2009/03/18 14:16:19  aar1069
/* resolved some warning messages by adding override annotations where
/* appropriate and renaming some conflicting constants.
/*
/* Revision 1.10  2009/03/18 14:10:08  aar1069
/* removed the icon from the about dialog; the JOptionPane uses its own
/*
/* Revision 1.9  2008/10/03 18:36:47  aar1069
/* Fixed the L&F map correctly using generics.
/*
/* Revision 1.8  2008/08/27 18:06:59  AAR1069
/* added a preference menu item for editor width instead of forcing it to be 35%
/*
/* Revision 1.7  2008/02/11 21:52:22  aar1069
/* updated the main method to use setVisible instead of show.
/*
/* Revision 1.6  2007/07/17 19:19:23  AAR1069
/* no message
/*
/* Revision 1.5  2006/11/01 23:15:49  aar1069
/* Removed the Objects option from the datasource menu.  The object browser is now
/* embedded in the connection panel.
/*
/* Revision 1.4  2006/09/21 19:26:51  aar1069
/* Expand handleException to provide more information.  Dump information via
/* log4j rather than simple print.
/*
/* Revision 1.3  2006/09/08 16:30:51  aar1069
/* Get the list of LAFs from the application properties.
/*
/* Revision 1.2  2006/09/08 16:22:01  aar1069
/* Add Plastic3D L&F.
/*
 *******************************************************************************/
@Component
public final class MainWindow extends JFrame implements InitializingBean {
    
    private static final Logger logger = LoggerFactory.getLogger(MainWindow.class);
    
    @Autowired
    private UILookAndFeelManager lafManager;
    
    /** Creates new form MainWindow
     * @throws java.lang.Exception */
    @Override
    public void afterPropertiesSet() throws Exception {
        initComponents();
        
        // set the tab close confirmation preferences.
        boolean confirmClosePreference = _appPreferences.getBoolean(CONFIRM_CLOSE_PROPERTY, true);
        confirmClosePreferenceMenuItem.setSelected(confirmClosePreference);
        
        _queryFileFilter.setExtension("sql");
        _queryFileFilter.setDescription("SQL Queries");
        _htmlFileFilter.setExtension("html");
        _htmlFileFilter.setDescription("HTML files");
        _csvFileFilter.setExtension("csv");
        _csvFileFilter.setDescription("CSV files");
        
        // setup the main pane
        _mainPane.setConfirmClose(confirmClosePreference);
        _mainPane.addChangeListener((ChangeEvent evt) -> {
            _mainPaneStateChanged(evt);
        });
        _mainPane.addContainerListener(new ContainerAdapter() {
            @Override
            public void componentAdded(ContainerEvent evt) {
                _mainPaneComponentAdded(evt);
            }
            @Override
            public void componentRemoved(ContainerEvent evt) {
                _mainPaneComponentRemoved(evt);
            }
        });
        _mainPane.addPropertyChangeListener((PropertyChangeEvent evt) -> {
            _mainPanePropertyChange(evt);
        });
        getContentPane().add(_mainPane, java.awt.BorderLayout.CENTER);

        // setup LAF options.
        // add a property change listener for the LAF, so that the popup menu's LAF is also updated
        lafManager.addPropertyChangeListener(UILookAndFeelManager.PROP_CURRENTLOOKANDFEELNAME, (PropertyChangeEvent evt) -> {
            logger.info("udpating LAF to " + lafManager.getCurrentLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(lookAndFeelPreferenceMenuItem);
        });

        // add the look and feel options.
        lafManager.initializePreferencesAndMenu(lookAndFeelPreferenceMenuItem, _lafMenuButtonGroup, _appPreferences, MainWindow.this);
        
        // establish the user preferences.
        // Set window size
        int h = _appPreferences.getInt(WIN_HEIGHT_KEY, -1);
        int w = _appPreferences.getInt(WIN_WIDTH_KEY, -1);
        if (h > 0 && w > 0) { setSize(new Dimension(w, h)); }
        
        // set window position.
        int x = _appPreferences.getInt(WIN_POSITION_X_KEY, -1);
        int y = _appPreferences.getInt(WIN_POSITION_Y_KEY, -1);
        if (x > 0 && y > 0) { setLocation(x, y); }
        
        // set the default divider location in connection panels to be 35% of the total width.
        float defaultEditorWidth = _appPreferences.getFloat(EDITOR_WIDTH_PROPERTY, 0.35f);
        int defDivLocation = (int) (getWidth() * defaultEditorWidth);
        _mainPane.setDividerInitialLocation(defDivLocation);
        
        // ready to go.
        setStatusMessage("Ready.");
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        _queryFileChooser = new javax.swing.JFileChooser();
        _htmlFileChooser = new javax.swing.JFileChooser();
        _csvFileChooser = new javax.swing.JFileChooser();
        _lafMenuButtonGroup = new javax.swing.ButtonGroup();
        _messageLabel = new javax.swing.JLabel();
        _toolbarPanel = new javax.swing.JPanel();
        fileToolBar = new javax.swing.JToolBar();
        openToolbarButton = new javax.swing.JButton();
        saveToolbarButton = new javax.swing.JButton();
        editToolBar = new javax.swing.JToolBar();
        cutToolbarButton = new javax.swing.JButton();
        copyToolbarButton = new javax.swing.JButton();
        pasteToolbarButton = new javax.swing.JButton();
        jSeparator10 = new javax.swing.JSeparator();
        findToolbarButton = new javax.swing.JButton();
        findAgainToolbarButton = new javax.swing.JButton();
        replaceToolbarButton = new javax.swing.JButton();
        queryToolbar = new javax.swing.JToolBar();
        executeQueryToolbarButton = new javax.swing.JButton();
        cancelQueryToolbarButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        jSeparator7 = new javax.swing.JSeparator();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        jSeparator6 = new javax.swing.JSeparator();
        selectAllMenuItem = new javax.swing.JMenuItem();
        jSeparator9 = new javax.swing.JSeparator();
        editPreferencesMenu = new javax.swing.JMenu();
        lookAndFeelPreferenceMenuItem = new javax.swing.JMenu();
        confirmClosePreferenceMenuItem = new javax.swing.JCheckBoxMenuItem();
        _sliderPositionPreferenceMenuItem = new javax.swing.JMenuItem();
        datasourceMenu = new javax.swing.JMenu();
        openConnectionMenuItem = new javax.swing.JMenuItem();
        disconnectMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        cloneConnectionMenuItem = new javax.swing.JMenuItem();
        reconnectMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        infoMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        profilesMenuItem = new javax.swing.JMenuItem();
        queryMenu = new javax.swing.JMenu();
        executeMenuItem = new javax.swing.JMenuItem();
        cancelQueryMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        saveResultsMenu = new javax.swing.JMenu();
        saveResultsAsHtmlMenuItem = new javax.swing.JMenuItem();
        saveResultsAsCSVMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        _queryFileChooser.setAcceptAllFileFilterUsed(false);
        _queryFileChooser.setFileFilter(_queryFileFilter);

        _htmlFileChooser.setAcceptAllFileFilterUsed(false);
        _htmlFileChooser.setFileFilter(_htmlFileFilter);

        _csvFileChooser.setAcceptAllFileFilterUsed(false);
        _csvFileChooser.setFileFilter(_csvFileFilter);

        setTitle("BlazarSQL");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                exitForm(evt);
            }
        });
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });

        _messageLabel.setText(" ");
        getContentPane().add(_messageLabel, java.awt.BorderLayout.SOUTH);

        _toolbarPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        fileToolBar.setName("File"); // NOI18N

        openToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Open16.gif"))); // NOI18N
        openToolbarButton.setToolTipText("Open query file.");
        openToolbarButton.setEnabled(false);
        openToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openToolbarButtonActionPerformed(evt);
            }
        });
        fileToolBar.add(openToolbarButton);

        saveToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Save16.gif"))); // NOI18N
        saveToolbarButton.setToolTipText("Save query to file.");
        saveToolbarButton.setEnabled(false);
        saveToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveToolbarButtonActionPerformed(evt);
            }
        });
        fileToolBar.add(saveToolbarButton);

        _toolbarPanel.add(fileToolBar);

        editToolBar.setName("Edit"); // NOI18N

        cutToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Cut16.gif"))); // NOI18N
        cutToolbarButton.setToolTipText("Cut");
        cutToolbarButton.setEnabled(false);
        cutToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutToolbarButtonActionPerformed(evt);
            }
        });
        editToolBar.add(cutToolbarButton);

        copyToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Copy16.gif"))); // NOI18N
        copyToolbarButton.setToolTipText("Copy");
        copyToolbarButton.setEnabled(false);
        copyToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyToolbarButtonActionPerformed(evt);
            }
        });
        editToolBar.add(copyToolbarButton);

        pasteToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Paste16.gif"))); // NOI18N
        pasteToolbarButton.setToolTipText("Paste");
        pasteToolbarButton.setEnabled(false);
        pasteToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteToolbarButtonActionPerformed(evt);
            }
        });
        editToolBar.add(pasteToolbarButton);

        jSeparator10.setOrientation(javax.swing.SwingConstants.VERTICAL);
        editToolBar.add(jSeparator10);

        findToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Find16.gif"))); // NOI18N
        findToolbarButton.setToolTipText("Find");
        findToolbarButton.setEnabled(false);
        findToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findToolbarButtonActionPerformed(evt);
            }
        });
        editToolBar.add(findToolbarButton);

        findAgainToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/FindAgain16.gif"))); // NOI18N
        findAgainToolbarButton.setToolTipText("Find again");
        findAgainToolbarButton.setEnabled(false);
        findAgainToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findAgainToolbarButtonActionPerformed(evt);
            }
        });
        editToolBar.add(findAgainToolbarButton);

        replaceToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Replace16.gif"))); // NOI18N
        replaceToolbarButton.setToolTipText("Replace");
        replaceToolbarButton.setEnabled(false);
        replaceToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceToolbarButtonActionPerformed(evt);
            }
        });
        editToolBar.add(replaceToolbarButton);

        _toolbarPanel.add(editToolBar);

        queryToolbar.setName("Query"); // NOI18N

        executeQueryToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/media/Play16.gif"))); // NOI18N
        executeQueryToolbarButton.setToolTipText("Execute Query");
        executeQueryToolbarButton.setEnabled(false);
        executeQueryToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeQueryToolbarButtonActionPerformed(evt);
            }
        });
        queryToolbar.add(executeQueryToolbarButton);

        cancelQueryToolbarButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Stop16.gif"))); // NOI18N
        cancelQueryToolbarButton.setToolTipText("Cancel Query");
        cancelQueryToolbarButton.setEnabled(false);
        cancelQueryToolbarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelQueryToolbarButtonActionPerformed(evt);
            }
        });
        queryToolbar.add(cancelQueryToolbarButton);

        _toolbarPanel.add(queryToolbar);

        getContentPane().add(_toolbarPanel, java.awt.BorderLayout.NORTH);

        fileMenu.setText("File");

        openMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Open16.gif")));
        openMenuItem.setText("Open");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);

        saveMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Save16.gif")));
        saveMenuItem.setText("Save");
        saveMenuItem.setToolTipText("Save query to file.");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);
        fileMenu.add(jSeparator4);

        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setText("Edit");

        undoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Z, java.awt.event.InputEvent.CTRL_MASK));
        undoMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Undo16.gif")));
        undoMenuItem.setText("Undo");
        undoMenuItem.setEnabled(false);
        undoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                undoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(undoMenuItem);

        redoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Y, java.awt.event.InputEvent.CTRL_MASK));
        redoMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Redo16.gif")));
        redoMenuItem.setText("Redo");
        redoMenuItem.setEnabled(false);
        redoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                redoMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(redoMenuItem);
        editMenu.add(jSeparator7);

        cutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cutMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Cut16.gif")));
        cutMenuItem.setText("Cut");
        cutMenuItem.setEnabled(false);
        cutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(cutMenuItem);

        copyMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Copy16.gif")));
        copyMenuItem.setText("Copy");
        copyMenuItem.setEnabled(false);
        copyMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(copyMenuItem);

        pasteMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        pasteMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Paste16.gif")));
        pasteMenuItem.setText("Paste");
        pasteMenuItem.setEnabled(false);
        pasteMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pasteMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(pasteMenuItem);
        editMenu.add(jSeparator6);

        selectAllMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        selectAllMenuItem.setText("Select All");
        selectAllMenuItem.setEnabled(false);
        selectAllMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAllMenuItemActionPerformed(evt);
            }
        });
        editMenu.add(selectAllMenuItem);
        editMenu.add(jSeparator9);

        editPreferencesMenu.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Preferences16.gif")));
        editPreferencesMenu.setText("Preferences");

        lookAndFeelPreferenceMenuItem.setText("Look & Feel");
        lookAndFeelPreferenceMenuItem.setToolTipText("Change the application look and feel setting.");
        editPreferencesMenu.add(lookAndFeelPreferenceMenuItem);

        confirmClosePreferenceMenuItem.setSelected(true);
        confirmClosePreferenceMenuItem.setText("Confirm tab close");
        confirmClosePreferenceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                confirmClosePreferenceMenuItemActionPerformed(evt);
            }
        });
        editPreferencesMenu.add(confirmClosePreferenceMenuItem);

        _sliderPositionPreferenceMenuItem.setText("Editor Width");
        _sliderPositionPreferenceMenuItem.setToolTipText("intial editor width");
        _sliderPositionPreferenceMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                _sliderPositionPreferenceMenuItemActionPerformed(evt);
            }
        });
        editPreferencesMenu.add(_sliderPositionPreferenceMenuItem);

        editMenu.add(editPreferencesMenu);

        menuBar.add(editMenu);

        datasourceMenu.setText("Datasource");

        openConnectionMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        openConnectionMenuItem.setText("Connect");
        openConnectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openConnectionMenuItemActionPerformed(evt);
            }
        });
        datasourceMenu.add(openConnectionMenuItem);

        disconnectMenuItem.setText("Disconnect");
        disconnectMenuItem.setEnabled(false);
        disconnectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectMenuItemActionPerformed(evt);
            }
        });
        datasourceMenu.add(disconnectMenuItem);
        datasourceMenu.add(jSeparator5);

        cloneConnectionMenuItem.setText("Clone");
        cloneConnectionMenuItem.setToolTipText("Clone the active connection.");
        cloneConnectionMenuItem.setEnabled(false);
        cloneConnectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cloneConnectionMenuItemActionPerformed(evt);
            }
        });
        datasourceMenu.add(cloneConnectionMenuItem);

        reconnectMenuItem.setText("Reconnect");
        reconnectMenuItem.setEnabled(false);
        reconnectMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnectMenuItemActionPerformed(evt);
            }
        });
        datasourceMenu.add(reconnectMenuItem);
        datasourceMenu.add(jSeparator1);

        infoMenuItem.setText("Info");
        infoMenuItem.setEnabled(false);
        infoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoMenuItemActionPerformed(evt);
            }
        });
        datasourceMenu.add(infoMenuItem);
        datasourceMenu.add(jSeparator2);

        profilesMenuItem.setText("Profiles");
        profilesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profilesMenuItemActionPerformed(evt);
            }
        });
        datasourceMenu.add(profilesMenuItem);

        menuBar.add(datasourceMenu);

        queryMenu.setText("Query");

        executeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        executeMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/media/Play16.gif")));
        executeMenuItem.setText("Execute");
        executeMenuItem.setEnabled(false);
        executeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                executeMenuItemActionPerformed(evt);
            }
        });
        queryMenu.add(executeMenuItem);

        cancelQueryMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/Stop16.gif")));
        cancelQueryMenuItem.setText("Cancel");
        cancelQueryMenuItem.setEnabled(false);
        cancelQueryMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelQueryMenuItemActionPerformed(evt);
            }
        });
        queryMenu.add(cancelQueryMenuItem);
        queryMenu.add(jSeparator3);

        saveResultsMenu.setText("Save Results");

        saveResultsAsHtmlMenuItem.setText("As HTML");
        saveResultsAsHtmlMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveResultsAsHtmlMenuItemActionPerformed(evt);
            }
        });
        saveResultsMenu.add(saveResultsAsHtmlMenuItem);

        saveResultsAsCSVMenuItem.setText("As CSV");
        saveResultsAsCSVMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveResultsAsCSVMenuItemActionPerformed(evt);
            }
        });
        saveResultsMenu.add(saveResultsAsCSVMenuItem);

        queryMenu.add(saveResultsMenu);

        menuBar.add(queryMenu);

        helpMenu.setText("Help");

        aboutMenuItem.setIcon(new ImageIcon(getClass().getResource("/toolbarButtonGraphics/general/About16.gif")));
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void _sliderPositionPreferenceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event__sliderPositionPreferenceMenuItemActionPerformed
        // TODO add your handling code here:
        SliderPositionSelectionPanel p = new SliderPositionSelectionPanel();
        p.setOffset(_appPreferences.getFloat(EDITOR_WIDTH_PROPERTY, 0.35f));
        if (JOptionPane.showConfirmDialog(this, p, "Editor width", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION) {
            float newWidth = p.getOffset();
            _appPreferences.putFloat(EDITOR_WIDTH_PROPERTY, newWidth);
            _mainPane.setDividerInitialLocation((int) (getWidth() * newWidth));
        }
    }//GEN-LAST:event__sliderPositionPreferenceMenuItemActionPerformed

    private void confirmClosePreferenceMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_confirmClosePreferenceMenuItemActionPerformed
        // TODO add your handling code here:
        boolean confirmClose = confirmClosePreferenceMenuItem.isSelected();
        _appPreferences.putBoolean(CONFIRM_CLOSE_PROPERTY, confirmClose);
        _mainPane.setConfirmClose(confirmClose);
    }//GEN-LAST:event_confirmClosePreferenceMenuItemActionPerformed
    
    private void cancelQueryMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelQueryMenuItemActionPerformed
        // Add your handling code here:
        cancelQuery();
    }//GEN-LAST:event_cancelQueryMenuItemActionPerformed
    
    private void cancelQueryToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelQueryToolbarButtonActionPerformed
        // Add your handling code here:
        cancelQuery();
    }//GEN-LAST:event_cancelQueryToolbarButtonActionPerformed
    
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        // Add your handling code here:
        if (_mainPane.getTabCount() > 0) {
            _mainPane.getActiveConnectionPanel().getEditor().requestFocus();
        }
    }//GEN-LAST:event_formWindowGainedFocus
                
    private void replaceToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceToolbarButtonActionPerformed
        // Add your handling code here:
        editorReplace();
    }//GEN-LAST:event_replaceToolbarButtonActionPerformed
    
    private void findAgainToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findAgainToolbarButtonActionPerformed
        // Add your handling code here:
        editorFindAgain();
    }//GEN-LAST:event_findAgainToolbarButtonActionPerformed
    
    private void editorFind() {
        try {
 //           _mainPane.getActiveConnectionPanel().getEditor().doSearch();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void editorReplace() {
        try {
//            _mainPane.getActiveConnectionPanel().getEditor().doReplace();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void editorFindAgain() {
        try {
 //           _mainPane.getActiveConnectionPanel().getEditor().doSearchAgain();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void findToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_findToolbarButtonActionPerformed
        // Add your handling code here:
        editorFind();
    }//GEN-LAST:event_findToolbarButtonActionPerformed
    
    private void redoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_redoMenuItemActionPerformed
        // Add your handling code here:
        try {
            _mainPane.getActiveConnectionPanel().getEditor().redoChange();
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_redoMenuItemActionPerformed
    
    private void undoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_undoMenuItemActionPerformed
        // Add your handling code here:
        try {
            _mainPane.getActiveConnectionPanel().getEditor().undoChange();
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_undoMenuItemActionPerformed
    
    private void _mainPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event__mainPaneStateChanged
        // Add your handling code here:

    }//GEN-LAST:event__mainPaneStateChanged
    
    @Autowired
    private Provider<AboutPanel> aboutPanelProvider;
    
    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        // Add your handling code here:
        AboutPanel aboutPanel = aboutPanelProvider.get();
        JOptionPane.showMessageDialog(this, aboutPanel, "About " + Constants.PROGRAM_NAME, JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_aboutMenuItemActionPerformed
    
    private void cloneConnectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cloneConnectionMenuItemActionPerformed
        // Add your handling code here:
        try {
            DatabaseConnectionPanel p = _mainPane.getActiveConnectionPanel();
            _mainPane.addConnection(p.getDatabaseConnection().getUserID(), p.getDatabaseConnection().getServerName(), p.getDatabaseConnection().getDatabaseName(), p.getDatabaseConnection().getDatabaseTypeName());
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_cloneConnectionMenuItemActionPerformed
    
    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        // Add your handling code here:
        if (isVisible()) {
            Dimension d = getSize();
            _appPreferences.put(WIN_HEIGHT_KEY, Integer.toString(d.height));
            _appPreferences.put(WIN_WIDTH_KEY, Integer.toString(d.width));
        }
    }//GEN-LAST:event_formComponentResized
    
    private void formComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentMoved
        // Add your handling code here:
        if (isVisible()) {
            Point currentPosition = getLocationOnScreen();
            _appPreferences.putInt(WIN_POSITION_X_KEY, currentPosition.x);
            _appPreferences.putInt(WIN_POSITION_Y_KEY, currentPosition.y);
        }
    }//GEN-LAST:event_formComponentMoved
    
    private void saveToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveToolbarButtonActionPerformed
        // Add your handling code here:
        handleSaveQuery();
    }//GEN-LAST:event_saveToolbarButtonActionPerformed
    
    private void openToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openToolbarButtonActionPerformed
        // Add your handling code here:
        handleOpenQuery();
    }//GEN-LAST:event_openToolbarButtonActionPerformed
    
    private void handleSaveQuery() {
        try {
            saveQuery();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        // Add your handling code here:
        handleSaveQuery();
    }//GEN-LAST:event_saveMenuItemActionPerformed
    
    private void handleOpenQuery() {
        try {
            openQuery();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        // Add your handling code here:
        handleOpenQuery();
    }//GEN-LAST:event_openMenuItemActionPerformed
    
    private void saveResultsAsCSVMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveResultsAsCSVMenuItemActionPerformed
        // Add your handling code here:
        try {
            saveQueryResultsAsCSV();
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_saveResultsAsCSVMenuItemActionPerformed
    
    private void saveResultsAsHtmlMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveResultsAsHtmlMenuItemActionPerformed
        // Add your handling code here:
        try {
            saveQueryResultsAsHTML();
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_saveResultsAsHtmlMenuItemActionPerformed
    
    @Autowired
    private Provider<ProfileManagerPanel> profileManagerPanel;
    
    private void profilesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profilesMenuItemActionPerformed
        // Add your handling code here:
        JOptionPane.showMessageDialog(this, profileManagerPanel.get(), "Profiles", JOptionPane.PLAIN_MESSAGE);
    }//GEN-LAST:event_profilesMenuItemActionPerformed
    
    private void infoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoMenuItemActionPerformed
        // Add your handling code here:
        try {
            handleDatabaseConnectionInfo();
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_infoMenuItemActionPerformed
    
    private void pasteToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteToolbarButtonActionPerformed
        // Add your handling code here:
        activeEditorPaste();
    }//GEN-LAST:event_pasteToolbarButtonActionPerformed
    
    private void copyToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyToolbarButtonActionPerformed
        // Add your handling code here:
        activeEditorCopy();
    }//GEN-LAST:event_copyToolbarButtonActionPerformed
    
    private void cutToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutToolbarButtonActionPerformed
        // Add your handling code here:
        activeEditorCut();
    }//GEN-LAST:event_cutToolbarButtonActionPerformed
    
    private void executeQueryToolbarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeQueryToolbarButtonActionPerformed
        // Add your handling code here:
        handleExecuteQuery();
    }//GEN-LAST:event_executeQueryToolbarButtonActionPerformed
    
    private void selectAllMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAllMenuItemActionPerformed
        // Add your handling code here:
        activeEditorSelectAll();
    }//GEN-LAST:event_selectAllMenuItemActionPerformed
    
    private void pasteMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pasteMenuItemActionPerformed
        // Add your handling code here:
        activeEditorPaste();
    }//GEN-LAST:event_pasteMenuItemActionPerformed
    
    private void copyMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyMenuItemActionPerformed
        // Add your handling code here:
        activeEditorCopy();
    }//GEN-LAST:event_copyMenuItemActionPerformed
    
    private void cutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cutMenuItemActionPerformed
        // Add your handling code here:
        activeEditorCut();
    }//GEN-LAST:event_cutMenuItemActionPerformed
    
    private void _mainPaneComponentRemoved(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event__mainPaneComponentRemoved
        // Add your handling code here:
        DatabaseConnectionPanel p = (DatabaseConnectionPanel) evt.getChild();
        updateEnabledMenuItems();
        p.removePropertyChangeListener("processingStage", _processingStageListener);
        p.getEditor().removePropertyChangeListener("undoable", _undoRedoStateListener);
        p.getEditor().removePropertyChangeListener("redoable", _undoRedoStateListener);
    }//GEN-LAST:event__mainPaneComponentRemoved
    
    private void _mainPaneComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event__mainPaneComponentAdded
        // Add your handling code here:
        if (evt.getChild() instanceof DatabaseConnectionPanel) {
            DatabaseConnectionPanel p = (DatabaseConnectionPanel) evt.getChild();
            updateEnabledMenuItems();
            p.addPropertyChangeListener("processingStage", _processingStageListener);
            p.getEditor().addPropertyChangeListener("undoable", _undoRedoStateListener);
            p.getEditor().addPropertyChangeListener("redoable", _undoRedoStateListener);
            p.getEditor().requestFocus();
            
        }
    }//GEN-LAST:event__mainPaneComponentAdded
    
    private void executeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_executeMenuItemActionPerformed
        // Add your handling code here:
        handleExecuteQuery();
    }//GEN-LAST:event_executeMenuItemActionPerformed
    
    private void disconnectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectMenuItemActionPerformed
        // Add your handling code here:
        try {
            _mainPane.closeActiveConnection();
        } catch (Exception e) {
            handleException(e);
        }
    }//GEN-LAST:event_disconnectMenuItemActionPerformed
    
    private void openConnectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openConnectionMenuItemActionPerformed
        // Add your handling code here:
        try {
            setCursor(APP_WAIT_CURSOR);
            connectDatabase();
        } catch (Exception e) {
            handleException(e);
        } finally {
            setCursor(APP_DEFAULT_CURSOR);
        }
    }//GEN-LAST:event_openConnectionMenuItemActionPerformed
    
    private void _mainPanePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event__mainPanePropertyChange
        // Add your handling code here:
        if (evt.getPropertyName().equals("statusMessage")) {
            setStatusMessage((String) evt.getNewValue());
        }
    }//GEN-LAST:event__mainPanePropertyChange
    
    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        shutdown();
    }//GEN-LAST:event_exitMenuItemActionPerformed
    
    /** Exit the Application */
    private void exitForm(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_exitForm
        shutdown();
    }//GEN-LAST:event_exitForm

    private void reconnectMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnectMenuItemActionPerformed
        try {
            reconnectDatabase();
        } catch (Exception ex) {
            handleException(ex);
        }
    }//GEN-LAST:event_reconnectMenuItemActionPerformed

    private void reconnectDatabase() throws SQLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DatabaseConnectionPanel p = _mainPane.getActiveConnectionPanel();
        p.reconnect();
    }
    
    /** Getter for property statusMessage.
     * @return Value of property statusMessage.
     *
     */
    public String getStatusMessage() {
        return this.statusMessage;
    }
    
    /** Setter for property statusMessage.
     * @param statusMessage New value of property statusMessage.
     *
     */
    public void setStatusMessage(String statusMessage) {
        String oldStatusMessage = this.statusMessage;
        this.statusMessage = statusMessage;
        _messageLabel.setText(statusMessage);
        repaintComponent(_messageLabel);
        firePropertyChange("statusMessage", oldStatusMessage, statusMessage);
    }
    
    private void handleException(Exception e) {
        logger.error("Caught exception:", e);
        Throwable causeE = e.getCause();
        while (causeE != null) {
            logger.error("Caused by: ", causeE);
            causeE = causeE.getCause();
        }
        if (e instanceof SQLException) {
            SQLException sqlE = (SQLException) e;
            logger.error("SQL Exception: ", sqlE.getNextException());
        }
        
        ExceptionDisplayPanel exPan = exceptionDisplayPanelProvider.get();
        exPan.setException(e);
        JOptionPane.showMessageDialog(this, exPan, "Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    @Autowired
    private ConnectionProfileManager profileManager;
    
    @Autowired
    private Provider<ConnectionParametersPanel> connectionParametersPanelProvider;
    
    /** Open a new connection to a database.
     * @throws SQLException on error communicating with database
     * @throws IOException on error reading configuration files for the database connection
     * @throws ClassNotFoundException on error finding class named in a database configuration file
     * @throws InstantiationException on error creating a class named in the database connection configuration file.
     * @throws IllegalAccessException on error accessing a class named in the database connection configuration file.
     */
    public void connectDatabase() throws SQLException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ConnectionParametersPanel connPanel = connectionParametersPanelProvider.get();
        int res = JOptionPane.showConfirmDialog(this, connPanel, "Open Connection", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (res == JOptionPane.OK_OPTION) {
            String userId = connPanel.getUserId();
            String serverName = connPanel.getServerName();
            String databaseName = connPanel.getDatabaseName();
            String databaseType = connPanel.getDatabaseType();
            
            if (connPanel.isSaveRequested()) {
                ConnectionProfile profile = new ConnectionProfile();
                profile.setUserID(userId);
                profile.setServerName(serverName);
                profile.setDatabaseName(databaseName);
                profile.setServerType(databaseType);
                
                // is this the default profile?
                if (connPanel.isDefaultProfile()) {
                    logger.info("setting default profile flag");
                    profile.setDefaultProfile(true);
                }
                
                // save
                profileManager.addProfile(profile);
            }
            
            _mainPane.addConnection(userId, serverName, databaseName, databaseType);
            setStatusMessage("Connection opened.");
        }
    }
    
    /** Execute a query in the active connection panel's {@link SQLEditor}.
     * @throws SQLException on error executing query
     */
    public void executeQuery() throws SQLException {
        _mainPane.executeQuery();
    }
    
    private void updateEnabledMenuItems() {
        boolean enabledStatus = _mainPane.getTabCount() > 0;
        JComponent[] items = { executeMenuItem, disconnectMenuItem, cutMenuItem, pasteMenuItem, copyMenuItem, selectAllMenuItem, cutToolbarButton, copyToolbarButton, pasteToolbarButton, executeQueryToolbarButton, infoMenuItem, openMenuItem, saveMenuItem, saveResultsMenu, openToolbarButton, saveToolbarButton, cloneConnectionMenuItem, findToolbarButton, findAgainToolbarButton, replaceToolbarButton, cancelQueryMenuItem, cancelQueryToolbarButton, reconnectMenuItem };
        for (JComponent item : items) {
            item.setEnabled(enabledStatus);
        }
    }
    
    /** Cut selected text in the active panel's {@link SQLEditor}. */
    public void activeEditorCut() {
        DatabaseConnectionPanel p = (DatabaseConnectionPanel) _mainPane.getSelectedComponent();
        p.getEditor().cut();
    }
    
    /** Copy selected text in the active panel's {@link SQLEditor}. */
    public void activeEditorCopy() {
        DatabaseConnectionPanel p = (DatabaseConnectionPanel) _mainPane.getSelectedComponent();
        p.getEditor().copy();
    }
    
    /** Paste into the active panel's {@link SQLEditor}. */
    public void activeEditorPaste() {
        DatabaseConnectionPanel p = (DatabaseConnectionPanel) _mainPane.getSelectedComponent();
        p.getEditor().paste();
    }
    
    /** Select all text in the active panel's {@link SQLEditor}. */
    public void activeEditorSelectAll() {
        DatabaseConnectionPanel p = (DatabaseConnectionPanel) _mainPane.getSelectedComponent();
        p.getEditor().selectAll();
    }
    
    private void handleExecuteQuery() {
        try {
            executeQuery();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    private void handleDatabaseConnectionInfo() throws SQLException {
        DatabaseConnectionPanel p = (DatabaseConnectionPanel) _mainPane.getSelectedComponent();
        DatabaseConnectionInfoPanel info = new DatabaseConnectionInfoPanel(p);
        JOptionPane.showMessageDialog(this, info, "Connection Info", JOptionPane.INFORMATION_MESSAGE);
    }
    
    /** Shutdown the application.  This will close all active connections. */
    public void shutdown() {
        System.out.println("shutting down....");
        _mainPane.closeAllConnections();
        System.out.println("exitting....");
        System.exit(0);
    }
    
    private void saveQueryResults(JFileChooser fileChooser, ResultSetWriter writer) throws IOException {
        // Select the output file.
        SingleExtensionFileFilter fileFilter = (SingleExtensionFileFilter) fileChooser.getFileFilter();
        String fileExtension = fileFilter.getExtension();
        int status = fileChooser.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File sf = fileChooser.getSelectedFile();
            if (!sf.getAbsolutePath().endsWith(fileExtension)) {
                // suffix the extension.
                sf = new File(sf.getAbsolutePath() + "." + fileExtension);
            }
            try (PrintWriter pw = new PrintWriter(sf)) {
                _mainPane.getActiveConnectionPanel().saveResults(writer, pw);
            }
        }
    }
    
    @Autowired
    private ResultSetWriterFactory resultWriterFactory;
    
    /** Save query results in the active connection panel as an HTML file.
     * @throws IOException on error writing file.
     */
    public void saveQueryResultsAsHTML() throws IOException {
        saveQueryResults(_htmlFileChooser, resultWriterFactory.getResultSetWriter("HTML"));
    }
    
    /** Save query results in the active connection panel as a CSV file.
     * @throws IOException on error writing file
     */
    public void saveQueryResultsAsCSV() throws IOException {
        saveQueryResults(_csvFileChooser, resultWriterFactory.getResultSetWriter("CSV"));
    }
    
    private File getQueryFile(boolean isSave) {
        int status = (isSave) ? _queryFileChooser.showSaveDialog(this) : _queryFileChooser.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File sf = _queryFileChooser.getSelectedFile();
            if (!sf.getAbsolutePath().endsWith("sql")) {
                sf = new File(sf.getAbsolutePath() + ".sql");
            }
            return sf;
        } else {
            return null;
        }
    }
    
    /** Open a query from a file.
     * @throws IOException on error reading file.
     */
    public void openQuery() throws IOException {
        File sf = getQueryFile(false);
        if (sf == null) { return; }
        
        BufferedReader is = new BufferedReader(new FileReader(sf));
        String query = "", line;
        while ((line = is.readLine()) != null) { query += line + "\n"; }
        _mainPane.getActiveConnectionPanel().getEditor().setSQLCode(query);
    }
    
    /** Save the query in the active connection {@link SQLEditor} to disk.
     * @throws IOException on error writing file.
     */
    public void saveQuery() throws IOException {
        // Ensure we have code to save.
        String query = _mainPane.getActiveConnectionPanel().getEditor().getSQLCode();
        if (query == null || query.length() == 0) {
            setStatusMessage("Error: No SQL to save.");
            return;
        }
        
        // get the save file.
        File sf = getQueryFile(true);
        if (sf == null) { return; }
        try (FileWriter os = new FileWriter(sf)) {
            os.write(query);
        }
    }
    
    private void repaintComponent(JComponent component) {
        ComponentRepainter r = new ComponentRepainter();
        r.setComponent(component);
        SwingUtilities.invokeLater(r);
    }
    
    public void cancelQuery() {
        try {
            _mainPane.getActiveConnectionPanel().cancelQuery();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    /** A class to listen for changes in the query processing stage from the active
     * database connection panel.  The status bar in the application will show the
     * current stage.
     */
    public class ProcessingStageListener implements PropertyChangeListener {
        
        /** Handle the change in the processing stage.  The status bar message will be
         * updated accordingly.
         * @param evt the property change event
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("processingStage")) {
                ProcessingStage stage = (ProcessingStage) evt.getNewValue();
                if (stage == ProcessingStage.WAITING) {
                    setCursor(APP_DEFAULT_CURSOR);
                } else {
                    // query is running or formatting.
                    setCursor(APP_WAIT_CURSOR);
                }
            }
        }
        
    }
    
    public class ComponentRepainter extends Thread {
        
        /** Holds value of property component. */
        private JComponent component;
        
        /** Getter for property component.
         * @return Value of property component.
         *
         */
        public JComponent getComponent() {
            return this.component;
        }
        
        /** Setter for property component.
         * @param component New value of property component.
         *
         */
        public void setComponent(JComponent component) {
            this.component = component;
        }
        
        @Override
        public void run() {
            getComponent().repaint();
        }
        
    }
    
    public class EditorUndoRedoStateChangeListener implements PropertyChangeListener {
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            SQLEditor editor = _mainPane.getActiveConnectionPanel().getEditor();
            undoMenuItem.setEnabled(editor.isUndoable());
            redoMenuItem.setEnabled(editor.isRedoable());
        }
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser _csvFileChooser;
    private javax.swing.JFileChooser _htmlFileChooser;
    private javax.swing.ButtonGroup _lafMenuButtonGroup;
    private javax.swing.JLabel _messageLabel;
    private javax.swing.JFileChooser _queryFileChooser;
    private javax.swing.JMenuItem _sliderPositionPreferenceMenuItem;
    private javax.swing.JPanel _toolbarPanel;
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem cancelQueryMenuItem;
    private javax.swing.JButton cancelQueryToolbarButton;
    private javax.swing.JMenuItem cloneConnectionMenuItem;
    private javax.swing.JCheckBoxMenuItem confirmClosePreferenceMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JButton copyToolbarButton;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JButton cutToolbarButton;
    private javax.swing.JMenu datasourceMenu;
    private javax.swing.JMenuItem disconnectMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenu editPreferencesMenu;
    private javax.swing.JToolBar editToolBar;
    private javax.swing.JMenuItem executeMenuItem;
    private javax.swing.JButton executeQueryToolbarButton;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JToolBar fileToolBar;
    private javax.swing.JButton findAgainToolbarButton;
    private javax.swing.JButton findToolbarButton;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem infoMenuItem;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JMenu lookAndFeelPreferenceMenuItem;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openConnectionMenuItem;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JButton openToolbarButton;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JButton pasteToolbarButton;
    private javax.swing.JMenuItem profilesMenuItem;
    private javax.swing.JMenu queryMenu;
    private javax.swing.JToolBar queryToolbar;
    private javax.swing.JMenuItem reconnectMenuItem;
    private javax.swing.JMenuItem redoMenuItem;
    private javax.swing.JButton replaceToolbarButton;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem saveResultsAsCSVMenuItem;
    private javax.swing.JMenuItem saveResultsAsHtmlMenuItem;
    private javax.swing.JMenu saveResultsMenu;
    private javax.swing.JButton saveToolbarButton;
    private javax.swing.JMenuItem selectAllMenuItem;
    private javax.swing.JMenuItem undoMenuItem;
    // End of variables declaration//GEN-END:variables
    
    @Autowired
    private MainConnectionPanel _mainPane;
    
    /** Holds value of property statusMessage. */
    private String statusMessage;
    
    // Keys in the preferences.
    private static final String WIN_POSITION_X_KEY = "WinPositionX";
    private static final String WIN_POSITION_Y_KEY = "WinPositionY";
    private static final String WIN_WIDTH_KEY = "WinWidth";
    private static final String WIN_HEIGHT_KEY = "WinHeight";
    
    private static final String CONFIRM_CLOSE_PROPERTY = "ConfirmTabClose";
    
    private static final String EDITOR_WIDTH_PROPERTY = "EditorWidth";
    
    private final Preferences _appPreferences = Preferences.userNodeForPackage(MainWindow.class);
    
    private static final Cursor APP_DEFAULT_CURSOR = Cursor.getDefaultCursor();
    
    private static final Cursor APP_WAIT_CURSOR = Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
    
    private final ProcessingStageListener _processingStageListener = new ProcessingStageListener();
    
    public static final Map<String, String> lafTypes = new HashMap<>();
    
    private final EditorUndoRedoStateChangeListener _undoRedoStateListener = new EditorUndoRedoStateChangeListener();
    
    private final SingleExtensionFileFilter _queryFileFilter = new SingleExtensionFileFilter();
    private final SingleExtensionFileFilter _htmlFileFilter = new SingleExtensionFileFilter();
    private final SingleExtensionFileFilter _csvFileFilter = new SingleExtensionFileFilter();
    
    @Autowired
    private Provider<ExceptionDisplayPanel> exceptionDisplayPanelProvider;
}
