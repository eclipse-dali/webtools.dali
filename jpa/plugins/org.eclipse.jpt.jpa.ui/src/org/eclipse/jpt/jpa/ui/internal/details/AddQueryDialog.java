/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.ui.internal.widgets.ValidatingDialog;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddQueryDialog
	extends ValidatingDialog<AddQueryStateObject>
{
	public static final String NAMED_QUERY = "namedQuery"; //$NON-NLS-1$
	public static final String NAMED_NATIVE_QUERY = "namedNativeQuery"; //$NON-NLS-1$

	/**
	 * The associated persistence unit
	 */
	private final PersistenceUnit pUnit;


	public AddQueryDialog(Shell parentShell, ResourceManager resourceManager, PersistenceUnit pUnit) {
		super(parentShell, resourceManager, JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_TITLE);
		this.pUnit = pUnit;
	}

	@Override
	protected AddQueryStateObject buildStateObject() {
		return new AddQueryStateObject(this.pUnit);
	}

	@Override
	protected String getDescriptionTitle() {
		return JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_DESCRIPTION_TITLE;
	}
	
	@Override
	protected String getDescription() {
		return JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_DESCRIPTION;
	}
	
	@Override
	protected DialogPane<AddQueryStateObject> buildLayout(Composite container) {
		return new QueryDialogPane(this.getSubjectHolder(), container, this.resourceManager);
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
	

	protected static class QueryDialogPane
		extends DialogPane<AddQueryStateObject>
	{
		private Text nameText;

		public QueryDialogPane(
				PropertyValueModel<AddQueryStateObject> subjectModel,
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
			this.addLabel(container, JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_NAME);
			this.nameText = addText(container, this.buildNameHolder());
			
			this.addLabel(container, JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_QUERY_TYPE);
			this.addCombo(
				container, 
				buildQueryTypeListHolder(), 
				buildQueryTypeHolder(), 
				buildStringConverter(),
				(String) null);
		}

		public ListValueModel<String> buildQueryTypeListHolder() {
			List<String> queryTypes = new ArrayList<String>();
			queryTypes.add(NAMED_QUERY);
			queryTypes.add(NAMED_NATIVE_QUERY);
			
			return new StaticListValueModel<String>(queryTypes);
		}
		
		public Transformer<String, String> buildStringConverter() {
			return new QueryTypeLabelTransformer();
		}

		static class QueryTypeLabelTransformer
			extends TransformerAdapter<String, String>
		{
			@Override
			public String transform(String value) {
				if (value == NAMED_QUERY) {
					return JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_NAMED_QUERY;
				}
				if (value == NAMED_NATIVE_QUERY) {
					return JptJpaUiDetailsMessages.ADD_QUERY_DIALOG_NAMED_NATIVE_QUERY;
				}
				return value;
			}
		}
		
		private ModifiablePropertyValueModel<String> buildNameHolder() {
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

		private ModifiablePropertyValueModel<String> buildQueryTypeHolder() {
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
