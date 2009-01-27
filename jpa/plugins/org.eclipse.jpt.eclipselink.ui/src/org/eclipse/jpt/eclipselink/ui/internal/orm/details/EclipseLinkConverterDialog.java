/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.orm.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.utility.internal.StringConverter;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
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
public class EclipseLinkConverterDialog extends ValidatingDialog<EclipseLinkConverterStateObject> {

	
	// ********** constructors **********

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public EclipseLinkConverterDialog(Shell parent) {
		super(parent);
	}

	@Override
	protected EclipseLinkConverterStateObject buildStateObject() {
		return new EclipseLinkConverterStateObject();
	}

	// ********** open **********

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	@Override
	protected String getTitle() {
		return EclipseLinkUiMappingsMessages.EclipseLinkConverterDialog_addConverter;
	}

	@Override
	protected String getDescriptionTitle() {
		return EclipseLinkUiMappingsMessages.EclipseLinkConverterDialog_addConverterDescriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return EclipseLinkUiMappingsMessages.EclipseLinkConverterDialog_addConverterDescription;
	}
	
	@Override
	protected DialogPane<EclipseLinkConverterStateObject> buildLayout(Composite container) {
		return new ConversionValueDialogPane(container);
	}
	
	@Override
	public void create() {
		super.create();

		ConversionValueDialogPane pane = (ConversionValueDialogPane) getPane();
		pane.selectAll();

		getButton(OK).setEnabled(false);
	}


	// ********** public API **********

	/**
	 * Return the data value set in the text widget.
	 */
	public String getName() {
		return getSubject().getName();
	}

	/**
	 * Return the object value set in the text widget.
	 */
	public String getConverterType() {
		return getSubject().getConverterType();
	}
	
	private class ConversionValueDialogPane extends DialogPane<EclipseLinkConverterStateObject> {

		private Text nameText;

		ConversionValueDialogPane(Composite parent) {
			super(EclipseLinkConverterDialog.this.getSubjectHolder(), parent);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.nameText = addLabeledText(
				container,
				EclipseLinkUiMappingsMessages.EclipseLinkConverterDialog_name,
				buildNameHolder()
			);
			
			addLabeledCombo(
				container, 
				EclipseLinkUiMappingsMessages.EclipseLinkConverterDialog_converterType, 
				buildConverterTypeListHolder(), 
				buildConverterTypeHolder(), 
				buildStringConverter(),
				null);
		}

		protected ListValueModel<String> buildConverterTypeListHolder() {
			List<String> converterTypes = new ArrayList<String>();
			converterTypes.add(EclipseLinkConverter.CUSTOM_CONVERTER);
			converterTypes.add(EclipseLinkConverter.OBJECT_TYPE_CONVERTER);
			converterTypes.add(EclipseLinkConverter.STRUCT_CONVERTER);
			converterTypes.add(EclipseLinkConverter.TYPE_CONVERTER);
			
			return new StaticListValueModel<String>(converterTypes);
		}
		
		private StringConverter<String> buildStringConverter() {
			return new StringConverter<String>() {
				public String convertToString(String value) {
					if (value == EclipseLinkConverter.CUSTOM_CONVERTER) {
						return EclipseLinkUiMappingsMessages.ConvertersComposite_customConverter;
					}
					if (value == EclipseLinkConverter.OBJECT_TYPE_CONVERTER) {
						return EclipseLinkUiMappingsMessages.ConvertersComposite_objectTypeConverter;
					}
					if (value == EclipseLinkConverter.STRUCT_CONVERTER) {
						return EclipseLinkUiMappingsMessages.ConvertersComposite_structConverter;
					}
					if (value == EclipseLinkConverter.TYPE_CONVERTER) {
						return EclipseLinkUiMappingsMessages.ConvertersComposite_typeConverter;
					}
					return value;
				}
			};
		}
		
		private WritablePropertyValueModel<String> buildNameHolder() {
			return new PropertyAspectAdapter<EclipseLinkConverterStateObject, String>(getSubjectHolder(), EclipseLinkConverterStateObject.NAME_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getName();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setName(value);
				}
			};
		}

		private WritablePropertyValueModel<String> buildConverterTypeHolder() {
			return new PropertyAspectAdapter<EclipseLinkConverterStateObject, String>(getSubjectHolder(), EclipseLinkConverterStateObject.CONVERTER_TYPE_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getConverterType();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setConverterType(value);
				}
			};
		}

		void selectAll() {
			this.nameText.selectAll();
		}
	}
}
