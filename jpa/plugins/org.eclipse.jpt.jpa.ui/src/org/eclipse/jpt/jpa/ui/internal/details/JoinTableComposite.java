/*******************************************************************************
 * Copyright (c) 2005, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.CollectionValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinTable;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class JoinTableComposite
	extends ReferenceTableComposite<JoinTable>
{
	private JoinColumnsComposite<JoinTable> inverseJoinColumnsComposite;


	public JoinTableComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends JoinTable> tableModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite) {
		super(parentPane, tableModel, enabledModel, parentComposite);
	}

	@Override
	protected Predicate<JoinTable> buildTableIsVirtualPredicate() {
		return TABLE_IS_VIRTUAL_PREDICATE;
	}

	public static final PredicateAdapter<JoinTable> TABLE_IS_VIRTUAL_PREDICATE = new TableIsVirtualPredicate();

	public static class TableIsVirtualPredicate
		extends PredicateAdapter<JoinTable>
	{
		@Override
		public boolean evaluate(JoinTable table) {
			return table.getParent().getRelationship().isVirtual();
		}
	}

	@Override
	protected Composite addComposite(Composite container) {
		return this.addSubPane(container, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_NAME);
		this.addTableCombo(container, JpaHelpContextIds.MAPPING_JOIN_TABLE_NAME);
		
		// schema widgets
		this.addLabel(container, JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_SCHEMA);
		this.addSchemaCombo(container, JpaHelpContextIds.MAPPING_JOIN_TABLE_SCHEMA);

		// catalog widgets
		this.addLabel(container, JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_CATALOG);
		this.addCatalogCombo(container, JpaHelpContextIds.MAPPING_JOIN_TABLE_CATALOG);

		// Join Columns group pane
		Group joinColumnGroupPane = addTitledGroup(
			container,
			JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_JOIN_COLUMN
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		joinColumnGroupPane.setLayoutData(gridData);

		// Override Default Join Columns check box
		addCheckBox(
			joinColumnGroupPane,
			JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_OVERRIDE_DEFAULT_JOIN_COLUMNS,
			buildOverrideDefaultJoinColumnModel(),
			null
		);

		this.joinColumnsComposite = new JoinColumnsComposite<>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor(),
			buildJoinColumnsPaneEnabledModel()
		);

		// Inverse Join Columns group pane
		Group inverseJoinColumnGroupPane = addTitledGroup(
			container,
			JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_INVERSE_JOIN_COLUMN
		);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		inverseJoinColumnGroupPane.setLayoutData(gridData);

		// Override Default Inverse Join Columns check box
		addCheckBox(
			inverseJoinColumnGroupPane,
			JptJpaUiDetailsMessages.JOIN_TABLE_COMPOSITE_OVERRIDE_DEFAULT_INVERSE_JOIN_COLUMNS,
			buildOverrideDefaultInverseJoinColumnModel(),
			null
		);

		this.inverseJoinColumnsComposite = new JoinColumnsComposite<>(
			this,
			inverseJoinColumnGroupPane,
			buildInverseJoinColumnsEditor(),
			this.buildInverseJoinColumnsPaneEnabledModel()
		);
	}

	protected PropertyValueModel<Boolean> buildInverseJoinColumnsPaneEnabledModel() {
		return CollectionValueModelTools.and(this.buildTableIsSpecifiedModel(), this.buildSpecifiedInverseJoinColumnsIsNotEmptyModel());
	}

	protected PropertyValueModel<Boolean> buildSpecifiedInverseJoinColumnsIsNotEmptyModel() {
		return ListValueModelTools.isNotEmptyPropertyValueModel(this.buildSpecifiedInverseJoinColumnsModel());
	}

	SpecifiedJoinColumn addInverseJoinColumn(JoinTable joinTable) {

		InverseJoinColumnInJoinTableDialog dialog = new InverseJoinColumnInJoinTableDialog(this.getShell(), this.getResourceManager(), joinTable);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			return addInverseJoinColumnFromDialog(dialog.getSubject());
		}
		return null;
	}

	SpecifiedJoinColumn addInverseJoinColumnFromDialog(InverseJoinColumnInJoinTableStateObject stateObject) {

		SpecifiedJoinTable subject = (SpecifiedJoinTable) getSubject();
		int index = subject.getSpecifiedInverseJoinColumnsSize();

		SpecifiedJoinColumn joinColumn = subject.addSpecifiedInverseJoinColumn(index);
		stateObject.updateJoinColumn(joinColumn);
		return joinColumn;
	}

	private void setSelectedInverseJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.inverseJoinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}

	private InverseJoinColumnsProvider buildInverseJoinColumnsEditor() {
		return new InverseJoinColumnsProvider();
	}

	private ModifiablePropertyValueModel<Boolean> buildOverrideDefaultInverseJoinColumnModel() {
		return ListValueModelTools.isNotEmptyModifiablePropertyValueModel(this.buildSpecifiedInverseJoinColumnsModel(), new OverrideDefaultInverseJoinColumnModelSetValueClosure());
	}
	
	protected ListValueModel<JoinColumn> buildSpecifiedInverseJoinColumnsModel() {
		return new ListAspectAdapter<JoinTable, JoinColumn>(this.getSubjectHolder(), JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<JoinColumn> getListIterable() {
				return IterableTools.upcast(this.subject.getSpecifiedInverseJoinColumns());
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedInverseJoinColumnsSize();
			}
		};
	}


	void editInverseJoinColumn(InverseJoinColumnInJoinTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	void editInverseJoinColumn(JoinColumn joinColumn) {

		InverseJoinColumnInJoinTableDialog dialog = new InverseJoinColumnInJoinTableDialog(this.getShell(), this.getResourceManager(), this.getSubject(), joinColumn);


		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			editInverseJoinColumn(dialog.getSubject());
		}
	}

	void updateInverseJoinColumns(boolean selected) {
		if (this.isPopulating()) {
			return;
		}
		
		SpecifiedJoinTable joinTable = (SpecifiedJoinTable) this.getSubject();
		if (joinTable == null) {
			return;
		}
		
		this.setPopulating(true);

		try {
			if (selected) {
				joinTable.convertDefaultInverseJoinColumnToSpecified();
				setSelectedInverseJoinColumn(joinTable.getSpecifiedInverseJoinColumn(0));
			} else {
				joinTable.clearSpecifiedInverseJoinColumns();
			}
		} finally {
			this.setPopulating(false);
		}
	}



	class InverseJoinColumnsProvider implements JoinColumnsEditor<JoinTable> {

		public SpecifiedJoinColumn addJoinColumn(JoinTable subject) {
			return JoinTableComposite.this.addInverseJoinColumn(subject);
		}

		public JoinColumn getDefaultJoinColumn(JoinTable subject) {
			return subject.getDefaultInverseJoinColumn();
		}

		public String getDefaultPropertyName() {
			return JoinTable.DEFAULT_INVERSE_JOIN_COLUMN;
		}

		public void editJoinColumn(JoinTable subject, JoinColumn joinColumn) {
			JoinTableComposite.this.editInverseJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(JoinTable subject) {
			return subject.hasSpecifiedInverseJoinColumns();
		}

		public void removeJoinColumn(JoinTable subject, SpecifiedJoinColumn joinColumn) {
			((SpecifiedJoinTable) subject).removeSpecifiedInverseJoinColumn(joinColumn);
		}

		public ListIterable<JoinColumn> getSpecifiedJoinColumns(JoinTable subject) {
			return new SuperListIterableWrapper<>(subject.getSpecifiedInverseJoinColumns());
		}

		public int getSpecifiedJoinColumnsSize(JoinTable subject) {
			return subject.getSpecifiedInverseJoinColumnsSize();
		}

		public String getSpecifiedJoinColumnsListPropertyName() {
			return JoinTable.SPECIFIED_INVERSE_JOIN_COLUMNS_LIST;
		}
	}
	
	class OverrideDefaultInverseJoinColumnModelSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			updateInverseJoinColumns(value);
		}
	}
}
