/*******************************************************************************
 * Copyright (c) 2007, 2016 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.utility.internal.model.value.PropertyAspectAdapterXXXX;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.Column;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.SpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.TableColumn;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelAdapter;
import org.eclipse.jpt.jpa.ui.internal.TriStateCheckBoxLabelModelStringTransformer;
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
	extends Pane<Column>
{
	public ColumnComposite(
			Pane<?> parentPane,
			PropertyValueModel<? extends Column> subjectHolder,
			Composite parent) {
		
		super(parentPane, subjectHolder, parent);
	}
	
	
	private ColumnCombo<Column> addColumnCombo(Composite container) {
		
		return new ColumnCombo<Column>(this, container) {
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
				Column column = this.getSubject();
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
		TableColumn.DEFAULT_TABLE_NAME_PROPERTY,
		TableColumn.SPECIFIED_TABLE_NAME_PROPERTY
	});

	ModifiablePropertyValueModel<String> buildColumnDefinitionModel() {
		return new PropertyAspectAdapterXXXX<Column, String>(getSubjectHolder(), NamedColumn.COLUMN_DEFINITION_PROPERTY) {
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
	
	ModifiablePropertyValueModel<Boolean> buildInsertableModel() {
		return new PropertyAspectAdapterXXXX<Column, Boolean>(getSubjectHolder(), BaseColumn.SPECIFIED_INSERTABLE_PROPERTY) {
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
	
	PropertyValueModel<String> buildInsertableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultInsertableModel(), INSERTABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> INSERTABLE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_INSERTABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_INSERTABLE
		);

	PropertyValueModel<Boolean> buildDefaultInsertableModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects_(
				this.getSubjectHolder(),
				BaseColumn.SPECIFIED_INSERTABLE_PROPERTY,
				m -> m.getSpecifiedInsertable(),
				BaseColumn.DEFAULT_INSERTABLE_PROPERTY,
				m -> m.getDefaultInsertable()
			);
	}
	
	ModifiablePropertyValueModel<Boolean> buildNullableModel() {
		return new PropertyAspectAdapterXXXX<Column, Boolean>(
				getSubjectHolder(),
				BaseColumn.SPECIFIED_NULLABLE_PROPERTY) {
			
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
	
	PropertyValueModel<String> buildNullableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultNullableModel(), NULLABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> NULLABLE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE
		);

	PropertyValueModel<Boolean> buildDefaultNullableModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects_(
				this.getSubjectHolder(),
				BaseColumn.SPECIFIED_NULLABLE_PROPERTY,
				m -> m.getSpecifiedNullable(),
				BaseColumn.DEFAULT_NULLABLE_PROPERTY,
				m -> m.getDefaultNullable()
			);
	}
	
	private Pane<Column> addTableCombo(Composite container) {
		
		return new DatabaseObjectCombo<Column>(this, container) {
			
			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(TableColumn.DEFAULT_TABLE_NAME_PROPERTY);
				propertyNames.add(TableColumn.SPECIFIED_TABLE_NAME_PROPERTY);
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
				Column column = this.getSubject();
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
	
	ModifiablePropertyValueModel<Boolean> buildUniqueModel() {
		return new PropertyAspectAdapterXXXX<Column, Boolean>(
				getSubjectHolder(),
				BaseColumn.SPECIFIED_UNIQUE_PROPERTY) {
			
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
	
	PropertyValueModel<String> buildUniqueStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultUniqueModel(), UNIQUE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> UNIQUE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UNIQUE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UNIQUE
		);

	PropertyValueModel<Boolean> buildDefaultUniqueModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects_(
				this.getSubjectHolder(),
				BaseColumn.SPECIFIED_UNIQUE_PROPERTY,
				m -> m.getSpecifiedUnique(),
				BaseColumn.DEFAULT_UNIQUE_PROPERTY,
				m -> m.getDefaultUnique()
			);
	}
	
	ModifiablePropertyValueModel<Boolean> buildUpdatableModel() {
		return new PropertyAspectAdapterXXXX<Column, Boolean>(
				getSubjectHolder(),
				BaseColumn.DEFAULT_UPDATABLE_PROPERTY,
				BaseColumn.SPECIFIED_UPDATABLE_PROPERTY) {
			
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
	
	PropertyValueModel<String> buildUpdatableStringModel() {
		return PropertyValueModelTools.transform_(this.buildDefaultUpdatableModel(), UPDATABLE_TRANSFORMER);
	}

	private static final Transformer<Boolean, String> UPDATABLE_TRANSFORMER = new TriStateCheckBoxLabelModelStringTransformer(
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UPDATABLE_WITH_DEFAULT,
			JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UPDATABLE
		);

	PropertyValueModel<Boolean> buildDefaultUpdatableModel() {
		return TriStateCheckBoxLabelModelAdapter.adaptSubjectModelAspects_(
				this.getSubjectHolder(),
				BaseColumn.SPECIFIED_UPDATABLE_PROPERTY,
				m -> m.getSpecifiedUpdatable(),
				BaseColumn.DEFAULT_UPDATABLE_PROPERTY,
				m -> m.getDefaultUpdatable()
			);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
				parent,
				JptJpaUiDetailsMessages.COLUMN_COMPOSITE_COLUMN_SECTION,
				2,
				null);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Column widgets
		this.addLabel(container, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NAME);
		this.addColumnCombo(container);

		// Table widgets
		this.addLabel(container, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_TABLE);
		this.addTableCombo(container);


		// Details sub-pane
		final Section detailsSection = this.getWidgetFactory().createSection(container, 
				ExpandableComposite.TWISTIE |
				ExpandableComposite.CLIENT_INDENT);
		detailsSection.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		detailsSection.setText(JptJpaUiDetailsMessages.COLUMN_COMPOSITE_DETAILS);
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
				JptJpaUiDetailsMessages.COLUMN_COMPOSITE_INSERTABLE,
				buildInsertableModel(),
				buildInsertableStringModel(),
				JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE);
		GridData gridData = new GridData();
		gridData.horizontalSpan = 2;
		insertableCheckBox.getCheckBox().setLayoutData(gridData);

		// Updatable tri-state check box
		TriStateCheckBox updatableCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UPDATABLE,
				buildUpdatableModel(),
				buildUpdatableStringModel(),
				JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		updatableCheckBox.getCheckBox().setLayoutData(gridData);

		// Unique tri-state check box
		TriStateCheckBox uniqueCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.COLUMN_COMPOSITE_UNIQUE,
				buildUniqueModel(),
				buildUniqueStringModel(),
				JpaHelpContextIds.MAPPING_COLUMN_UNIQUE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		uniqueCheckBox.getCheckBox().setLayoutData(gridData);

		// Nullable tri-state check box
		TriStateCheckBox nullableCheckBox = addTriStateCheckBoxWithDefault(
				detailsClient,
				JptJpaUiDetailsMessages.COLUMN_COMPOSITE_NULLABLE,
				buildNullableModel(),
				buildNullableStringModel(),
				JpaHelpContextIds.MAPPING_COLUMN_NULLABLE);
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		nullableCheckBox.getCheckBox().setLayoutData(gridData);

		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_LENGTH);		
		this.addLengthCombo(detailsClient);

		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_PRECISION);		
		this.addPrecisionCombo(detailsClient);

		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_SCALE);		
		this.addScaleCombo(detailsClient);

		// Column Definition widgets
		this.addLabel(detailsClient, JptJpaUiDetailsMessages.COLUMN_COMPOSITE_COLUMN_DEFINITION);
		this.addText(detailsClient, buildColumnDefinitionModel());

		return detailsClient;
	}

	@SuppressWarnings("unused")
	private void addLengthCombo(Composite container) {
		new LengthCombo(this, container);
	}

	static class LengthCombo
		extends IntegerCombo<Column>
	{
		LengthCombo(Pane<? extends Column> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.MAPPING_COLUMN_LENGTH;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return new PropertyAspectAdapterXXXX<Column, Integer>(getSubjectHolder(), Column.DEFAULT_LENGTH_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return Integer.valueOf(this.subject.getDefaultLength());
				}
			};
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_LENGTH_PROPERTY) {
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
	}

	@SuppressWarnings("unused")
	private void addPrecisionCombo(Composite container) {
		new PrecisionCombo(this, container);
	}

	static class PrecisionCombo extends IntegerCombo<Column> {
		PrecisionCombo(Pane<? extends Column> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.MAPPING_COLUMN_PRECISION;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return new PropertyAspectAdapterXXXX<Column, Integer>(getSubjectHolder(), Column.DEFAULT_PRECISION_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return Integer.valueOf(this.subject.getDefaultPrecision());
				}
			};
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_PRECISION_PROPERTY) {
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
	}

	@SuppressWarnings("unused")
	private void addScaleCombo(Composite container) {
		new ScaleCombo(this, container);
	}

	static class ScaleCombo
		extends IntegerCombo<Column>
	{
		ScaleCombo(Pane<? extends Column> parentPane, Composite parent) {
			super(parentPane, parent);
		}

		@Override
		protected String getHelpId() {
			return JpaHelpContextIds.MAPPING_COLUMN_SCALE;
		}

		@Override
		protected PropertyValueModel<Integer> buildDefaultModel() {
			return new PropertyAspectAdapterXXXX<Column, Integer>(getSubjectHolder(), Column.DEFAULT_SCALE_PROPERTY) {
				@Override
				protected Integer buildValue_() {
					return Integer.valueOf(this.subject.getDefaultScale());
				}
			};
		}

		@Override
		protected ModifiablePropertyValueModel<Integer> buildSelectedItemModel() {
			return new PropertyAspectAdapterXXXX<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_SCALE_PROPERTY) {
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
	}
}
