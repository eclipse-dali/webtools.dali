/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
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
import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PrimaryKeyJoinColumn;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.WidgetFactory;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.AbstractAdapter;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeListValueModel;
import org.eclipse.jpt.utility.internal.model.value.ItemPropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | - Join Columns ---------------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | AddRemoveListPane                                                 | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Entity
 * @see InheritanceComposite - The container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnsComposite extends AbstractFormPane<Entity>
{
	private WritablePropertyValueModel<PrimaryKeyJoinColumn> joinColumnHolder;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnsComposite(AbstractFormPane<? extends Entity> subjectHolder,
	                                      Composite parent) {

		super(subjectHolder, parent);
	}

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsComposite</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public PrimaryKeyJoinColumnsComposite(PropertyValueModel<? extends Entity> subjectHolder,
	                                      Composite parent,
	                                      WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {

		Entity subject = subject();
		int index = subject.specifiedPrimaryKeyJoinColumnsSize();

		PrimaryKeyJoinColumn joinColumn = subject.addSpecifiedPrimaryKeyJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addPrimaryKeyJoinColumn() {

		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(
			shell(),
			subject(),
			null
		);

		dialog.openDialog(buildAddPrimaryKeyJoinColumnPostExecution());
	}

	private PostExecution<PrimaryKeyJoinColumnDialog> buildAddPrimaryKeyJoinColumnPostExecution() {
		return new PostExecution<PrimaryKeyJoinColumnDialog>() {
			public void execute(PrimaryKeyJoinColumnDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumn(dialog.subject());
				}
			}
		};
	}

	private PropertyValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<Entity, PrimaryKeyJoinColumn>(getSubjectHolder(), Entity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected PrimaryKeyJoinColumn buildValue_() {
				return subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<PrimaryKeyJoinColumn>(buildDefaultJoinColumnHolder());

	}

	private PostExecution<PrimaryKeyJoinColumnDialog> buildEditPrimaryKeyJoinColumnPostExecution() {
		return new PostExecution<PrimaryKeyJoinColumnDialog>() {
			public void execute(PrimaryKeyJoinColumnDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
				}
			}
		};
	}

	private WritablePropertyValueModel<PrimaryKeyJoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<PrimaryKeyJoinColumn>();
	}

	private String buildJoinColumnLabel(PrimaryKeyJoinColumn joinColumn) {
		if (joinColumn.isVirtual()) {
			return NLS.bind(
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}

		return NLS.bind(
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParams,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private Adapter buildJoinColumnsAdapter() {
		return new AbstractAdapter() {
			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addPrimaryKeyJoinColumn();
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editPrimaryKeyJoinColumn(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {

				int[] selectedIndices = listSelectionModel.selectedIndices();
				Entity entity = subject();

				for (int index = selectedIndices.length; --index >= 0; ) {
					entity.removeSpecifiedPrimaryKeyJoinColumn(selectedIndices[index]);
				}
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				return buildJoinColumnLabel((PrimaryKeyJoinColumn) element);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<PrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<PrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<PrimaryKeyJoinColumn>, PrimaryKeyJoinColumn>(list);
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<PrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			NamedColumn.SPECIFIED_NAME_PROPERTY,
			NamedColumn.DEFAULT_NAME_PROPERTY,
			AbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			AbstractJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	private ListValueModel<PrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<Entity, PrimaryKeyJoinColumn>(getSubjectHolder(), Entity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<PrimaryKeyJoinColumn> listIterator_() {
				return subject.specifiedPrimaryKeyJoinColumns();
			}

			@Override
			protected int size_() {
				return subject.specifiedPrimaryKeyJoinColumnsSize();
			}
		};
	}

	private void editJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	private void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {

		PrimaryKeyJoinColumn joinColumn = (PrimaryKeyJoinColumn) listSelectionModel.selectedValue();

		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(
			shell(),
			subject(),
			joinColumn
		);

		dialog.openDialog(buildEditPrimaryKeyJoinColumnPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		joinColumnHolder = buildJoinColumnHolder();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Primary Key Join Columns group pane
		Group groupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);

		// Override Default Join Columns check box
		buildCheckBox(
			buildSubPane(groupPane, 8),
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultJoinColumnHolder()
		);

		// Primary Key Join Columns list pane
		AddRemoveListPane<Entity> joinColumnsListPane = new AddRemoveListPane<Entity>(
			this,
			groupPane,
			buildJoinColumnsAdapter(),
			buildPrimaryKeyJoinColumnsListModel(),
			joinColumnHolder,
			buildJoinColumnsListLabelProvider(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		installJoinColumnsListPaneEnabler(joinColumnsListPane);
	}

	private void installJoinColumnsListPaneEnabler(AddRemoveListPane<Entity> pane) {
		new PaneEnabler(
			buildOverrideDefaultJoinColumnHolder(),
			pane
		);
	}

	private void updateJoinColumns(boolean selected) {

		if (isPopulating()) {
			return;
		}

		setPopulating(true);

		try {
			Entity subject = subject();

			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				PrimaryKeyJoinColumn defaultJoinColumn = subject.getDefaultPrimaryKeyJoinColumn();

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					PrimaryKeyJoinColumn pkJoinColumn = subject.addSpecifiedPrimaryKeyJoinColumn(0);
					pkJoinColumn.setSpecifiedName(columnName);
					pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);

					joinColumnHolder.setValue(pkJoinColumn);
				}
			}
			// Remove all the specified join columns
			else {
				for (int index = subject.specifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
					subject.removeSpecifiedPrimaryKeyJoinColumn(index);
				}
			}
		}
		finally {
			setPopulating(false);
		}
	}

	private class OverrideDefaultJoinColumnHolder extends ListPropertyValueModelAdapter<Boolean>
	                                              implements WritablePropertyValueModel<Boolean> {

		public OverrideDefaultJoinColumnHolder() {
			super(buildSpecifiedJoinColumnsListHolder());
		}

		@Override
		protected Boolean buildValue() {
			return listHolder.size() > 0;
		}

		public void setValue(Boolean value) {
			updateJoinColumns(value);
		}
	}
}