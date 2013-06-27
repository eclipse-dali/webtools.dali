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
import org.eclipse.jpt.common.ui.internal.swt.events.SelectionAdapter;
import org.eclipse.jpt.common.ui.internal.swt.listeners.SWTListenerTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;

abstract class AbstractComboSelectionBinding<E, CA extends AbstractComboSelectionBinding.ComboAdapter>
	implements ListWidgetModelBinding.SelectionBinding
{
	// ***** model
	/**
	 * The underlying list.
	 */
	final ArrayList<E> list;

	/**
	 * A modifiable value model on the underlying model value.
	 */
	final ModifiablePropertyValueModel<E> valueModel;

	/**
	 * A listener that allows us to synchronize the combo's
	 * selection/text with the model value.
	 */
	final PropertyChangeListener valueListener;

	// ***** UI
	/**
	 * The combo whose selection/text we keep synchronized
	 * with the model selection.
	 */
	final CA combo;

	/**
	 * A listener that allows us to synchronize our selected item model
	 * with the combo's selection/text.
	 */
	final SelectionListener comboSelectionListener;


	AbstractComboSelectionBinding(
			ArrayList<E> list,
			ModifiablePropertyValueModel<E> valueModel,
			CA combo
	) {
		super();
		if ((list == null) || (valueModel == null) || (combo == null)) {
			throw new NullPointerException();
		}
		this.list = list;
		this.valueModel = valueModel;
		this.combo = combo;

		this.valueListener = this.buildValueListener();
		this.valueModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);

		this.comboSelectionListener = this.buildComboSelectionListener();
		this.combo.addSelectionListener(this.comboSelectionListener);
	}


	// ********** initialization **********

	private PropertyChangeListener buildValueListener() {
		return SWTListenerTools.wrap(new ValueListener(), this.combo.getDisplay());
	}

	/* CU private */ class ValueListener
		extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			AbstractComboSelectionBinding.this.valueChanged(event);
		}
	}

	private SelectionListener buildComboSelectionListener() {
		return new ComboSelectionListener();
	}

	/* CU private */ class ComboSelectionListener
		extends SelectionAdapter
	{
		@Override
		public void widgetSelected(SelectionEvent event) {
			AbstractComboSelectionBinding.this.comboSelectionChanged();
		}
		@Override
		public void widgetDefaultSelected(SelectionEvent event) {
			AbstractComboSelectionBinding.this.comboDoubleClicked();
		}
	}


	// ********** model events **********

	/* CU private */ void valueChanged(PropertyChangeEvent event) {
		if ( ! this.combo.isDisposed()) {
			@SuppressWarnings("unchecked")
			E item = (E) event.getNewValue();
			this.valueChanged_(item);
		}
	}

	/**
	 * The combo is not disposed.
	 */
	abstract void valueChanged_(E item);


	// ********** combo events **********

	/* CU private */ void comboSelectionChanged() {
		this.valueModel.setValue(this.getComboSelectedItem());
	}

	/* CU private */ void comboDoubleClicked() {
		this.comboSelectionChanged();
	}

	private E getComboSelectedItem() {
		int index = this.combo.getSelectionIndex();
		return (index == -1) ? null : this.list.get(index);
	}


	// ********** misc **********

	public void dispose() {
		this.combo.removeSelectionListener(this.comboSelectionListener);
		this.valueModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.valueListener);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.valueModel);
	}


	// ********** adapter interface **********

	/**
	 * Adapter used by the selection binding to query and manipulate
	 * the combo. (We have this adapter just in case someone needs to use a
	 * {@link org.eclipse.swt.custom.CCombo CCombo}. Although, that's not
	 * so advisable, as <code>CCombo</code> has bugs....)
	 */
	interface ComboAdapter {
		/**
		 * Return the combo's display.
		 */
		Display getDisplay();

		/**
		 * Return whether the combo is <em>disposed</em>.
		 */
		boolean isDisposed();

		/**
		 * Add the specified selection listener to the combo.
		 */
		void addSelectionListener(SelectionListener listener);

		/**
		 * Remove the specified selection listener from the combo.
		 */
		void removeSelectionListener(SelectionListener listener);

		/**
		 * Return the index of the combo's selection.
		 */
		int getSelectionIndex();
	}
}
