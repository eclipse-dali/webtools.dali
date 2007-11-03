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

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 * This implementation of the CachingComboBoxModel interface can be used
 * whenever there is no need for caching (i.e. the contents of the selection
 * list can be generated with little latency). All the normal ComboBoxModel
 * behavior is delegated to a client-supplied ComboBoxModel.
 */
public class NonCachingComboBoxModel implements CachingComboBoxModel {
	private ComboBoxModel wrappedComboBoxModel;

	public NonCachingComboBoxModel(ComboBoxModel wrappedComboBoxModel) {
		this.wrappedComboBoxModel = wrappedComboBoxModel;
	}


	// ********** CachingComboBoxModel implementation **********

	public void cacheList() {
		//do nothing
	}

	public void uncacheList() {
		//do nothing
	}

	public boolean isCached() {
		return false;
	}


	// ********** ComboBoxModel implementation **********

	public void setSelectedItem(Object anItem) {
		this.wrappedComboBoxModel.setSelectedItem(anItem);
	}

	public Object getSelectedItem() {
		return this.wrappedComboBoxModel.getSelectedItem();
	}


	// ********** ListModel implementation **********

	public int getSize() {
		return this.wrappedComboBoxModel.getSize();
	}

	public Object getElementAt(int index) {
		return this.wrappedComboBoxModel.getElementAt(index);
	}

	public void addListDataListener(ListDataListener l) {
		this.wrappedComboBoxModel.addListDataListener(l);
	}

	public void removeListDataListener(ListDataListener l) {
		this.wrappedComboBoxModel.removeListDataListener(l);
	}  

}
