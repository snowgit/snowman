package com.sun.darkstar.example.tool;

import java.awt.Component;
import java.awt.GridLayout;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class EnumButtonBar<K extends Enum<K>> extends JPanel implements ActionListener {
	Class enumClassName;
	List<EnumButtonBarListener> listeners = new ArrayList<EnumButtonBarListener>();
	public EnumButtonBar(K ... buttons) {
		enumClassName = buttons[0].getClass();
		setLayout(new GridLayout(1,0));
		for(Enum e : buttons){
			addButton(e);
		}
		JToggleButton first = (JToggleButton)getComponent(0);
		first.setSelected(true);
	}

	private void addButton(Enum e) {
		JToggleButton button = new JToggleButton(e.name());
		button.addActionListener(this);
		add(button);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JToggleButton buttonPressed = (JToggleButton) e.getSource(); 
		for (int i=0; i< getComponentCount();i++){
			Component c = getComponent(i);
			if (c instanceof JToggleButton){
				if (!buttonPressed.equals(c)){
					((JToggleButton)c).setSelected(false);
				}
			}
		}
		fireEnum(Enum.valueOf(enumClassName, buttonPressed.getText()));	
	}

	private void fireEnum(Enum actualEnum) {
		for(EnumButtonBarListener l : listeners){
			l.enumSet(actualEnum);
		}
		
	}
	
	public void addListener(EnumButtonBarListener l){
		listeners.add(l);
	}
	

}
