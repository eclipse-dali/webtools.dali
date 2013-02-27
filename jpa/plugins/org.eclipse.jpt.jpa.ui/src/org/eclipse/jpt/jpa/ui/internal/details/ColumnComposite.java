/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.common.utility.internal.iterable.EmptyIterable;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyBaseColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTableColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.db.ColumnCombo;
import org.eclipse.jpt.jpa.ui.internal.details.db.DatabaseObjectCombo;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.events.ExpansionAdapter;
import org.eclipse.ui.forms.events.ExpansionEvent;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class ColumnComposite
	extends Pane<ReadOnlyColumn>
{
	public ColumnComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends ReadOnlyColumn> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	private ColumnCombo<ReadOnlyColumn> addColumnCombo(Composite container) {
		
		return new ColumnCombo<ReadOnlyColumn>(this, container) {
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.addAll(COLUMN_PICK_LIST_PROPERTIES);
			}
			
			@Override
			protected void propertyChanged(String propertyName) {
				if (COLUMN_PICK_LIST_PROPERTIES.contains(propertyName)) {
					this.repopulateComboBox();
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
				((SpecifiedColumn) this.getSubject()).setSpecifiedName(value);
			}
			
			@Override
			protected Table getDbTable_() {
				ReadOnlyColumn column = this.getSubject();
				return (column == null) ? null : column.getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedName();
			}
			
			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						JptCommonUiMessages.NONE_SELECTED);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_COLUMN;
			}

			@Override
			public String toString() {
				return "ColumnComposite.columnCombo"; //$NON-NLS-1$
			}
		};
	}
	
	/* CU private */ static final Collection<String> COLUMN_PICK_LIST_PROPERTIES = Arrays.asList(new String[] {
		ReadOnlyTableColumn.DEFAULT_TABLE_NAME_PROPERTY,
		ReadOnlyTableColumn.SPECIFIED_TABLE_NAME_PROPERTY
	});

	ModifiablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, String>(getSubjectHolder(), NamedColumn.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColumnDefinition();
			}
			
			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				((SpecifiedColumn) this.subject).setColumnDefinition(value);
			}
		};
	}
	
	ModifiablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(getSubjectHolder(), ReadOnlyBaseColumn.SPECIFIED_INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				((SpecifiedColumn) this.subject).setSpecifiedInsertable(value);
			}
		};
	}
	
	PropertyValueModel<String> buildInsertableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultInsertableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_insertableWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_insertable;
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
	
	ModifiablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_NULLABLE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedNullable();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				((SpecifiedColumn) this.subject).setSpecifiedNullable(value);
			}
		};
	}
	
	PropertyValueModel<String> buildNullableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultNullableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_nullableWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_nullable;
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
				propertyNames.add(ReadOnlyBaseColumn.DEFAULT_TABLE_NAME_PROPERTY);
				propertyNames.add(ReadOnlyBaseColumn.SPECIFIED_TABLE_NAME_PROPERTY);
			}
			
			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultTableName();
			}
			
			@Override
			protected void setValue(String value) {
				((SpecifiedColumn) this.getSubject()).setSpecifiedTableName(value);
			}
			
			@Override
			protected String getValue() {
				return this.getSubject().getSpecifiedTableName();
			}
			
			// TODO we need to listen for this list to change...
			@Override
			protected Iterable<String> getValues_() {
				ReadOnlyColumn column = this.getSubject();
				return (column != null) ? column.getCandidateTableNames() : EmptyIterable.<String> instance();
			}
			
			@Override
			protected String buildNullDefaultValueEntry() {
				return NLS.bind(
						JptCommonUiMessages.DEFAULT_WITH_ONE_PARAM,
						JptCommonUiMessages.NONE_SELECTED);
			}

			@Override
			protected String getHelpId() {
				return JpaHelpContextIds.MAPPING_COLUMN_TABLE;
			}

			@Override
			public String toString() {
				return "ColumnComposite.tableCombo"; //$NON-NLS-1$
			}
		};
	}
	
	ModifiablePropertyValueModel<Boolean> buildUniqueHolder() {
		return new PropertyAspectAdapter<ReadOnlyColumn, Boolean>(
				getSubjectHolder(),
				ReadOnlyBaseColumn.SPECIFIED_UNIQUE_PROPERTY) {
			
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedUnique();
			}
			
			@Override
			protected void setValue_(Boolean value) {
				((SpecifiedColumn) this.subject).setSpecifiedUnique(value);
			}
		};
	}
	
	PropertyValueModel<String> buildUniqueStringHolder() {
		
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUniqueHolder()) {
			
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_uniqueWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_unique;
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
	
	ModifiablePropertyValueModel<Boolean> buildUpdatableHolder() {
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
				((SpecifiedColumn) this.subject).setSpecifiedUpdatable(value);
			}
		};
	}
	
	PropertyValueModel<String> buildUpdatableStringHolder() {
		
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUpdatableHolder()) {
			
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptCommonUiMessages.BOOLEAN_TRUE : JptCommonUiMessages.BOOLEAN_FALSE;
					return NLS.bind(JptJpaUiDetailsMessages.ColumnComposite_updatableWithDefault, defaultStringValue);
				}
				return JptJpaUiDetailsMessages.ColumnComposite_updatable;
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
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
				parent,
				JptJpaUiDetailsMessages.ColumnComposite_columnSection,
				2,
				null);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Column widgets
		this.addLabel(container, JptJpaUiDetailsMessages.ColumnComposite_name);
		this.addColumnCombo(container);

		// Table widgets
		this.addLabel(container, JptJpaUiDetailsMessages.ColumnComposite_table);
		this.addTableCombo(container);


		// Details sub-pane
		final Section detailsSection = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE |
				ExpandableComposite.CLIENT_INDENT);
		detailsSection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detailsSection.setText(JptJpaUiDetailsMessages.ColumnComposite_details);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		detailsSection.setLayoutData(gridData);

		detailsSection.addExpansionListener(new ExpansionAdapter() {
			@Override
			public void expansionStateChanging(ExpansionEvent e) {
				if (e.getState() && detailsSection.getClient() == null) {
					detailsSection.setClient(ColumnComposite.this.initializeDetailsSection(detailsSection));
				}
			}
		});
	}

	protected Composite initializeDetailsSection(Composite container) {
		Composite detailsClient = this.addSubPane(container, 2, 0, 0, 0, 0);

		// Insertable tri-state check box
		TriStateCheckBox insertableCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.ColumnComposite_insertable,
				buildInsertableHolder(),
				buildInsertableStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		insertableCheckBox.getCheckBox().setLayoutData(gridData);

		// Updatable tri-state check box
		TriStateCheckBox updatableCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.ColumnComposite_updatable,
				buildUpdatableHolder(),
				buildUpdatableStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		updatableCheckBox.getCheckBox().setLayoutData(gridData);

		// Unique tri-state check box
		TriStateCheckBox uniqueCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.ColumnComposite_unique,
				buildUniqueHolder(),
				buildUniqueStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_UNIQUE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		uniqueCheckBox.getCheckBox().setLayoutData(gridData);

		// Nullable tri-state check box
		TriStateCheckBox nullableCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.ColumnComposite_nullable,
				buildNullableHolder(),
				buildNullableStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_NULLABLE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		nullableCheckBox.getCheckBox().setLayoutData(gridData);

		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_length);		
		this.addLengthCombo(detailsClient);

		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_precision);		
		this.addPrecisionCombo(detailsClient);

		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_scale);		
		this.addScaleCombo(detailsClient);

		// Column Definition widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.ColumnComposite_columnDefinition);
		this.addText(detailsClient, buildColumnDefinitionHolder());

		return detailsClient;
	}
	
	private void addLengthCombo(Composite container) {
		new IntegerCombo<ReadOnlyColumn>(this, container) {				
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
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.SPECIFIED_LENGTH_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedLength();
					}
					
					@Override
					protected void setValue_(Integer value) {
						((SpecifiedColumn) this.subject).setSpecifiedLength(value);
					}
				};
			}
		};
	}

	private void addPrecisionCombo(Composite container) {
		new IntegerCombo<ReadOnlyColumn>(this, container) {					
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
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.SPECIFIED_PRECISION_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedPrecision();
					}
					
					@Override
					protected void setValue_(Integer value) {
						((SpecifiedColumn) this.subject).setSpecifiedPrecision(value);
					}
				};
			}
		};
	}

	private void addScaleCombo(Composite container) {
		new IntegerCombo<ReadOnlyColumn>(this, container) {					
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
			protected ModifiablePropertyValueModel<Integer> buildSelectedItemHolder() {
				return new PropertyAspectAdapter<ReadOnlyColumn, Integer>(getSubjectHolder(), ReadOnlyColumn.SPECIFIED_SCALE_PROPERTY) {
					@Override
					protected Integer buildValue_() {
						return this.subject.getSpecifiedScale();
					}
					
					@Override
					protected void setValue_(Integer value) {
						((SpecifiedColumn) this.subject).setSpecifiedScale(value);
					}
				};
			}
		};
	}
}
