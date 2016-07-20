/*******************************************************************************
 * Copyright (c) 2008, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.parser.Entity;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.swt.bindings.SWTBindingTools;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiableCollectionValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.JpaNamedContextModel;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
import org.eclipse.jpt.jpa.core.jpa2_1.context.QueryContainer2_1;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.part.PageBook;

/**
 * This pane shows the list of named queries and named native queries.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | NamedQueryPropertyComposite or NamedNativeQueryPropertyComposite      | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see Query
 * @see NamedNativeQuery
 * @see NamedQuery
 * @see AbstractEntityComposite - The parent container
 * @see NamedNativeQueryPropertyComposite
 * @see NamedQueryPropertyComposite
 *
 * @version 3.3
 * @since 2.0
 */
public class QueriesComposite
	extends Pane<QueryContainer>
{
	Pane<? extends NamedNativeQuery> namedNativeQueryPane; //lazy initialized to avoid unnecessary handles 
	Pane<? extends NamedQuery> namedQueryPane; //lazy initialized to avoid unnecessary handles

	private ModifiableCollectionValueModel<Query> selectedQueriesModel;
	private PropertyValueModel<Query> selectedQueryModel;


	public QueriesComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends QueryContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.selectedQueriesModel = this.buildSelectedQueriesModel();
		this.selectedQueryModel = this.buildSelectedQueryModel();
	}

	private ModifiableCollectionValueModel<Query> buildSelectedQueriesModel() {
		return new SimpleCollectionValueModel<>();
	}

	private PropertyValueModel<Query> buildSelectedQueryModel() {
		return CollectionValueModelTools.singleElementPropertyValueModel(this.selectedQueriesModel);
	}

	protected PropertyValueModel<Query> getSelectedQueryModel() {
		return this.selectedQueryModel;
	}

	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		this.addListPane(container);

		// Property pane
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		installPaneSwitcher(pageBook);
	}
	
	@SuppressWarnings("unused")
	private AddRemoveListPane<QueryContainer, Query> addListPane(Composite container) {

		return new AddRemoveListPane<QueryContainer, Query>(
			this,
			container,
			buildQueriesAdapter(),
			buildDisplayableQueriesListModel(),
			this.selectedQueriesModel,
			buildQueriesListLabelProvider(),
			JpaHelpContextIds.MAPPING_NAMED_QUERIES
		);
	}


	private void installPaneSwitcher(PageBook pageBook) {
		SWTBindingTools.bind(this.getSelectedQueryModel(), this.buildPaneTransformer(pageBook), pageBook);
	}

	public Query addQuery() {
		return addQueryFromDialog(buildAddQueryDialog());
	}

	protected AddQueryDialog buildAddQueryDialog() {
		return new AddQueryDialog(getShell(), this.getResourceManager(), this.getSubject().getPersistenceUnit());
	}

	protected Query addQueryFromDialog(AddQueryDialog dialog) {
		if (dialog.open() != Window.OK) {
			return null;
		}
		String queryType = dialog.getQueryType();
		Query query;
		if (queryType == AddQueryDialog.NAMED_QUERY) {
			query = this.getSubject().addNamedQuery();
		}
		else if (queryType == AddQueryDialog.NAMED_NATIVE_QUERY) {
			query = this.getSubject().addNamedNativeQuery();
		}
		else if (queryType == AddQueryDialog.NAMED_NATIVE_QUERY) {
			query = ((QueryContainer2_1) this.getSubject()).addNamedStoredProcedureQuery();
		}
		else {
			throw new IllegalArgumentException();
		}
		query.setName(dialog.getName());
		return query;
	}

	private ListValueModel<Query> buildDisplayableQueriesListModel() {
		return new ItemPropertyListValueModelAdapter<>(
			buildQueriesListModel(),
			JpaNamedContextModel.NAME_PROPERTY
		);
	}

	protected ListValueModel<NamedNativeQuery> buildNamedNativeQueriesListModel() {
		return new ListAspectAdapter<QueryContainer, NamedNativeQuery>(
			getSubjectHolder(),
			QueryContainer.NAMED_NATIVE_QUERIES_LIST)
		{
			@Override
			protected ListIterable<NamedNativeQuery> getListIterable() {
				return new SuperListIterableWrapper<>(this.subject.getNamedNativeQueries());
			}

			@Override
			protected int size_() {
				return this.subject.getNamedNativeQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedNativeQuery> buildSelectedNamedNativeQueryModel() {
		return PropertyValueModelTools.filter(this.getSelectedQueryModel(), NamedNativeQuery.class);
	}

	protected ListValueModel<NamedQuery> buildNamedQueriesListModel() {
		return new ListAspectAdapter<QueryContainer, NamedQuery>(
			getSubjectHolder(),
			QueryContainer.NAMED_QUERIES_LIST)
		{
			@Override
			protected ListIterable<NamedQuery> getListIterable() {
				return new SuperListIterableWrapper<>(this.subject.getNamedQueries());
			}

			@Override
			protected int size_() {
				return this.subject.getNamedQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedQuery> buildSelectedNamedQueryModel() {
		return PropertyValueModelTools.filter(this.getSelectedQueryModel(), NamedQuery.class);
	}

	protected Transformer<Query, Control> buildPaneTransformer(PageBook pageBook) {
		return new PaneTransformer(pageBook);
	}
	
	protected class PaneTransformer
		extends TransformerAdapter<Query, Control>
	{
		private final PageBook pageBook;

		protected PaneTransformer(PageBook pageBook) {
			this.pageBook = pageBook;
		}

		@Override
		public Control transform(Query query) {
			if (query == null) {
				return null;
			}
			if (query instanceof NamedNativeQuery) {
				return QueriesComposite.this.getNamedNativeQueryPropertyComposite(this.pageBook).getControl();
			}
			return QueriesComposite.this.getNamedQueryPropertyComposite(this.pageBook).getControl();
		}
	}

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
				else {
					getSubject().removeNamedNativeQuery((NamedNativeQuery) item);
				}
			}
		};
	}

	protected ListValueModel<Query> buildQueriesListModel() {
		List<ListValueModel<? extends Query>> list = new ArrayList<>();
		list.add(buildNamedQueriesListModel());
		list.add(buildNamedNativeQueriesListModel());
		return CompositeListValueModel.forModels(list);
	}

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
					else {
						index = IterableTools.indexOf(getSubject().getNamedNativeQueries(), query);
					}

					name = NLS.bind(JptJpaUiDetailsMessages.QUERIES_COMPOSITE_DISPLAY_STRING, Integer.valueOf(index));
				}

				return name;
			}
		};
	}

	public Pane<? extends NamedQuery> getNamedQueryPropertyComposite(PageBook pageBook) {
		if (this.namedQueryPane == null) {
			this.namedQueryPane = this.buildNamedQueryPropertyComposite(pageBook);
		}
		return this.namedQueryPane;
	}

	protected Pane<? extends NamedQuery> buildNamedQueryPropertyComposite(PageBook pageBook) {
		return new NamedQueryPropertyComposite<>(
			this,
			this.buildSelectedNamedQueryModel(),
			pageBook
		);
	}
	
	public Pane<? extends NamedNativeQuery> getNamedNativeQueryPropertyComposite(PageBook pageBook) {
		if (this.namedNativeQueryPane == null) {
			this.namedNativeQueryPane = this.buildNamedNativeQueryPropertyComposite(pageBook);
		}
		return this.namedNativeQueryPane;
	}

	public Pane<? extends NamedNativeQuery> buildNamedNativeQueryPropertyComposite(PageBook pageBook) {
		return new NamedNativeQueryPropertyComposite(
			this,
			this.buildSelectedNamedNativeQueryModel(),
			pageBook
		);
	}
}