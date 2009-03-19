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

import java.util.Collection;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JoinTable;
import org.eclipse.jpt.core.context.Table;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.mappings.details.JoinColumnsComposite.IJoinColumnsEditor;
import org.eclipse.jpt.ui.internal.widgets.FormPane;
import org.eclipse.jpt.ui.internal.widgets.PostExecution;
import org.eclipse.jpt.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.ListValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
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
 * | - Join Columns ---------------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | JoinColumnsComposite                                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | - Inverse Join Columns -------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | JoinColumnsComposite                                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link JoinTable}
 * @see {@link JoinTableJoiningStrategyPane}
 * @see {@link JoinColumnsComposite
 *
 * @version 2.1
 * @since 1.0
 */
public class JoinTableComposite extends FormPane<JoinTable>
{
	private WritablePropertyValueModel<Boolean> inverseJoinColumnsPaneEnablerHolder;
	private WritablePropertyValueModel<Boolean> joinColumnsPaneEnablerHolder;
	private Button overrideDefaultInverseJoinColumnsCheckBox;
	private Button overrideDefaultJoinColumnsCheckBox;

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public JoinTableComposite(
			FormPane<?> parentPane,
			PropertyValueModel<? extends JoinTable> subjectHolder,
			Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	/**
	 * Creates a new <code>JoinTableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IJoinTable</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public JoinTableComposite(PropertyValueModel<? extends JoinTable> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initialize() {
		super.initialize();

		this.joinColumnsPaneEnablerHolder        = buildJoinColumnsPaneEnablerHolder();
		this.inverseJoinColumnsPaneEnablerHolder = buildInverseJoinColumnsPaneEnablerHolder();
	}
	
	private WritablePropertyValueModel<Boolean> buildInverseJoinColumnsPaneEnablerHolder() {
		return new SimplePropertyValueModel<Boolean>();
	}

	private WritablePropertyValueModel<Boolean> buildJoinColumnsPaneEnablerHolder() {
		return new SimplePropertyValueModel<Boolean>();
	}

	@Override
	protected void initializeLayout(Composite container) {

		int groupBoxMargin = getGroupBoxMargin();

		// Name widgets
		TableCombo<JoinTable> tableCombo = addTableCombo(container);

		addLabeledComposite(
			addPane(container, groupBoxMargin),
			JptUiMappingsMessages.JoinTableComposite_name,
			tableCombo.getControl(),
			JpaHelpContextIds.MAPPING_JOIN_TABLE_NAME
		);

		// Join Columns group pane
		Group joinColumnGroupPane = addTitledGroup(
			container,
			JptUiMappingsMessages.JoinTableComposite_joinColumn
		);

		// Override Default Join Columns check box
		this.overrideDefaultJoinColumnsCheckBox = addCheckBox(
			addSubPane(joinColumnGroupPane, 8),
			JptUiMappingsMessages.JoinTableComposite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);

		this.overrideDefaultJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultSelectionListener()
		);

		JoinColumnsComposite<JoinTable> joinColumnsComposite = new JoinColumnsComposite<JoinTable>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor()
		);

		installJoinColumnsPaneEnabler(joinColumnsComposite);

		// Inverse Join Columns group pane
		Group inverseJoinColumnGroupPane = addTitledGroup(
			container,
			JptUiMappingsMessages.JoinTableComposite_inverseJoinColumn
		);

		// Override Default Inverse Join Columns check box
		this.overrideDefaultInverseJoinColumnsCheckBox = addCheckBox(
			addSubPane(inverseJoinColumnGroupPane, 8),
			JptUiMappingsMessages.JoinTableComposite_overrideDefaultInverseJoinColumns,
			buildOverrideDefaultInverseJoinColumnHolder(),
			null
		);

		this.overrideDefaultInverseJoinColumnsCheckBox.addSelectionListener(
			buildOverrideDefaultInverseSelectionListener()
		);

		JoinColumnsComposite<JoinTable> inverseJoinColumnsComposite = new JoinColumnsComposite<JoinTable>(
			this,
			inverseJoinColumnGroupPane,
			buildInverseJoinColumnsEditor()
		);

		installInverseJoinColumnsPaneEnabler(inverseJoinColumnsComposite);
	}

	private void installInverseJoinColumnsPaneEnabler(JoinColumnsComposite<JoinTable> pane) {
		pane.installJoinColumnsPaneEnabler(this.inverseJoinColumnsPaneEnablerHolder);
	}

	private void installJoinColumnsPaneEnabler(JoinColumnsComposite<JoinTable> pane) {
		pane.installJoinColumnsPaneEnabler(this.joinColumnsPaneEnablerHolder);
	}

	private void addInverseJoinColumn(JoinTable joinTable) {

		InverseJoinColumnInJoinTableDialog dialog =
			new InverseJoinColumnInJoinTableDialog(getShell(), joinTable, null);

		dialog.openDialog(buildAddInverseJoinColumnPostExecution());
	}

	private void addInverseJoinColumnFromDialog(InverseJoinColumnInJoinTableStateObject stateObject) {

		JoinTable subject = getSubject();
		int index = subject.specifiedInverseJoinColumnsSize();

		JoinColumn joinColumn = subject.addSpecifiedInverseJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private void addJoinColumn(JoinTable joinTable) {

		JoinColumnInJoinTableDialog dialog =
			new JoinColumnInJoinTableDialog(getShell(), joinTable, null);

		dialog.openDialog(buildAddJoinColumnPostExecution());
	}

	private void addJoinColumnFromDialog(JoinColumnInJoinTableStateObject stateObject) {

		JoinTable subject = getSubject();
		int index = subject.specifiedJoinColumnsSize();

		JoinColumn joinColumn = getSubject().addSpecifiedJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
	}

	private PostExecution<InverseJoinColumnInJoinTableDialog> buildAddInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnInJoinTableDialog>() {
			public void execute(InverseJoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addInverseJoinColumnFromDialog(dialog.getSubject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildAddJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					addJoinColumnFromDialog(dialog.getSubject());
				}
			}
		};
	}

	private PostExecution<InverseJoinColumnInJoinTableDialog> buildEditInverseJoinColumnPostExecution() {
		return new PostExecution<InverseJoinColumnInJoinTableDialog>() {
			public void execute(InverseJoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editInverseJoinColumn(dialog.getSubject());
				}
			}
		};
	}

	private PostExecution<JoinColumnInJoinTableDialog> buildEditJoinColumnPostExecution() {
		return new PostExecution<JoinColumnInJoinTableDialog>() {
			public void execute(JoinColumnInJoinTableDialog dialog) {
				if (dialog.wasConfirmed()) {
					editJoinColumn(dialog.getSubject());
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

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}

	private WritablePropertyValueModel<Boolean> buildOverrideDefaultInverseJoinColumnHolder() {
		return new OverrideDefaultInverseJoinColumnHolder();
	}
		
	private ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<JoinTable, JoinColumn>(getSubjectHolder(), JoinTable.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<JoinColumn> listIterator_() {
				return subject.specifiedJoinColumns();
			}

			@Override
			protected int size_() {
				return subject.specifiedJoinColumnsSize();
			}
		};
	}
	
	private ListValueModel<JoinColumn> buildSpecifiedInverseJoinColumnsListHolder() {
		return new ListAspectAdapter<JoinTable, JoinColumn>(getSubjectHolder(), JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterator<JoinColumn> listIterator_() {
				return subject.specifiedInverseJoinColumns();
			}

			@Override
			protected int size_() {
				return subject.specifiedInverseJoinColumnsSize();
			}
		};
	}

	private SelectionListener buildOverrideDefaultInverseSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateInverseJoinColumns();
			}
		};
	}

	private SelectionListener buildOverrideDefaultSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				updateJoinColumns();
			}
		};
	}

	private Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 10, groupBoxMargin);
	}

	private TableCombo<JoinTable> addTableCombo(Composite container) {

		return new TableCombo<JoinTable>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_NAME_PROPERTY);
				propertyNames.add(Table.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(Table.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(Table.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(Table.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(Table.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				super.propertyChanged(propertyName);
				if (propertyName == Table.DEFAULT_SCHEMA_PROPERTY 
					|| propertyName == Table.SPECIFIED_SCHEMA_PROPERTY
					|| propertyName == Table.DEFAULT_CATALOG_PROPERTY
					|| propertyName == Table.SPECIFIED_CATALOG_PROPERTY ) {
					repopulate();
				}
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				this.getSubject().setSpecifiedName(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedName();
			}

			@Override
			protected Schema getDbSchema_() {
				return this.getSubject().getDbSchema();
			}

		};
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
		updateJoinColumnPanesEnablement(true);
	}

	private void editInverseJoinColumn(InverseJoinColumnInJoinTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	private void editInverseJoinColumn(JoinColumn joinColumn) {

		InverseJoinColumnInJoinTableDialog dialog =
			new InverseJoinColumnInJoinTableDialog(getShell(), getSubject(), joinColumn);

		dialog.openDialog(buildEditInverseJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumn joinColumn) {

		JoinColumnInJoinTableDialog dialog =
			new JoinColumnInJoinTableDialog(getShell(), getSubject(), joinColumn);

		dialog.openDialog(buildEditJoinColumnPostExecution());
	}

	private void editJoinColumn(JoinColumnInJoinTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		updateJoinColumnPanesEnablement(enabled);
	}


	private void updateInverseJoinColumns() {
		if (isPopulating()) {
			return;
		}
		
		JoinTable joinTable = getSubject();
		if (joinTable == null) {
			return;
		}
		
		boolean selected = this.overrideDefaultInverseJoinColumnsCheckBox.getSelection();
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				JoinColumn defaultJoinColumn = joinTable.getDefaultInverseJoinColumn(); //TODO null check, override default button disabled

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					JoinColumn joinColumn = joinTable.addSpecifiedInverseJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
			}
			else {
				for (int index = joinTable.specifiedInverseJoinColumnsSize(); --index >= 0; ) {
					joinTable.removeSpecifiedInverseJoinColumn(index);
				}
			}

			this.inverseJoinColumnsPaneEnablerHolder.setValue(selected);
		}
		finally {
			setPopulating(false);
		}
	}

	private void updateJoinColumnPanesEnablement(boolean globalEnablement) {

		JoinTable subject      = getSubject();
		boolean enabled        = globalEnablement && (subject != null) && subject.containsSpecifiedJoinColumns();
		boolean inverseEnabled = globalEnablement && (subject != null) && subject.containsSpecifiedInverseJoinColumns();

		this.overrideDefaultJoinColumnsCheckBox.setSelection(enabled);
		this.overrideDefaultInverseJoinColumnsCheckBox.setSelection(inverseEnabled);

		this.joinColumnsPaneEnablerHolder.setValue(Boolean.valueOf(enabled));
		this.inverseJoinColumnsPaneEnablerHolder.setValue(Boolean.valueOf(inverseEnabled));
	}

	private void updateJoinColumns() {
		if (isPopulating()) {
			return;
		}
		
		JoinTable joinTable = getSubject();
		if (joinTable == null) {
			return;
		}
		
		boolean selected = this.overrideDefaultJoinColumnsCheckBox.getSelection();
		setPopulating(true);

		try {
			// Add a join column by creating a specified one using the default
			// one if it exists
			if (selected) {

				JoinColumn defaultJoinColumn = joinTable.getDefaultJoinColumn(); //TODO null check, override default button disabled

				if (defaultJoinColumn != null) {
					String columnName = defaultJoinColumn.getDefaultName();
					String referencedColumnName = defaultJoinColumn.getDefaultReferencedColumnName();

					JoinColumn joinColumn = joinTable.addSpecifiedJoinColumn(0);
					joinColumn.setSpecifiedName(columnName);
					joinColumn.setSpecifiedReferencedColumnName(referencedColumnName);
				}
			}
			else {
				for (int index = joinTable.specifiedJoinColumnsSize(); --index >= 0; ) {
					joinTable.removeSpecifiedJoinColumn(index);
				}
			}

			this.joinColumnsPaneEnablerHolder.setValue(Boolean.valueOf(selected));
		}
		finally {
			setPopulating(false);
		}
	}

	private class InverseJoinColumnsProvider implements IJoinColumnsEditor<JoinTable> {

		public void addJoinColumn(JoinTable subject) {
			JoinTableComposite.this.addInverseJoinColumn(subject);
		}

		public JoinColumn defaultJoinColumn(JoinTable subject) {
			return subject.getDefaultInverseJoinColumn();
		}

		public String defaultPropertyName() {
			return JoinTable.DEFAULT_INVERSE_JOIN_COLUMN;
		}

		public void editJoinColumn(JoinTable subject, JoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinTable subject) {
			return subject.containsSpecifiedInverseJoinColumns();
		}

		public void removeJoinColumns(JoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedInverseJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(JoinTable subject) {
			return subject.specifiedInverseJoinColumns();
		}

		public int specifiedJoinColumnsSize(JoinTable subject) {
			return subject.specifiedInverseJoinColumnsSize();
		}

		public String specifiedListPropertyName() {
			return JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
	}

	private class JoinColumnsProvider implements IJoinColumnsEditor<JoinTable> {

		public void addJoinColumn(JoinTable subject) {
			JoinTableComposite.this.addJoinColumn(subject);
		}

		public JoinColumn defaultJoinColumn(JoinTable subject) {
			return subject.getDefaultJoinColumn();
		}

		public String defaultPropertyName() {
			return JoinTable.DEFAULT_JOIN_COLUMN;
		}

		public void editJoinColumn(JoinTable subject, JoinColumn joinColumn) {
			JoinTableComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinTable subject) {
			return subject.containsSpecifiedJoinColumns();
		}

		public void removeJoinColumns(JoinTable subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; --index >= 0; ) {
				subject.removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterator<JoinColumn> specifiedJoinColumns(JoinTable subject) {
			return subject.specifiedJoinColumns();
		}

		public int specifiedJoinColumnsSize(JoinTable subject) {
			return subject.specifiedJoinColumnsSize();
		}

		public String specifiedListPropertyName() {
			return JoinTable.SPECIFIED_JOIN_COLUMNS_LIST;
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
			updateJoinColumns();
		}
	}

	
	private class OverrideDefaultInverseJoinColumnHolder extends ListPropertyValueModelAdapter<Boolean>
	    implements WritablePropertyValueModel<Boolean> {
	
		public OverrideDefaultInverseJoinColumnHolder() {
			super(buildSpecifiedInverseJoinColumnsListHolder());
		}
	
		@Override
		protected Boolean buildValue() {
			return listHolder.size() > 0;
		}
	
		public void setValue(Boolean value) {
			updateInverseJoinColumns();
		}
	}


}