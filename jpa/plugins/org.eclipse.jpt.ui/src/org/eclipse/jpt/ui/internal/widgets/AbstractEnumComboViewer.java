/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * This pane simply shows a combo where its data is populating through
 * {@link #choices()} and a default value can also be added.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | | I                                                                   |v| |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
abstract class AbstractEnumComboViewer<T extends Model, V> extends AbstractPane<T>
{
	/**
	 * The main widget of this pane.
	 */
	private ComboViewer comboViewer;

	/**
	 * A constant used to represent the <code>null</code> value.
	 */
	public static final String NULL_VALUE = "null";

	/**
	 * Creates a new <code>AbstractEnumComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	AbstractEnumComboViewer(AbstractPane<? extends T> parentPane,
	                        Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>AbstractEnumComboViewer</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	AbstractEnumComboViewer(AbstractPane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>AbstractEnumComboViewer</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	AbstractEnumComboViewer(PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent,
	                        WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/**
	 * Creates the list of choices and add an extra element that represents the
	 * default value.
	 *
	 * @return The combo's choices including the default value
	 */
	private Object[] buildChoices() {
		V[] choices = choices();
		Arrays.sort(choices, buildComparator());

		Object[] extendedChoices = new Object[choices.length + 1];
		System.arraycopy(choices, 0, extendedChoices, 1, choices.length);
		extendedChoices[0] = NULL_VALUE;

		return extendedChoices;
	}

	/**
	 * Creates the <code>ComboViewer</code> with the right combo widgets.
	 *
	 * @param container The container of the combo
	 * @return A new <code>ComboViewer</code> containing the right combo widget
	 */
	abstract ComboViewer buildComboViewer(Composite container);

	private Comparator<Object> buildComparator() {
		return new Comparator<Object>() {
			final LabelProvider labelProvider = buildLabelProvider();

			public int compare(Object value1, Object value2) {
				String displayString1 = labelProvider.getText(value1);
				String displayString2 = labelProvider.getText(value2);
				return Collator.getInstance().compare(displayString1, displayString2);
			}
		};
	}

	/**
	 * Retrieves the localized string from the given NLS class by creating the
	 * key. That key is the concatenation of the composite's short class name
	 * with the toString() of the given value separated by an underscore.
	 *
	 * @param nlsClass The NLS class used to retrieve the localized text
	 * @param compositeClass The class used for creating the key, its short class
	 * name is the beginning of the key
	 * @param value The value used to append its toString() to the generated key
	 * @return The localized text associated with the value
	 */
	protected final String buildDisplayString(Class<?> nlsClass,
	                                          Class<?> compositeClass,
	                                          Object value) {

		return SWTUtil.buildDisplayString(nlsClass, compositeClass, value);
	}

	/**
	 * Retrieves the localized string from the given NLS class by creating the
	 * key. That key is the concatenation of the composite's short class name
	 * with the toString() of the given value separated by an underscore.
	 *
	 * @param nlsClass The NLS class used to retrieve the localized text
	 * @param composite The object used to retrieve the short class name that is
	 * the beginning of the key
	 * @param value The value used to append its toString() to the generated key
	 * @return The localized text associated with the value
	 */
	protected final String buildDisplayString(Class<?> nlsClass,
	                                          Object composite,
	                                          Object value) {

		return SWTUtil.buildDisplayString(nlsClass, composite, value);
	}

	/**
	 * Creates the display string for the given element. If the element is the
	 * virtual <code>null</code> value then its display string will be "Default"
	 * appended by the actual default value, if it exists.
	 *
	 * @param value The value to convert into a human readable string
	 * @return The string representation of the given element
	 */
	@SuppressWarnings("unchecked")
	private String buildDisplayString(Object value) {
		if (value == NULL_VALUE) {
			V defaultValue = (subject() != null) ? defaultValue() : null;

			if (defaultValue != null) {
				String displayString = displayString(defaultValue);
				return NLS.bind(JptUiMessages.EnumComboViewer_defaultWithDefault, displayString);
			}
			else {
				return JptUiMessages.EnumComboViewer_default;
			}
		}

		return displayString((V) value);
	}

	final LabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildDisplayString(element);
			}
		};
	}

	private ISelection buildSelection() {
		Object value = (subject() != null) ? getValue() : null;

		if (value == null) {
			value = NULL_VALUE;
		}

		return new StructuredSelection(value);
	}

	private ISelectionChangedListener buildSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {
				if (!isPopulating()) {
					StructuredSelection selection = (StructuredSelection) e.getSelection();
					valueChanged(selection.getFirstElement());
				}
			}
		};
	}

	/**
	 * Returns the possible choices to show in the viewer.
	 *
	 * @return The items to show in the combos
	 */
	protected abstract V[] choices();

	/**
	 * Returns the default value, this method is not called if the subject is
	 * <code>null</code>.
	 *
	 * @return The value that is declared as being the default when it is not
	 * defined or <code>null</code> if there is no default value
	 */
	protected abstract V defaultValue();

	/**
	 * Returns the displayable string for the given value.
	 *
	 * @param value The value to translate into a human readable string
	 * @return The localized text representing the given value
	 */
	protected abstract String displayString(V value);

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		this.populateCombo();
	}

	/**
	 * Returns
	 *
	 * @return
	 */
	final ComboViewer getComboViewer() {
		return comboViewer;
	}

	/**
	 * Retrieves the subject's value. The subject is never <code>null</code>.
	 *
	 * @return The subject' value, which can be <code>null</code>
	 */
	protected abstract V getValue();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected final void initializeLayout(Composite container) {

		this.comboViewer = this.buildComboViewer(container);
		this.comboViewer.addSelectionChangedListener(buildSelectionChangedListener());
	}

	/**
	 * Populates the combo by re-adding all the items.
	 */
	private void populateCombo() {

		removeAll();
		comboViewer.add(buildChoices());
		updateSelection();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);
		this.updateSelection();
	}

	/**
	 * Removes all the items from the combo.
	 */
	abstract void removeAll();

	/**
	 * Requests the given new value be set on the subject.
	 *
	 * @param value The new value to be set
	 */
	protected abstract void setValue(V value);

	/**
	 * Updates the cursor, which is required to show the entire selected item
	 * within the combo's area.
	 */
	abstract void updateCursor();

	/**
	 * Updates the combo's selected item.
	 */
	private void updateSelection() {
		comboViewer.setSelection(buildSelection());
		updateCursor();
	}

	/**
	 * The selection changes, notify the subclass to set the value.
	 *
	 * @param value The new selected item
	 */
	@SuppressWarnings("unchecked")
	private void valueChanged(Object value) {

		// Convert the default "null" value to a real null
		if (value == NULL_VALUE) {
			value = null;
		}

		setPopulating(true);

		try {
			setValue((V) value);
		}
		finally {
			setPopulating(false);
		}
	}
}
