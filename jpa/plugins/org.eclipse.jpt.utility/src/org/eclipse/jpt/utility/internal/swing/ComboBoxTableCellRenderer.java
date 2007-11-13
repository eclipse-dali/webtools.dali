/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.internal.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.eclipse.jpt.utility.internal.ClassTools;

/**
 * Make the cell look like a combo-box.
 */
public class ComboBoxTableCellRenderer implements TableCellEditorAdapter.Renderer {

	/* caching the combo box because we are caching the comboBoxModel.
	 * Everytime we rebuilt the comboBox we would set the model on it and not
	 * remove the model from the old combo box.  This meant that new listeners
	 * kept being added to the comboBoxModel for every comboBox build.
	 * Not sure if there is a way to clear out the old combo box, or why
	 * we were buildig a new combo box every time so I went with caching it.
	 */
	private JComboBox comboBox;
	
	/** the items used to populate the combo box */
	private CachingComboBoxModel model;
	private ListCellRenderer renderer;
	Object value;
	private static int height = -1;
	boolean fakeFocusFlag;

	/** the listener to be notified on an immediate edit */
	protected TableCellEditorAdapter.ImmediateEditListener immediateEditListener;
	
	/** hold the original colors of the combo-box */
	private static Color defaultForeground;
	private static Color defaultBackground;

	/** "normal" border - assume the default table "focus" border is 1 pixel thick */
	private static final Border NO_FOCUS_BORDER = BorderFactory.createEmptyBorder(1, 1, 1, 1);


	// ********** constructors/initialization **********

	/**
	 * Default constructor.
	 */
	private ComboBoxTableCellRenderer() {
		super();
		initialize();
	}

	/**
	 * Construct a cell renderer that uses the specified combo-box model.
	 */
	public ComboBoxTableCellRenderer(ComboBoxModel model) {
		this(new NonCachingComboBoxModel(model));
	}
	
	/**
	 * Construct a cell renderer that uses the specified caching combo-box model.
	 */
	public ComboBoxTableCellRenderer(CachingComboBoxModel model) {
		this();
		this.model = model;
	}

	/**
	 * Construct a cell renderer that uses the specified
	 * combo-box model and renderer.
	 */
	public ComboBoxTableCellRenderer(ComboBoxModel model, ListCellRenderer renderer) {
		this(new NonCachingComboBoxModel(model), renderer);
	}
	
	/**
	 * Construct a cell renderer that uses the specified
	 * caching combo-box model and renderer.
	 */
	public ComboBoxTableCellRenderer(CachingComboBoxModel model, ListCellRenderer renderer) {
		this(model);
		this.renderer = renderer;
	}

	protected void initialize() {
		// save the original colors of the combo-box, so we
		// can use them to paint non-selected cells
		if (height == -1) {
			JComboBox cb = new JComboBox();
			cb.addItem("m");

			// add in space for the border top and bottom
			height = cb.getPreferredSize().height + 2;

			defaultForeground = cb.getForeground();
			defaultBackground = cb.getBackground();
		}
	}

    static JLabel prototypeLabel = new JLabel("Prototype", new EmptyIcon(16), SwingConstants.LEADING);

    protected JComboBox buildComboBox() {

		final JComboBox result = new JComboBox() {
			private boolean fakeFocus;
			@Override
			public boolean hasFocus() {
				return fakeFocus || super.hasFocus();
			}
			@Override
			public void paint(Graphics g) {
				fakeFocus = ComboBoxTableCellRenderer.this.fakeFocusFlag;
				super.paint(g);
				fakeFocus = false;
			}
			//wrap the renderer to deal with the prototypeDisplayValue
		    @Override
			public void setRenderer(final ListCellRenderer aRenderer) {
		        super.setRenderer(new ListCellRenderer(){
		            public Component getListCellRendererComponent(JList list, Object v, int index, boolean isSelected, boolean cellHasFocus) {
		                if (v == prototypeLabel) {
		                    return prototypeLabel;
		                }
		                return aRenderer.getListCellRendererComponent(list, v, index, isSelected, cellHasFocus);
		            }
		        });
		    }
			@Override
			public int getSelectedIndex() {
		        boolean listNotCached = !listIsCached();
		        if (listNotCached) {
		            cacheList();
		        }
		        
				int index = super.getSelectedIndex();

		        if (listNotCached) {
		            uncacheList();
		        }
				return index;
		   }

		};
		// stole this code from javax.swing.DefaultCellEditor
		result.putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);
		result.addActionListener(this.buildActionListener());
		result.addPopupMenuListener(this.buildPopupMenuListener());
		
        //These are used to workaround problems with Swing trying to 
        //determine the size of a comboBox with a large model
        result.setPrototypeDisplayValue(prototypeLabel);
        getListBox(result).setPrototypeCellValue(prototypeLabel);
        
		return result;
	}
	
    
    private JList getListBox(JComboBox result) {
        return (JList) ClassTools.getFieldValue(result.getUI(), "listBox");
    }

	
	private ActionListener buildActionListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				Object selectedItem = cb.getSelectedItem();

				// Only update the selected item and invoke immediateEdit() if the
				// selected item actually changed, during the initialization of the
				// editing, the model changes and causes this method to be invoked,
				// it causes CR#3963675 to occur because immediateEdit() stop the
				// editing, which is done at the wrong time
				if (ComboBoxTableCellRenderer.this.value != selectedItem) {
					ComboBoxTableCellRenderer.this.value = cb.getSelectedItem();
					ComboBoxTableCellRenderer.this.immediateEdit();
				}
			}
		};
	}

	void immediateEdit() {
		if (this.immediateEditListener != null) {
			this.immediateEditListener.immediateEdit();
		}
	}
	
	private PopupMenuListener buildPopupMenuListener() {
		return new PopupMenuListener() {
		
			public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
				if (listIsCached()) {
					uncacheList();
				}
				cacheList();
			}
		
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
	            if (listIsCached()) {
	                uncacheList();
	            }

			}
		
			public void popupMenuCanceled(PopupMenuEvent e) {
	            if (listIsCached()) {
	                uncacheList();
	            }
			}
		};
	}

	
	void cacheList() {
		this.model.cacheList();
	}
	
	void uncacheList() {
		this.model.uncacheList();
	}	
	
	boolean listIsCached() {
		return this.model.isCached();
	}
	// ********** TableCellRenderer implementation **********

	public Component getTableCellRendererComponent(JTable table, Object val, boolean selected, boolean hasFocus, int row, int column) {
		this.fakeFocusFlag = selected || hasFocus;
		if (this.comboBox == null) {
			this.comboBox = this.buildComboBox();
	
			this.comboBox.setComponentOrientation(table.getComponentOrientation());
			this.comboBox.setModel(this.model);
			if (this.renderer != null) {
				this.comboBox.setRenderer(this.renderer);
			}
			this.comboBox.setFont(table.getFont());
			this.comboBox.setEnabled(table.isEnabled());
			this.comboBox.setBorder(this.border(table, val, selected, hasFocus, row, column));
		}

		// We need to go through the model since JComboBox might prevent us from
		// selecting the value. This can happen when the value is not contained
		// in the model, see CR#3950044 for an example
		this.model.setSelectedItem(val);

		return this.comboBox;
	}

	/**
	 * Return the cell's foreground color.
	 */
	protected Color foregroundColor(JTable table, Object val, boolean selected, boolean hasFocus, int row, int column) {
		if (selected) {
			if (hasFocus && table.isCellEditable(row, column)) {
				return defaultForeground;
			}
			return table.getSelectionForeground();
		}
		return defaultForeground;
	}

	/**
	 * Return the cell's background color.
	 */
	protected Color backgroundColor(JTable table, Object val, boolean selected, boolean hasFocus, int row, int column) {
		if (selected) {
			if (hasFocus && table.isCellEditable(row, column)) {
				return defaultBackground;
			}
			return table.getSelectionBackground();
		}
		return defaultBackground;
	}

	/**
	 * Return the cell's border.
	 */
	protected Border border(JTable table, Object val, boolean selected, boolean hasFocus, int row, int column) {
		return hasFocus ?
			UIManager.getBorder("Table.focusCellHighlightBorder")
		:
			NO_FOCUS_BORDER;
	}


	// ********** TableCellEditorAdapter.Renderer implementation **********

	public Object getValue() {
		return this.value;
	}
	
	public void setImmediateEditListener(TableCellEditorAdapter.ImmediateEditListener listener) {
		this.immediateEditListener = listener;
	}


	// ********** public API **********

	/**
	 * Return the renderer's preferred height. This allows you
	 * to set the row height to something the combo-box will look good in....
	 */
	public int getPreferredHeight() {
		return height;
	}

}