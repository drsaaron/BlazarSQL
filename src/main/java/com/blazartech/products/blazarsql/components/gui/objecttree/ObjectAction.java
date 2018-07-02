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
 * Interface for actions to be done on objects.
 */
public interface ObjectAction {

    /**
     * Implement the action.
     * @param path path for the object to act on.
     * @throws java.sql.SQLException database error
     */
    public void doAction(TreePath path) throws SQLException;

    /**
     * Raise the connection panel in the main UI.
     */
    public void raiseConnectionPanel();

}
