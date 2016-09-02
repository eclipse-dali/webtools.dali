/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Arrays;
import java.util.Collection;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.closure.BooleanClosure;
import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListValueModelTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateTools;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.predicate.Predicate;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedReferenceTable;
import org.eclipse.jpt.jpa.core.context.SpecifiedSchemaReference;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.TableCombo;
import org.eclipse.swt.widgets.Composite;

public abstract class ReferenceTableComposite<T extends ReferenceTable>
	extends Pane<T>
{
	protected JoinColumnsComposite<T> joinColumnsComposite;

	protected ReferenceTableComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> tableModel,
			Composite parentComposite) {
		super(parentPane, tableModel, parentComposite);
	}

	protected ReferenceTableComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> tableModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite) {
		super(parentPane, tableModel, enabledModel, parentComposite);
	}


	protected PropertyValueModel<Boolean> buildJoinColumnsPaneEnabledModel() {
		return PropertyValueModelTools.and(this.buildTableIsSpecifiedModel(), this.buildSpecifiedJoinColumnsIsNotEmptyModel());
	}

	protected PropertyValueModel<Boolean> buildTableIsSpecifiedModel() {
		return PropertyValueModelTools.valueAffirms(this.getSubjectHolder(), this.buildTableIsSpecifiedPredicate(), false);
	}

	protected Predicate<T> buildTableIsSpecifiedPredicate() {
		return PredicateTools.not(this.buildTableIsVirtualPredicate());
	}

	protected abstract Predicate<T> buildTableIsVirtualPredicate();

	protected PropertyValueModel<Boolean> buildSpecifiedJoinColumnsIsNotEmptyModel() {
		return ListValueModelTools.isNotEmptyPropertyValueModel(this.buildSpecifiedJoinColumnsModel());
	}

	SpecifiedJoinColumn addJoinColumn(T referenceTable) {

		JoinColumnInReferenceTableDialog dialog = new JoinColumnInReferenceTableDialog(this.getShell(), this.getResourceManager(), referenceTable);

		dialog.setBlockOnOpen(true);
		dialog.open();
		return (dialog.wasConfirmed()) ? addJoinColumnFromDialog(dialog.getSubject()) : null;
	}

	SpecifiedJoinColumn addJoinColumnFromDialog(JoinColumnInReferenceTableStateObject stateObject) {
		SpecifiedJoinColumn joinColumn = ((SpecifiedReferenceTable) getSubject()).addSpecifiedJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		return joinColumn;
	}

	protected JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	protected ModifiablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnModel() {
		return ListValueModelTools.isNotEmptyModifiablePropertyValueModel(this.buildSpecifiedJoinColumnsModel(), new OverrideDefaultJoinColumnModelSetValueClosure());
	}

	protected ListValueModel<JoinColumn> buildSpecifiedJoinColumnsModel() {
		return new ListAspectAdapter<T, JoinColumn>(this.getSubjectHolder(), ReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<JoinColumn> getListIterable() {
				return IterableTools.upcast(this.subject.getSpecifiedJoinColumns());
			}
			@Override
			protected int size_() {
				return this.subject.getSpecifiedJoinColumnsSize();
			}
		};
	}

	protected CatalogCombo<T> addCatalogCombo(Composite container, final String helpId) {

		return new CatalogCombo<T>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SpecifiedSchemaReference.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(SpecifiedSchemaReference.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultCatalog();
			}

			@Override
			protected void setValue(String value) {
				((SpecifiedReferenceTable) this.getSubject()).setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedCatalog();
			}

			@Override
			protected String getHelpId() {
				return helpId;
			}
		};
	}

	protected SchemaCombo<T> addSchemaCombo(Composite container, final String helpId) {

		return new SchemaCombo<T>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(SpecifiedSchemaReference.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(SpecifiedSchemaReference.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.addAll(SCHEMA_PICK_LIST_PROPERTIES);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (SCHEMA_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
				} else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultSchema();
			}

			@Override
			protected void setValue(String value) {
				((SpecifiedReferenceTable) this.getSubject()).setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedSchema();
			}
			
			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				SpecifiedReferenceTable table = this.getTable();
				return (table == null) ? null : table.getDbSchemaContainer();
			}

			protected SpecifiedReferenceTable getTable() {
				ReferenceTable table = this.getSubject();
				return (table instanceof SpecifiedReferenceTable) ? (SpecifiedReferenceTable) table : null;
			}

			@Override
			protected String getHelpId() {
				return helpId;
			}
		};
	}

	/* CU private */ static final Collection<String> SCHEMA_PICK_LIST_PROPERTIES = Arrays.asList(new String[] {
		SpecifiedSchemaReference.DEFAULT_CATALOG_PROPERTY,
		SpecifiedSchemaReference.SPECIFIED_CATALOG_PROPERTY
	});

	protected TableCombo<T> addTableCombo(Composite container, final String helpId) {

		return new TableCombo<T>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Table.DEFAULT_NAME_PROPERTY);
				propertyNames.add(Table.SPECIFIED_NAME_PROPERTY);
				propertyNames.addAll(TABLE_PICK_LIST_PROPERTIES);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (TABLE_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
				} else {
					super.propertyChanged(propertyName);
				}
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				((SpecifiedReferenceTable) this.getSubject()).setSpecifiedName(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedName();
			}

			@Override
			protected Schema getDbSchema_() {
				SpecifiedReferenceTable table = this.getTable();
				return (table == null) ? null : table.getDbSchema();
			}

			protected SpecifiedReferenceTable getTable() {
				ReferenceTable table = this.getSubject();
				return (table instanceof SpecifiedReferenceTable) ? (SpecifiedReferenceTable) table : null;
			}

			@Override
			protected String getHelpId() {
				return helpId;
			}
		};
	}

	/* CU private */ static final Collection<String> TABLE_PICK_LIST_PROPERTIES = Arrays.asList(ArrayTools.addAll(SCHEMA_PICK_LIST_PROPERTIES.toArray(StringTools.EMPTY_STRING_ARRAY),
		new String[] {
			SpecifiedSchemaReference.DEFAULT_SCHEMA_PROPERTY,
			SpecifiedSchemaReference.SPECIFIED_SCHEMA_PROPERTY
		}
	));

	void editJoinColumn(JoinColumn joinColumn) {

		JoinColumnInReferenceTableDialog dialog = new JoinColumnInReferenceTableDialog(this.getShell(), this.getResourceManager(), this.getSubject(), joinColumn);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			editJoinColumn(dialog.getSubject());
		}
	}

	void editJoinColumn(JoinColumnInReferenceTableStateObject stateObject) {
		stateObject.updateJoinColumn(stateObject.getJoinColumn());
	}

	void updateJoinColumns(boolean selected) {
		if (this.isPopulating()) {
			return;
		}
		
		SpecifiedReferenceTable referenceTable = (SpecifiedReferenceTable) this.getSubject();
		if (referenceTable == null) {
			return;
		}
		
		this.setPopulating(true);

		try {
			if (selected) {
				referenceTable.convertDefaultJoinColumnToSpecified();
				setSelectedJoinColumn(referenceTable.getSpecifiedJoinColumn(0));
			} else {
				referenceTable.clearSpecifiedJoinColumns();
			}
		} finally {
			this.setPopulating(false);
		}
	}

	private void setSelectedJoinColumn(SpecifiedJoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}

	class JoinColumnsProvider implements JoinColumnsEditor<T> {

		public SpecifiedJoinColumn addJoinColumn(T subject) {
			return ReferenceTableComposite.this.addJoinColumn(subject);
		}

		public JoinColumn getDefaultJoinColumn(T subject) {
			return subject.getDefaultJoinColumn();
		}

		public String getDefaultPropertyName() {
			return ReferenceTable.DEFAULT_JOIN_COLUMN_PROPERTY;
		}

		public void editJoinColumn(T subject, JoinColumn joinColumn) {
			ReferenceTableComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(T subject) {
			return subject.hasSpecifiedJoinColumns();
		}

		public void removeJoinColumn(T subject, SpecifiedJoinColumn joinColumn) {
			((SpecifiedReferenceTable) subject).removeSpecifiedJoinColumn(joinColumn);
		}

		public ListIterable<JoinColumn> getSpecifiedJoinColumns(T subject) {
			return IterableTools.upcast(subject.getSpecifiedJoinColumns());
		}

		public int getSpecifiedJoinColumnsSize(T subject) {
			return subject.getSpecifiedJoinColumnsSize();
		}

		public String getSpecifiedJoinColumnsListPropertyName() {
			return ReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
	
	
	class OverrideDefaultJoinColumnModelSetValueClosure
		implements BooleanClosure.Adapter
	{
		public void execute(boolean value) {
			updateJoinColumns(value);
		}
	}
}
