/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
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

	
	// ********** constructors **********

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public AddGeneratorDialog(Shell parent) {
		super(parent);
	}

	@Override
	protected AddGeneratorStateObject buildStateObject() {
		return new AddGeneratorStateObject();
	}

	// ********** open **********

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	@Override
	protected String getTitle() {
		return JptUiOrmMessages.AddGeneratorDialog_title;
	}

	@Override
	protected String getDescriptionTitle() {
		return JptUiOrmMessages.AddGeneratorDialog_descriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return JptUiOrmMessages.AddGeneratorDialog_description;
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
				JptUiOrmMessages.AddGeneratorDialog_name,
				buildNameHolder()
			);
			
			addLabeledCombo(
				container, 
				JptUiOrmMessages.AddGeneratorDialog_generatorType, 
				buildGeneratorTypeListHolder(), 
				buildGeneratorTypeHolder(), 
				buildStringConverter(),
				null);
		}

		protected ListValueModel<String> buildGeneratorTypeListHolder() {
			List<String> generatorTypes = new ArrayList<String>();
			generatorTypes.add(Generator.TABLE_GENERATOR);
			generatorTypes.add(Generator.SEQUENCE_GENERATOR);
			
			return new StaticListValueModel<String>(generatorTypes);
		}
		
		private StringConverter<String> buildStringConverter() {
			return new StringConverter<String>() {
				public String convertToString(String value) {
					if (value == Generator.TABLE_GENERATOR) {
						return JptUiOrmMessages.AddGeneratorDialog_tableGenerator;
					}
					if (value == Generator.SEQUENCE_GENERATOR) {
						return JptUiOrmMessages.AddGeneratorDialog_sequenceGenerator;
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
