/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.editor;

import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

/**
 *
 * @author aar1069
 * @version $Id: EditorUndoableEventListener.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
class EditorUndoableEventListener implements UndoableEditListener {

    private SQLEditor editor;

    public SQLEditor getEditor() {
        return editor;
    }

    public void setEditor(SQLEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void undoableEditHappened(UndoableEditEvent e) {
        getEditor().getUndoManager().addEdit(e.getEdit());
        getEditor().updateUndoStatus();
        getEditor().updateRedoStatus();
    }

    public EditorUndoableEventListener(SQLEditor editor) {
        this.editor = editor;
    }

    
}
