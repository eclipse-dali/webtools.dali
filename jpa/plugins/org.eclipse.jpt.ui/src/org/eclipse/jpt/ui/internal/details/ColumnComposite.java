/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.details;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.details.db.DatabaseObjectCombo;
import org.eclipse.jpt.ui.internal.widgets.IntegerCombo;
import org.eclipse.jpt.ui.internal.widgets.Pane;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | ColumnCombo                                                           | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------- |
 * | |                                                                       | |
 * | | TableCombo                                                            | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * |                                                                           |
 * | > Details                                                                 |
 * |                                                                           |
 * |   x Insertable                                                            |
 * |                                                                           |
 * |   x Updatable                                                             |
 * |                                                                           |
 * |   x Unique                                                                |
 * |                                                                           |
 * |   x Nullable                                                              |
 * |                                                                           |
 * |                      ---------------                                      |
 * |   Length:            | I         |I|  Default (XXX)                       |
 * |                      ---------------                                      |
 * |                      ---------------                                      |
 * |   Precision:         | I         |I|  Default (XXX)                       |
 * |                      ---------------                                      |
 * |                      ---------------                                      |
 * |   Scale:             | I         |I|  Default (XXX)                       |
 * |                      ---------------                                      |
 * |                      ---------------------------------------------------- |
 * |   Column Definition: | I                                                | |
 * |                      ---------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see Column
 * @see ColumnCombo
 * @see TableCombo
 * @see BasicMappingComposite - A container of this pane
 * @see EmbeddedMappingOverridesComposite - A container of this pane
 * @see IdMappingComposite - A container of this pane
 * @see VersionMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class ColumnComposite extends Pane<Column> {

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 */
	public ColumnComposite(Pane<?> parentPane,
	                       PropertyValueModel<? extends Column> subjectHolder,
	                       Composite parent) {

		super(parentPane, subjectHolder, parent, false);
	}

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 */
	public ColumnComposite(Pane<?> parentPane,
	                       PropertyValueModel<? extends Column> subjectHolder,
	                       Composite parent,
	                       boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}
	
	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 * @param automaticallyAlignWidgets <code>true</code> to make the widgets
	 * this pane aligned with the widgets of the given parent controller;
	 * <code>false</code> to not align them
	 */
	public ColumnComposite(Pane<?> parentPane,
	                       PropertyValueModel<? extends Column> subjectHolder,
	                       Composite parent,
	                       boolean automaticallyAlignWidgets,
	                       boolean parentManagePane) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets, parentManagePane);
	}

	private ColumnCombo<Column> addColumnCombo(Composite container) {

		return new ColumnCombo<Column>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(BaseColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(BaseColumn.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {
				if (propertyName == BaseColumn.DEFAULT_TABLE_PROPERTY ||
				    propertyName == BaseColumn.SPECIFIED_TABLE_PROPERTY) {
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
				getSubject().setSpecifiedName(value);
			}

			@Override
			protected Table getDbTable_() {
				return getSubject().getDbTable();
			}

			@Override
			protected String getValue() {
				return getSubject().getSpecifiedName();
			}
			@Override
			public String toString() {
				return "ColumnComposite.columnCombo"; //$NON-NLS-1$
			}
		};
	}

	private WritablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<Column, String>(getSubjectHolder(), NamedColumn.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return this.subject.getColumnDefinition();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				this.subject.setColumnDefinition(value);
			}
		};
	}
	
	private WritablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(getSubjectHolder(), BaseColumn.SPECIFIED_INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedInsertable(value);
			}
		};
	}

	private PropertyValueModel<String> buildInsertableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultInsertableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_insertableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_insertable;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultInsertableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_INSERTABLE_PROPERTY,
			BaseColumn.DEFAULT_INSERTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedInsertable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultInsertable());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedNullable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedNullable(value);
			}
		};
	}

	private PropertyValueModel<String> buildNullableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultNullableHolder()) {
			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_nullableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_nullable;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultNullableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_NULLABLE_PROPERTY,
			BaseColumn.DEFAULT_NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedNullable() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultNullable());
			}
		};
	}

	private Pane<Column> addTableCombo(Composite container) {

		return new DatabaseObjectCombo<Column>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(BaseColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(BaseColumn.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected String getDefaultValue() {
				return this.getSubject().getDefaultTable();
			}

			@Override
			protected void setValue(String value) {
				this.getSubject().setSpecifiedTable(value);
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
				return this.getSubject().getOwner().getTypeMapping().associatedTableNamesIncludingInherited();
			}
			@Override
			public String toString() {
				return "ColumnComposite.tableCombo"; //$NON-NLS-1$
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildUniqueHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_UNIQUE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedUnique();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedUnique(value);
			}
		};
	}

	private PropertyValueModel<String> buildUniqueStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUniqueHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_uniqueWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_unique;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultUniqueHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_UNIQUE_PROPERTY,
			BaseColumn.DEFAULT_UNIQUE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				if (this.subject.getSpecifiedUnique() != null) {
					return null;
				}
				return Boolean.valueOf(this.subject.isDefaultUnique());
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.DEFAULT_UPDATABLE_PROPERTY,
			BaseColumn.SPECIFIED_UPDATABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return this.subject.getSpecifiedUpdatable();
			}

			@Override
			protected void setValue_(Boolean value) {
				this.subject.setSpecifiedUpdatable(value);
			}
		};
	}

	private PropertyValueModel<String> buildUpdatableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildDefaultUpdatableHolder()) {

			@Override
			protected String transform(Boolean value) {
				if (value != null) {
					String defaultStringValue = value.booleanValue() ? JptUiDetailsMessages.Boolean_True : JptUiDetailsMessages.Boolean_False;
					return NLS.bind(JptUiDetailsMessages.ColumnComposite_updatableWithDefault, defaultStringValue);
				}
				return JptUiDetailsMessages.ColumnComposite_updatable;
			}
		};
	}
	
	private PropertyValueModel<Boolean> buildDefaultUpdatableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			BaseColumn.SPECIFIED_UPDATABLE_PROPERTY,
			BaseColumn.DEFAULT_UPDATABLE_PROPERTY)
		{
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
			JptUiDetailsMessages.ColumnComposite_columnSection
		);

		// Column widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.ColumnComposite_name,
			addColumnCombo(container),
			JpaHelpContextIds.MAPPING_COLUMN
		);

		// Table widgets
		addLabeledComposite(
			container,
			JptUiDetailsMessages.ColumnComposite_table,
			addTableCombo(container),
			JpaHelpContextIds.MAPPING_COLUMN_TABLE
		);

		// Details sub-pane
		container = addCollapsibleSubSection(
			container,
			JptUiDetailsMessages.ColumnComposite_details,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		new DetailsComposite(this, getSubjectHolder(), addSubPane(container, 0, 16));
	}
	
	protected class DetailsComposite extends Pane<Column> {
				
		public DetailsComposite(Pane<?> parentPane,
            PropertyValueModel<? extends Column> subjectHolder,
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
				JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
			);

			// Updatable tri-state check box
			addTriStateCheckBoxWithDefault(
				container,
				JptUiDetailsMessages.ColumnComposite_updatable,
				buildUpdatableHolder(),
				buildUpdatableStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
			);

			// Unique tri-state check box
			addTriStateCheckBoxWithDefault(
				container,
				JptUiDetailsMessages.ColumnComposite_unique,
				buildUniqueHolder(),
				buildUniqueStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
			);

			// Nullable tri-state check box
			addTriStateCheckBoxWithDefault(
				container,
				JptUiDetailsMessages.ColumnComposite_nullable,
				buildNullableHolder(),
				buildNullableStringHolder(),
				JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
			);

			addLengthCombo(container);
			addPrecisionCombo(container);
			addScaleCombo(container);

			// Column Definition widgets
			addLabeledText(
				container,
				JptUiDetailsMessages.ColumnComposite_columnDefinition,
				buildColumnDefinitionHolder()
			);
		}

		private void addLengthCombo(Composite container) {
			new IntegerCombo<Column>(this, container) {
				
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
					return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.DEFAULT_LENGTH_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return Integer.valueOf(this.subject.getDefaultLength());
						}
					};
				}
				
				@Override
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
					return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_LENGTH_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return this.subject.getSpecifiedLength();
						}

						@Override
						protected void setValue_(Integer value) {
							this.subject.setSpecifiedLength(value);
						}
					};
				}
			};
		}

		private void addPrecisionCombo(Composite container) {
			new IntegerCombo<Column>(this, container) {
				
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
					return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.DEFAULT_PRECISION_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return Integer.valueOf(this.subject.getDefaultPrecision());
						}
					};
				}
				
				@Override
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
					return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_PRECISION_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return this.subject.getSpecifiedPrecision();
						}

						@Override
						protected void setValue_(Integer value) {
							this.subject.setSpecifiedPrecision(value);
						}
					};
				}
			};
		}

		private void addScaleCombo(Composite container) {
			new IntegerCombo<Column>(this, container) {
				
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
					return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.DEFAULT_SCALE_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return Integer.valueOf(this.subject.getDefaultScale());
						}
					};
				}
				
				@Override
				protected WritablePropertyValueModel<Integer> buildSelectedItemHolder() {
					return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_SCALE_PROPERTY) {
						@Override
						protected Integer buildValue_() {
							return this.subject.getSpecifiedScale();
						}

						@Override
						protected void setValue_(Integer value) {
							this.subject.setSpecifiedScale(value);
						}
					};
				}
			};
		}
	}
}