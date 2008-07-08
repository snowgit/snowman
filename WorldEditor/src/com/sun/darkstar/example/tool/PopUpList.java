package com.sun.darkstar.example.tool;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.tree.TreePath;

public class PopUpList extends JList{
	JPopupMenu popup;
	
	public PopUpList(ListModel model){
		super(model);
		popup = new JPopupMenu();
        //popup.add(new JMenuItem("Default Popup Menu"));
        popup.setOpaque(true);
        popup.setLightWeightPopupEnabled(false);
        addMouseListener(
                new MouseAdapter() {
            @Override
            public void mouseReleased( MouseEvent e ) {
            	setSelectedIndex(locationToIndex(new Point(e.getX(),e.getY())));
                if ( e.isPopupTrigger()) {
                    popup.show( (JComponent)e.getSource(), e.getX(), e.getY() );
                }
            }
        }
        );
	}
	
	public void addToPopup(JMenuItem item){
        popup.add(item);
    }
}
