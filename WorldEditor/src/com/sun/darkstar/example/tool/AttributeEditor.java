/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sun.darkstar.example.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * This class extends JTable to create a very sepcific editor for Maps
 * 
 * @author Jeffrey Kesselman
 */
public class AttributeEditor extends JTable {
	List<AttributeEditorListener> listeners = new ArrayList<AttributeEditorListener>();
	public AttributeEditor() {
		super();
		setAttributes(new HashMap());
		setColumnModel(new AEColumnModel());
		setCellSelectionEnabled(true);
	}

	/**
	 * This call sets the map that this editor represents/edits
	 * 
	 * @param map
	 */
	public void setAttributes(Map map) {
		setModel(new MapModel(map));
		
	}
	
	public void fireAttributeChanged(String key, String value){
		for(AttributeEditorListener l : listeners) {
			l.attributeChanged(key, value);
		}
	}
	
	public void addListener(AttributeEditorListener l){
		listeners.add(l);
	}

	class MapModel extends AbstractTableModel {
		Map map;

		public MapModel(Map map) {
			this.map = map;
		}

		public int getRowCount() {
			return map.size();
		}

		public int getColumnCount() {
			return 2;
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			Entry entry = (Entry) map.entrySet().toArray()[rowIndex];
			if (columnIndex == 0) {
				return entry.getKey();
			} else {
				return entry.getValue();
			}
		}

		public boolean isCellEditable(int row, int col) {
			return col == 1;
		}

		@Override
		public void setValueAt(Object value, int rowIndex, int columnIndex) {
			if (columnIndex == 1) {
				String tag = (String) ((Entry) map.entrySet().toArray()[rowIndex])
						.getKey();
				map.put(tag, value);
				AttributeEditor.this.fireAttributeChanged(tag,(String)value);
			}
		}

	}

	class AEColumnModel extends DefaultTableColumnModel {
		TableColumn[] cols  = new TableColumn[2];
		public AEColumnModel() {
			cols[0] = new TableColumn(0) {
				public Object getHeaderValue() {
					return "Attribute";
				}
			};
			cols[1] = new TableColumn(1) {
				public Object getHeaderValue() {
					return "Value";
				}
			};

		}

		@Override
		public TableColumn getColumn(int columnIndex) {
			// TODO Auto-generated method stub
			return cols[columnIndex];
		}
		
		

	}
}
