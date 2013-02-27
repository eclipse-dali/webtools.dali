/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.iterable.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyModifiablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.SpecifiedReferenceTable;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnsComposite.JoinColumnsEditor;
import org.eclipse.jpt.jpa.ui.internal.details.db.CatalogCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.SchemaCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.TableCombo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public abstract class ReferenceTableComposite<T extends ReadOnlyReferenceTable>
	extends Pane<T>
{
	protected Button overrideDefaultJoinColumnsCheckBox;

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


	protected PropertyValueModel<Boolean> buildJoinColumnsEnabledModel() {
		return new JoinColumnsEnabledModel();
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

	protected ModifiablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}
		
	ListValueModel<JoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<T, JoinColumn>(getSubjectHolder(), ReadOnlyReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<JoinColumn> getListIterable() {
				return new SuperListIterableWrapper<JoinColumn>(this.subject.getSpecifiedJoinColumns());
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
				propertyNames.add(Table.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(Table.SPECIFIED_CATALOG_PROPERTY);
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
				propertyNames.add(Table.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(Table.SPECIFIED_SCHEMA_PROPERTY);
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
				ReadOnlyReferenceTable table = this.getSubject();
				return (table instanceof SpecifiedReferenceTable) ? (SpecifiedReferenceTable) table : null;
			}

			@Override
			protected String getHelpId() {
				return helpId;
			}
		};
	}

	/* CU private */ static final Collection<String> SCHEMA_PICK_LIST_PROPERTIES = Arrays.asList(new String[] {
		Table.DEFAULT_CATALOG_PROPERTY,
		Table.SPECIFIED_CATALOG_PROPERTY
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
				ReadOnlyReferenceTable table = this.getSubject();
				return (table instanceof SpecifiedReferenceTable) ? (SpecifiedReferenceTable) table : null;
			}

			@Override
			protected String getHelpId() {
				return helpId;
			}
		};
	}

	/* CU private */ static final Collection<String> TABLE_PICK_LIST_PROPERTIES = Arrays.asList(ArrayTools.addAll(SCHEMA_PICK_LIST_PROPERTIES.toArray(new String[0]),
		Table.DEFAULT_SCHEMA_PROPERTY,
		Table.SPECIFIED_SCHEMA_PROPERTY
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

	void updateJoinColumns() {
		if (this.isPopulating()) {
			return;
		}
		
		SpecifiedReferenceTable referenceTable = (SpecifiedReferenceTable) this.getSubject();
		if (referenceTable == null) {
			return;
		}
		
		boolean selected = this.overrideDefaultJoinColumnsCheckBox.getSelection();
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
			return ReadOnlyReferenceTable.DEFAULT_JOIN_COLUMN_PROPERTY;
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
			return new SuperListIterableWrapper<JoinColumn>(subject.getSpecifiedJoinColumns());
		}

		public int getSpecifiedJoinColumnsSize(T subject) {
			return subject.getSpecifiedJoinColumnsSize();
		}

		public String getSpecifiedJoinColumnsListPropertyName() {
			return ReadOnlyReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST;
		}
	}
	
	
	private class OverrideDefaultJoinColumnHolder extends ListPropertyValueModelAdapter<Boolean>
	    implements ModifiablePropertyValueModel<Boolean> {
	
		public OverrideDefaultJoinColumnHolder() {
			super(buildSpecifiedJoinColumnsListHolder());
		}
	
		@Override
		protected Boolean buildValue() {
			return Boolean.valueOf(this.listModel.size() > 0);
		}
	
		public void setValue(Boolean value) {
			updateJoinColumns();
		}
	}

	
	/* CU private */ class JoinColumnsEnabledModel 
		extends TransformationPropertyValueModel<T, Boolean> 
	{
		private StateChangeListener stateChangeListener;
		
		
		JoinColumnsEnabledModel() {
			super(
				new ValueListAdapter<T>(
					new ReadOnlyModifiablePropertyValueModelWrapper<T>(getSubjectHolder()), 
					ReadOnlyReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST
				)
			);
			this.stateChangeListener = buildStateChangeListener();
		}
		
		
		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					JoinColumnsEnabledModel.this.valueStateChanged();
				}
			};
		}
		
		void valueStateChanged() {
			Object old = this.value;
			this.firePropertyChanged(VALUE, old, this.value = this.transform(this.valueModel.getValue()));
		}
		
		@Override
		protected Boolean transform(T v) {
			return (v == null) ? Boolean.FALSE : super.transform(v);
		}
		
		@Override
		protected Boolean transform_(T v) {
			boolean virtual = ReferenceTableComposite.this.tableIsVirtual(v);
			return Boolean.valueOf(! virtual && v.getSpecifiedJoinColumnsSize() > 0);
		}
		
		@Override
		protected void engageModel() {
			super.engageModel();
			this.valueModel.addStateChangeListener(this.stateChangeListener);
		}
		
		@Override
		protected void disengageModel() {
			this.valueModel.removeStateChangeListener(this.stateChangeListener);
			super.disengageModel();
		}
	}
	
	protected abstract boolean tableIsVirtual(T referenceTable);
}
