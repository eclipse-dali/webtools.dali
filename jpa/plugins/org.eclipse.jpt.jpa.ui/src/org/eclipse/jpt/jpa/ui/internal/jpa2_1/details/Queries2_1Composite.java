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
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.jpa2_1.context.NamedStoredProcedureQuery2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.QueryContainer2_1;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AddQueryDialog;
import org.eclipse.jpt.jpa.ui.internal.details.AddQueryDialog2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.Queries2_0Composite;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;


public class Queries2_1Composite
	extends Queries2_0Composite
{
	Pane<? extends NamedStoredProcedureQuery2_1> namedStoredProceduerQueryPane; //lazy initialized to avoid unnecessary handles

	public Queries2_1Composite(
			Pane<?> parentPane, 
			PropertyValueModel<? extends QueryContainer> subjectHolder,
			Composite parent) {
		super(parentPane, subjectHolder, parent);
	}
	
	
	@Override
	protected Query addQueryFromDialog(AddQueryDialog dialog) {
		if (dialog.open() != Window.OK) {
			return null;
		}
		String queryType = dialog.getQueryType();
		Query query;
		if (queryType == AddQueryDialog2_1.NAMED_QUERY) {
			query = this.getSubject().addNamedQuery();
		}
		else if (queryType == AddQueryDialog2_1.NAMED_NATIVE_QUERY) {
			query = this.getSubject().addNamedNativeQuery();
		}
		else if (queryType == AddQueryDialog2_1.NAMED_STORED_PROCEDURE_QUERY) {
			query = ((QueryContainer2_1) this.getSubject()).addNamedStoredProcedureQuery();
		}
		else {
			throw new IllegalArgumentException();
		}
		query.setName(dialog.getName());
		return query;
	}
	
	@Override
	protected AddQueryDialog buildAddQueryDialog() {
		return new AddQueryDialog2_1(getShell(), this.getResourceManager(), this.getSubject().getPersistenceUnit());
	}

	protected ListValueModel<NamedStoredProcedureQuery2_1> buildNamedStoredProcedureQueriesListHolder() {
		return new ListAspectAdapter<QueryContainer, NamedStoredProcedureQuery2_1>(
			getSubjectHolder(),
			QueryContainer2_1.NAMED_STORED_PROCEDURE_QUERIES_LIST)
		{
			@Override
			protected ListIterable<NamedStoredProcedureQuery2_1> getListIterable() {
				return new SuperListIterableWrapper<NamedStoredProcedureQuery2_1>(((QueryContainer2_1) this.subject).getNamedStoredProcedureQueries());
			}

			@Override
			protected int size_() {
				return ((QueryContainer2_1) this.subject).getNamedStoredProcedureQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedStoredProcedureQuery2_1> buildSelectedNamedQueryModel() {
		return new TransformationPropertyValueModel<Query, NamedStoredProcedureQuery2_1>(this.getSelectedQueryModel()) {
			@Override
			protected NamedStoredProcedureQuery2_1 transform_(Query value) {
				return (value instanceof NamedStoredProcedureQuery2_1) ? (NamedStoredProcedureQuery2_1) value : null;
			}
		};
	}

	@Override
	protected Transformer<Query, Control> buildPaneTransformer(PageBook pageBook) {
		return new PaneTransformer(pageBook);
	}
	
	protected class PaneTransformer
		extends AbstractTransformer<Query, Control>
	{
		private final PageBook pageBook;

		protected PaneTransformer(PageBook pageBook) {
			this.pageBook = pageBook;
		}

		@Override
		public Control transform_(Query query) {
			if (query instanceof NamedNativeQuery) {
				return Queries2_1Composite.this.getNamedNativeQueryPropertyComposite(this.pageBook).getControl();
			}
			if (query instanceof NamedQuery) {
				return Queries2_1Composite.this.getNamedQueryPropertyComposite(this.pageBook).getControl();
			}
			return null; // This is for UI tolerance. Full UI support should be doing as below:
//				return Queries2_1Composite.this.getNamedStoredProcedureQueryPropertyComposite(pageBook).getControl();
		}
	}

	@Override
	protected Adapter<Query> buildQueriesAdapter() {

		return new AddRemoveListPane.AbstractAdapter<Query>() {

			public Query addNewItem() {
				return addQuery();
			}

			@Override
			public PropertyValueModel<Boolean> buildRemoveButtonEnabledModel(CollectionValueModel<Query> selectedItemsModel) {
				//enable the remove button only when 1 item is selected, same as the optional button
				return this.buildSingleSelectedItemEnabledModel(selectedItemsModel);
			}

			public void removeSelectedItems(CollectionValueModel<Query> selectedItemsModel) {
				//assume only 1 item since remove button is disabled otherwise
				Query item = selectedItemsModel.iterator().next();
				if (item instanceof NamedQuery) {
					getSubject().removeNamedQuery((NamedQuery) item);
				}
				else if (item instanceof NamedNativeQuery) {
					getSubject().removeNamedNativeQuery((NamedNativeQuery) item);
				}
				else {
					((QueryContainer2_1) getSubject()).removeNamedStoredProcedureQuery((NamedStoredProcedureQuery2_1) item);
				}
			}
		};
	}

	@Override
	protected ListValueModel<Query> buildQueriesListHolder() {
		List<ListValueModel<? extends Query>> list = new ArrayList<ListValueModel<? extends Query>>();
		list.add(buildNamedQueriesListHolder());
		list.add(buildNamedNativeQueriesListHolder());
		list.add(buildNamedStoredProcedureQueriesListHolder());
		return CompositeListValueModel.forModels(list);
	}

	@Override
	protected ILabelProvider buildQueriesListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				Query query = (Query) element;
				String name = query.getName();

				if (name == null) {
					int index = -1;

					if (query instanceof NamedQuery) {
						index = IterableTools.indexOf(getSubject().getNamedQueries(), query);
					}
					else if (query instanceof NamedNativeQuery) {
						index = IterableTools.indexOf(getSubject().getNamedNativeQueries(), query);
					}
					else {
						index = IterableTools.indexOf(((QueryContainer2_1) getSubject()).getNamedStoredProcedureQueries(), query);
					}

					name = NLS.bind(JptJpaUiDetailsMessages.QueriesComposite_displayString, Integer.valueOf(index));
				}

				return name;
			}
		};
	}

}
