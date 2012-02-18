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

import java.util.Collection;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.common.utility.internal.iterables.SuperListIterableWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.ListAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ListPropertyValueModelAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.ReadOnlyWritablePropertyValueModelWrapper;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.ValueListAdapter;
import org.eclipse.jpt.common.utility.model.event.StateChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.StateChangeListener;
import org.eclipse.jpt.common.utility.model.value.ListValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
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

	/**
	 * Creates a new <code>ReferenceTableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	protected ReferenceTableComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends T> subjectHolder,
			Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	/**
	 * Creates a new <code>ReferenceTableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>CollectionTable2_0</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	protected ReferenceTableComposite(PropertyValueModel<? extends T> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}


	protected void installJoinColumnsPaneEnabler(JoinColumnsComposite<T> pane) {
		pane.installJoinColumnsPaneEnabler(new JoinColumnPaneEnablerHolder());
	}

	void addJoinColumn(T referenceTable) {

		JoinColumnInReferenceTableDialog dialog =
			new JoinColumnInReferenceTableDialog(getShell(), referenceTable, null);

		dialog.setBlockOnOpen(true);
		dialog.open();
		if (dialog.wasConfirmed()) {
			addJoinColumnFromDialog(dialog.getSubject());
		}
	}

	void addJoinColumnFromDialog(JoinColumnInReferenceTableStateObject stateObject) {
		JoinColumn joinColumn = ((ReferenceTable) getSubject()).addSpecifiedJoinColumn();
		stateObject.updateJoinColumn(joinColumn);
		this.setSelectedJoinColumn(joinColumn);
	}

	private void setSelectedJoinColumn(JoinColumn joinColumn) {
		this.joinColumnsComposite.setSelectedJoinColumn(joinColumn);
	}

	protected JoinColumnsProvider buildJoinColumnsEditor() {
		return new JoinColumnsProvider();
	}

	protected ModifiablePropertyValueModel<Boolean> buildOverrideDefaultJoinColumnHolder() {
		return new OverrideDefaultJoinColumnHolder();
	}
		
	ListValueModel<ReadOnlyJoinColumn> buildSpecifiedJoinColumnsListHolder() {
		return new ListAspectAdapter<T, ReadOnlyJoinColumn>(getSubjectHolder(), ReadOnlyReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST) {
			@Override
			protected ListIterable<ReadOnlyJoinColumn> getListIterable() {
				return new SuperListIterableWrapper<ReadOnlyJoinColumn>(this.subject.getSpecifiedJoinColumns());
			}

			@Override
			protected int size_() {
				return this.subject.getSpecifiedJoinColumnsSize();
			}
		};
	}

	protected Composite addPane(Composite container, int groupBoxMargin) {
		return addSubPane(container, 0, groupBoxMargin, 10, groupBoxMargin);
	}

	protected TableCombo<T> addTableCombo(Composite container) {

		return new TableCombo<T>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyTable.DEFAULT_NAME_PROPERTY);
				propertyNames.add(ReadOnlyTable.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(ReadOnlyTable.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(ReadOnlyTable.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(ReadOnlyTable.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(ReadOnlyTable.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				super.propertyChanged(propertyName);
				if (propertyName == ReadOnlyTable.DEFAULT_SCHEMA_PROPERTY 
					|| propertyName == ReadOnlyTable.SPECIFIED_SCHEMA_PROPERTY
					|| propertyName == ReadOnlyTable.DEFAULT_CATALOG_PROPERTY
					|| propertyName == ReadOnlyTable.SPECIFIED_CATALOG_PROPERTY ) {
					repopulate();
				}
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultName();
			}

			@Override
			protected void setValue(String value) {
				((ReferenceTable) this.getSubject()).setSpecifiedName(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedName();
			}

			@Override
			protected Schema getDbSchema_() {
				ReferenceTable table = this.getTable();
				return (table == null) ? null : table.getDbSchema();
			}

			protected ReferenceTable getTable() {
				ReadOnlyReferenceTable table = this.getSubject();
				return (table instanceof ReferenceTable) ? (ReferenceTable) table : null;
			}
		};
	}
	
	protected SchemaCombo<T> addSchemaCombo(Composite container) {

		return new SchemaCombo<T>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyTable.DEFAULT_SCHEMA_PROPERTY);
				propertyNames.add(ReadOnlyTable.SPECIFIED_SCHEMA_PROPERTY);
				propertyNames.add(ReadOnlyTable.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(ReadOnlyTable.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				super.propertyChanged(propertyName);
				if (propertyName == ReadOnlyTable.DEFAULT_CATALOG_PROPERTY
					|| propertyName == ReadOnlyTable.SPECIFIED_CATALOG_PROPERTY ) {
					repopulate();
				}
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultSchema();
			}

			@Override
			protected void setValue(String value) {
				((ReferenceTable) this.getSubject()).setSpecifiedSchema(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedSchema();
			}
			
			@Override
			protected SchemaContainer getDbSchemaContainer_() {
				ReferenceTable table = this.getTable();
				return (table == null) ? null : table.getDbSchemaContainer();
			}

			protected ReferenceTable getTable() {
				ReadOnlyReferenceTable table = this.getSubject();
				return (table instanceof ReferenceTable) ? (ReferenceTable) table : null;
			}
		};
	}
	
	protected CatalogCombo<T> addCatalogCombo(Composite container) {

		return new CatalogCombo<T>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyTable.DEFAULT_CATALOG_PROPERTY);
				propertyNames.add(ReadOnlyTable.SPECIFIED_CATALOG_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultCatalog();
			}

			@Override
			protected void setValue(String value) {
				((ReferenceTable) this.getSubject()).setSpecifiedCatalog(value);
			}

			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedCatalog();
			}
		};
	}

	void editJoinColumn(ReadOnlyJoinColumn joinColumn) {

		JoinColumnInReferenceTableDialog dialog =
			new JoinColumnInReferenceTableDialog(getShell(), getSubject(), joinColumn);

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
		
		ReferenceTable referenceTable = (ReferenceTable) this.getSubject();
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
				for (int index = referenceTable.getSpecifiedJoinColumnsSize(); --index >= 0; ) {
					referenceTable.removeSpecifiedJoinColumn(index);
				}
			}
		} finally {
			this.setPopulating(false);
		}
	}

	class JoinColumnsProvider implements JoinColumnsEditor<T> {

		public void addJoinColumn(T subject) {
			ReferenceTableComposite.this.addJoinColumn(subject);
		}

		public ReadOnlyJoinColumn getDefaultJoinColumn(T subject) {
			return subject.getDefaultJoinColumn();
		}

		public String getDefaultPropertyName() {
			return ReadOnlyReferenceTable.DEFAULT_JOIN_COLUMN_PROPERTY;
		}

		public void editJoinColumn(T subject, ReadOnlyJoinColumn joinColumn) {
			ReferenceTableComposite.this.editJoinColumn(joinColumn);
		}

		public boolean hasSpecifiedJoinColumns(T subject) {
			return subject.hasSpecifiedJoinColumns();
		}

		public void removeJoinColumns(T subject, int[] selectedIndices) {
			for (int index = selectedIndices.length; index-- > 0; ) {
				((ReferenceTable) subject).removeSpecifiedJoinColumn(selectedIndices[index]);
			}
		}

		public ListIterable<ReadOnlyJoinColumn> getSpecifiedJoinColumns(T subject) {
			return new SuperListIterableWrapper<ReadOnlyJoinColumn>(subject.getSpecifiedJoinColumns());
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

	
	/* CU private */ class JoinColumnPaneEnablerHolder 
		extends TransformationPropertyValueModel<T, Boolean> 
	{
		private StateChangeListener stateChangeListener;
		
		
		JoinColumnPaneEnablerHolder() {
			super(
				new ValueListAdapter<T>(
					new ReadOnlyWritablePropertyValueModelWrapper<T>(getSubjectHolder()), 
					ReadOnlyReferenceTable.SPECIFIED_JOIN_COLUMNS_LIST
				)
			);
			this.stateChangeListener = buildStateChangeListener();
		}
		
		
		private StateChangeListener buildStateChangeListener() {
			return new StateChangeListener() {
				public void stateChanged(StateChangeEvent event) {
					JoinColumnPaneEnablerHolder.this.valueStateChanged();
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