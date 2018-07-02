/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.objecttree;

import com.blazartech.products.blazarsql.components.gui.ObjectTree;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.tree.TreePath;

/**
 * Base class for action implementations.
 */
public abstract class ObjectActionBase implements ObjectAction {

    private final ObjectTree objectTree;

    public ObjectActionBase(final ObjectTree outer2) {
        this.objectTree = outer2;
    }

    public ObjectTree getObjectTree() {
        return objectTree;
    }
    
    /**
     * raise the connection panel in the main UI.
     */
    @Override
    public void raiseConnectionPanel() {
        JTabbedPane mainPane = (JTabbedPane) getObjectTree().getDatabaseConnectionPanel().getParent();
        mainPane.setSelectedComponent(getObjectTree().getDatabaseConnectionPanel());
    }

    /**
     * Show a status message.
     * @param messageText the message
     */
    public void showStatusMessage(String messageText) {
        getObjectTree().getDatabaseConnectionPanel().setStatusMessage(messageText);
    }

    /**
     * Get the user ID from the object path.
     * @param path object path
     * @return user ID
     */
    public String getUserId(TreePath path) {
        return path.getPathComponent(1).toString();
    }

    /**
     * Get the object name from the object path.
     * @param path object path
     * @return object name
     */
    public String getObjectName(TreePath path) {
        return path.getPathComponent(3).toString();
    }
    private static final int WARN_ROW_COUNT = 5000;

    /**
     * check the size of a table.  if the number of rows exceeds a threshold
     * (@see WARN_ROW_COUNT), then abort the operation.
     *
     * @param owner
     * @param tableName
     * @return
     */
    public boolean continueWithDataOperation(String owner, String tableName) {
        // get an idea of the size of the table first.
        int size = getObjectTree().getDatabaseObjectInformation().getTableSize(getObjectTree().getDatabaseConnection(), owner, tableName);
        if (size > WARN_ROW_COUNT) {
            int i = JOptionPane.showConfirmDialog(null, "Table " + tableName + " has " + size + " rows.  Are you sure you want to get the data?");
            if (i != JOptionPane.YES_OPTION) {
                showStatusMessage("Get table data aborted.");
                return false;
            }
        }
        return true;
    }
    
}
