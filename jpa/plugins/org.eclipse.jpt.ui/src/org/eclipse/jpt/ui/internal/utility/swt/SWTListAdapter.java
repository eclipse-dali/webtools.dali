/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.utility.swt;

import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

/**
 * Adapt an SWT {@link List} to the list widget expected by
 * {@link ListWidgetModelBinding}.
 */
final class SWTListAdapter
	extends AbstractListWidgetAdapter<List>
{
	SWTListAdapter(List list) {
		super(list);
	}
	public void addSelectionListener(SelectionListener listener) {
		this.widget.addSelectionListener(listener);
	}
	public void removeSelectionListener(SelectionListener listener) {
		this.widget.removeSelectionListener(listener);
	}
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
}
