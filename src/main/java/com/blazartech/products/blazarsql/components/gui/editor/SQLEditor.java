/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.editor;

import javax.swing.JEditorPane;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * base class for an editor of SQL code. All methods are implemented, and thus
 * the object as defined is usable. But it is expected that derived will provide
 * unique functionality.
 *
 * @author aar1069
 * @version $Id: SQLEditor.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
public abstract class SQLEditor extends JEditorPane implements InitializingBean {

    private final EditorUndoableEventListener undoEventListener;

    private boolean undoable;
    public static final String PROP_UNDOABLE = "undoable";

    public boolean isUndoable() {
        return undoable;
    }

    public void setUndoable(boolean undoable) {
        boolean oldUndoable = this.undoable;
        this.undoable = undoable;
        firePropertyChange(PROP_UNDOABLE, oldUndoable, undoable);
    }

    private boolean redoable;
    public static final String PROP_REDOABLE = "redoable";

    /**
     * Get the value of redoable
     *
     * @return the value of redoable
     */
    public boolean isRedoable() {
        return redoable;
    }

    /**
     * Set the value of redoable
     *
     * @param redoable new value of redoable
     */
    public void setRedoable(boolean redoable) {
        boolean oldRedoable = this.redoable;
        this.redoable = redoable;
        firePropertyChange(PROP_REDOABLE, oldRedoable, redoable);
    }

    private final UndoManager undoManager = new UndoManager();

    protected UndoManager getUndoManager() {
        return undoManager;
    }

    protected void updateUndoStatus() {
        setUndoable(getUndoManager().canUndo());
    }

    protected void updateRedoStatus() {
        setRedoable(getUndoManager().canRedo());
    }

    public SQLEditor() {
        super();
        undoEventListener = new EditorUndoableEventListener(this);
    }

    @Override
    public void copy() {
        requestFocus();
        super.copy();
    }

    @Override
    public void cut() {
        requestFocus();
        super.cut();
    }

    @Override
    public void paste() {
        requestFocus();
        super.paste();
    }

    @Override
    public void selectAll() {
        requestFocus();
        super.selectAll();
    }

    /**
     * get the selected SQL code
     *
     * @return
     */
    public String getSQLCode() {
        String sql = getSelectedText();
        return (sql == null) ? getText() : sql;
    }

    /**
     * set the SQL code in the editor.
     *
     * @param SQLCode
     */
    public void setSQLCode(String SQLCode) {
        setText(SQLCode);
    }

    /**
     * Undo the latest change in the editor.
     *
     * @throws CannotUndoException if there is an error undoing the change.
     */
    public void undoChange() throws CannotUndoException {
        undoManager.undo();
        updateUndoStatus();
        updateRedoStatus();
    }

    /**
     * Redo the last undo.
     *
     * @throws CannotRedoException if there is an error redoing the change.
     */
    public void redoChange() throws CannotRedoException {
        undoManager.redo();
        updateUndoStatus();
        updateRedoStatus();
    }
    
    @Override
    public void afterPropertiesSet() throws Exception {
        getDocument().addUndoableEditListener(undoEventListener);
    }

}
