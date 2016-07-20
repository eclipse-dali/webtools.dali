/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ComboTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * This is a replacement for a Spinner.  It is a combo that only accepts integers.
 * It also includes a Default option in the combo.
 */
public abstract class IntegerCombo<T extends Model>
	extends Pane<T>
{
	/**
	 * The main (only) widget of this pane.
	 */
	private Combo comboBox;

	private PropertyValueModel<String> defaultValueModel;
	

	protected IntegerCombo(Pane<? extends T> parentPane, Composite parent) {
		super(parentPane, parent);
	}

	protected IntegerCombo(
		Pane<? extends T> parentPane,
		Composite parent,
		PropertyValueModel<Boolean> enabledModel
	) {
		super(parentPane, parent, enabledModel);
	}

	protected IntegerCombo(
						Pane<?> parentPane,
						PropertyValueModel<? extends T> subjectHolder,
						Composite parent
	) {
		super(parentPane, subjectHolder, parent);
	}

	protected IntegerCombo(
						Pane<?> parentPane,
						PropertyValueModel<? extends T> subjectHolder,
						PropertyValueModel<Boolean> enabledModel,
						Composite parent
	) {
		super(parentPane, subjectHolder, enabledModel, parent);
	}


	@Override
	protected boolean addsComposite() {
		return false;
	}

	@Override
	public Combo getControl() {
		return this.comboBox;
	}

	@Override
	protected void initializeLayout(Composite container) {
		this.defaultValueModel = this.buildDefaultStringModel();
		this.comboBox = this.addIntegerCombo(container);

		int margin = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData.horizontalIndent = margin;
		gridData.grabExcessHorizontalSpace = false;
		this.comboBox.setLayoutData(gridData);
		
		this.comboBox.addVerifyListener(this.buildVerifyListener());
		ComboTools.handleDefaultValue(this.comboBox);
	}
	
	protected Combo addIntegerCombo(Composite container) {
		return this.addEditableCombo(
				container,
				buildDefaultListModel(),
				buildSelectedItemStringModel(),
				TransformerTools.<String>objectToStringTransformer(),
				getHelpId()
			);
		
	}
	
	protected VerifyListener buildVerifyListener() {
		return new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				IntegerCombo.this.verifyComboBox(e);
			}
		};
	}

	protected ListValueModel<String> buildDefaultListModel() {
		return new PropertyListValueModelAdapter<>(this.defaultValueModel);
	}
	
	private PropertyValueModel<String> buildDefaultStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultModel(), new DefaultStringTransformer());
	}

	class DefaultStringTransformer
		extends TransformerAdapter<Integer, String>
	{
		@Override
		public String transform(Integer integer) {
			return (integer == null) ? JptCommonUiMessages.NONE_SELECTED : IntegerCombo.this.getDefaultValueString(integer);
		}
	}
	
	/* CU private */ String getDefaultValueString(Integer defaultValue) {
		return NLS.bind(JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM, defaultValue);
	}
	
	/* CU private */ String getDefaultValueString() {
		return this.defaultValueModel.getValue();
	}

	protected ModifiablePropertyValueModel<String> buildSelectedItemStringModel() {
		return PropertyValueModelTools.transform_(this.buildSelectedItemModel(), new SelectedItemStringModelGetTransformer(), new SelectedItemStringModelSetTransformer());
	}

	public class SelectedItemStringModelGetTransformer
		extends TransformerAdapter<Integer, String>
	{
		@Override
		public String transform(Integer integer) {
			return (integer == null) ? IntegerCombo.this.getDefaultValueString() : integer.toString();
		}
	}

	public class SelectedItemStringModelSetTransformer
		extends TransformerAdapter<String, Integer>
	{
		@Override
		public Integer transform(String string) {
			try {
				return Integer.valueOf(string);
			} catch (NumberFormatException ex) {
				// if the default is selected from the combo, set length to null
				return null;
			}
		}
	}

	// ********** abstract methods **********
	
	protected abstract String getHelpId();

	protected abstract PropertyValueModel<Integer> buildDefaultModel();
	
	protected abstract ModifiablePropertyValueModel<Integer> buildSelectedItemModel();

	// ********** combo-box verify listener callback **********

	protected void verifyComboBox(VerifyEvent e) {
		if (e.character == '\b') {
			return; // ignore backspace
		}
		if (e.text.equals("") // DefaultValueHandler sets the text to "" //$NON-NLS-1$
				|| e.text.equals(this.defaultValueModel.getValue())) { 
			return;
		}
		try {
			Integer.parseInt(e.text);
		} catch (NumberFormatException ex) {
			e.doit = false;
		}
	}
}
