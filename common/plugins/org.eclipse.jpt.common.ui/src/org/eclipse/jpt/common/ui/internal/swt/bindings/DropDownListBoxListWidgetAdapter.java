/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.swt.bindings;

import java.util.ArrayList;
import org.eclipse.jpt.common.ui.internal.swt.bindings.ListWidgetModelBinding.SelectionBinding;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.widgets.Combo;

/**
 * Adapt an SWT {@link Combo} to the {@link ListWidgetModelBinding.ListWidget
 * list widget} expected by {@link ListWidgetModelBinding} and the
 * {@link DropDownListBoxSelectionBinding.DropDownListBox drop-down list box}
 * expected by {@link DropDownListBoxSelectionBinding}.
 */
final class DropDownListBoxListWidgetAdapter<E>
	extends AbstractComboListWidgetAdapter<E>
	implements DropDownListBoxSelectionBinding.DropDownListBox
{
	DropDownListBoxListWidgetAdapter(Combo combo) {
		super(combo);
	}

	// ********** ListWidgetModelBinding.ListWidget **********
	@SuppressWarnings("unchecked")
	public SelectionBinding buildSelectionBinding(ArrayList<E> list, Object selectionModel) {
		return new DropDownListBoxSelectionBinding<E>(list, (ModifiablePropertyValueModel<E>) selectionModel, this);
	}

	// ********** DropDownListBoxSelectionBinding.DropDownListBox **********
	public void select(int index) {
		this.widget.select(index);
	}
	public void deselect(int index) {
		this.widget.deselect(index);
	}
	public void deselectAll() {
		this.widget.deselectAll();
	}
}
