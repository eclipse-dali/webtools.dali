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
import org.eclipse.jpt.core.context.NamedNativeQuery;
import org.eclipse.jpt.core.context.Query;
import org.eclipse.jpt.core.context.QueryHolder;
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
 * @see NamedNativeQueryPropertyComposite
 *
 * @version 2.0
 * @since 2.0
 */
public class NamedNativeQueriesComposite extends AbstractFormPane<QueryHolder>
{
	private WritablePropertyValueModel<NamedNativeQuery> namedNativeQueryHolder;

	/**
	 * Creates a new <code>NamedNativeQueriesComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public NamedNativeQueriesComposite(AbstractFormPane<? extends QueryHolder> parentPane,
	                                   Composite parent) {
		super(parentPane, parent);
	}

	private void addNamedNativeQuery(ObjectListSelectionModel listSelectionModel) {

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setDialogTitle(JptUiMappingsMessages.NamedNativeQueriesComposite_addQueryTitle);
		builder.setDescription(JptUiMappingsMessages.NamedNativeQueriesComposite_addQueryDescription);
		builder.setDescriptionTitle(JptUiMappingsMessages.NamedNativeQueriesComposite_addQueryDescriptionTitle);
		builder.setLabelText(JptUiMappingsMessages.NamedNativeQueriesComposite_label);
		builder.setExistingNames(namedNativeQueryNames());

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildNewNamedNativeQueryPostExecution(listSelectionModel));
	}

	private ListValueModel<NamedNativeQuery> buildDisplayableNamedNativeQueriesListHolder() {
		return new ItemPropertyListValueModelAdapter<NamedNativeQuery>(
			buildNamedNativeQueriesListHolder(),
			Query.NAME_PROPERTY
		);
	}

	private PostExecution<NewNameDialog> buildEditNamedNativeQueryPostExecution() {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					NamedNativeQuery namedNativeQuery = namedNativeQueryHolder.value();
					namedNativeQuery.setName(dialog.getName());
				}
			}
		};
	}

	private Adapter buildNamedNativeQueriesAdapter() {
		return new AddRemoveListPane.AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addNamedNativeQuery(listSelectionModel);
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.NamedNativeQueriesComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editNamedNativeQuery(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				for (Object item : listSelectionModel.selectedValues()) {
					subject().removeNamedNativeQuery((NamedNativeQuery) item);
				}
			}
		};
	}

	private ListValueModel<NamedNativeQuery> buildNamedNativeQueriesListHolder() {
		return new ListAspectAdapter<QueryHolder, NamedNativeQuery>(getSubjectHolder(), QueryHolder.NAMED_NATIVE_QUERIES_LIST) {
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

	private ILabelProvider buildNamedNativeQueriesListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				NamedNativeQuery namedNativeQuery = (NamedNativeQuery) element;
				String name = namedNativeQuery.getName();

				if (name == null) {
					int index = CollectionTools.indexOf(subject().namedNativeQueries(), namedNativeQuery);
					name = NLS.bind(JptUiMappingsMessages.NamedNativeQueriesComposite_displayString, index);
				}

				return name;
			}
		};
	}

	private WritablePropertyValueModel<NamedNativeQuery> buildNamedNativeQueryHolder() {
		return new SimplePropertyValueModel<NamedNativeQuery>();
	}

	private PostExecution<NewNameDialog> buildNewNamedNativeQueryPostExecution(final ObjectListSelectionModel listSelectionModel) {
		return new PostExecution<NewNameDialog>() {
			public void execute(NewNameDialog dialog) {
				if (dialog.wasConfirmed()) {
					NamedNativeQuery namedNativeQuery = subject().addNamedNativeQuery(subject().namedNativeQueriesSize());
					namedNativeQuery.setName(dialog.getName());
					namedNativeQueryHolder.setValue(namedNativeQuery);
					listSelectionModel.setSelectedValue(namedNativeQuery);
				}
			}
		};
	}

	private PropertyValueModel<Boolean> buildPaneEnablerHolder(PropertyValueModel<NamedNativeQuery> namedNativeQueryHolder) {
		return new TransformationPropertyValueModel<NamedNativeQuery, Boolean>(namedNativeQueryHolder) {
			@Override
			protected Boolean transform(NamedNativeQuery value) {
				return (value != null);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	private void editNamedNativeQuery(ObjectListSelectionModel listSelectionModel) {

		NewNameDialogBuilder builder = new NewNameDialogBuilder(shell());
		builder.setDialogTitle(JptUiMappingsMessages.NamedNativeQueriesComposite_editQueryTitle);
		builder.setDescription(JptUiMappingsMessages.NamedNativeQueriesComposite_editQueryDescription);
		builder.setDescriptionTitle(JptUiMappingsMessages.NamedNativeQueriesComposite_editQueryDescriptionTitle);
		builder.setLabelText(JptUiMappingsMessages.NamedNativeQueriesComposite_label);
		builder.setExistingNames(namedNativeQueryNames());
		builder.setName(namedNativeQueryHolder.value().getName());

		NewNameDialog dialog = builder.buildDialog();
		dialog.openDialog(buildEditNamedNativeQueryPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		namedNativeQueryHolder = buildNamedNativeQueryHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		container = buildTitledPane(
			container,
			JptUiMappingsMessages.NamedNativeQueriesComposite_title
		);

		// List pane
		new AddRemoveListPane<QueryHolder>(
			this,
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			buildNamedNativeQueriesAdapter(),
			buildDisplayableNamedNativeQueriesListHolder(),
			namedNativeQueryHolder,
			buildNamedNativeQueriesListLabelProvider(),
			JpaHelpContextIds.MAPPING_NAMED_NATIVE_QUERIES
		);

		// Named Native Query property pane
		NamedNativeQueryPropertyComposite pane = new NamedNativeQueryPropertyComposite(
			this,
			namedNativeQueryHolder,
			container
		);

		installPaneEnabler(pane, namedNativeQueryHolder);
	}

	private void installPaneEnabler(NamedNativeQueryPropertyComposite pane,
	                                PropertyValueModel<NamedNativeQuery> namedNativeQueryHolder) {

		new PaneEnabler(
			buildPaneEnablerHolder(namedNativeQueryHolder),
			pane
		);
	}

	private Iterator<String> namedNativeQueryNames() {
		return new TransformationIterator<NamedNativeQuery, String>(subject().namedNativeQueries()) {
			@Override
			protected String transform(NamedNativeQuery next) {
				return next.getName();
			}
		};
	}
}
