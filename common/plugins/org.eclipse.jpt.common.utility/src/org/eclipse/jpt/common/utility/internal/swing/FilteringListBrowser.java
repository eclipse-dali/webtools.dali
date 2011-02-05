/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.utility.internal.swing;

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

/**
 * This implementation of LongListComponent.Browser uses a
 * JOptionPane to prompt the user for the selection. The JOPtionPane
 * is passed a FilteringListPanel to assist the user in making
 * a selection.
 */
public class FilteringListBrowser<T> 
	implements ListChooser.ListBrowser 
{
	private FilteringListPanel<T> panel;

	/**
	 * Default constructor.
	 */
	public FilteringListBrowser() {
		super();
		this.panel = this.buildPanel();
	}

	protected FilteringListPanel<T> buildPanel() {
		return new LocalFilteringListPanel<T>();
	}

	/**
	 * Prompt the user using a JOptionPane with a filtering
	 * list panel.
	 */
	public void browse(ListChooser chooser) {	
		this.initializeCellRenderer(chooser);
		
		int option = 
			JOptionPane.showOptionDialog(
				chooser, 
				this.message(chooser), 
				this.title(chooser), 
				this.optionType(chooser), 
				this.messageType(chooser), 
				this.icon(chooser), 
				this.selectionValues(chooser), 
				this.initialSelectionValue(chooser)
		);
		
		if (option == JOptionPane.OK_OPTION) {
			chooser.getModel().setSelectedItem(this.panel.selection());
		}
		
		// clear the text field so the list box is re-filtered
		this.panel.textField().setText(""); //$NON-NLS-1$
	}
	
	protected void initializeCellRenderer(JComboBox comboBox) {
		// default behavior should be to use the cell renderer from the combobox.
		this.panel.listBox().setCellRenderer(comboBox.getRenderer());
	}

	/**
	 * the message can be anything - here we build a component
	 */
	protected Object message(JComboBox comboBox) {
		this.panel.setCompleteList(this.convertToArray(comboBox.getModel()));
		this.panel.setSelection(comboBox.getModel().getSelectedItem());
		return this.panel;
	}

	protected String title(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	protected int optionType(@SuppressWarnings("unused") JComboBox comboBox) {
		return JOptionPane.OK_CANCEL_OPTION;
	}

	protected int messageType(@SuppressWarnings("unused") JComboBox comboBox) {
		return JOptionPane.QUESTION_MESSAGE;
	}

	protected Icon icon(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	protected Object[] selectionValues(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	protected Object initialSelectionValue(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	/**
	 * Convert the list of objects in the specified list model
	 * into an array.
	 */
	protected Object[] convertToArray(ListModel model) {
		int size = model.getSize();
		Object[] result = new Object[size];
		for (int i = 0; i < size; i++) {
			result[i] = model.getElementAt(i);
		}
		return result;
	}
	
	
	// ********** custom panel **********
	
	protected static class LocalFilteringListPanel<S> extends FilteringListPanel<S> {
		protected static final Object[] EMPTY_ARRAY = new Object[0];

		protected LocalFilteringListPanel() {
			super(EMPTY_ARRAY, null);
		}
	
		/**
		 * Disable the performance tweak because JOptionPane
		 * will try open wide enough to disable the horizontal scroll bar;
		 * and it looks a bit clumsy.
		 */
		@Override
		protected String prototypeCellValue() {
			return null;
		}
	
	}

}
