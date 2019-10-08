/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blazartech.products.blazarsql.components.gui.editor.jsyntax;

import de.sciss.syntaxpane.DefaultSyntaxKit;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.JViewport;
import javax.swing.event.UndoableEditEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * the syntax kit package, which provides syntax highlighting for SQL code,
 * should only be initialized after the component has been added to a scroll
 * pane.  Otherwise, we get a null pointer exception when it tries to add the
 * line numbers.  Rather than managing that in the connection panel which
 * instantiates this editor, define this listener for hierarchy changes, which
 * will fire when the component is added to the scroll pane, and use that to
 * setup the syntax kit.
 * 
 * @author aar1069
 * @version $Id: EditorHierarchyChangeListener.java 30 2015-04-23 19:52:54Z aar1069 $
 */

/* $Log$
 *******************************************************************************/
@Component
class EditorHierarchyChangeListener implements HierarchyListener {
    
    private static final Logger logger = LoggerFactory.getLogger(EditorHierarchyChangeListener.class);
    
    private static boolean initialized = false;
    
    // provide long versions of the change type flags, which for some reason
    // are ints in the event class.
    private static final long PARENT_CHANGED = (long) HierarchyEvent.PARENT_CHANGED;
    private static final long ANCESTOR_MOVED = (long) HierarchyEvent.ANCESTOR_MOVED;
    private static final long ANCESTOR_RESIZED = (long) HierarchyEvent.ANCESTOR_RESIZED;
    private static final long HIERARCHY_CHANGED = (long) HierarchyEvent.HIERARCHY_CHANGED;
    private static final long SHOWING_CHANGED = (long) HierarchyEvent.SHOWING_CHANGED;

    private void checkEventType(long changeFlags, long flag, String message) {
        if ((changeFlags & flag) == flag) {
            logger.debug(message);
        } else {
            logger.debug("no " + message);
        }
    }

    @Override
    public void hierarchyChanged(HierarchyEvent e) {
        logger.debug("got hierarchy event " + e);
        long changeFlags = e.getChangeFlags();
        checkEventType(changeFlags, PARENT_CHANGED, "parent changed");
        checkEventType(changeFlags, ANCESTOR_MOVED, "ancestor moved");
        checkEventType(changeFlags, ANCESTOR_RESIZED, "ancestor resized");
        checkEventType(changeFlags, HIERARCHY_CHANGED, "hierarchy changed");
        checkEventType(changeFlags, SHOWING_CHANGED, "showing changed");
        if ((changeFlags & PARENT_CHANGED) == PARENT_CHANGED) {
            logger.debug("changed parent = " + e.getChangedParent());
            // detect if we've been added to a scroll pane
            if (e.getChangedParent() instanceof JViewport) {
                // we only need to initialize the kit once.
                if (!initialized) {
                    logger.debug("initializing the syntax kit");
                    DefaultSyntaxKit.initKit();
                    initialized = true;
                }
                // set content type to text/sql to get the syntax highlighting
                logger.debug("setting content type");
                final SQLEditorJSyntaxPaneImpl editor = (SQLEditorJSyntaxPaneImpl) e.getComponent();
                editor.setContentType("text/sql");
                
                /* add a document listener to update the undo/redo status in the editor.
                 * we have to do this here because we are dependent on the document being
                 * set, and that's done by setting the content type.
                 */
                editor.getDocument().addUndoableEditListener((UndoableEditEvent e1) -> {
                    logger.debug("updating undo/redo status.");
                    editor.setUndoable(e1.getEdit().canUndo());
                    editor.setRedoable(e1.getEdit().canRedo());
                });
            }
        } else if ((changeFlags & SHOWING_CHANGED) == SHOWING_CHANGED) {
            if (e.getComponent().isShowing()) {
                logger.debug("component is showing");
            } else {
                logger.debug("component is not showing");
            }
            //TODO: find a way to remove the line number bar when the
            // editor is no longer showing.
        }
    }
    
}
