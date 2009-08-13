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

import org.eclipse.jpt.utility.internal.BitTools;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.iterables.ArrayIterable;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyCollectionValueModelAdapter;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritableCollectionValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Widget;

/**
 * Various SWT tools.
 */
@SuppressWarnings("nls")
public final class SWTTools {

	// ********** check-box/radio button/toggle button **********

	/**
	 * Bind the specified button (check-box, radio button, or toggle button)
	 * to the specified boolean.
	 * If the boolean is <code>null<code>, the button's 'selection' state will
	 * be <code>false<code>.
	 */
	public static void bind(WritablePropertyValueModel<Boolean> booleanHolder, Button button) {
		bind(booleanHolder, button, false);
	}

	/**
	 * Bind the specified button (check-box, radio button, or toggle button)
	 * to the specified boolean.
	 * If the boolean is <code>null<code>, the button's 'selection' state will
	 * be the specified default value.
	 */
	public static void bind(WritablePropertyValueModel<Boolean> booleanHolder, Button button, boolean defaultValue) {
		// the new binding will add itself as a listener to the value model and the button
		new BooleanButtonModelBinding(booleanHolder, button, defaultValue);
	}


	// ********** list box **********

	/**
	 * Bind the specified model list to the specified list box.
	 * The list box selection is ignored.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, List listBox) {
		bind(listHolder, listBox, StringConverter.Default.<E>instance());
	}

	/**
	 * Bind the specified model list to the specified list box.
	 * The list box selection is ignored.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, List listBox, StringConverter<E> stringConverter) {
		bind(listHolder, new SWTListAdapter(listBox), stringConverter);
	}

	/**
	 * Bind the specified model list and selection to the specified list box.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritablePropertyValueModel<E> selectedItemHolder, List listBox) {
		bind(listHolder, selectedItemHolder, listBox, StringConverter.Default.<E>instance());
	}

	/**
	 * Adapt the specified model list and selection to the specified list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritablePropertyValueModel<E> selectedItemHolder, List listBox, StringConverter<E> stringConverter) {
		checkForSingleSelectionStyle(listBox);
		bind(listHolder, new WritablePropertyCollectionValueModelAdapter<E>(selectedItemHolder), listBox, stringConverter);
	}

	/**
	 * Bind the specified model list and selections to the specified list box.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritableCollectionValueModel<E> selectedItemsHolder, List listBox) {
		bind(listHolder, selectedItemsHolder, listBox, StringConverter.Default.<E>instance());
	}

	/**
	 * Bind the specified model list and selections to the specified list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritableCollectionValueModel<E> selectedItemsHolder, List listBox, StringConverter<E> stringConverter) {
		bind(
				listHolder,
				new SWTListAdapter(listBox),
				stringConverter,
				new ListBoxSelectionBinding<E>(listHolder, selectedItemsHolder, listBox)
		);
	}

	private static void checkForSingleSelectionStyle(List listBox) {
		if ( ! BitTools.flagIsSet(listBox.getStyle(), SWT.SINGLE)) {
			throw new IllegalStateException("list box must be single-selection: " + listBox);
		}
	}


	// ********** drop-down list box **********

	/**
	 * Bind the specified model list and selection to the specified drop-down list box.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the drop-down list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritablePropertyValueModel<E> selectedItemHolder, Combo dropDownListBox) {
		bind(listHolder, selectedItemHolder, dropDownListBox, StringConverter.Default.<E>instance());
	}

	/**
	 * Adapt the specified model list and selection to the specified drop-down list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the drop-down list box.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritablePropertyValueModel<E> selectedItemHolder, Combo dropDownListBox, StringConverter<E> stringConverter) {
		checkForReadOnlyStyle(dropDownListBox);
		SWTComboAdapter comboAdapter = new SWTComboAdapter(dropDownListBox);
		bind(
				listHolder,
				comboAdapter,
				stringConverter,
				new DropDownListBoxSelectionBinding<E>(listHolder, selectedItemHolder, comboAdapter)
		);
	}

	/**
	 * Bind the specified model list and selection to the specified drop-down list box.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the drop-down list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritablePropertyValueModel<E> selectedItemHolder, CCombo dropDownListBox) {
		bind(listHolder, selectedItemHolder, dropDownListBox, StringConverter.Default.<E>instance());
	}

	/**
	 * Adapt the specified model list and selection to the specified drop-down list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the drop-down list box.
	 */
	public static <E> void bind(ListValueModel<E> listHolder, WritablePropertyValueModel<E> selectedItemHolder, CCombo dropDownListBox, StringConverter<E> stringConverter) {
		checkForReadOnlyStyle(dropDownListBox);
		SWTCComboAdapter comboAdapter = new SWTCComboAdapter(dropDownListBox);
		bind(
				listHolder,
				comboAdapter,
				stringConverter,
				new DropDownListBoxSelectionBinding<E>(listHolder, selectedItemHolder, comboAdapter)
		);
	}

	private static void checkForReadOnlyStyle(Widget comboBox) {
		if ( ! BitTools.flagIsSet(comboBox.getStyle(), SWT.READ_ONLY)) {
			throw new IllegalStateException("combo-box must be read-only: " + comboBox);
		}
	}


	// ********** list "widget" **********

	/**
	 * Bind the specified model list to the specified list widget.
	 * The list widget's selection is ignored.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	private static <E> void bind(ListValueModel<E> listHolder, ListWidgetModelBinding.ListWidget listWidget, StringConverter<E> stringConverter) {
		bind(listHolder, listWidget, stringConverter, ListWidgetModelBinding.SelectionBinding.Null.instance());
	}

	/**
	 * Bind the specified model list to the specified list widget.
	 * Use the specified selection binding to control the list widget's selection.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	private static <E> void bind(ListValueModel<E> listHolder, ListWidgetModelBinding.ListWidget listWidget, StringConverter<E> stringConverter, ListWidgetModelBinding.SelectionBinding selectionBinding) {
		// the new binding will add itself as a listener to the value models and the list box
		new ListWidgetModelBinding<E>(listHolder, listWidget, stringConverter, selectionBinding);
	}


	// ********** 'enabled' state **********

	/**
	 * Control the 'enabled' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'enabled' state will
	 * be <code>false<code>.
	 */
	public static void controlEnabledState(PropertyValueModel<Boolean> booleanHolder, Control... controls) {
		controlEnabledState(booleanHolder, controls, false);
	}

	/**
	 * Control the 'enabled' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'enabled' state will
	 * be the specified default value.
	 */
	public static void controlEnabledState(PropertyValueModel<Boolean> booleanHolder, Control[] controls, boolean defaultValue) {
		controlEnabledState(booleanHolder, new ArrayIterable<Control>(controls), defaultValue);
	}

	/**
	 * Control the 'enabled' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'enabled' state will
	 * be <code>false<code>.
	 */
	public static void controlEnabledState(PropertyValueModel<Boolean> booleanHolder, Iterable<? extends Control> controls) {
		controlEnabledState(booleanHolder, controls, false);
	}

	/**
	 * Control the 'enabled' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'enabled' state will
	 * be the specified default value.
	 */
	public static void controlEnabledState(PropertyValueModel<Boolean> booleanHolder, Iterable<? extends Control> controls, boolean defaultValue) {
		control(booleanHolder, controls, defaultValue, ENABLED_ADAPTER);
	}

	private static final BooleanStateController.Adapter ENABLED_ADAPTER =
			new BooleanStateController.Adapter() {
				public void setState(Control control, boolean b) {
					control.setEnabled(b);
				}
			};
   

	// ********** 'visible' state **********

	/**
	 * Control the 'visible' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'visible' state will
	 * be <code>false<code>.
	 */
	public static void controlVisibleState(PropertyValueModel<Boolean> booleanHolder, Control... controls) {
		controlVisibleState(booleanHolder, controls, false);
	}

	/**
	 * Control the 'visible' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'visible' state will
	 * be the specified default value.
	 */
	public static void controlVisibleState(PropertyValueModel<Boolean> booleanHolder, Control[] controls, boolean defaultValue) {
		controlVisibleState(booleanHolder, new ArrayIterable<Control>(controls), defaultValue);
	}

	/**
	 * Control the 'visible' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'visible' state will
	 * be <code>false<code>.
	 */
	public static void controlVisibleState(PropertyValueModel<Boolean> booleanHolder, Iterable<? extends Control> controls) {
		controlVisibleState(booleanHolder, controls, false);
	}

	/**
	 * Control the 'visible' state of the specified controls with the specified
	 * boolean. If the boolean is <code>null<code>, the button's 'visible' state will
	 * be the specified default value.
	 */
	public static void controlVisibleState(PropertyValueModel<Boolean> booleanHolder, Iterable<? extends Control> controls, boolean defaultValue) {
		control(booleanHolder, controls, defaultValue, VISIBLE_ADAPTER);
	}

	private static final BooleanStateController.Adapter VISIBLE_ADAPTER =
			new BooleanStateController.Adapter() {
				public void setState(Control control, boolean b) {
					control.setVisible(b);
				}
			};
   

	// ********** boolean state controller **********

	private static void control(PropertyValueModel<Boolean> booleanHolder, Iterable<? extends Control> controls, boolean defaultValue, BooleanStateController.Adapter adapter) {
		// the new controller will add itself as a listener to the value model and the controls
		new BooleanStateController(booleanHolder, controls, defaultValue, adapter);
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private SWTTools() {
		super();
		throw new UnsupportedOperationException();
	}

}
