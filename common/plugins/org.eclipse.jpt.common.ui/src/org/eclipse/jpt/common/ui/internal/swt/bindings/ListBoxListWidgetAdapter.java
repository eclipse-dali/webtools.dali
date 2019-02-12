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

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.swt.bindings.ListWidgetModelBinding.SelectionBinding;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.List;

/**
 * Adapt an SWT {@link List} to the {@link ListWidgetModelBinding.ListWidget
 * list widget} expected by {@link ListWidgetModelBinding}.
 */
final class ListBoxListWidgetAdapter<E>
	extends AbstractListWidgetAdapter<E, List>
{
	ListBoxListWidgetAdapter(List list) {
		super(list);
	}
	@SuppressWarnings("unchecked")
	public SelectionBinding buildSelectionBinding(ArrayList<E> list, Object selectionModel) {
		return new ListBoxSelectionBinding<E>(list, (ModifiableCollectionValueModel<E>) selectionModel, this.widget);
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
