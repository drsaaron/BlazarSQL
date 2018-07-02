/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.objecttree;

import com.blazartech.products.blazarsql.components.gui.ObjectTree;
import java.sql.SQLException;
import javax.swing.tree.TreePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Action to get all data in a table.
 */
public class GetTableDataAction extends ObjectActionBase implements ObjectAction {

    private static final Logger logger = LoggerFactory.getLogger(GetTableDataAction.class);

    public GetTableDataAction(final ObjectTree outer) {
        super(outer);
    }

    /**
     * implement the action
     * @param path object path
     * @throws java.sql.SQLException database error
     */
    @Override
    public void doAction(TreePath path) throws SQLException {
        // get the user ID and table name from the path.
        String userId = getUserId(path);
        String tableName = getObjectName(path);
        showStatusMessage("Getting table data for " + tableName);
        // get an idea of the size of the table first.
        if (!continueWithDataOperation(userId, tableName)) {
            return;
        }
        // run the query.
        String query = getObjectTree().getDatabaseObjectInformation().getTableDataSQL(getObjectTree().getDatabaseConnection(), userId, tableName);
        logger.debug("table data query = " + query);
        getObjectTree().getDatabaseConnectionPanel().executeQuery(query);
    }
    
}
