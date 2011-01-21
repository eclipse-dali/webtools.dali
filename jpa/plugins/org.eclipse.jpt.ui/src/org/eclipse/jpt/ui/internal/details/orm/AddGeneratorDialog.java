/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
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
public class AddGeneratorDialog extends ValidatingDialog<AddGeneratorStateObject> {
	public static final String SEQUENCE_GENERATOR = "sequenceGenerator"; //$NON-NLS-1$
	public static final String TABLE_GENERATOR = "tableGenerator"; //$NON-NLS-1$

	/**
	 * The associated persistence unit
	 */
	private PersistenceUnit pUnit;
	
	// ********** constructors **********

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public AddGeneratorDialog(Shell parent, PersistenceUnit pUnit) {
		super(parent);
		this.pUnit = pUnit;
	}

	@Override
	protected AddGeneratorStateObject buildStateObject() {
		return new AddGeneratorStateObject(this.pUnit);
	}

	// ********** open **********

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	@Override
	protected String getTitle() {
		return JptUiDetailsOrmMessages.AddGeneratorDialog_title;
	}

	@Override
	protected String getDescriptionTitle() {
		return JptUiDetailsOrmMessages.AddGeneratorDialog_descriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return JptUiDetailsOrmMessages.AddGeneratorDialog_description;
	}
	
	@Override
	protected DialogPane<AddGeneratorStateObject> buildLayout(Composite container) {
		return new GeneratorDialogPane(container);
	}
	
	@Override
	public void create() {
		super.create();

		GeneratorDialogPane pane = (GeneratorDialogPane) getPane();
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
	public String getGeneratorType() {
		return getSubject().getGeneratorType();
	}
	
	private class GeneratorDialogPane extends DialogPane<AddGeneratorStateObject> {

		private Text nameText;

		GeneratorDialogPane(Composite parent) {
			super(AddGeneratorDialog.this.getSubjectHolder(), parent);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.nameText = addLabeledText(
				container,
				JptUiDetailsOrmMessages.AddGeneratorDialog_name,
				buildNameHolder()
			);
			
			addLabeledCombo(
				container, 
				JptUiDetailsOrmMessages.AddGeneratorDialog_generatorType, 
				buildGeneratorTypeListHolder(), 
				buildGeneratorTypeHolder(), 
				buildStringConverter(),
				null);
		}

		protected ListValueModel<String> buildGeneratorTypeListHolder() {
			List<String> generatorTypes = new ArrayList<String>();
			generatorTypes.add(TABLE_GENERATOR);
			generatorTypes.add(SEQUENCE_GENERATOR);
			
			return new StaticListValueModel<String>(generatorTypes);
		}
		
		private StringConverter<String> buildStringConverter() {
			return new StringConverter<String>() {
				public String convertToString(String value) {
					if (value == TABLE_GENERATOR) {
						return JptUiDetailsOrmMessages.AddGeneratorDialog_tableGenerator;
					}
					if (value == SEQUENCE_GENERATOR) {
						return JptUiDetailsOrmMessages.AddGeneratorDialog_sequenceGenerator;
					}
					return value;
				}
			};
		}
		
		private WritablePropertyValueModel<String> buildNameHolder() {
			return new PropertyAspectAdapter<AddGeneratorStateObject, String>(getSubjectHolder(), AddGeneratorStateObject.NAME_PROPERTY) {
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

		private WritablePropertyValueModel<String> buildGeneratorTypeHolder() {
			return new PropertyAspectAdapter<AddGeneratorStateObject, String>(getSubjectHolder(), AddGeneratorStateObject.GENERATOR_TYPE_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getGeneratorType();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setGeneratorType(value);
				}
			};
		}

		void selectAll() {
			this.nameText.selectAll();
		}
	}
}
