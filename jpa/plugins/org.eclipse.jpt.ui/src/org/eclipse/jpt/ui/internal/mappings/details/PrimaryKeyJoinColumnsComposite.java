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
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.util.PaneEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.IWidgetFactory;
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
 * @see IEntity
 * @see InheritanceComposite - The container of this pane
 *
 * @version 2.0
 * @since 2.0
 */
public class PrimaryKeyJoinColumnsComposite extends AbstractFormPane<IEntity>
{
	private WritablePropertyValueModel<IPrimaryKeyJoinColumn> joinColumnHolder;

	/**
	 * Creates a new <code>PrimaryKeyJoinColumnsComposite</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public PrimaryKeyJoinColumnsComposite(AbstractFormPane<? extends IEntity> subjectHolder,
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
	public PrimaryKeyJoinColumnsComposite(PropertyValueModel<? extends IEntity> subjectHolder,
	                                      Composite parent,
	                                      IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {

		IEntity subject = subject();
		int index = subject.specifiedPrimaryKeyJoinColumnsSize();

		IPrimaryKeyJoinColumn joinColumn = subject.addSpecifiedPrimaryKeyJoinColumn(index);
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

	private PropertyValueModel<IPrimaryKeyJoinColumn> buildDefaultJoinColumnHolder() {
		return new PropertyAspectAdapter<IEntity, IPrimaryKeyJoinColumn>(getSubjectHolder(), IEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMN) {
			@Override
			protected IPrimaryKeyJoinColumn buildValue_() {
				return subject.getDefaultPrimaryKeyJoinColumn();
			}
		};
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildDefaultJoinColumnListHolder() {
		return new PropertyListValueModelAdapter<IPrimaryKeyJoinColumn>(buildDefaultJoinColumnHolder());

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

	private WritablePropertyValueModel<IPrimaryKeyJoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<IPrimaryKeyJoinColumn>();
	}

	private String buildJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
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
				IEntity entity = subject();

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
				return buildJoinColumnLabel((IPrimaryKeyJoinColumn) element);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListHolder() {
		List<ListValueModel<IPrimaryKeyJoinColumn>> list = new ArrayList<ListValueModel<IPrimaryKeyJoinColumn>>();
		list.add(buildSpecifiedJoinColumnsListHolder());
		list.add(buildDefaultJoinColumnListHolder());
		return new CompositeListValueModel<ListValueModel<IPrimaryKeyJoinColumn>, IPrimaryKeyJoinColumn>(list);
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildPrimaryKeyJoinColumnsListModel() {
		return new ItemPropertyListValueModelAdapter<IPrimaryKeyJoinColumn>(
			buildPrimaryKeyJoinColumnsListHolder(),
			INamedColumn.SPECIFIED_NAME_PROPERTY,
			INamedColumn.DEFAULT_NAME_PROPERTY,
			IAbstractJoinColumn.SPECIFIED_REFERENCED_COLUMN_NAME_PROPERTY,
			IAbstractJoinColumn.DEFAULT_REFERENCED_COLUMN_NAME_PROPERTY
		);
	}

	private ListValueModel<IPrimaryKeyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<IEntity, IPrimaryKeyJoinColumn>(getSubjectHolder(), IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<IPrimaryKeyJoinColumn> listIterator_() {
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

		IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) listSelectionModel.selectedValue();

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
		AddRemoveListPane<IEntity> joinColumnsListPane = new AddRemoveListPane<IEntity>(
			this,
			groupPane,
			buildJoinColumnsAdapter(),
			buildPrimaryKeyJoinColumnsListModel(),
			joinColumnHolder,
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);

		installJoinColumnsListPaneEnabler(joinColumnsListPane);
	}

	private void installJoinColumnsListPaneEnabler(AddRemoveListPane<IEntity> pane) {
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
			IEntity subject = subject();

			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				IPrimaryKeyJoinColumn defaultJoinColumn = subject.getDefaultPrimaryKeyJoinColumn();

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IPrimaryKeyJoinColumn pkJoinColumn = subject.addSpecifiedPrimaryKeyJoinColumn(0);
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