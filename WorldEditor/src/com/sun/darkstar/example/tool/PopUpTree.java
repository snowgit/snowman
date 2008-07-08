/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.tool;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.MenuElement;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 * This super class of JTree creates a tree with a popup menu
 * @author Jeffrey Kesselman
 */
class PopUpTree extends JTree {
    JPopupMenu popup;
    JMenuItem mi;
    
    public PopUpTree(TreeModel model) {
        super(model);
        
        popup = new JPopupMenu();
        //popup.add(new JMenuItem("Default Popup Menu"));
        popup.setOpaque(true);
        popup.setLightWeightPopupEnabled(false);
        addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseReleased( MouseEvent e ) {
                if ( e.isPopupTrigger()) {
                    TreePath clickedElement = 
                            getPathForLocation (e.getX(),e.getY());
                    setSelectionPath(clickedElement);        
                    popup.show( (JComponent)e.getSource(), e.getX(), e.getY() );
                }
            }
        }
        );
        
    }
    
  /**
   * This method is called to add an item to the tree's popup menu
   * @param item The menu item to add
   */
    public void addToPopup(JMenuItem item){
        popup.add(item);
    }
    
    private int findItemRow(JMenuItem item){
        int i=0;
        for (MenuElement el : popup.getSubElements()){
            if (el == item){
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }
    
}