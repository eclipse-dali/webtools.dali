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

/**
 * This implementation of ListChooser.Browser uses a
 * JOptionPane to prompt the user for the selection. Subclasses 
 * can change the dialog's title, message, and/or icon.
 */
public class SimpleListBrowser
	implements ListChooser.ListBrowser 
{
	/** Default constructor */
	protected SimpleListBrowser() {
		super();
	}
	
	/**
	 * Prompt the user using a JOptionPane.
	 */
	public void browse(ListChooser chooser) {
		Object selection = 
			JOptionPane.showInputDialog(
				chooser, 
				this.message(chooser), 
				this.title(chooser), 
				this.messageType(chooser), 
				this.icon(chooser), 
				this.selectionValues(chooser), 
				this.initialSelectionValue(chooser)
			);
		
		if (selection != null) {
			chooser.getModel().setSelectedItem(selection);
		}
	}
	
	protected Object message(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}
	
	protected String title(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}
	
	protected int messageType(@SuppressWarnings("unused") JComboBox comboBox) {
		return JOptionPane.QUESTION_MESSAGE;
	}
	
	protected Icon icon(@SuppressWarnings("unused") JComboBox comboBox) {
		return null;
	}
	
	protected Object[] selectionValues(JComboBox comboBox) {
		return this.convertToArray(comboBox.getModel());
	}
	
	protected Object initialSelectionValue(JComboBox comboBox) {
		return comboBox.getModel().getSelectedItem();
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
}
