/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.swing;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * A table cell editor that wraps a table cell renderer.
 */
public class TableCellEditorAdapter extends AbstractCellEditor implements TableCellEditor {

	/** delegate to a renderer */
	private Renderer renderer;
	
	
	// ********** constructors/initialization **********
	
	private TableCellEditorAdapter() {
		super();
	}
	
	/**
	 * Construct a cell editor that behaves like the specified renderer.
	 */
	public TableCellEditorAdapter(Renderer renderer) {
		this();
		this.initialize(renderer);
	}
	
	protected void initialize(Renderer r) {
		this.renderer = r;
		r.setImmediateEditListener(this.buildImmediateEditListener());
	}
	
	private ImmediateEditListener buildImmediateEditListener() {
		return new ImmediateEditListener() {
			public void immediateEdit() {
				TableCellEditorAdapter.this.stopCellEditing();
			}
		};
	}
	
	
	// ********** CellEditor implementation **********
	
	public Object getCellEditorValue() {
		return this.renderer.getValue();
	}
	
	
	// ********** TableCellEditor implementation **********

	public Component getTableCellEditorComponent(JTable table, Object value, boolean selected, int row, int column) {
		return this.renderer.getTableCellRendererComponent(table, value, selected, true, row, column);
	}
	
	
	// ********** Member classes **********************************************
	
	/**
	 * This interface defines the methods that must be implemented by a renderer
	 * that can be wrapped by a TableCellEditorAdapter.
	 */
	public interface Renderer extends TableCellRenderer {
		
		/**
		 * Return the current value of the renderer.
		 */
		Object getValue();
		
		/**
		 * Set the immediate edit listener
		 */
		void setImmediateEditListener(ImmediateEditListener listener);
	}
	
	
	public interface ImmediateEditListener {
		
		/**
		 * Called when the renderer does an "immediate edit"
		 */
		void immediateEdit();
	}
}
