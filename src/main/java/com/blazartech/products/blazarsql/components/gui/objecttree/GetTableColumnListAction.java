/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.objecttree;

import com.blazartech.products.blazarsql.components.dataobjects.TableColumnDescriptor;
import com.blazartech.products.blazarsql.components.gui.ObjectTree;
import com.blazartech.products.blazarsql.components.gui.TableColumnListTableModel;
import java.sql.SQLException;
import java.util.Collection;
import javax.swing.JTable;
import javax.swing.tree.TreePath;

/**
 * Action to get list of table columns.
 */
public class GetTableColumnListAction extends ObjectActionBase implements ObjectAction {

    public GetTableColumnListAction(final ObjectTree outer) {
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
        showStatusMessage("Getting table columns code for " + tableName);
        // get the columns
        Collection<TableColumnDescriptor> columnList = getObjectTree().getDatabaseObjectInformation().getTableColumns(getObjectTree().getDatabaseConnection(), tableName, userId);
        // show the results in a table.
        TableColumnListTableModel model = new TableColumnListTableModel(columnList);
        JTable table = new JTable(model);
        getObjectTree().getDatabaseConnectionPanel().showTableResult(table);
    }
    
}
