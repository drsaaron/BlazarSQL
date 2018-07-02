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
 * Action to get the code for a table creation.
 */
public class GetTableExtractCodeAction extends ObjectActionBase implements ObjectAction {


    public GetTableExtractCodeAction(final ObjectTree outer) {
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
        showStatusMessage("Getting full table extraction for " + tableName);
        // get the code.
        String sql = getObjectTree().getDatabaseObjectInformation().extractFullTableDefinition(getObjectTree().getDatabaseConnection(), userId, tableName);
        // show the results.
        getObjectTree().getDatabaseConnectionPanel().showStringResult(sql);
    }
    
}
