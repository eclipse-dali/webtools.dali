/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHolder;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.ControlSwitcher;
import org.eclipse.jpt.ui.internal.widgets.AbstractPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.NewNameDialog;
import org.eclipse.jpt.ui.internal.widgets.NewNameDialogBuilder;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
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
public class QueriesComposite extends AbstractPane<QueryHolder>
{
	private AddRemoveListPane<QueryHolder> listPane;
	private NamedNativeQueryPropertyComposite namedNativeQueryPane;
	private NamedQueryPropertyComposite namedQueryPane;
	private WritablePropertyValueModel<Query> queryHolder;

	/**
	 * Creates a new <code>QueriesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public QueriesComposite(AbstractPane<? extends QueryHolder> parentPane,
	                        Composite parent) {

		super(parentPane, parent);
	}

	private void addNamedNativeQuery(ObjectListSelectionModel listSelectionModel) {

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setDialogTitle(JptUiMappingsMessages.QueriesComposite_addNamedNativeQueryTitle);
		builder.setDescription(JptUiMappingsMessages.QueriesComposite_addNamedNativeQueryDescription);
		builder.setDescriptionTitle(JptUiMappingsMessages.QueriesComposite_addNamedNativeQueryDescriptionTitle);
		builder.setLabelText(JptUiMappingsMessages.QueriesComposite_label);
		builder.setExistingNames(namedNativeQueryNames());

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildNewNamedNativeQueryPostExecution(listSelectionModel));
	}

	private void addNamedQuery(ObjectListSelectionModel listSelectionModel) {

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setDialogTitle(JptUiMappingsMessages.QueriesComposite_addNamedQueryTitle);
		builder.setDescription(JptUiMappingsMessages.QueriesComposite_addNamedQueryDescription);
		builder.setDescriptionTitle(JptUiMappingsMessages.QueriesComposite_addNamedQueryDescriptionTitle);
		builder.setLabelText(JptUiMappingsMessages.QueriesComposite_label);
		builder.setExistingNames(namedQueryNames());

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildNewNamedQueryPostExecution(listSelectionModel));
	}

	private ListValueModel<Query> buildDisplayableQueriesListHolder() {
		return new ItemPropertyListValueModelAdapter<Query>(
			buildQueriesListHolder(),
			Query.NAME_PROPERTY
		);
	}

	private PostExecution<NewNameDialog> buildEditNamedQueryPostExecution() {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					Query query = queryHolder.getValue();
					query.setName(dialog.getName());
				}
			}
		};
	}

	private AddRemoveListPane<QueryHolder> buildListPane(Composite container) {

		return new AddRemoveListPane<QueryHolder>(
			this,
			container,
			buildQueriesAdapter(),
			buildDisplayableQueriesListHolder(),
			queryHolder,
			buildQueriesListLabelProvider(),
			JpaHelpContextIds.MAPPING_NAMED_QUERIES
		)
		{
			@Override
			protected void addCustomButtonAfterAddButton(Composite container,
			                                             String helpId) {

				Button button = buildButton(
					container,
					JptUiMappingsMessages.QueriesComposite_addNamedNativeQuery,
					helpId,
					buildNewNamedNativeQueryAction(getSelectionModel())
				);

				addAlignRight(button);
			}
		};
	}

	private ListValueModel<NamedNativeQuery> buildNamedNativeQueriesListHolder() {
		return new ListAspectAdapter<QueryHolder, NamedNativeQuery>(
			getSubjectHolder(),
			QueryHolder.NAMED_NATIVE_QUERIES_LIST)
		{
			@Override
			protected ListIterator<NamedNativeQuery> listIterator_() {
				return subject.namedNativeQueries();
			}

			@Override
			protected int size_() {
				return subject.namedNativeQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedNativeQuery> buildNamedNativeQueryHolder() {
		return new TransformationPropertyValueModel<Query, NamedNativeQuery>(queryHolder) {
			@Override
			protected NamedNativeQuery transform_(Query value) {
				return (value instanceof NamedNativeQuery) ? (NamedNativeQuery) value : null;
			}
		};
	}

	private ListValueModel<NamedQuery> buildNamedQueriesListHolder() {
		return new ListAspectAdapter<QueryHolder, NamedQuery>(
			getSubjectHolder(),
			QueryHolder.NAMED_QUERIES_LIST)
		{
			@Override
			protected ListIterator<NamedQuery> listIterator_() {
				return subject.namedQueries();
			}

			@Override
			protected int size_() {
				return subject.namedQueriesSize();
			}
		};
	}

	private PropertyValueModel<NamedQuery> buildNamedQueryHolder() {
		return new TransformationPropertyValueModel<Query, NamedQuery>(queryHolder) {
			@Override
			protected NamedQuery transform_(Query value) {
				return (value instanceof NamedQuery) ? (NamedQuery) value : null;
			}
		};
	}

	protected Runnable buildNewNamedNativeQueryAction(final ObjectListSelectionModel selectionModel) {
		return new Runnable() {
			public void run() {
				addNamedNativeQuery(selectionModel);
			}
		};
	}

	private PostExecution<NewNameDialog> buildNewNamedNativeQueryPostExecution(final ObjectListSelectionModel listSelectionModel) {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					NamedNativeQuery namedNativeQuery = subject().addNamedNativeQuery(subject().namedNativeQueriesSize());
					namedNativeQuery.setName(dialog.getName());
					queryHolder.setValue(namedNativeQuery);
					listSelectionModel.setSelectedValue(namedNativeQuery);
				}
			}
		};
	}

	private PostExecution<NewNameDialog> buildNewNamedQueryPostExecution(final ObjectListSelectionModel selectionModel) {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					Query query = subject().addNamedQuery(subject().namedQueriesSize());
					query.setName(dialog.getName());
					queryHolder.setValue(query);
					selectionModel.setSelectedValue(query);
				}
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
					return namedNativeQueryPane.getControl();
				}

				return namedQueryPane.getControl();
			}
		};
	}
	private Adapter buildQueriesAdapter() {

		return new AddRemoveListPane.AbstractAdapter() {

			@Override
			public String addButtonText() {
				return JptUiMappingsMessages.QueriesComposite_addNamedQuery;
			}

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addNamedQuery(listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.QueriesComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editQuery(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					if (item instanceof NamedQuery) {
						subject().removeNamedQuery((NamedQuery) item);
					}
					else {
						subject().removeNamedNativeQuery((NamedNativeQuery) item);
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
						index = CollectionTools.indexOf(subject().namedQueries(), query);
					}
					else {
						index = CollectionTools.indexOf(subject().namedNativeQueries(), query);
					}

					name = NLS.bind(JptUiMappingsMessages.QueriesComposite_displayString, index);
				}

				return name;
			}
		};
	}

	private WritablePropertyValueModel<Query> buildQueryHolder() {
		return new SimplePropertyValueModel<Query>();
	}

	private void editQuery(ObjectListSelectionModel listSelectionModel) {

		Query query = queryHolder.getValue();

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setLabelText(JptUiMappingsMessages.QueriesComposite_label);
		builder.setName(query.getName());

		if (query instanceof NamedNativeQuery) {
			builder.setDialogTitle(JptUiMappingsMessages.QueriesComposite_editNamedNativeQueryTitle);
			builder.setDescription(JptUiMappingsMessages.QueriesComposite_editNamedNativeQueryDescription);
			builder.setDescriptionTitle(JptUiMappingsMessages.QueriesComposite_editNamedNativeQueryDescriptionTitle);
			builder.setExistingNames(namedNativeQueryNames());
		}
		else {
			builder.setDialogTitle(JptUiMappingsMessages.QueriesComposite_editNamedQueryTitle);
			builder.setDescription(JptUiMappingsMessages.QueriesComposite_editNamedQueryDescription);
			builder.setDescriptionTitle(JptUiMappingsMessages.QueriesComposite_editNamedQueryDescriptionTitle);
			builder.setExistingNames(namedQueryNames());
		}

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildEditNamedQueryPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		listPane.enableWidgets(enabled);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		queryHolder = buildQueryHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// List pane
		listPane = buildListPane(container);

		// Property pane
		PageBook pageBook = new PageBook(container, SWT.NULL);
		pageBook.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Named Query property pane
		namedQueryPane = new NamedQueryPropertyComposite(
			this,
			buildNamedQueryHolder(),
			pageBook
		);

		// Named Native Query property pane
		namedNativeQueryPane = new NamedNativeQueryPropertyComposite(
			this,
			buildNamedNativeQueryHolder(),
			pageBook
		);

		installPaneSwitcher(pageBook);
	}

	private void installPaneSwitcher(PageBook pageBook) {
		new ControlSwitcher(queryHolder, buildPaneTransformer(), pageBook);
	}

	private Iterator<String> namedNativeQueryNames() {
		return new TransformationIterator<Query, String>(subject().namedNativeQueries()) {
			@Override
			protected String transform(Query next) {
				return next.getName();
			}
		};
	}

	private Iterator<String> namedQueryNames() {
		return new TransformationIterator<Query, String>(subject().namedQueries()) {
			@Override
			protected String transform(Query next) {
				return next.getName();
			}
		};
	}
}