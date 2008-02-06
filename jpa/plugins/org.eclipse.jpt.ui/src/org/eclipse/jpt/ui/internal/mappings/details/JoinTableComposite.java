/*******************************************************************************
 * Copyright (c) 2005, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJoinTable;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |         ---------------------------------------------------------------   |
 * |   Name: |                                                           |v|   |
 * |         ---------------------------------------------------------------   |
 * |                                                                           |
 * |   x Override Default Join Columns                                         |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JoinColumnsComposite                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * |   x Override Default Inverse Join Columns                                 |
 * |                                                                           |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | JoinColumnsComposite                                                  | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IJoinTable
 * @see OneToManyMappingComposite - A container of this pane
 * @see ManyToManyMappingComposite - A container of this pane
 * @see JoinColumnsComposite
 *
 * @version 2.0
 * @since 1.0
 */
public class JoinTableComposite extends AbstractFormPane<IJoinTable>
{
	private Button overrideDefaultInverseJoinColumnsCheckBox;
	private Button overrideDefaultJoinColumnsCheckBox;

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinTableComposite(AbstractFormPane<?> parentPane,
	                          PropertyValueModel<? extends IJoinTable> subjectHolder,
	                          Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IJoinTable</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinTableComposite(PropertyValueModel<? extends IJoinTable> subjectHolder,
	                          Composite parent,
	                          IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private void addInverseJoinColumn(IJoinTable joinTable) {

		InverseJoinColumnDialog dialog = new InverseJoinColumnDialog(shell(), joinTable);
		dialog.openDialog(buildAddInverseJoinColumnPostExecution());
	}

	private void addInverseJoinColumnFromDialog(JoinColumnInJoinTableStateObject stateObject) {

		int index = subject().specifiedInverseJoinColumnsSize();
		IJoinColumn joinColumn = subject().addSpecifiedInverseJoinColumn(index);
		joinColumn.setSpecifiedName(stateObject.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(stateObject.getSpecifiedReferencedColumnName());
	}

	private void addJoinColumn(IJoinTable joinTable) {

		JoinColumnInJoinTableDialog dialog = new JoinColumnInJoinTableDialog(shell(), joinTable);
		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumnFromDialog(JoinColumnInJoinTableStateObject stateObject) {

		int index = subject().specifiedJoinColumnsSize();
		IJoinColumn joinColumn = subject().addSpecifiedJoinColumn(index);
		joinColumn.setSpecifiedName(stateObject.getSelectedName());
		joinColumn.setSpecifiedReferencedColumnName(stateObject.getSpecifiedReferencedColumnName());
	}

	private PostExecution<InverseJoinColumnDialog> buildAddInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnDialog>() {
			public void execute(InverseJoinColumnDialog dialog) {
				if (dialog.wasConfirmed()) {
					addInverseJoinColumnFromDialog(dialog.subject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumnFromDialog(dialog.subject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildEditInverseJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.subject());
				}
			}
		};
	}

	private InverseJoinColumnsProvider buildInverseJoinColumnsEditor() {
		return new InverseJoinColumnsProvider();
	}

	private JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultHolder() {
		// TODO
		return new SimplePropertyValueModel<Boolean>();
	}

	private SelectionListener buildOverrideDefaultInverseSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				IJoinTable joinTable = subject();

				if (button.getSelection()) {
					IJoinColumn defaultJoinColumn = joinTable.getDefaultInverseJoinColumn(); //TODO null check, override default button disabled
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IJoinColumn joinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
				else {
					for (int index = joinTable.specifiedInverseJoinColumnsSize(); --index >= 0; ) {
						joinTable.removeSpecifiedJoinColumn(index);
					}
				}
			}
		};
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Button button = (Button) e.widget;
				IJoinTable joinTable = subject();

				if (button.getSelection()) {
					IJoinColumn defaultJoinColumn = joinTable.getDefaultJoinColumn(); //TODO null check, override default button disabled
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					IJoinColumn joinColumn = joinTable.addSpecifiedJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
				else {
					for (int index = joinTable.specifiedJoinColumnsSize(); --index >= 0; ) {
						joinTable.removeSpecifiedJoinColumn(index);
					}
				}
			}
		};
	}

	private Composite buildPane(Composite container, int groupBoxMargin) {
		return buildSubPane(container, groupBoxMargin, 0, groupBoxMargin, 0, groupBoxMargin);
	}


	private TableCombo<IJoinTable> buildTableCombo(Composite container) {

		return new TableCombo<IJoinTable>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IJoinTable.DEFAULT_NAME_PROPERTY);
				propertyNames.add(IJoinTable.SPECIFIED_NAME_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedName(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
			}

			private Schema tableSchema() {
				return database().schemaNamed(subject().getSchema());
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}

			@Override
			protected Iterator<String> values() {
				Schema schema = tableSchema();

				if (schema != null) {
					return schema.tableNames();
				}

				return EmptyIterator.instance();
			}
		};
	}

	private void editInverseJoinColumn(IJoinColumn joinColumn) {

		InverseJoinColumnDialog dialog =
			new InverseJoinColumnDialog(shell(), joinColumn);

		dialog.openDialog(buildEditInverseJoinColumnPostExecution());
	}

	private void editJoinColumn(IJoinColumn joinColumn) {

		JoinColumnInJoinTableDialog dialog =
			new JoinColumnInJoinTableDialog(shell(), joinColumn);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumnInJoinTableStateObject stateObject) {

		IJoinColumn joinColumn = stateObject.getJoinColumn();
		String name = stateObject.getSelectedName();
		String referencedColumnName = stateObject.getSpecifiedReferencedColumnName();

		if (stateObject.isDefaultNameSelected()) {
			if (joinColumn.getSpecifiedName() != null) {
				joinColumn.setSpecifiedName(null);
			}
		}
		else if (joinColumn.getSpecifiedName() == null ||
		        !joinColumn.getSpecifiedName().equals(name)){

			joinColumn.setSpecifiedName(name);
		}

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

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = groupBoxMargin();

		// Name widgets
		TableCombo<IJoinTable> tableCombo = buildTableCombo(container);

		buildLabeledComposite(
			buildPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinTableComposite_name,
			tableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_JOIN_TABLE_NAME
		);

		// Override Default Join Columns check box
		overrideDefaultJoinColumnsCheckBox = buildCheckBox(
			buildPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinTableComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultHolder()
		);

		overrideDefaultJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultSelectionListener()
		);

		// Join Columns widgets
		Group joinColumnGroupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.JoinTableComposite_joinColumn
		);

		new JoinColumnsComposite<IJoinTable>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor()
		);

		// Override Default Inverse Join Columns check box
		overrideDefaultInverseJoinColumnsCheckBox = buildCheckBox(
			buildPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinTableComposite_overrideDefaultInverseJoinColumns,
			buildOverrideDefaultHolder()
		);

		overrideDefaultInverseJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultInverseSelectionListener()
		);

		// Inverse Join Columns widgets
		Group inverseJoinColumnGroupPane = buildTitledPane(
			container,
			JptUiMappingsMessages.JoinTableComposite_inverseJoinColumn
		);

		new JoinColumnsComposite<IJoinTable>(
			this,
			inverseJoinColumnGroupPane,
			buildInverseJoinColumnsEditor()
		);
	}

	private class InverseJoinColumnsProvider implements IJoinColumnsEditor<IJoinTable> {

		public void addJoinColumn(IJoinTable subject) {
			JoinTableComposite.this.addInverseJoinColumn(subject);
		}

		public void editJoinColumn(IJoinTable subject, IJoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(IJoinTable subject) {
			return subject.containsSpecifiedInverseJoinColumns();
		}

		public ListIterator<IJoinColumn> joinColumns(IJoinTable subject) {
			return subject.inverseJoinColumns();
		}

		public String[] propertyNames() {
			return new String[] {
				IJoinTable.DEFAULT_INVERSE_JOIN_COLUMN,//TODO
				IJoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST
			};
		}

		public void removeJoinColumns(IJoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedInverseJoinColumn(selectedIndices[index]);
			}
		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<IJoinTable> {

		public void addJoinColumn(IJoinTable subject) {
			JoinTableComposite.this.addJoinColumn(subject);
		}

		public void editJoinColumn(IJoinTable subject, IJoinColumn joinColumn) {
			JoinTableComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(IJoinTable subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public ListIterator<IJoinColumn> joinColumns(IJoinTable subject) {
			return subject.joinColumns();
		}

		public String[] propertyNames() {
			return new String[] {
				IJoinTable.DEFAULT_JOIN_COLUMN, //TODO
				IJoinTable.SPECIFIED_JOIN_COLUMNS_LIST
			};
		}

		public void removeJoinColumns(IJoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}
	}
}