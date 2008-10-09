/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.mappings.details;

import java.util.Set;
import org.eclipse.jpt.eclipselink.core.context.ConversionValue;
import org.eclipse.jpt.eclipselink.core.context.ObjectTypeConverter;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
public class ConversionValueDialog extends ValidatingDialog<ConversionValueStateObject> {

	/**
	 * when creating a new EclipseLinkConversionValue, 'conversionValue' will be null
	 */
	private final ConversionValue conversionValue;

	private ObjectTypeConverter objectTypeConverter;
	
	// ********** constructors **********

	/**
	 * Use this constructor to create a new conversion value
	 */
	public ConversionValueDialog(Shell parent, ObjectTypeConverter objectTypeConverter) {
		this(parent,objectTypeConverter, null);
	}

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public ConversionValueDialog(Shell parent, ObjectTypeConverter objectTypeConverter, ConversionValue conversionValue) {
		super(parent);
		this.objectTypeConverter = objectTypeConverter;
		this.conversionValue = conversionValue;
	}

	@Override
	protected ConversionValueStateObject buildStateObject() {
		String dataValue = null;
		String objectValue = null;
		Set<String> dataValues = CollectionTools.set(this.objectTypeConverter.dataValues());
		if (isEditDialog()) {
			dataValue = this.conversionValue.getDataValue();
			objectValue = this.conversionValue.getObjectValue();
			//remove *this* dataValue, don't want a duplicate data value error
			dataValues.remove(dataValue);
		}
		return new ConversionValueStateObject(
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
						EclipseLinkUiMappingsMessages.ConversionValueDialog_addConversionValue
					:
						EclipseLinkUiMappingsMessages.ConversionValueDialog_editConversionValue;
	}

	@Override
	protected String getDescriptionTitle() {
		return (this.isAddDialog()) ?
			EclipseLinkUiMappingsMessages.ConversionValueDialog_addConversionValueDescriptionTitle
		:
			EclipseLinkUiMappingsMessages.ConversionValueDialog_editConversionValueDescriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return (this.isAddDialog()) ?
			EclipseLinkUiMappingsMessages.ConversionValueDialog_addConversionValueDescription
		:
			EclipseLinkUiMappingsMessages.ConversionValueDialog_editConversionValueDescription;
	}
	
	@Override
	protected DialogPane<ConversionValueStateObject> buildLayout(Composite container) {
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
	
	private class ConversionValueDialogPane extends DialogPane<ConversionValueStateObject> {

		private Text dataValueText;
		private Text objectValueText;

		ConversionValueDialogPane(Composite parent) {
			super(ConversionValueDialog.this.getSubjectHolder(), parent);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.dataValueText = addLabeledText(
				container,
				EclipseLinkUiMappingsMessages.ConversionValueDialog_dataValue,
				buildDataValueHolder()
			);
			
			this.objectValueText = addLabeledText(
				container,
				EclipseLinkUiMappingsMessages.ConversionValueDialog_objectValue,
				buildObjectValueHolder()
			);
		}

		private WritablePropertyValueModel<String> buildDataValueHolder() {
			return new PropertyAspectAdapter<ConversionValueStateObject, String>(getSubjectHolder(), ConversionValueStateObject.DATA_VALUE_PROPERTY) {
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

		private WritablePropertyValueModel<String> buildObjectValueHolder() {
			return new PropertyAspectAdapter<ConversionValueStateObject, String>(getSubjectHolder(), ConversionValueStateObject.OBJECT_VALUE_PROPERTY) {
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
