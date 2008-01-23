/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.internal.model.Model;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | | I                                                                   |v| |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see ColumnComposite
 * @see EnumTypeComposite
 * @see FetchTypeComposite
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public abstract class EnumComboViewer<T extends Model, V> extends BaseJpaController<T>
{
	private ComboViewer comboViewer;

	/**
	 * A constant used to represent the <code>null</code> value.
	 */
	private static final String NULL_VALUE = "null";

	/**
	 * Creates a new <code>EnumComboViewer</code>.
	 *
	 * @param parentController The parent container of this one
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumComboViewer(BaseJpaController<? extends T> parentController,
	                          Composite parent) {

		super(parentController, parent);
	}

	/**
	 * Creates a new <code>EnumComboViewer</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various widgets
	 */
	protected EnumComboViewer(PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          TabbedPropertySheetWidgetFactory widgetFactory) {

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
		Object[] extendedChoices;

		if (subject() != null) {
			extendedChoices = new Object[choices.length + 1];
			System.arraycopy(choices, 0, extendedChoices, 1, choices.length);
			extendedChoices[0] = NULL_VALUE;
		}
		else {
			extendedChoices = choices();
		}

		Arrays.sort(extendedChoices, buildComparator());
		return extendedChoices;
	}

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

		StringBuilder sb = new StringBuilder();
		sb.append(ClassTools.shortNameFor(compositeClass));
		sb.append("_");
		sb.append(value.toString().toLowerCase());

		return (String) ClassTools.getStaticFieldValue(nlsClass, sb.toString());
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

		return this.buildDisplayString(nlsClass, composite.getClass(), value);
	}

	@SuppressWarnings("unchecked")
	private LabelProvider buildLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {

				if (element == NULL_VALUE) {
					V defaultValue = (subject() != null) ? defaultValue() : null;

					if (defaultValue != null) {
						String displayString = displayString(defaultValue);
						return NLS.bind(JptUiMappingsMessages.EnumComboViewer_defaultWithDefault, displayString);
					}
					else {
						return JptUiMappingsMessages.EnumComboViewer_default;
					}
				}

				return displayString((V) element);
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

	@SuppressWarnings("unchecked")
	private ISelectionChangedListener buildSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent e) {

				if (isPopulating()) {
					return;
				}

				StructuredSelection selection = (StructuredSelection) e.getSelection();
				Object value = selection.getFirstElement();

				// Convert the default "null" value to a real null
				if (value == NULL_VALUE) {
					value = null;
				}

				EnumComboViewer.this.setValue((V) value);
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

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);

		if (!this.comboViewer.getCCombo().isDisposed()) {
			this.comboViewer.getCCombo().setEnabled(enabled);
		}
	}

	protected final CCombo getCombo() {
		return this.comboViewer.getCCombo();
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
	protected void initializeLayout(Composite container) {

		this.comboViewer = buildComboViewer(container, buildLabelProvider());
		this.comboViewer.addSelectionChangedListener(buildSelectionChangedListener());
	}

	private void populateCombo() {
		this.getCombo().removeAll();
		this.comboViewer.add(this.buildChoices());
		this.comboViewer.setSelection(this.buildSelection());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);
		this.comboViewer.setSelection(this.buildSelection());
	}

	/**
	 * Returns the property name used to listen for changes of the value when it
	 * is done outside of this viewer.
	 *
	 * @return The property name associated with the value being shown by this
	 * viewer
	 */
	protected abstract String propertyName();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected String[] propertyNames() {
		return new String[] { this.propertyName() };
	}

	/**
	 * Requests the given new value be set on the subject.
	 *
	 * @param value The new value to be set
	 */
	protected abstract void setValue(V value);
}