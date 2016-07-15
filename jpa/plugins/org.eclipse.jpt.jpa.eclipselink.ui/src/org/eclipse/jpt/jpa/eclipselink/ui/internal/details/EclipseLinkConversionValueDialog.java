/*******************************************************************************
 * Copyright (c) 2008, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import java.util.Set;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConversionValue;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EclipseLinkConversionValueDialog
	extends ValidatingDialog<EclipseLinkConversionValueStateObject>
{
	private final EclipseLinkObjectTypeConverter objectTypeConverter;
	
	/**
	 * This will be <code>null</code> when creating a new conversion value.
	 */
	private final EclipseLinkConversionValue conversionValue;


	/**
	 * Use this constructor to create a <em>new</em> conversion value.
	 */
	public EclipseLinkConversionValueDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			EclipseLinkObjectTypeConverter objectTypeConverter) {
		this(parentShell, resourceManager, objectTypeConverter, null);
	}

	/**
	 * Use this constructor to edit an <em>existing</em> conversion value.
	 */
	public EclipseLinkConversionValueDialog(
			Shell parentShell,
			ResourceManager resourceManager,
			EclipseLinkObjectTypeConverter objectTypeConverter,
			EclipseLinkConversionValue conversionValue) {
		super(parentShell, resourceManager, buildTitle(conversionValue));
		this.objectTypeConverter = objectTypeConverter;
		this.conversionValue = conversionValue;
	}

	private static String buildTitle(EclipseLinkConversionValue conversionValue) {
		return (conversionValue == null) ?
				JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_ADD_CONVERSION_VALUE :
				JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_EDIT_CONVERSION_VALUE;
	}

	@Override
	protected EclipseLinkConversionValueStateObject buildStateObject() {
		String dataValue = null;
		String objectValue = null;
		Set<String> dataValues = CollectionTools.hashSet(this.objectTypeConverter.getDataValues(), this.objectTypeConverter.getDataValuesSize());
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
	protected String getDescriptionTitle() {
		return (this.isAddDialog()) ?
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_ADD_CONVERSION_VALUE_DESCRIPTION_TITLE :
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_EDIT_CONVERSION_VALUE_DESCRIPTION_TITLE;
	}
	
	@Override
	protected String getDescription() {
		return (this.isAddDialog()) ?
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_ADD_CONVERSION_VALUE_DESCRIPTION :
			JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_EDIT_CONVERSION_VALUE_DESCRIPTION;
	}
	
	@Override
	protected DialogPane<EclipseLinkConversionValueStateObject> buildLayout(Composite container) {
		return new ConversionValueDialogPane(this.getSubjectHolder(), container, this.resourceManager);
	}
	
	@Override
	public void create() {
		super.create();
		this.getPane().selectAll();
		this.getButton(OK).setEnabled(false);
	}

	@Override
	protected ConversionValueDialogPane getPane() {
		return (ConversionValueDialogPane) super.getPane();
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
	
	
	static class ConversionValueDialogPane
		extends DialogPane<EclipseLinkConversionValueStateObject>
	{
		private Text dataValueText;
		private Text objectValueText;

		ConversionValueDialogPane(
				PropertyValueModel<EclipseLinkConversionValueStateObject> subjectModel,
				Composite parentComposite,
				ResourceManager resourceManager) {
			super(subjectModel, parentComposite, resourceManager);
		}

		@Override
		protected Composite addComposite(Composite container) {
			return this.addSubPane(container, 2, 0, 0, 0, 0);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.addLabel(container,JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_DATA_VALUE);
			this.dataValueText = this.addText(
				container,
				buildDataValueHolder()
			);
			
			this.addLabel(container,JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_CONVERSION_VALUE_DIALOG_OBJECT_VALUE);
			this.objectValueText = this.addText(
				container,
				buildObjectValueHolder()
			);
		}

		private ModifiablePropertyValueModel<String> buildDataValueHolder() {
			return new PropertyAspectAdapterXXXX<EclipseLinkConversionValueStateObject, String>(getSubjectHolder(), EclipseLinkConversionValueStateObject.DATA_VALUE_PROPERTY) {
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
			return new PropertyAspectAdapterXXXX<EclipseLinkConversionValueStateObject, String>(getSubjectHolder(), EclipseLinkConversionValueStateObject.OBJECT_VALUE_PROPERTY) {
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
