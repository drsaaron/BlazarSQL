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
 * Action to get stored procedure code.
 */
public class GetStoredProcedureCodeAction extends ObjectActionBase implements ObjectAction {

    public GetStoredProcedureCodeAction(final ObjectTree outer) {
        super(outer);
    }

    /**
     * implement the action
     * @param path object path
     * @throws java.sql.SQLException database error
     */
    @Override
    public void doAction(TreePath path) throws SQLException {
        // get the user ID and procedure name from the path.
        String userId = getUserId(path);
        String procName = getObjectName(path);
        showStatusMessage("Getting stored procedure code for " + procName);
        // get the code.
        String procCode = getObjectTree().getDatabaseObjectInformation().getStoredProcedureCode(getObjectTree().getDatabaseConnection(), procName, userId);
        // show the code in the connection panel.
        getObjectTree().getDatabaseConnectionPanel().showStringResult(procCode);
    }
    
}
