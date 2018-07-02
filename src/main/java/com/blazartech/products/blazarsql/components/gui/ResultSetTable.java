/*
 * ResultSetTable.java
 *
 * Created on January 24, 2008, 3:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.blazartech.products.blazarsql.components.gui;

import java.awt.Component;
import java.sql.Date;
import java.sql.Timestamp;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * A specialization of table that will provide cell renderers that can handle NULL
 * values nicely.
 *
 * @author Dr. Scott E. Aaron
 * @version $Id: ResultSetTable.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log: ResultSetTable.java,v $
/* Revision 1.2  2008/02/11 21:51:50  aar1069
/* added special logic for date and timestamps since the default one doesn't seem to work so well.
/*
/* Revision 1.1  2008/01/24 21:59:00  aar1069
/* initial revision
/*
 *******************************************************************************/

public class ResultSetTable extends JTable {
    private static class ResultSetDefaultRenderer implements TableCellRenderer {
        /**
         * Returns the component used for drawing the cell.  This method is
         * used to configure the renderer appropriately before drawing.
         * <p>
         * The <code>TableCellRenderer</code> is also responsible for rendering the
         * the cell representing the table's current DnD drop location if
         * it has one. If this renderer cares about rendering
         * the DnD drop location, it should query the table directly to
         * see if the given row and column represent the drop location:
         * <pre>
         *     JTable.DropLocation dropLocation = table.getDropLocation();
         *     if (dropLocation != null
         *             && !dropLocation.isInsertRow()
         *             && !dropLocation.isInsertColumn()
         *             && dropLocation.getRow() == row
         *             && dropLocation.getColumn() == column) {
         *
         *         // this cell represents the current drop location
         *         // so render it specially, perhaps with a different color
         *     }
         * </pre>
         * <p>
         * During a printing operation, this method will be called with
         * <code>isSelected</code> and <code>hasFocus</code> values of
         * <code>false</code> to prevent selection and focus from appearing
         * in the printed output. To do other customization based on whether
         * or not the table is being printed, check the return value from
         * {@link javax.swing.JComponent#isPaintingForPrint()}.
         *
         *
         * @param table		the <code>JTable</code> that is asking the
         * 				renderer to draw; can be <code>null</code>
         * @param value		the value of the cell to be rendered.  It is
         * 				up to the specific renderer to interpret
         * 				and draw the value.  For example, if
         * 				<code>value</code>
         * 				is the string "true", it could be rendered as a
         * 				string or it could be rendered as a check
         * 				box that is checked.  <code>null</code> is a
         * 				valid value
         * @param isSelected	true if the cell is to be rendered with the
         * 				selection highlighted; otherwise false
         * @param hasFocus	if true, render cell appropriately.  For
         * 				example, put a special border on the cell, if
         * 				the cell can be edited, render in the color used
         * 				to indicate editing
         * @param row	        the row index of the cell being drawn.  When
         * 				drawing the header, the value of
         * 				<code>row</code> is -1
         * @param column	        the column index of the cell being drawn
         * @see javax.swing.JComponent#isPaintingForPrint()
         */
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value != null) {
                if (value instanceof Date || value instanceof Timestamp) {
                    return table.getDefaultRenderer(String.class).getTableCellRendererComponent(table, value.toString(), isSelected, hasFocus, row, column);
                } else {
                    return getRealDefaultRenderer().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                }
            } else {
                return table.getDefaultRenderer(String.class).getTableCellRendererComponent(table, "NULL", isSelected, hasFocus, row, column);
            }
        }
        
        /**
         * Holds value of property realDefaultRenderer.
         */
        private TableCellRenderer realDefaultRenderer;
        
        /**
         * Getter for property realDefaultRenderer.
         * @return Value of property realDefaultRenderer.
         */
        public TableCellRenderer getRealDefaultRenderer() {
            return this.realDefaultRenderer;
        }
        
        /**
         * Setter for property realDefaultRenderer.
         * @param realDefaultRenderer New value of property realDefaultRenderer.
         */
        public void setRealDefaultRenderer(TableCellRenderer realDefaultRenderer) {
            this.realDefaultRenderer = realDefaultRenderer;
        }
        
        
    }
    
    /** Creates a new instance of ResultSetTable */
    public ResultSetTable() {
    }
    
    /**
     * Returns the cell renderer to be used when no renderer has been set in
     * a <code>TableColumn</code>. During the rendering of cells the renderer is fetched from
     * a <code>Hashtable</code> of entries according to the class of the cells in the column. If
     * there is no entry for this <code>columnClass</code> the method returns
     * the entry for the most specific superclass. The <code>JTable</code> installs entries
     * for <code>Object</code>, <code>Number</code>, and <code>Boolean</code>, all of which can be modified
     * or replaced.
     *
     *
     * @param columnClass   return the default cell renderer
     * 			      for this columnClass
     * @return the renderer for this columnClass
     * @see #setDefaultRenderer
     * @see #getColumnClass
     */
    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
        if (columnClass.equals(String.class)) {
            return super.getDefaultRenderer(columnClass);
        } else {
            ResultSetDefaultRenderer r = new ResultSetDefaultRenderer();
            TableCellRenderer d = super.getDefaultRenderer(columnClass);
            r.setRealDefaultRenderer(d);
            return r;
        }
    }
    
}
