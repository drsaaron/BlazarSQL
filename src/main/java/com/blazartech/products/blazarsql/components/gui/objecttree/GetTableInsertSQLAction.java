/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.objecttree;

import com.blazartech.products.blazarsql.components.gui.ObjectTree;
import java.sql.SQLException;
import javax.swing.tree.TreePath;

/**
 * Action to get the insert statements for the data in a table.
 */
public class GetTableInsertSQLAction extends ObjectActionBase implements ObjectAction {

    public GetTableInsertSQLAction(final ObjectTree outer) {
        super(outer);
    }

    /**
     * Implement the action.
     *
     * @param path path for the object to act on.
     * @throws java.sql.SQLException database error
     */
    @Override
    public void doAction(TreePath path) throws SQLException {
        String userID = getUserId(path);
        String tableName = getObjectName(path);
        if (!continueWithDataOperation(userID, tableName)) {
            return;
        }
        String insertSQL = getObjectTree().getDatabaseObjectInformation().getTableDataInsertSQL(getObjectTree().getDatabaseConnection(), userID, tableName);
        getObjectTree().getDatabaseConnectionPanel().showStringResult(insertSQL);
    }
    
}
