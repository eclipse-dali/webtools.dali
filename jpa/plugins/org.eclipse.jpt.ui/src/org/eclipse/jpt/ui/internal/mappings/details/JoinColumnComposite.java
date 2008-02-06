/*******************************************************************************
 * Copyright (c) 2005, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.ListIterator;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.ISingleRelationshipMapping;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.Adapter;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListValueModel;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SortedListValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.swing.ObjectListSelectionModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |                                                                           |
 * | x Override Default                                                        |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see ISingleRelationshipMapping
 * @see IJoinColumn
 * @see ManyToOneMappingComposite - A container of this pane
 * @see OneToOneMappingComposite - A container of this pane
 * @see JoinColumnInRelationshipMappingDialog
 *
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnComposite extends AbstractFormPane<ISingleRelationshipMapping>
{
	private Button overrideDefaultJoinColumnsCheckBox;

	/**
	 * Creates a new <code>JoinColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param parent The parent container
	 */
	protected JoinColumnComposite(AbstractFormPane<? extends ISingleRelationshipMapping> parentPane,
	                              Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>JoinColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ISingleRelationshipMapping</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinColumnComposite(PropertyValueModel<? extends ISingleRelationshipMapping> subjectHolder,
	                           Composite parent,
	                           IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addJoinColumn() {

		JoinColumnInRelationshipMappingDialog dialog =
			new JoinColumnInRelationshipMappingDialog(shell(), subject());

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumn(JoinColumnInRelationshipMappingStateObject stateObject) {

		int index = subject().specifiedJoinColumnsSize();

		IJoinColumn joinColumn = subject().addSpecifiedJoinColumn(index);
		joinColumn.setSpecifiedName(stateObject.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(stateObject.getSpecifiedReferencedColumnName());

		if (!stateObject.isDefaultTableSelected()) {
			// Not checking this for name and referenced column name because
			// there is no default option when you are adding a second join
			// column. There is always at least 1 join column (the default)
			joinColumn.setSpecifiedTable(stateObject.getSelectedTable());
		}
	}

	private PostExecution<JoinColumnInRelationshipMappingDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInRelationshipMappingDialog>() {
			public void execute(JoinColumnInRelationshipMappingDialog dialog) {
				if (dialog.wasConfirmed()) {
					JoinColumnInRelationshipMappingStateObject stateObject = dialog.subject();
					addJoinColumn(stateObject);
				}
			}
		};
	}

	private String buildDefaultJoinColumnLabel(IJoinColumn joinColumn) {
		return NLS.bind(
			JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsDefault,
			joinColumn.getName(),
			joinColumn.getReferencedColumnName()
		);
	}

	private PostExecution<JoinColumnInRelationshipMappingDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInRelationshipMappingDialog>() {
			public void execute(JoinColumnInRelationshipMappingDialog dialog) {
				if (dialog.wasConfirmed()) {
					updateJoinColumn(dialog.subject());
				}
			}
		};
	}

	private WritablePropertyValueModel<IJoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<IJoinColumn>();
	}

	private String buildJoinColumnLabel(IJoinColumn joinColumn) {
		if (joinColumn.getSpecifiedName() == null) {
			if (joinColumn.getSpecifiedReferencedColumnName() == null) {
				return NLS.bind(
					JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsBothDefault,
					joinColumn.getName(),
					joinColumn.getReferencedColumnName()
				);
			}

			return NLS.bind(
				JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsFirstDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null) {
			return NLS.bind(
				JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParamsSecDefault,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
		else {
			return NLS.bind(
				JptUiMappingsMessages.JoinColumnComposite_mappingBetweenTwoParams,
				joinColumn.getName(),
				joinColumn.getReferencedColumnName()
			);
		}
	}

	private Adapter buildJoinColumnsAdapter() {
		return new AddRemovePane.AbstractAdapter() {

			public void addNewItem(ObjectListSelectionModel listSelectionModel) {
				addJoinColumn();
			}

			@Override
			public boolean hasOptionalButton() {
				return true;
			}

			@Override
			public String optionalButtonText() {
				return JptUiMappingsMessages.JoinColumnComposite_edit;
			}

			@Override
			public void optionOnSelection(ObjectListSelectionModel listSelectionModel) {
				editJoinColumn(listSelectionModel);
			}

			public void removeSelectedItems(ObjectListSelectionModel listSelectionModel) {
				removeJoinColumn(listSelectionModel);
			}
		};
	}

	private ListValueModel<IJoinColumn> buildJoinColumnsListHolder() {
		return new ListAspectAdapter<ISingleRelationshipMapping, IJoinColumn>(
			getSubjectHolder(),
			ISingleRelationshipMapping.DEFAULT_JOIN_COLUMN,//TODO
			ISingleRelationshipMapping.SPECIFIED_JOIN_COLUMNS_LIST)
		{
			@Override
			protected ListIterator<IJoinColumn> listIterator_() {
				return subject.joinColumns();
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IJoinColumn joinColumn = (IJoinColumn) element;

				return (subject().specifiedJoinColumnsSize() == 0) ?
					buildDefaultJoinColumnLabel(joinColumn) :
					buildJoinColumnLabel(joinColumn);
			}
		};
	}

	private SelectionAdapter buildOverrideDefaultJoinColumnsSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				if (isPopulating()) {
					return;
				}

				if (overrideDefaultJoinColumnsCheckBox.getSelection()) {
					IJoinColumn defaultJoinColumn = subject().getDefaultJoinColumn();//TODO could be null, disable override default check box?
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IJoinColumn joinColumn = subject().addSpecifiedJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
				else {
					for (int index = subject().specifiedJoinColumnsSize(); --index >= 0; ) {
						subject().removeSpecifiedJoinColumn(index);
					}
				}
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinsColumnHolder() {
		// TODO
		return new SimplePropertyValueModel<Boolean>();
	}

	private Composite buildPane(Composite container, int groupBoxMargin) {
		return buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin);
	}

	private ListValueModel<IJoinColumn> buildSortedJoinColumnsListHolder() {
		return new SortedListValueModelAdapter<IJoinColumn>(
			buildJoinColumnsListHolder()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		overrideDefaultJoinColumnsCheckBox.setSelection(subject() != null && subject().containsSpecifiedJoinColumns());
	}

	private void editJoinColumn(ObjectListSelectionModel listSelectionModel) {

		IJoinColumn joinColumn = (IJoinColumn) listSelectionModel.selectedValue();

		JoinColumnInRelationshipMappingDialog dialog =
			new JoinColumnInRelationshipMappingDialog(shell(), joinColumn);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Override Default Join Columns check box
		overrideDefaultJoinColumnsCheckBox = buildCheckBox(
			buildPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinColumnComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinsColumnHolder()
		);

		overrideDefaultJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultJoinColumnsSelectionListener()
		);

		// Join Columns group
		Group joinColumnsGroup = buildTitledPane(
			container,
			JptUiMappingsMessages.JoinColumnComposite_joinColumn
		);

		// Join Columns list pane
		new AddRemoveListPane<ISingleRelationshipMapping>(
			this,
			joinColumnsGroup,
			buildJoinColumnsAdapter(),
			buildSortedJoinColumnsListHolder(),
			buildJoinColumnHolder(),
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}

	private void removeJoinColumn(ObjectListSelectionModel listSelectionModel) {

		int[] selectedIndices = listSelectionModel.selectedIndices();

		for (int index = selectedIndices.length; --index >= 0; ) {
			subject().removeSpecifiedJoinColumn(selectedIndices[index]);
		}
	}

	private void updateJoinColumn(JoinColumnInRelationshipMappingStateObject stateObject) {

		IJoinColumn joinColumn = stateObject.getJoinColumn();
		String name = stateObject.getSelectedName();
		String referencedColumnName = stateObject.getSpecifiedReferencedColumnName();
		String table = stateObject.getSelectedTable();

		// Name
		if (stateObject.isDefaultNameSelected()) {

			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null ||
		        !joinColumn.getSpecifiedName().equals(name)){

			joinColumn.setSpecifiedName(name);
		}

		// Referenced Column Name
		if (stateObject.isDefaultReferencedColumnNameSelected()) {

			if (joinColumn.getSpecifiedReferencedColumnName() != null) {
				joinColumn.setSpecifiedReferencedColumnName(null);
			}
		}
		else if (joinColumn.getSpecifiedReferencedColumnName() == null ||
		        !joinColumn.getSpecifiedReferencedColumnName().equals(referencedColumnName)){

			joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
		}

		// Specified Table
		if (stateObject.isDefaultTableSelected()) {

			if (joinColumn.getSpecifiedTable() != null) {
				joinColumn.setSpecifiedTable(null);
			}
		}
		else if (joinColumn.getSpecifiedTable() == null ||
		        !joinColumn.getSpecifiedTable().equals(table)){

			joinColumn.setSpecifiedTable(table);
		}

		// Insertable
		Boolean insertable = stateObject.getInsertable();

		if (joinColumn.getInsertable() != insertable) {
			joinColumn.setSpecifiedInsertable(insertable);
		}

		// Updatable
		Boolean updatable = stateObject.getUpdatable();

		if (joinColumn.getUpdatable() != updatable) {
			joinColumn.setSpecifiedUpdatable(updatable);
		}
	}
}