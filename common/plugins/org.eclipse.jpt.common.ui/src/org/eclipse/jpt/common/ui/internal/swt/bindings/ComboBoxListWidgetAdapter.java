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
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Combo;

/**
 * Adapt an SWT {@link Combo} to the {@link ListWidgetModelBinding.ListWidget
 * list widget} expected by {@link ListWidgetModelBinding} and the
 * {@link ComboBoxSelectionBinding.ComboBox combo-box}
 * expected by {@link ComboBoxSelectionBinding}.
 * <p>
 * <strong>NB:</strong>
 * We work around what could be considered a combo bug. That is, the combo will
 * change (or clear) the text field if the corresponding item in the pick list is
 * replaced in (or removed from) the pick list.
 * And this behavior is a bit arbitrary: If the user types
 * in a string that exactly matches an entry in the pick list, as long as the
 * drop-down list is never displayed, the text field will <em>not</em> be
 * changed (or cleared) when the corresponding entry is replaced in (or removed
 * from) the pick list. Only if the combo's selection index is not -1 and points
 * to the item corresponding to the current text field string will the text
 * field be modified if the list is modified.
 * <p>
 * There are three ways the selection index is set:<ul>
 * <li>The drop-down list is displayed and an item in the list is selected
 *     via a mouse click
 * <li>The drop-down list is displayed and an item in the list is selected
 *     automatically because the current string in the text field matches
 *     the item (in part or whole)
 * <li>If we programmatically change the automatic selection described above
 *     (i.e. upon a selection change event, we set the text field string to the
 *     appopriate value but immediately change the selection index to -1),
 *     the selection will still be changed automatically when the drop-down
 *     list is closed (and we receive <em>no</em> notification of this change).
 * </ul>
 * We work around this bug by saving and restoring the text field string
 * whenever the pick list changes. We also set
 * {@link ComboBoxSelectionBinding#modifyingComboList a flag} in the selection
 * binding so these changes are not unnecessarily forwarded to the model. This
 * hack should be OK since we have control of the UI thread when setting and
 * clearing the flag and, as a result, we will not get any user-initiated modify
 * events from the combo until <em>after</em> we are finished handling the pick
 * list model changes. (Note: This may be a problem only on Windows....)
 */
final class ComboBoxListWidgetAdapter
	extends AbstractComboListWidgetAdapter<String>
	implements ComboBoxSelectionBinding.ComboBox
{
	private ComboBoxSelectionBinding selectionBinding;
	private static final boolean DEBUG = false;


	ComboBoxListWidgetAdapter(Combo combo) {
		super(combo);
		if (DEBUG) {
			new PrintSelection().run();
		}
	}

	// ********** ListWidgetModelBinding.ListWidget **********
	@SuppressWarnings("unchecked")
	public SelectionBinding buildSelectionBinding(ArrayList<String> list, Object selectionModel) {
		if (this.selectionBinding == null) {
			this.selectionBinding = new ComboBoxSelectionBinding(list, (ModifiablePropertyValueModel<String>) selectionModel, this);
		}
		return this.selectionBinding;
	}
	@Override
	public void setItem(int index, String item) {
		this.selectionBinding.modifyingComboList = true;
		try {
			String s = this.widget.getText();
			super.setItem(index, item);
			this.widget.setText(s);
		} finally {
			this.selectionBinding.modifyingComboList = false;
		}
	}
	@Override
	public void setItems(String[] items) {
		this.selectionBinding.modifyingComboList = true;
		try {
			String s = this.widget.getText();
			super.setItems(items);
			this.widget.setText(s);
		} finally {
			this.selectionBinding.modifyingComboList = false;
		}
	}
	@Override
	public void add(String item, int index) {
		this.selectionBinding.modifyingComboList = true;
		try {
			String s = this.widget.getText();
			super.add(item, index);
			this.widget.setText(s);
		} finally {
			this.selectionBinding.modifyingComboList = false;
		}
	}
	@Override
	public void remove(int start, int end) {
		this.selectionBinding.modifyingComboList = true;
		try {
			String s = this.widget.getText();
			super.remove(start, end);
			this.widget.setText(s);
		} finally {
			this.selectionBinding.modifyingComboList = false;
		}
	}
	@Override
	public void removeAll() {
		this.selectionBinding.modifyingComboList = true;
		try {
			String s = this.widget.getText();
			super.removeAll();
			this.widget.setText(s);
		} finally {
			this.selectionBinding.modifyingComboList = false;
		}
	}

	// ********** ComboBoxSelectionBinding.ComboBox **********
	public void addModifyListener(ModifyListener listener) {
		this.widget.addModifyListener(listener);
	}
	public void removeModifyListener(ModifyListener listener) {
		this.widget.removeModifyListener(listener);
	}
	public String getText() {
		return this.widget.getText();
	}
	public void setText(String text) {
		this.widget.setText(text);
	}

	/**
	 * Every 0.25 sec. print the combo's selection index if it has changed.
	 */
	class PrintSelection
		extends RunnableAdapter
	{
		private int index = -2;
		@Override
		public void run() {
			if ( ! ComboBoxListWidgetAdapter.this.isDisposed()) {
				this.run_();
			}
		}
		private void run_() {
			int selection = ComboBoxListWidgetAdapter.this.getSelectionIndex();
			if (selection != this.index) {
				this.index = selection;
				System.out.println("selection: " + selection); //$NON-NLS-1$
			}
			DisplayTools.timerExec(250, this);
		}
	}
}
