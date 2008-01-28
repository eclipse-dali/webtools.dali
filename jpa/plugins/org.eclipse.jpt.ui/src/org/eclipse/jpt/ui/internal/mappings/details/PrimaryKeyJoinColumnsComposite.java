/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.AddRemoveListPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.ui.internal.widgets.AddRemovePane.AbstractAdapter;
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
 * | x Override Default Join Columns                                           |
 * |                                                                           |
 * | - Primary Key Join Columns ---------------------------------------------- |
 * | |                                                                       | |
 * | | AddRemoveListPane                                                     | |
 * | |                                                                       | |
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
	private Button overrideDefaultJoinColumnsCheckBox;

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

		int index = subject().specifiedPrimaryKeyJoinColumnsSize();

		IPrimaryKeyJoinColumn joinColumn = subject().addSpecifiedPrimaryKeyJoinColumn(index);
		joinColumn.setSpecifiedName(stateObject.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(stateObject.getSpecifiedReferencedColumnName());
	}

	private void addPrimaryKeyJoinColumn() {
		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(shell(), subject());
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

	String buildDefaultJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
		return NLS.bind(JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_mappingBetweenTwoParamsDefault, joinColumn.getName(), joinColumn.getReferencedColumnName());
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

	private WritablePropertyValueModel<IJoinColumn> buildJoinColumnHolder() {
		return new SimplePropertyValueModel<IJoinColumn>();
	}

	private String buildJoinColumnLabel(IPrimaryKeyJoinColumn joinColumn) {
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

	private ListValueModel/*<IPrimaryKeyJoinColumn>*/ buildJoinColumnsListHolder() {
		return new ListAspectAdapter<IEntity, IPrimaryKeyJoinColumn>(
			getSubjectHolder(),
			IEntity.DEFAULT_PRIMARY_KEY_JOIN_COLUMNS_LIST,
			IEntity.SPECIFIED_PRIMARY_KEY_JOIN_COLUMNS_LIST)
		{
			@Override
			protected ListIterator<IPrimaryKeyJoinColumn> listIterator_() {
				return subject.primaryKeyJoinColumns();
			}
		};
	}

	private ILabelProvider buildJoinColumnsListLabelProvider() {
		return new LabelProvider() {
			@Override
			public String getText(Object element) {
				IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) element;
				return subject().specifiedPrimaryKeyJoinColumnsSize() > 0 ?
					buildJoinColumnLabel(joinColumn) :
					buildDefaultJoinColumnLabel(joinColumn);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		// TODO
		return new SimplePropertyValueModel<Boolean>();
	}

	private SelectionAdapter buildOverrideDefaultJoinColumnSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				IEntity entity = subject();

				if (button.getSelection()) {
					IPrimaryKeyJoinColumn defaultJoinColumn = entity.specifiedPrimaryKeyJoinColumns().next();
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IPrimaryKeyJoinColumn pkJoinColumn = entity.addSpecifiedPrimaryKeyJoinColumn(0);
					pkJoinColumn.setSpecifiedName(columnName);
					pkJoinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
				else {
					for (int index = entity.specifiedPrimaryKeyJoinColumnsSize(); --index >= 0; ) {
						entity.removeSpecifiedPrimaryKeyJoinColumn(index);
					}
				}
			}
		};
	}

	private ListValueModel/*<IPrimaryKeyJoinColumn>*/ buildSortedJoinColumnsListHolder() {
		return new SortedListValueModelAdapter<IPrimaryKeyJoinColumn>(
			buildJoinColumnsListHolder()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		overrideDefaultJoinColumnsCheckBox.setSelection(subject().specifiedPrimaryKeyJoinColumnsSize() > 0);
	}

	private void editJoinColumn(PrimaryKeyJoinColumnStateObject stateObject) {

		IPrimaryKeyJoinColumn joinColumn = stateObject.getJoinColumn();

		String name = stateObject.getSelectedName();
		String referencedColumnName = stateObject.getSpecifiedReferencedColumnName();

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
	}

	private void editPrimaryKeyJoinColumn(ObjectListSelectionModel listSelectionModel) {

		IPrimaryKeyJoinColumn joinColumn = (IPrimaryKeyJoinColumn) listSelectionModel.selectedValue();

		PrimaryKeyJoinColumnDialog dialog = new PrimaryKeyJoinColumnDialog(shell(), joinColumn);
		dialog.openDialog(buildEditPrimaryKeyJoinColumnPostExecution());
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Override Default Join Columns check box
		overrideDefaultJoinColumnsCheckBox = buildCheckBox(
			buildSubPane(container, 0, groupBoxMargin, 0, groupBoxMargin),
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_overrideDefaultPrimaryKeyJoinColumns,
			buildOverrideDefaultJoinColumnHolder());

		overrideDefaultJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultJoinColumnSelectionListener()
		);

		// Primary Key Join Columns group pane
		Group pkJoinColumnsGroup = buildTitledPane(
			container,
			JptUiMappingsMessages.PrimaryKeyJoinColumnsComposite_primaryKeyJoinColumn
		);

		// Primary Key Join Columns list pane
		new AddRemoveListPane<IEntity>(
			this,
			pkJoinColumnsGroup,
			buildJoinColumnsAdapter(),
			buildSortedJoinColumnsListHolder(),
			buildJoinColumnHolder(),
			buildJoinColumnsListLabelProvider(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_COLUMNS
		);
	}
}