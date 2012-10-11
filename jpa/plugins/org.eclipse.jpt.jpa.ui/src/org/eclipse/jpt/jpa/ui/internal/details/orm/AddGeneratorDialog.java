/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
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
		protected Composite addComposite(Composite container) {
			return this.addSubPane(container, 2, 0, 0, 0, 0);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.addLabel(container, JptUiDetailsOrmMessages.AddGeneratorDialog_name);
			this.nameText = this.addText(
				container,
				buildNameHolder()
			);
			
			this.addLabel(container, JptUiDetailsOrmMessages.AddGeneratorDialog_generatorType);
			this.addCombo(
				container,  
				buildGeneratorTypeListHolder(), 
				buildGeneratorTypeHolder(), 
				buildStringConverter(),
				(String) null);
		}

		protected ListValueModel<String> buildGeneratorTypeListHolder() {
			List<String> generatorTypes = new ArrayList<String>();
			generatorTypes.add(TABLE_GENERATOR);
			generatorTypes.add(SEQUENCE_GENERATOR);
			
			return new StaticListValueModel<String>(generatorTypes);
		}
		
		private Transformer<String, String> buildStringConverter() {
			return new TransformerAdapter<String, String>() {
				@Override
				public String transform(String value) {
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
		
		private ModifiablePropertyValueModel<String> buildNameHolder() {
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

		private ModifiablePropertyValueModel<String> buildGeneratorTypeHolder() {
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
