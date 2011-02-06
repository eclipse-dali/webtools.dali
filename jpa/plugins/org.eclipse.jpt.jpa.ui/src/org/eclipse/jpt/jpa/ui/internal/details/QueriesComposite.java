/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
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
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.Transformer;
import org.eclipse.jpt.common.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.NamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.NamedQuery;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.QueryContainer;
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
 * @version 2.0
 * @since 2.0
 */
public class QueriesComposite extends Pane<QueryContainer>
{
	private AddRemoveListPane<QueryContainer> listPane;
	NamedNativeQueryPropertyComposite namedNativeQueryPane;
	NamedQueryPropertyComposite<? extends NamedQuery> namedQueryPane;
	private WritablePropertyValueModel<Query> queryHolder;


	public QueriesComposite(
		Pane<?> parentPane, 
		PropertyValueModel<? extends QueryContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent, false);
	}

	void addQuery() {
		addQueryFromDialog(buildAddQueryDialog());
	}

	protected AddQueryDialog buildAddQueryDialog() {
		return new AddQueryDialog(getShell(), this.getSubject().getPersistenceUnit());
	}

	protected void addQueryFromDialog(AddQueryDialog dialog) {
		if (dialog.open() != Window.OK) {
			return;
		}
		String queryType = dialog.getQueryType();
		Query query;
		if (queryType == AddQueryDialog.NAMED_QUERY) {
			query = this.getSubject().addNamedQuery();
		}
		else if (queryType == AddQueryDialog.NAMED_NATIVE_QUERY) {
			query = this.getSubject().addNamedNativeQuery();
		}
		else {
			throw new IllegalArgumentException();
		}
		query.setName(dialog.getName());
		this.getQueryHolder().setValue(query);//so that it gets selected in the List for the user to edit
	}

	private ListValueModel<Query> buildDisplayableQueriesListHolder() {
		return new ItemPropertyListValueModelAdapter<Query>(
			buildQueriesListHolder(),
			Query.NAME_PROPERTY
		);
	}
	
	private AddRemoveListPane<QueryContainer> addListPane(Composite container) {

		return new AddRemoveListPane<QueryContainer>(
			this,
			container,
			buildQueriesAdapter(),
			buildDisplayableQueriesListHolder(),
			this.getQueryHolder(),
			buildQueriesListLabelProvider(),
			JpaHelpContextIds.MAPPING_NAMED_QUERIES
		);
	}

	private ListValueModel<NamedNativeQuery> buildNamedNativeQueriesListHolder() {
		return new ListAspectAdapter<QueryContainer, NamedNativeQuery>(
			getSubjectHolder(),
			QueryContainer.NAMED_NATIVE_QUERIES_LIST)
		{
			@Override
			protected ListIterator<NamedNativeQuery> listIterator_() {
				return this.subject.namedNativeQueries();
			}

			@Override
			protected int size_() {
				return this.subject.namedNativeQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedNativeQuery> buildNamedNativeQueryHolder() {
		return new TransformationPropertyValueModel<Query, NamedNativeQuery>(this.getQueryHolder()) {
			@Override
			protected NamedNativeQuery transform_(Query value) {
				return (value instanceof NamedNativeQuery) ? (NamedNativeQuery) value : null;
			}
		};
	}

	private ListValueModel<NamedQuery> buildNamedQueriesListHolder() {
		return new ListAspectAdapter<QueryContainer, NamedQuery>(
			getSubjectHolder(),
			QueryContainer.NAMED_QUERIES_LIST)
		{
			@Override
			protected ListIterator<NamedQuery> listIterator_() {
				return this.subject.namedQueries();
			}

			@Override
			protected int size_() {
				return this.subject.namedQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedQuery> buildNamedQueryHolder() {
		return new TransformationPropertyValueModel<Query, NamedQuery>(this.getQueryHolder()) {
			@Override
			protected NamedQuery transform_(Query value) {
				return (value instanceof NamedQuery) ? (NamedQuery) value : null;
			}
		};
	}

	private Transformer<Query, Control> buildPaneTransformer() {
		return new Transformer<Query, Control>() {
			public Control transform(Query query) {

				if (query == null) {
					return null;
				}

				if (query instanceof NamedNativeQuery) {
					return QueriesComposite.this.namedNativeQueryPane.getControl();
				}

				return QueriesComposite.this.namedQueryPane.getControl();
			}
		};
	}
	
	private Adapter buildQueriesAdapter() {

		return new AddRemoveListPane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addQuery();
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					if (item instanceof NamedQuery) {
						getSubject().removeNamedQuery((NamedQuery) item);
					}
					else {
						getSubject().removeNamedNativeQuery((NamedNativeQuery) item);
					}
				}
			}
		};
	}

	private ListValueModel<Query> buildQueriesListHolder() {
		List<ListValueModel<? extends Query>> list = new ArrayList<ListValueModel<? extends Query>>();
		list.add(buildNamedQueriesListHolder());
		list.add(buildNamedNativeQueriesListHolder());
		return new CompositeListValueModel<ListValueModel<? extends Query>, Query>(list);
	}

	private ILabelProvider buildQueriesListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				Query query = (Query) element;
				String name = query.getName();

				if (name == null) {
					int index = -1;

					if (query instanceof NamedQuery) {
						index = CollectionTools.indexOf(getSubject().namedQueries(), query);
					}
					else {
						index = CollectionTools.indexOf(getSubject().namedNativeQueries(), query);
					}

					name = NLS.bind(JptUiDetailsMessages.QueriesComposite_displayString, Integer.valueOf(index));
				}

				return name;
			}
		};
	}

	private WritablePropertyValueModel<Query> buildQueryHolder() {
		return new SimplePropertyValueModel<Query>();
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		this.listPane.enableWidgets(enabled);
	}

	@Override
	protected void initialize() {
		super.initialize();
		this.queryHolder = buildQueryHolder();
	}

	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		this.listPane = this.addListPane(container);

		// Property pane
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Named Query property pane
		this.namedQueryPane = this.buildNamedQueryPropertyComposite(pageBook);

		// Named Native Query property pane
		this.namedNativeQueryPane = new NamedNativeQueryPropertyComposite(
			this,
			this.buildNamedNativeQueryHolder(),
			pageBook
		);

		installPaneSwitcher(pageBook);
	}
	
	protected NamedQueryPropertyComposite<? extends NamedQuery> buildNamedQueryPropertyComposite(PageBook pageBook) {
		return new NamedQueryPropertyComposite<NamedQuery>(
			this,
			this.buildNamedQueryHolder(),
			pageBook
		);
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(this.getQueryHolder(), this.buildPaneTransformer(), pageBook);
	}
	
	protected WritablePropertyValueModel<Query> getQueryHolder() {
		return queryHolder;
	}
}