/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.common.utility.internal.StringConverter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class EclipseLinkConverterDialog
	extends ValidatingDialog<EclipseLinkConverterStateObject>
{
	/**
	 * The associated persistence unit
	 */
	EclipseLinkPersistenceUnit pUnit;
	
	// ********** constructors **********

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public EclipseLinkConverterDialog(Shell parent, EclipseLinkPersistenceUnit pUnit) {
		super(parent);
		this.pUnit = pUnit;
	}

	@Override
	protected EclipseLinkConverterStateObject buildStateObject() {
		return new EclipseLinkConverterStateObject(pUnit);
	}

	// ********** open **********

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	@Override
	protected String getTitle() {
		return EclipseLinkUiDetailsMessages.EclipseLinkConverterDialog_addConverter;
	}

	@Override
	protected String getDescriptionTitle() {
		return EclipseLinkUiDetailsMessages.EclipseLinkConverterDialog_addConverterDescriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return EclipseLinkUiDetailsMessages.EclipseLinkConverterDialog_addConverterDescription;
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
	public Class<? extends EclipseLinkConverter> getConverterType() {
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
				EclipseLinkUiDetailsMessages.EclipseLinkConverterDialog_name,
				buildNameHolder()
			);
			
			addLabeledCombo(
				container, 
				EclipseLinkUiDetailsMessages.EclipseLinkConverterDialog_converterType, 
				buildConverterTypeListHolder(), 
				buildConverterTypeHolder(), 
				buildStringConverter(),
				null);
		}

		protected ListValueModel<Class<? extends EclipseLinkConverter>> buildConverterTypeListHolder() {
			return new StaticListValueModel<Class<? extends EclipseLinkConverter>>(EclipseLinkConverter.TYPES);
		}
		
		private StringConverter<Class<? extends EclipseLinkConverter>> buildStringConverter() {
			return new StringConverter<Class<? extends EclipseLinkConverter>>() {
				public String convertToString(Class<? extends EclipseLinkConverter> value) {
					if (value == null) {
						return null;
					}
					if (value == EclipseLinkCustomConverter.class) {
						return EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_customConverter;
					}
					if (value == EclipseLinkObjectTypeConverter.class) {
						return EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_objectTypeConverter;
					}
					if (value == EclipseLinkStructConverter.class) {
						return EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_structConverter;
					}
					if (value == EclipseLinkTypeConverter.class) {
						return EclipseLinkUiDetailsMessages.EclipseLinkConvertersComposite_typeConverter;
					}
					return value.getSimpleName();
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

		private WritablePropertyValueModel<Class<? extends EclipseLinkConverter>> buildConverterTypeHolder() {
			return new PropertyAspectAdapter<EclipseLinkConverterStateObject, Class<? extends EclipseLinkConverter>>(getSubjectHolder(), EclipseLinkConverterStateObject.CONVERTER_TYPE_PROPERTY) {
				@Override
				protected Class<? extends EclipseLinkConverter> buildValue_() {
					return this.subject.getConverterType();
				}

				@Override
				protected void setValue_(Class<? extends EclipseLinkConverter> value) {
					this.subject.setConverterType(value);
				}
			};
		}

		void selectAll() {
			this.nameText.selectAll();
		}
	}
}
