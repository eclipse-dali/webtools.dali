/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.details;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.internal.widgets.DialogPane;
import org.eclipse.jpt.common.utility.internal.model.value.StaticListValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AddQueryDialog;
import org.eclipse.jpt.jpa.ui.internal.details.AddQueryStateObject;
import org.eclipse.jpt.jpa.ui.jpa2_1.details.JptJpaUiDetailsMessages2_1;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

public class AddQueryDialog2_1
	extends AddQueryDialog
{
	public static final String NAMED_STORED_PROCEDURE_QUERY = "namedStoredProceduerQuery"; //$NON-NLS-1$

	public AddQueryDialog2_1(
			Shell parentShell,
			ResourceManager resourceManager, 
			PersistenceUnit pUnit) {
		super(parentShell, resourceManager, pUnit);
	}

	@Override
	protected DialogPane<AddQueryStateObject> buildLayout(Composite container) {
		return new QueryDialogPane2_1(this.getSubjectHolder(), container, this.resourceManager);
	}

	static class QueryDialogPane2_1
		extends QueryDialogPane
	{
		QueryDialogPane2_1(
				PropertyValueModel<AddQueryStateObject> subjectModel,
				Composite parentComposite,
				ResourceManager resourceManager) {
			super(subjectModel, parentComposite, resourceManager);
		}

		@Override
		public ListValueModel<String> buildQueryTypeListHolder() {
			List<String> queryTypes = new ArrayList<String>();
			queryTypes.add(NAMED_QUERY);
			queryTypes.add(NAMED_NATIVE_QUERY);
			queryTypes.add(NAMED_STORED_PROCEDURE_QUERY);

			return new StaticListValueModel<String>(queryTypes);
		}

		@Override
		public Transformer<String, String> buildStringConverter() {
			return new QueryTypeLabelTransformer();
		}

		static class QueryTypeLabelTransformer
			extends TransformerAdapter<String, String>
		{
			@Override
			public String transform(String value) {
				if (value == NAMED_QUERY) {
					return JptJpaUiDetailsMessages.AddQueryDialog_namedQuery;
				}
				if (value == NAMED_NATIVE_QUERY) {
					return JptJpaUiDetailsMessages.AddQueryDialog_namedNativeQuery;
				}
				if (value == NAMED_STORED_PROCEDURE_QUERY) {
					return JptJpaUiDetailsMessages2_1.ADD_QUERY_DIALOG__NAMED_STORED_PROCEDURE_QUERY;
				}
				return value;
			}
		}
	}
}
