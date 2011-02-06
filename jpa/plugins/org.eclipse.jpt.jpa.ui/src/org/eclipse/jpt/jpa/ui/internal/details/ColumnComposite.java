/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyNamedColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.DatabaseObjectCombo;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

public class ColumnComposite
	extends Pane<ReadOnlyColumn>
{
	public ColumnComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends ReadOnlyColumn> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent, false);
	}
	
	public ColumnComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends ReadOnlyColumn> subjectHolder,
			Composite parent,
			boolean automaticallyAlignWidgets) {
		
		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}
	
	public ColumnComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends ReadOnlyColumn> subjectHolder,
			Composite parent,
			boolean automaticallyAlignWidgets,
			boolean parentManagePane) {
		
		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets, parentManagePane);
	}
	
	
	private ColumnCombo<ReadOnlyColumn> addColumnCombo(Composite container) {
		
		return new ColumnCombo<ReadOnlyColumn>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyNamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(ReadOnlyNamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(ReadOnlyBaseColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(ReadOnlyBaseColumn.SPECIFIED_TABLE_PROPERTY);
			}
			
			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == ReadOnlyBaseColumn.DEFAULT_TABLE_PROPERTY ||
				    propertyName == ReadOnlyBaseColumn.SPECIFIED_TABLE_PROPERTY) {
					this.doPopulate();
				} else {
					super.propertyChanged(propertyName);
				}
			}
			
			@Override
			protected String getDefaultValue() {
				return getSubject().getDefaultName();
			}
			
			@Override
			protected void setValue(String value) {
				((Column) this.getSubject()).setSpecifiedName(value);
			}
			
			@Override
			protected Table getDbTable_() {
				Column column = this.getColumn();
				return (column == null) ? null : column.getDbTable();
			}

			protected Column getColumn() {
				ReadOnlyColumn column = this.getSubject();
				return (column instanceof Column) ? (Column) column : null;
			}
			
			@Override
			protected String getValue() {
				return getSubject().getSpecifiedName();
			}
			
			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DefaultWithOneParam,
						JptCommonUiMessages.NoneSelected);
			}
			
			@Override
			public String toString() {
				return "ColumnComposite.columnCombo"; //$NON-NLS-1$
			}
		};
	}
	
	WritablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, String>(getSubjectHolder(), ReadOnlyNamedColumn.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColumnDefinition();
			}
			
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				((Column) this.subject).setColumnDefinition(value);
			}
		};
	}
	
	WritablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(getSubjectHolder(), ReadOnlyBaseColumn.SPECIFIED_INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				((Column) this.subject).setSpecifiedInsertable(value);
			}
		};
	}
	
	PropertyValueModel<String> buildInsertableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultInsertableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_insertableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_insertable;
			}
		};
	}
	
	PropertyValueModel<Boolean> buildDefaultInsertableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_INSERTABLE_PROPERTY,
				ReadOnlyBaseColumn.DEFAULT_INSERTABLE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedInsertable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultInsertable());
			}
		};
	}
	
	WritablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_NULLABLE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedNullable();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				((Column) this.subject).setSpecifiedNullable(value);
			}
		};
	}
	
	PropertyValueModel<String> buildNullableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultNullableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_nullableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_nullable;
			}
		};
	}
	
	PropertyValueModel<Boolean> buildDefaultNullableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_NULLABLE_PROPERTY,
				ReadOnlyBaseColumn.DEFAULT_NULLABLE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedNullable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultNullable());
			}
		};
	}
	
	private Pane<ReadOnlyColumn> addTableCombo(Composite container) {
		
		return new DatabaseObjectCombo<ReadOnlyColumn>(this, container) {
			
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(ReadOnlyBaseColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(ReadOnlyBaseColumn.SPECIFIED_TABLE_PROPERTY);
			}
			
			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultTable();
			}
			
			@Override
			protected void setValue(String value) {
				((Column) this.getSubject()).setSpecifiedTable(value);
			}
			
			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedTable();
			}
			
			@Override
			protected Iterable<String> getValues_() {
				return CollectionTools.iterable(this.values());
			}
			
			protected Iterator<String> values() {
				Column column = this.getColumn();
				return (column == null) ? null : column.candidateTableNames();
			}
			
			protected Column getColumn() {
				ReadOnlyColumn column = this.getSubject();
				return (column instanceof Column) ? (Column) column : null;
			}
			
			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DefaultWithOneParam,
						JptCommonUiMessages.NoneSelected);
			}
			
			@Override
			public String toString() {
				return "ColumnComposite.tableCombo"; //$NON-NLS-1$
			}
		};
	}
	
	WritablePropertyValueModel<Boolean> buildUniqueHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_UNIQUE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedUnique();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				((Column) this.subject).setSpecifiedUnique(value);
			}
		};
	}
	
	PropertyValueModel<String> buildUniqueStringHolder() {
		
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUniqueHolder()) {
			
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_uniqueWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_unique;
			}
		};
	}
	
	PropertyValueModel<Boolean> buildDefaultUniqueHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_UNIQUE_PROPERTY,
				ReadOnlyBaseColumn.DEFAULT_UNIQUE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedUnique() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultUnique());
			}
		};
	}
	
	WritablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.DEFAULT_UPDATABLE_PROPERTY,
				ReadOnlyBaseColumn.SPECIFIED_UPDATABLE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedUpdatable();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				((Column) this.subject).setSpecifiedUpdatable(value);
			}
		};
	}
	
	PropertyValueModel<String> buildUpdatableStringHolder() {
		
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUpdatableHolder()) {
			
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.Boolean_True : JptCommonUiMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_updatableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_updatable;
			}
		};
	}
	
	PropertyValueModel<Boolean> buildDefaultUpdatableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_UPDATABLE_PROPERTY,
				ReadOnlyBaseColumn.DEFAULT_UPDATABLE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedUpdatable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultUpdatable());
			}
		};
	}
	
	@Override
	protected void initializeLayout(Composite container) {
		// Column group pane
		container = addTitledGroup(
				container,
				JptUiDetailsMessages.ColumnComposite_columnSection);
		
		// Column widgets
		addLabeledComposite(
				container,
				JptUiDetailsMessages.ColumnComposite_name,
				addColumnCombo(container),
				JpaHelpContextIds.MAPPING_COLUMN);
		
		// Table widgets
		addLabeledComposite(
				container,
				JptUiDetailsMessages.ColumnComposite_table,
				addTableCombo(container),
				JpaHelpContextIds.MAPPING_COLUMN_TABLE);
		
		// Details sub-pane
		container = addCollapsibleSubSection(
				container,
				JptUiDetailsMessages.ColumnComposite_details,
				new SimplePropertyValueModel<Boolean>(Boolean.FALSE));
		
		new DetailsComposite(this, getSubjectHolder(), addSubPane(container, 0, 16));
	}
	
	protected class DetailsComposite extends Pane<ReadOnlyColumn> {
				
		public DetailsComposite(
				Pane<?> parentPane,
	            PropertyValueModel<? extends ReadOnlyColumn> subjectHolder,
	            Composite parent) {
			
			super(parentPane, subjectHolder, parent, false);
		}
		
		@Override
		protected void initializeLayout(Composite container) {
			// Insertable tri-state check box
			addTriStateCheckBoxWithDefault(
					addSubPane(container, 4),
					JptUiDetailsMessages.ColumnComposite_insertable,
					buildInsertableHolder(),
					buildInsertableStringHolder(),
					JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE);
			
			// Updatable tri-state check box
			addTriStateCheckBoxWithDefault(
					container,
					JptUiDetailsMessages.ColumnComposite_updatable,
					buildUpdatableHolder(),
					buildUpdatableStringHolder(),
					JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE);
			
			// Unique tri-state check box
			addTriStateCheckBoxWithDefault(
					container,
					JptUiDetailsMessages.ColumnComposite_unique,
					buildUniqueHolder(),
					buildUniqueStringHolder(),
					JpaHelpContextIds.MAPPING_COLUMN_UNIQUE);
			
			// Nullable tri-state check box
			addTriStateCheckBoxWithDefault(
					container,
					JptUiDetailsMessages.ColumnComposite_nullable,
					buildNullableHolder(),
					buildNullableStringHolder(),
					JpaHelpContextIds.MAPPING_COLUMN_NULLABLE);
			
			addLengthCombo(container);
			addPrecisionCombo(container);
			addScaleCombo(container);
			
			// Column Definition widgets
			addLabeledText(
					container,
					JptUiDetailsMessages.ColumnComposite_columnDefinition,
					buildColumnDefinitionHolder());
		}
		
		private void addLengthCombo(Composite container) {
			new IntegerCombo<ReadOnlyColumn>(this, container) {
				@Override
				protected String getLabelText() {
					return JptUiDetailsMessages.ColumnComposite_length;
				}
				
				@Override
				protected String getHelpId() {
					return JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
				}
				
				@Override
				protected PropertyValueModel<Integer> buildDefaultHolder() {
					return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.DEFAULT_LENGTH_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return Integer.valueOf(this.subject.getDefaultLength());
						}
					};
				}
				
				@Override
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
					return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.SPECIFIED_LENGTH_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return this.subject.getSpecifiedLength();
						}
						
						@Override
						protected void setValue_(Integer value) {
							((Column) this.subject).setSpecifiedLength(value);
						}
					};
				}
			};
		}
		
		private void addPrecisionCombo(Composite container) {
			new IntegerCombo<ReadOnlyColumn>(this, container) {	
				@Override
				protected String getLabelText() {
					return JptUiDetailsMessages.ColumnComposite_precision;
				}
				
				@Override
				protected String getHelpId() {
					return JpaHelpContextIds.MAPPING_COLUMN_PRECISION;
				}
				
				@Override
				protected PropertyValueModel<Integer> buildDefaultHolder() {
					return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.DEFAULT_PRECISION_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return Integer.valueOf(this.subject.getDefaultPrecision());
						}
					};
				}
				
				@Override
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
					return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.SPECIFIED_PRECISION_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return this.subject.getSpecifiedPrecision();
						}
						
						@Override
						protected void setValue_(Integer value) {
							((Column) this.subject).setSpecifiedPrecision(value);
						}
					};
				}
			};
		}
		
		private void addScaleCombo(Composite container) {
			new IntegerCombo<ReadOnlyColumn>(this, container) {	
				@Override
				protected String getLabelText() {
					return JptUiDetailsMessages.ColumnComposite_scale;
				}
				
				@Override
				protected String getHelpId() {
					return JpaHelpContextIds.MAPPING_COLUMN_SCALE;
				}
				
				@Override
				protected PropertyValueModel<Integer> buildDefaultHolder() {
					return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.DEFAULT_SCALE_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return Integer.valueOf(this.subject.getDefaultScale());
						}
					};
				}
				
				@Override
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
					return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.SPECIFIED_SCALE_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return this.subject.getSpecifiedScale();
						}
						
						@Override
						protected void setValue_(Integer value) {
							((Column) this.subject).setSpecifiedScale(value);
						}
					};
				}
			};
		}
	}
}
