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

import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.NewNameDialog;
import org.eclipse.jpt.ui.internal.widgets.NewNameDialogBuilder;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
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
 * | | NamedQueryPropertyComposite                                           | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see QueriesComposite - The parent container
 * @see NamedQueryPropertyComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class NamedQueriesComposite extends AbstractFormPane<Entity>
{
	private WritablePropertyValueModel<NamedQuery> namedQueryHolder;

	/**
	 * Creates a new <code>NamedQueriesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public NamedQueriesComposite(AbstractFormPane<? extends Entity> parentPane,
	                             Composite parent) {
		super(parentPane, parent);
	}

	private void addNamedQuery(ObjectListSelectionModel listSelectionModel) {

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setDialogTitle(JptUiMappingsMessages.NamedQueriesComposite_addQueryTitle);
		builder.setDescription(JptUiMappingsMessages.NamedQueriesComposite_addQueryDescription);
		builder.setDescriptionTitle(JptUiMappingsMessages.NamedQueriesComposite_addQueryDescriptionTitle);
		builder.setLabelText(JptUiMappingsMessages.NamedQueriesComposite_label);
		builder.setExistingNames(namedQueryNames());

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildNewNamedQueryPostExecution());
	}

	private PostExecution<NewNameDialog> buildEditNamedQueryPostExecution() {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					NamedQuery namedQuery = namedQueryHolder.value();
					namedQuery.setName(dialog.getName());
				}
			}
		};
	}

	private Adapter buildNamedQueriesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addNamedQuery(listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.NamedQueriesComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editNamedQuery(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					subject().removeNamedQuery((NamedQuery) item);
				}
			}
		};
	}

	private ListValueModel<NamedQuery> buildDisplayableNamedQueriesListHolder() {
		return new ItemPropertyListValueModelAdapter<NamedQuery>(
			buildNamedQueriesListHolder(),
			Query.NAME_PROPERTY
		);
	}

	private ListValueModel<NamedQuery> buildNamedQueriesListHolder() {
		return new ListAspectAdapter<Entity, NamedQuery>(getSubjectHolder(), Entity.NAMED_QUERIES_LIST) {
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

	private ILabelProvider buildNamedQueriesListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				NamedQuery namedQuery = (NamedQuery) element;
				String name = namedQuery.getName();

				if (name == null) {
					int index = CollectionTools.indexOf(subject().namedQueries(), namedQuery);
					name = NLS.bind(JptUiMappingsMessages.NamedQueriesComposite_displayString, index);
				}

				return name;
			}
		};
	}

	private WritablePropertyValueModel<NamedQuery> buildNamedQueryHolder() {
		return new SimplePropertyValueModel<NamedQuery>();
	}

	private PostExecution<NewNameDialog> buildNewNamedQueryPostExecution() {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					NamedQuery namedQuery = subject().addNamedQuery(subject().namedQueriesSize());
					namedQuery.setName(dialog.getName());
					namedQueryHolder.setValue(namedQuery);
				}
			}
		};
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(PropertyValueModel<NamedQuery> namedQueryHolder) {
		return new TransformationPropertyValueModel<NamedQuery, Boolean>(namedQueryHolder) {
			@Override
			protected Boolean transform(NamedQuery value) {
				return (value != null);
			}
		};
	}

	private void editNamedQuery(ObjectListSelectionModel listSelectionModel) {

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setDialogTitle(JptUiMappingsMessages.NamedQueriesComposite_editQueryTitle);
		builder.setDescription(JptUiMappingsMessages.NamedQueriesComposite_editQueryDescription);
		builder.setDescriptionTitle(JptUiMappingsMessages.NamedQueriesComposite_editQueryDescriptionTitle);
		builder.setLabelText(JptUiMappingsMessages.NamedQueriesComposite_label);
		builder.setExistingNames(namedQueryNames());
		builder.setName(namedQueryHolder.value().getName());

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildEditNamedQueryPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		namedQueryHolder = buildNamedQueryHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		container = buildTitledPane(
			container,
			JptUiMappingsMessages.NamedQueriesComposite_title
		);

		// List pane
		new AddRemoveListPane<Entity>(
			this,
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			buildNamedQueriesAdapter(),
			buildDisplayableNamedQueriesListHolder(),
			namedQueryHolder,
			buildNamedQueriesListLabelProvider(),
			JpaHelpContextIds.MAPPING_NAMED_QUERIES
		);

		// Named Query property pane
		NamedQueryPropertyComposite pane = new NamedQueryPropertyComposite(
			this,
			namedQueryHolder,
			container
		);

		installPaneEnabler(pane, namedQueryHolder);
	}

	private void installPaneEnabler(NamedQueryPropertyComposite pane,
	                                PropertyValueModel<NamedQuery> namedQueryHolder) {

		new PaneEnabler(
			buildPaneEnablerHolder(namedQueryHolder),
			pane
		);
	}

	private Iterator<String> namedQueryNames() {
		return new TransformationIterator<NamedQuery, String>(subject().namedQueries()) {
			@Override
			protected String transform(NamedQuery next) {
				return next.getName();
			}
		};
	}
}
