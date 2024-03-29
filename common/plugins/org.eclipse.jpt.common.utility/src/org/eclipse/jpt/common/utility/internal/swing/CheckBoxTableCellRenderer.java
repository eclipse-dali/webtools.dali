/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import org.eclipse.jpt.common.utility.internal.swing.TableCellEditorAdapter.ImmediateEditListener;

/**
 * Make the cell look like a check box.
 */
public class CheckBoxTableCellRenderer
	implements TableCellEditorAdapter.Renderer
{
	/** the component used to paint the cell */
	private final JCheckBox checkBox;
	
	/** the listener to be notified on an immediate edit */
	protected TableCellEditorAdapter.ImmediateEditListener immediateEditListener;

	/** "normal" border - assume the default table "focus" border is 1 pixel thick */
	private static final Border NO_FOCUS_BORDER = BorderFactory.createEmptyBorder(1, 1, 1, 1);


	// ********** constructors/initialization **********

	/**
	 * Construct a cell renderer with no label or icon.
	 */
	public CheckBoxTableCellRenderer() {
		super();
		this.checkBox = this.buildCheckBox();
		// by default, check boxes do not paint their borders
		this.checkBox.setBorderPainted(true);
		// this setting is recommended for check boxes inside of trees and tables
		this.checkBox.setBorderPaintedFlat(true);
	}

	/**
	 * Construct a cell renderer with the specified text and icon,
	 * either of which may be null.
	 */
	public CheckBoxTableCellRenderer(String text, Icon icon) {
		this();
		this.setText(text);
		this.setIcon(icon);
	}

	/**
	 * Construct a cell renderer with the specified text.
	 */
	public CheckBoxTableCellRenderer(String text) {
		this(text, null);
	}

	/**
	 * Construct a cell renderer with the specified icon.
	 */
	public CheckBoxTableCellRenderer(Icon icon) {
		this(null, icon);
	}

	protected JCheckBox buildCheckBox() {
		JCheckBox cb = new JCheckBox();
		cb.addActionListener(this.buildActionListener());
		return cb;
	}
	
	private ActionListener buildActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (CheckBoxTableCellRenderer.this.immediateEditListener != null) {
					CheckBoxTableCellRenderer.this.immediateEditListener.immediateEdit();
				}
			}
		};
	}


	// ********** TableCellRenderer implementation **********

	public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean hasFocus, int row, int column) {
	    this.checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		this.checkBox.setComponentOrientation(table.getComponentOrientation());
		this.checkBox.setFont(table.getFont());
		this.checkBox.setEnabled(table.isEnabled());

		this.checkBox.setForeground(this.foregroundColor(table, value, selected, hasFocus, row, column));
		this.checkBox.setBackground(this.backgroundColor(table, value, selected, hasFocus, row, column));
		// once the colors are set, calculate opaque setting
		this.checkBox.setOpaque(this.cellIsOpaqueIn(table, value, selected, hasFocus, row, column));
		this.checkBox.setBorder(this.border(table, value, selected, hasFocus, row, column));

		this.setValue(value);
		return this.checkBox;
	}

	/**
	 * Return the cell's foreground color.
	 */
	protected Color foregroundColor(JTable table, @SuppressWarnings("unused") Object value, boolean selected, boolean hasFocus, int row, int column) {
		if (selected) {
			if (hasFocus && table.isCellEditable(row, column)) {
				return UIManager.getColor("Table.focusCellForeground"); //$NON-NLS-1$
			}
			return table.getSelectionForeground();
		}
		return table.getForeground();
	}

	/**
	 * Return the cell's background color.
	 */
	protected Color backgroundColor(JTable table, @SuppressWarnings("unused") Object value, boolean selected, boolean hasFocus, int row, int column) {
		if (selected) {
			if (hasFocus && table.isCellEditable(row, column)) {
				return UIManager.getColor("Table.focusCellBackground"); //$NON-NLS-1$
			}
			return table.getSelectionBackground();
		}
		return table.getBackground();
	}

	/**
	 * Return the cell's border.
	 */
	protected Border border(@SuppressWarnings("unused") JTable table, @SuppressWarnings("unused") Object value, @SuppressWarnings("unused") boolean selected, boolean hasFocus, @SuppressWarnings("unused") int row, @SuppressWarnings("unused") int column) {
		return hasFocus ?  UIManager.getBorder("Table.focusCellHighlightBorder") : NO_FOCUS_BORDER; //$NON-NLS-1$
	}

	/**
	 * Return whether the cell should be opaque in the table.
	 * If the cell's background is the same as the table's background
	 * and table is opaque, we don't need to paint the background -
	 * the table will do it.
	 */
	protected boolean cellIsOpaqueIn(JTable table, @SuppressWarnings("unused") Object value, @SuppressWarnings("unused") boolean selected, @SuppressWarnings("unused") boolean hasFocus, @SuppressWarnings("unused") int row, @SuppressWarnings("unused") int column) {
		Color cellBackground = this.checkBox.getBackground();
		Color tableBackground = table.getBackground();
		return ! (table.isOpaque() && cellBackground.equals(tableBackground));
	}

	/**
	 * Set the check box's value.
	 */
	protected void setValue(Object value) {
		// CR#3999318 - This null check needs to be removed once JDK bug is fixed
		if (value == null) {
			value = Boolean.FALSE;
		}
		this.checkBox.setSelected(((Boolean) value).booleanValue());
	}


	// ********** TableCellEditorAdapter.Renderer implementation **********

	public Object getValue() {
		return Boolean.valueOf(this.checkBox.isSelected());
	}
	
	public void setImmediateEditListener(ImmediateEditListener listener) {
		this.immediateEditListener = listener;
	}

	// ********** public API **********

	/**
	 * Set the check box's text; which by default is blank.
	 */
	public void setText(String text) {
		this.checkBox.setText(text);
	}

	/**
	 * Set the check box's icon; which by default is not present.
	 */
	public void setIcon(Icon icon) {
		this.checkBox.setIcon(icon);
	}

	/**
	 * Return the renderer's preferred height. This allows you
	 * to set the table's row height to something the check box
	 * will look good in....
	 */
	public int preferredHeight() {
		// add in space for the border top and bottom
		return (int) this.checkBox.getPreferredSize().getHeight() + 2;
	}
}
