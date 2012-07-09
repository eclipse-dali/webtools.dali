/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Set;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Clients can use this dialog to prompt the user for SecondaryTable settings.
 * Use the following once the dialog is closed:
 *     @see #getSelectedTable()
 *     @see #getSelectedCatalog()
 *     @see #getSelectedSchema()
 * @version 2.1
 * @since 2.1
 */
public class EclipseLinkConversionValueDialog extends ValidatingDialog<EclipseLinkConversionValueStateObject> {

	/**
	 * when creating a new EclipseLinkConversionValue, 'conversionValue' will be null
	 */
	private final EclipseLinkConversionValue conversionValue;

	private EclipseLinkObjectTypeConverter objectTypeConverter;
	
	// ********** constructors **********

	/**
	 * Use this constructor to create a new conversion value
	 */
	public EclipseLinkConversionValueDialog(Shell parent, EclipseLinkObjectTypeConverter objectTypeConverter) {
		this(parent,objectTypeConverter, null);
	}

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public EclipseLinkConversionValueDialog(Shell parent, EclipseLinkObjectTypeConverter objectTypeConverter, EclipseLinkConversionValue conversionValue) {
		super(parent);
		this.objectTypeConverter = objectTypeConverter;
		this.conversionValue = conversionValue;
	}

	@Override
	protected EclipseLinkConversionValueStateObject buildStateObject() {
		String dataValue = null;
		String objectValue = null;
		Set<String> dataValues = CollectionTools.set(this.objectTypeConverter.getDataValues(), this.objectTypeConverter.getDataValuesSize());
		if (isEditDialog()) {
			dataValue = this.conversionValue.getDataValue();
			objectValue = this.conversionValue.getObjectValue();
			//remove *this* dataValue, don't want a duplicate data value error
			dataValues.remove(dataValue);
		}
		return new EclipseLinkConversionValueStateObject(
			dataValue, 
			objectValue, 
			dataValues);
	}

	// ********** open **********

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	@Override
	protected String getTitle() {
		return (this.isAddDialog()) ?
						EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_addConversionValue
					:
						EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_editConversionValue;
	}

	@Override
	protected String getDescriptionTitle() {
		return (this.isAddDialog()) ?
			EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_addConversionValueDescriptionTitle
		:
			EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_editConversionValueDescriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return (this.isAddDialog()) ?
			EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_addConversionValueDescription
		:
			EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_editConversionValueDescription;
	}
	
	@Override
	protected DialogPane<EclipseLinkConversionValueStateObject> buildLayout(Composite container) {
		return new ConversionValueDialogPane(container);
	}
	
	@Override
	public void create() {
		super.create();

		ConversionValueDialogPane pane = (ConversionValueDialogPane) getPane();
		pane.selectAll();

		getButton(OK).setEnabled(false);
	}


	// ********** convenience methods **********

	protected boolean isAddDialog() {
		return this.conversionValue == null;
	}

	protected boolean isEditDialog() {
		return ! this.isAddDialog();
	}


	// ********** public API **********

	/**
	 * Return the data value set in the text widget.
	 */
	public String getDataValue() {
		return getSubject().getDataValue();
	}

	/**
	 * Return the object value set in the text widget.
	 */
	public String getObjectValue() {
		return getSubject().getObjectValue();
	}
	
	private class ConversionValueDialogPane extends DialogPane<EclipseLinkConversionValueStateObject> {

		private Text dataValueText;
		private Text objectValueText;

		ConversionValueDialogPane(Composite parent) {
			super(EclipseLinkConversionValueDialog.this.getSubjectHolder(), parent);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.dataValueText = addLabeledText(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_dataValue,
				buildDataValueHolder()
			);
			
			this.objectValueText = addLabeledText(
				container,
				EclipseLinkUiDetailsMessages.EclipseLinkConversionValueDialog_objectValue,
				buildObjectValueHolder()
			);
		}

		private ModifiablePropertyValueModel<String> buildDataValueHolder() {
			return new PropertyAspectAdapter<EclipseLinkConversionValueStateObject, String>(getSubjectHolder(), EclipseLinkConversionValueStateObject.DATA_VALUE_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getDataValue();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setDataValue(value);
				}
			};
		}

		private ModifiablePropertyValueModel<String> buildObjectValueHolder() {
			return new PropertyAspectAdapter<EclipseLinkConversionValueStateObject, String>(getSubjectHolder(), EclipseLinkConversionValueStateObject.OBJECT_VALUE_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getObjectValue();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setObjectValue(value);
				}
			};
		}

		void selectAll() {
			this.dataValueText.selectAll();
			this.objectValueText.selectAll();
		}
	}
}