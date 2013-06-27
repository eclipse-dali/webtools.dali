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

import java.util.Arrays;
import org.eclipse.jpt.common.utility.internal.BitTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.ModifiablePropertyCollectionValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.NullPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.StaticCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Caret;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.forms.widgets.Form;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.forms.widgets.ImageHyperlink;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.PageBook;

/**
 * Various SWT binding tools.
 */
@SuppressWarnings("nls")
public final class SWTBindingTools {

	// ********** "label" **********

	/**
	 * Bind the specified button's label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, Button button) {
		bindImage(imageModel, new ButtonLabelAdapter(button));
	}

	/**
	 * Bind the specified button's label to the specified text model.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, Button button) {
		bindText(textModel, new ButtonLabelAdapter(button));
	}

	/**
	 * Bind the specified button's label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, Button button) {
		bind(imageModel, textModel, new ButtonLabelAdapter(button));
	}

	/**
	 * Bind the specified "custom" label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, CLabel label) {
		bindImage(imageModel, new CLabelLabelAdapter(label));
	}

	/**
	 * Bind the specified "custom" label to the specified text model.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, CLabel label) {
		bindText(textModel, new CLabelLabelAdapter(label));
	}

	/**
	 * Bind the specified "custom" label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, CLabel label) {
		bind(imageModel, textModel, new CLabelLabelAdapter(label));
	}

	/**
	 * Bind the specified form's label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, Form form) {
		bindImage(imageModel, new FormLabelAdapter(form));
	}

	/**
	 * Bind the specified form's label to the specified text model.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, Form form) {
		bindText(textModel, new FormLabelAdapter(form));
	}

	/**
	 * Bind the specified form's label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, Form form) {
		bind(imageModel, textModel, new FormLabelAdapter(form));
	}

	/**
	 * Bind the specified hyperlink's label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, ImageHyperlink hyperlink) {
		bindImage(imageModel, new ImageHyperlinkLabelAdapter(hyperlink));
	}

	/**
	 * Bind the specified hyperlink's label to the specified text model.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, ImageHyperlink hyperlink) {
		bindText(textModel, new ImageHyperlinkLabelAdapter(hyperlink));
	}

	/**
	 * Bind the specified hyperlink's label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, ImageHyperlink hyperlink) {
		bind(imageModel, textModel, new ImageHyperlinkLabelAdapter(hyperlink));
	}

	/**
	 * Bind the specified label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, Label label) {
		bindImage(imageModel, new LabelLabelAdapter(label));
	}

	/**
	 * Bind the specified label to the specified text model.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, Label label) {
		bindText(textModel, new LabelLabelAdapter(label));
	}

	/**
	 * Bind the specified label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, Label label) {
		bind(imageModel, textModel, new LabelLabelAdapter(label));
	}

	/**
	 * Bind the specified form's label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, ScrolledForm form) {
		bindImage(imageModel, new ScrolledFormLabelAdapter(form));
	}

	/**
	 * Bind the specified form's label to the specified text models.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, ScrolledForm form) {
		bindText(textModel, new ScrolledFormLabelAdapter(form));
	}

	/**
	 * Bind the specified form's label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, ScrolledForm form) {
		bind(imageModel, textModel, new ScrolledFormLabelAdapter(form));
	}

	/**
	 * Bind the specified item's label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, Item item) {
		bindImage(imageModel, new ItemLabelAdapter(item));
	}

	/**
	 * Bind the specified item's label to the specified text model.
	 */
	public static void bindTextLabel(PropertyValueModel<String> textModel, Item item) {
		bindText(textModel, new ItemLabelAdapter(item));
	}

	/**
	 * Bind the specified item's label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, Item item) {
		bind(imageModel, textModel, new ItemLabelAdapter(item));
	}

	/**
	 * Bind the specified window's label to the specified image model.
	 */
	public static void bindImageLabel(PropertyValueModel<Image> imageModel, Shell window) {
		bindImage(imageModel, new ShellLabelAdapter(window));
	}

	/**
	 * Bind the specified window's label to the specified text model.
	 */
	public static void bindLabel(PropertyValueModel<String> textModel, Shell window) {
		bindText(textModel, new ShellLabelAdapter(window));
	}

	/**
	 * Bind the specified window's label to the specified image and
	 * text models.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, Shell window) {
		bind(imageModel, textModel, new ShellLabelAdapter(window));
	}

	/**
	 * Bind the specified caret to the specified image model.
	 */
	public static void bindLabel(PropertyValueModel<Image> imageModel, Caret caret) {
		bindImage(imageModel, new CaretLabelAdapter(caret));
	}

	/**
	 * Bind the specified group's text to the specified text model.
	 */
	public static void bindLabel(PropertyValueModel<String> textModel, Group group) {
		bindText(textModel, new GroupLabelAdapter(group));
	}

	/**
	 * Bind the specified hyperlink's text to the specified text model.
	 */
	public static void bindLabel(PropertyValueModel<String> textModel, Hyperlink hyperlink) {
		bindText(textModel, new HyperlinkLabelAdapter(hyperlink));
	}

	/**
	 * Bind the specified link's text to the specified text model.
	 */
	public static void bindLabel(PropertyValueModel<String> textModel, Link link) {
		bindText(textModel, new LinkLabelAdapter(link));
	}

	/**
	 * Bind the specified label adapter's widget's label to the specified
	 * image model.
	 */
	@SuppressWarnings("unused")
	public static void bindImage(PropertyValueModel<Image> imageModel, WidgetLabelAdapter labelAdapter) {
		new LabelModelBinding(imageModel, new NullPropertyValueModel<String>(), labelAdapter);
	}

	/**
	 * Bind the specified label adapter's widget's label to the specified
	 * text model.
	 */
	@SuppressWarnings("unused")
	public static void bindText(PropertyValueModel<String> textModel, WidgetLabelAdapter labelAdapter) {
		new LabelModelBinding(new NullPropertyValueModel<Image>(), textModel, labelAdapter);
	}

	/**
	 * Bind the specified label adapter's widget's label to the specified
	 * image and text models.
	 */
	@SuppressWarnings("unused")
	public static void bind(PropertyValueModel<Image> imageModel, PropertyValueModel<String> textModel, WidgetLabelAdapter labelAdapter) {
		new LabelModelBinding(imageModel, textModel, labelAdapter);
	}


	// ********** check-box/radio button/toggle button **********

	/**
	 * Bind the specified button (check-box, radio button, or toggle button)
	 * to the specified boolean model.
	 * If the boolean model is <code>null<code>, the button's 'selection' state will
	 * be <code>false<code>.
	 */
	public static void bind(ModifiablePropertyValueModel<Boolean> booleanModel, Button button) {
		bind(booleanModel, button, false);
	}

	/**
	 * Bind the specified button (check-box, radio button, or toggle button)
	 * to the specified boolean model.
	 * If the boolean model is <code>null<code>, the button's 'selection' state will
	 * be the specified default value.
	 */
	@SuppressWarnings("unused")
	public static void bind(ModifiablePropertyValueModel<Boolean> booleanModel, Button button, boolean defaultValue) {
		// the new binding will add itself as a listener to the boolean model and the button
		new BooleanButtonModelBinding(booleanModel, button, defaultValue);
	}


	// ********** text field **********

	/**
	 * Bind the specified text model to the specified text field.
	 */
	@SuppressWarnings("unused")
	public static <E> void bind(ModifiablePropertyValueModel<String> textModel, Text textField) {
		// the new binding will add itself as a listener to the text model and the text field
		new TextFieldModelBinding(textModel, textField);
	}


	// ********** list box **********

	/**
	 * Bind the specified model list to the specified list box.
	 * The list box selection is ignored.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listModel, List listBox) {
		bind(listModel, listBox, TransformerTools.<E>objectToStringTransformer());
	}

	/**
	 * Bind the specified model list to the specified list box.
	 * The list box selection is ignored.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static <E> void bind(ListValueModel<E> listModel, List listBox, Transformer<E, String> transformer) {
		bind(listModel, new SimpleCollectionValueModel<E>(), listBox, transformer);
	}

	/**
	 * Bind the specified model list and selection to the specified list box.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listModel, ModifiablePropertyValueModel<E> selectedItemModel, List listBox) {
		bind(listModel, selectedItemModel, listBox, TransformerTools.<E>objectToStringTransformer());
	}

	/**
	 * Adapt the specified model list and selection to the specified list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static <E> void bind(ListValueModel<E> listModel, ModifiablePropertyValueModel<E> selectedItemModel, List listBox, Transformer<E, String> transformer) {
		checkForSingleSelectionStyle(listBox);
		bind(listModel, new ModifiablePropertyCollectionValueModelAdapter<E>(selectedItemModel), listBox, transformer);
	}

	/**
	 * Bind the specified model list and selections to the specified list box.
	 * Use the default string converter to convert the model items to strings
	 * to be displayed in the list box, which calls {@link Object#toString()}
	 * on the items in the model list.
	 */
	public static <E> void bind(ListValueModel<E> listModel, ModifiableCollectionValueModel<E> selectedItemsModel, List listBox) {
		bind(listModel, selectedItemsModel, listBox, TransformerTools.<E>objectToStringTransformer());
	}

	/**
	 * Bind the specified model list and selections to the specified list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	public static <E> void bind(ListValueModel<E> listModel, ModifiableCollectionValueModel<E> selectedItemsModel, List listBox, Transformer<E, String> transformer) {
		bind(
			listModel,
			selectedItemsModel,
			new ListBoxListWidgetAdapter<E>(listBox),
			transformer
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
	 * @see #bindComboBox(ListValueModel, ModifiablePropertyValueModel, Combo)
	 */
	public static <E> void bindDropDownListBox(ListValueModel<E> listModel, ModifiablePropertyValueModel<E> selectedItemModel, Combo dropDownListBox) {
		bindDropDownListBox(listModel, selectedItemModel, dropDownListBox, TransformerTools.<E>objectToStringTransformer());
	}

	/**
	 * Adapt the specified model list and selection to the specified drop-down list box.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the drop-down list box.
	 * @see #bindComboBox(ListValueModel, ModifiablePropertyValueModel, Combo)
	 */
	public static <E> void bindDropDownListBox(ListValueModel<E> listModel, ModifiablePropertyValueModel<E> selectedItemModel, Combo dropDownListBox, Transformer<E, String> transformer) {
		checkForReadOnlyStyle(dropDownListBox);
		bind(
			listModel,
			selectedItemModel,
			new DropDownListBoxListWidgetAdapter<E>(dropDownListBox),
			transformer
		);
	}

	private static void checkForReadOnlyStyle(Widget comboBox) {
		if ( ! BitTools.flagIsSet(comboBox.getStyle(), SWT.READ_ONLY)) {
			throw new IllegalStateException("combo-box must be read-only: " + comboBox);
		}
	}


	// ********** combo-box **********

	/**
	 * Bind the specified model list of strings and selection to the specified
	 * combo-box. None of the items in the model list can be <code>null</code>.
	 * @see #bindDropDownListBox(ListValueModel, ModifiablePropertyValueModel, Combo)
	 * @see #bindDropDownListBox(ListValueModel, ModifiablePropertyValueModel, Combo, Transformer)
	 */
	public static void bindComboBox(ListValueModel<String> listModel, ModifiablePropertyValueModel<String> valueModel, Combo comboBox) {
		checkForReadWriteStyle(comboBox);
		bind(
			listModel,
			valueModel,
			new ComboBoxListWidgetAdapter(comboBox),
			TransformerTools.<String>passThruTransformer()
		);
	}

	private static void checkForReadWriteStyle(Widget comboBox) {
		if (BitTools.flagIsSet(comboBox.getStyle(), SWT.READ_ONLY)) {
			throw new IllegalStateException("combo-box must be read-write: " + comboBox);
		}
	}


	// ********** list "widget" **********

	/**
	 * Bind the specified model list and selectedions to the specified list widget.
	 * Use the specified string converter to convert the model items to strings
	 * to be displayed in the list box.
	 */
	@SuppressWarnings("unused")
	private static <E> void bind(ListValueModel<E> listModel, Object selectionModel, ListWidgetModelBinding.ListWidget<E> listWidget, Transformer<E, String> transformer) {
		// the new binding will add itself as a listener to the value models and the list box
		new ListWidgetModelBinding<E>(listModel, selectionModel, listWidget, transformer);
	}


	// ********** page book **********

	/**
	 * Bind the specified control model to the specified page book. The model's
	 * value will determine which page the page book displays.
	 */
	public static void bind(PropertyValueModel<Control> controlModel, PageBook pageBook) {
		bind(controlModel, pageBook, null);
	}

	/**
	 * Bind the specified control model to the specified page book. The model's
	 * value will determine which page the page book displays. If the model's
	 * value is <code>null</code>, the page book will display the specified
	 * default page.
	 */
	public static void bind(PropertyValueModel<Control> controlModel, PageBook pageBook, Control defaultPage) {
		bind(controlModel, TransformerTools.<Control>passThruTransformer(), pageBook, defaultPage);
	}

	/**
	 * Bind the specified model to the specified page book. The model's value will
	 * determine which page the page book displays. The specified transformer
	 * will convert the model's value into the control to be displayed in the
	 * page book.
	 */
	public static <T> void bind(PropertyValueModel<T> valueModel, Transformer<? super T, Control> transformer, PageBook pageBook) {
		bind(valueModel, transformer, pageBook, null);
	}

	/**
	 * Bind the specified model to the specified page book. The model's value will
	 * determine which page the page book displays. The specified transformer
	 * will convert the model's value into the control to be displayed in the
	 * page book. If the transformer converts the model's value into
	 * <code>null</code>, the page book will display the specified default
	 * page.
	 */
	@SuppressWarnings("unused")
	public static <T> void bind(PropertyValueModel<T> valueModel, Transformer<? super T, Control> transformer, PageBook pageBook, Control defaultPage) {
		new PageBookModelBinding<T>(valueModel, transformer, pageBook, defaultPage);
	}


	// ********** 'enabled' state **********

	/**
	 * Bind the <em>enabled</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>enabled</em> states will be <code>false<code>.
	 */
	public static void bindEnabledState(PropertyValueModel<Boolean> booleanModel, Control... controls) {
		bindEnabledState(booleanModel, controls, false);
	}

	/**
	 * Bind the <em>enabled</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>enabled</em> states will be the specified default value.
	 */
	public static void bindEnabledState(PropertyValueModel<Boolean> booleanModel, Control[] controls, boolean defaultValue) {
		switch (controls.length) {
			case 0:
				throw new IllegalArgumentException("empty controls array: " + Arrays.toString(controls));
			case 1:
				bindEnabledState(booleanModel, controls[0], defaultValue);
				break;
			default:
				bindEnabledState(booleanModel, new StaticCollectionValueModel<Control>(controls), defaultValue);
				break;
		}
	}

	/**
	 * Bind the <em>enabled</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>enabled</em> states will be <code>false<code>.
	 */
	public static void bindEnabledState(PropertyValueModel<Boolean> booleanModel, Iterable<? extends Control> controls) {
		bindEnabledState(booleanModel, controls, false);
	}

	/**
	 * Bind the <em>enabled</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>enabled</em> states will be the specified default value.
	 */
	public static <C extends Control> void bindEnabledState(PropertyValueModel<Boolean> booleanModel, Iterable<C> controls, boolean defaultValue) {
		BooleanControlStateModelBinding.Adapter<C> adapter = enabledAdapter();
		bindState(booleanModel, controls, defaultValue, adapter);
	}

	/**
	 * Bind the <em>enabled</em> state of the specified control with the
	 * specified boolean. If the boolean is <code>null<code>, the control's
	 * <em>enabled</em> state will be the specified default value.
	 */
	public static void bindEnabledState(PropertyValueModel<Boolean> booleanModel, Control control, boolean defaultValue) {
		bindState(booleanModel, control, defaultValue, enabledAdapter());
	}

	@SuppressWarnings("unchecked")
	private static <C extends Control> BooleanControlStateModelBinding.Adapter<C> enabledAdapter() {
		return ENABLED_ADAPTER;
	}
	@SuppressWarnings("rawtypes")
	private static final BooleanControlStateModelBinding.Adapter ENABLED_ADAPTER = new EnabledAdapter();
	/** CU private */ static class EnabledAdapter<C extends Control>
		implements BooleanControlStateModelBinding.Adapter<C>
	{
		public void setState(Control control, boolean b) {
			control.setEnabled(b);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}
   

	// ********** 'visible' state **********

	/**
	 * Bind the <em>visible</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>visible</em> states will be <code>false<code>.
	 */
	public static void bindVisibleState(PropertyValueModel<Boolean> booleanModel, Control... controls) {
		bindVisibleState(booleanModel, controls, false);
	}

	/**
	 * Bind the <em>visible</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>visible</em> states will be the specified default value.
	 */
	public static void bindVisibleState(PropertyValueModel<Boolean> booleanModel, Control[] controls, boolean defaultValue) {
		switch (controls.length) {
			case 0:
				throw new IllegalArgumentException("empty controls array: " + Arrays.toString(controls));
			case 1:
				bindVisibleState(booleanModel, controls[0], defaultValue);
				break;
			default:
				bindVisibleState(booleanModel, new StaticCollectionValueModel<Control>(controls), defaultValue);
				break;
		}
	}

	/**
	 * Bind the <em>visible</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>visible</em> states will be <code>false<code>.
	 */
	public static void bindVisibleState(PropertyValueModel<Boolean> booleanModel, Iterable<? extends Control> controls) {
		bindVisibleState(booleanModel, controls, false);
	}

	/**
	 * Bind the <em>visible</em> state of the specified controls with the
	 * specified boolean. If the boolean is <code>null<code>, the controls'
	 * <em>visible</em> states will be the specified default value.
	 */
	public static <C extends Control> void bindVisibleState(PropertyValueModel<Boolean> booleanModel, Iterable<C> controls, boolean defaultValue) {
		BooleanControlStateModelBinding.Adapter<C> adapter = visibleAdapter();
		bindState(booleanModel, controls, defaultValue, adapter);
	}

	/**
	 * Bind the <em>visible</em> state of the specified control with the
	 * specified boolean. If the boolean is <code>null<code>, the control's
	 * <em>visible</em> state will be the specified default value.
	 */
	public static void bindVisibleState(PropertyValueModel<Boolean> booleanModel, Control control, boolean defaultValue) {
		bindState(booleanModel, control, defaultValue, visibleAdapter());
	}

	@SuppressWarnings("unchecked")
	private static <C extends Control> BooleanControlStateModelBinding.Adapter<C> visibleAdapter() {
		return VISIBLE_ADAPTER;
	}
	@SuppressWarnings("rawtypes")
	private static final BooleanControlStateModelBinding.Adapter VISIBLE_ADAPTER = new VisibleAdapter();
  	/** CU private */ static class VisibleAdapter<C extends Control>
		implements BooleanControlStateModelBinding.Adapter<C>
	{
		public void setState(C control, boolean b) {
			control.setVisible(b);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}


	// ********** boolean state **********

	@SuppressWarnings("unused")
	private static <C extends Control> void bindState(PropertyValueModel<Boolean> booleanModel, Iterable<C> controls, boolean defaultValue, BooleanControlStateModelBinding.Adapter<C> adapter) {
		// the new binding will add itself as a listener to the value model and the controls
		new CompositeBooleanControlStateModelBinding<C>(booleanModel, controls, defaultValue, adapter);
	}

	@SuppressWarnings("unused")
	private static <C extends Control> void bindState(PropertyValueModel<Boolean> booleanModel, C control, boolean defaultValue, BooleanControlStateModelBinding.Adapter<C> adapter) {
		// the new binding will add itself as a listener to the value model and the controls
		new SimpleBooleanControlStateModelBinding<C>(booleanModel, control, defaultValue, adapter);
	}


	// ********** 'expanded' state **********

	/**
	 * Bind the <em>expanded</em> state of the specified section with the
	 * specified boolean model. If the boolean is <code>null<code>, the section's
	 * <em>expanded</em> state will be <code>false</code>.
	 */
	public static void bindExpandedState(PropertyValueModel<Boolean> booleanModel, Section section) {
		bindExpandedState(booleanModel, section, false);
	}

	/**
	 * Bind the <em>expanded</em> state of the specified section with the
	 * specified boolean model. If the boolean is <code>null<code>, the section's
	 * <em>expanded</em> state will be the specified default value.
	 */
	public static void bindExpandedState(PropertyValueModel<Boolean> booleanModel, Section section, boolean defaultValue) {
		bindState(booleanModel, section, defaultValue, EXPANDED_ADAPTER);
	}

	private static final BooleanControlStateModelBinding.Adapter<Section> EXPANDED_ADAPTER = new ExpandedAdapter();
	/** CU private */ static class ExpandedAdapter
		implements BooleanControlStateModelBinding.Adapter<Section>
	{
		public void setState(Section section, boolean b) {
			section.setExpanded(b);
		}
		@Override
		public String toString() {
			return ObjectTools.singletonToString(this);
		}
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private SWTBindingTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
