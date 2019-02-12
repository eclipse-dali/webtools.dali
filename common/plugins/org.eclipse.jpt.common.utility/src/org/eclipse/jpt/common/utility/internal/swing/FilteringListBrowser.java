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

import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.ListModel;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * This implementation of ListChooser.ListBrowser uses a
 * {@link JOptionPane} to prompt the user for the selection. The {@link JOptionPane}
 * is passed a {@link FilteringListPanel} to assist the user in making
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
				this.buildMessage(chooser), 
				this.buildTitle(chooser), 
				this.buildOptionType(chooser), 
				this.buildMessageType(chooser), 
				this.buildIcon(chooser), 
				this.buildSelectionValues(chooser), 
				this.buildInitialSelectionValue(chooser)
		);
		
		if (option == JOptionPane.OK_OPTION) {
			chooser.getModel().setSelectedItem(this.panel.getSelection());
		}
		
		// clear the text field so the list box is re-filtered
		this.panel.getTextField().setText(""); //$NON-NLS-1$
	}
	
	protected void initializeCellRenderer(JComboBox comboBox) {
		// default behavior should be to use the cell renderer from the combobox.
		this.panel.getListBox().setCellRenderer(comboBox.getRenderer());
	}

	/**
	 * the message can be anything - here we build a component
	 */
	protected Object buildMessage(JComboBox comboBox) {
		this.panel.setCompleteList(this.convertListModelToArray(comboBox.getModel()));
		this.panel.setSelection(comboBox.getModel().getSelectedItem());
		return this.panel;
	}

	/**
	 * Convert the list of objects in the specified list model
	 * into an array.
	 */
	@SuppressWarnings("unchecked")
	protected T[] convertListModelToArray(ListModel model) {
		int size = model.getSize();
		T[] result = (T[]) new Object[size];
		for (int i = 0; i < size; i++) {
			result[i] = (T) model.getElementAt(i);
		}
		return result;
	}
	
	protected String buildTitle(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	protected int buildOptionType(@SuppressWarnings("unused") JComboBox comboBox) {
		return JOptionPane.OK_CANCEL_OPTION;
	}

	protected int buildMessageType(@SuppressWarnings("unused") JComboBox comboBox) {
		return JOptionPane.QUESTION_MESSAGE;
	}

	protected Icon buildIcon(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	protected Object[] buildSelectionValues(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	protected Object buildInitialSelectionValue(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}

	
	// ********** custom panel **********
	
	protected static class LocalFilteringListPanel<S>
		extends FilteringListPanel<S>
	{
		private static final long serialVersionUID = 1L;

		@SuppressWarnings("unchecked")
		protected LocalFilteringListPanel() {
			super((S[]) ObjectTools.EMPTY_OBJECT_ARRAY, null);
		}
	
		/**
		 * Disable the performance tweak because JOptionPane
		 * will try open wide enough to disable the horizontal scroll bar;
		 * and it looks a bit clumsy.
		 */
		@Override
		protected String getPrototypeCellValue() {
			return null;
		}
	}
}
