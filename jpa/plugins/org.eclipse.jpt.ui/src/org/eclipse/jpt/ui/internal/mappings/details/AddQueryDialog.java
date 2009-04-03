/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
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
public class AddQueryDialog extends ValidatingDialog<AddQueryStateObject> {

	
	// ********** constructors **********

	/**
	 * Use this constructor to edit an existing conversion value
	 */
	public AddQueryDialog(Shell parent) {
		super(parent);
	}

	@Override
	protected AddQueryStateObject buildStateObject() {
		return new AddQueryStateObject();
	}

	// ********** open **********

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(this.getTitle());
	}

	@Override
	protected String getTitle() {
		return JptUiMappingsMessages.AddQueryDialog_title;
	}

	@Override
	protected String getDescriptionTitle() {
		return JptUiMappingsMessages.AddQueryDialog_descriptionTitle;
	}
	
	@Override
	protected String getDescription() {
		return JptUiMappingsMessages.AddQueryDialog_description;
	}
	
	@Override
	protected DialogPane<AddQueryStateObject> buildLayout(Composite container) {
		return new QueryDialogPane(container);
	}
	
	@Override
	public void create() {
		super.create();

		QueryDialogPane pane = (QueryDialogPane) getPane();
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
	public String getQueryType() {
		return getSubject().getQueryType();
	}
	
	private class QueryDialogPane extends DialogPane<AddQueryStateObject> {

		private Text nameText;

		QueryDialogPane(Composite parent) {
			super(AddQueryDialog.this.getSubjectHolder(), parent);
		}

		@Override
		protected void initializeLayout(Composite container) {
			this.nameText = addLabeledText(
				container,
				JptUiMappingsMessages.AddQueryDialog_name,
				buildNameHolder()
			);
			
			addLabeledCombo(
				container, 
				JptUiMappingsMessages.AddQueryDialog_queryType, 
				buildQueryTypeListHolder(), 
				buildQueryTypeHolder(), 
				buildStringConverter(),
				null);
		}

		protected ListValueModel<String> buildQueryTypeListHolder() {
			List<String> queryTypes = new ArrayList<String>();
			queryTypes.add(Query.NAMED_QUERY);
			queryTypes.add(Query.NAMED_NATIVE_QUERY);
			
			return new StaticListValueModel<String>(queryTypes);
		}
		
		private StringConverter<String> buildStringConverter() {
			return new StringConverter<String>() {
				public String convertToString(String value) {
					if (value == Query.NAMED_QUERY) {
						return JptUiMappingsMessages.AddQueryDialog_namedQuery;
					}
					if (value == Query.NAMED_NATIVE_QUERY) {
						return JptUiMappingsMessages.AddQueryDialog_namedNativeQuery;
					}
					return value;
				}
			};
		}
		
		private WritablePropertyValueModel<String> buildNameHolder() {
			return new PropertyAspectAdapter<AddQueryStateObject, String>(getSubjectHolder(), AddQueryStateObject.NAME_PROPERTY) {
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

		private WritablePropertyValueModel<String> buildQueryTypeHolder() {
			return new PropertyAspectAdapter<AddQueryStateObject, String>(getSubjectHolder(), AddQueryStateObject.QUERY_TYPE_PROPERTY) {
				@Override
				protected String buildValue_() {
					return this.subject.getQueryType();
				}

				@Override
				protected void setValue_(String value) {
					this.subject.setQueryType(value);
				}
			};
		}

		void selectAll() {
			this.nameText.selectAll();
		}
	}
}
