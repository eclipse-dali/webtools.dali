/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;

/**
 * Common {@link Combo} adaptation.
 */
abstract class AbstractComboListWidgetAdapter<E>
	extends AbstractListWidgetAdapter<E, Combo>
	implements AbstractComboSelectionBinding.ComboAdapter
{
	AbstractComboListWidgetAdapter(Combo combo) {
		super(combo);
	}

	// ********** ListWidgetModelBinding.ListWidget **********
	public String[] getItems() {
		return this.widget.getItems();
	}
	public void setItem(int index, String item) {
		this.widget.setItem(index, item);
	}
	public void setItems(String[] items) {
		this.widget.setItems(items);
	}
	public void add(String item, int index) {
		this.widget.add(item, index);
	}
	public void remove(int start, int end) {
		this.widget.remove(start, end);
	}
	public void removeAll() {
		this.widget.removeAll();
	}

	// ********** AbstractComboSelectionBinding.ComboAdapter **********
	public void addSelectionListener(SelectionListener listener) {
		this.widget.addSelectionListener(listener);
	}
	public void removeSelectionListener(SelectionListener listener) {
		this.widget.removeSelectionListener(listener);
	}
	public int getSelectionIndex() {
		return this.widget.getSelectionIndex();
	}
}
