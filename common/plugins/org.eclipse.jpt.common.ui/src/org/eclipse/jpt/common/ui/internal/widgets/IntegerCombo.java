/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationWritablePropertyValueModel;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
						Pane<?> parentPane,
						PropertyValueModel<? extends T> subjectHolder,
						Composite parent
	) {
		super(parentPane, subjectHolder, parent);
	}

	public Combo getCombo() {
		return this.comboBox;
	}
	
	// ********** initialization **********

	@Override
	protected void initializeLayout(Composite container) {
		this.defaultValueHolder = buildDefaultStringHolder();
		this.comboBox = this.addIntegerCombo(container);

		int margin = FieldDecorationRegistry.getDefault().getMaximumDecorationWidth();
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL_HORIZONTAL;
		gridData.horizontalIndent = margin;
		gridData.grabExcessHorizontalSpace = false;
		this.comboBox.setLayoutData(gridData);
		
		this.comboBox.addVerifyListener(this.buildVerifyListener());
		SWTUtil.attachDefaultValueHandler(this.comboBox);
	}
	
	protected Combo addIntegerCombo(Composite container) {
		return this.addLabeledEditableCombo(
				container,
				getLabelText(),
				buildDefaultListHolder(),
				buildSelectedItemStringHolder(),
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
					return JptCommonUiMessages.NoneSelected;
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
				JptCommonUiMessages.DefaultWithOneParam,
				defaultValue
			);
	}
	
	private String getDefaultValueString() {
		return this.defaultValueHolder.getValue();
	}

	protected WritablePropertyValueModel<String> buildSelectedItemStringHolder() {
		return new TransformationWritablePropertyValueModel<Integer, String>(buildSelectedItemHolder()) {
			@Override
			protected String transform(Integer value) {
				return value == null ? 
						getDefaultValueString()
					: 
						value.toString();
			}
			
			@Override
			protected Integer reverseTransform_(String value) {
				int intLength;
				try {
					intLength = Integer.parseInt(value);
				}
				catch (NumberFormatException e) {
					//if the default is selected from the combo, set length to null
					return null;
				}
				return Integer.valueOf(intLength);
			}
		};
	}

	// ********** abstract methods **********
	
	protected abstract String getLabelText();
	
	protected abstract String getHelpId();

	protected abstract PropertyValueModel<Integer> buildDefaultHolder();
	
	protected abstract WritablePropertyValueModel<Integer> buildSelectedItemHolder();

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
