/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.swt.widgets.ComboTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
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


	private PropertyValueModel<String> defaultValueHolder;
	
	// ********** constructors **********

	protected IntegerCombo(
						Pane<? extends T> parentPane,
						Composite parent
	) {
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

	// ********** initialization **********

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
		this.defaultValueHolder = this.buildDefaultStringHolder();
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
				buildDefaultListHolder(),
				buildSelectedItemStringHolder(),
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

	protected ListValueModel<String> buildDefaultListHolder() {
		return new PropertyListValueModelAdapter<String>(this.defaultValueHolder);
	}
	
	private PropertyValueModel<String> buildDefaultStringHolder() {
		return new TransformationPropertyValueModel<Integer, String>(buildDefaultHolder()) {
			@Override
			protected String transform(Integer value) {
				if (value == null) {
					return JptCommonUiMessages.NONE_SELECTED;
				}
				return super.transform(value);
			}
			@Override
			protected String transform_(Integer value) {
				return getDefaultValueString(value);
			}
		};
	}
	
	private String getDefaultValueString(Integer defaultValue) {
		return NLS.bind(
				JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
				defaultValue
			);
	}
	
	/* CU private */ String getDefaultValueString() {
		return this.defaultValueHolder.getValue();
	}

	protected ModifiablePropertyValueModel<String> buildSelectedItemStringHolder() {
		return new TransformationModifiablePropertyValueModel<Integer, String>(buildSelectedItemHolder()) {
			@Override
			protected String transform(Integer v) {
				return (v == null) ? getDefaultValueString() : v.toString();
			}
			
			@Override
			protected Integer reverseTransform_(String v) {
				try {
					return Integer.valueOf(v);
				} catch (NumberFormatException ex) {
					// if the default is selected from the combo, set length to null
					return null;
				}
			}
		};
	}

	// ********** abstract methods **********
	
	protected abstract String getHelpId();

	protected abstract PropertyValueModel<Integer> buildDefaultHolder();
	
	protected abstract ModifiablePropertyValueModel<Integer> buildSelectedItemHolder();

	// ********** combo-box verify listener callback **********

	protected void verifyComboBox(VerifyEvent e) {
		if (e.character == '\b') {
			//ignore backspace
			return;
		}
		if (e.text.equals("") //DefaultValueHandler sets the text to "" //$NON-NLS-1$
				|| e.text.equals(this.defaultValueHolder.getValue())) { 
			return;
		}
		try {
			Integer.parseInt(e.text);
		}
		catch (NumberFormatException exception) {
			e.doit = false;
		}
	}
}
