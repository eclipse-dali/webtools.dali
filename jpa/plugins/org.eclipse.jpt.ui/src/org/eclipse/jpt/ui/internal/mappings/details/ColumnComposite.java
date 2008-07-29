/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.util.LabeledControlUpdater;
import org.eclipse.jpt.ui.internal.util.LabeledLabel;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;

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
 * @see EmbeddedAttributeOverridesComposite - A container of this pane
 * @see IdMappingComposite - A container of this pane
 * @see VersionMappingComposite - A container of this pane
 *
 * @version 2.0
 * @since 1.0
 */
public class ColumnComposite extends AbstractFormPane<Column> {

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 */
	public ColumnComposite(AbstractFormPane<?> parentPane,
	                       PropertyValueModel<? extends Column> subjectHolder,
	                       Composite parent) {

		super(parentPane, subjectHolder, parent);
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
	public ColumnComposite(AbstractFormPane<?> parentPane,
	                       PropertyValueModel<? extends Column> subjectHolder,
	                       Composite parent,
	                       boolean automaticallyAlignWidgets) {

		super(parentPane, subjectHolder, parent, automaticallyAlignWidgets);
	}

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ColumnComposite(PropertyValueModel<? extends Column> subjectHolder,
	                       Composite parent,
	                       WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private ColumnCombo<Column> buildColumnCombo(Composite container) {

		return new ColumnCombo<Column>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Column.DEFAULT_NAME_PROPERTY);
				propertyNames.add(Column.SPECIFIED_NAME_PROPERTY);
				propertyNames.add(Column.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(Column.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected void propertyChanged(String propertyName) {

				if (propertyName == Column.DEFAULT_TABLE_PROPERTY ||
				    propertyName == Column.SPECIFIED_TABLE_PROPERTY) {

					doPopulate();
				}
				else {
					super.propertyChanged(propertyName);
				}
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
			protected Table getDBTable_() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}
		};
	}

	private WritablePropertyValueModel<String> buildColumnDefinitionHolder() {
		return new PropertyAspectAdapter<Column, String>(getSubjectHolder(), Column.COLUMN_DEFINITION_PROPERTY) {
			@Override
			protected String buildValue_() {
				return subject.getColumnDefinition();
			}

			@Override
			protected void setValue_(String value) {
				if (value.length() == 0) {
					value = null;
				}
				subject.setColumnDefinition(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildDefaultLengthHolder() {
		return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.DEFAULT_LENGTH_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultLength();
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Integer.MIN_VALUE, newValue);
				}
			}
		};
	}

	private Control buildDefaultLengthLabel(Composite container) {

		Label label = buildLabel(
			container,
			JptUiMappingsMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultLengthLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultLengthLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultLengthHolder()) {

			@Override
			protected String transform(Integer value) {

				Integer defaultValue = (subject() != null) ? subject().getDefaultLength() :
				                                             Column.DEFAULT_LENGTH;

				return NLS.bind(
					JptUiMappingsMessages.DefaultWithValue,
					defaultValue
				);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildDefaultPrecisionHolder() {
		return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.DEFAULT_PRECISION_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultPrecision();
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Integer.MIN_VALUE, newValue);
				}
			}
		};
	}

	private Control buildDefaultPrecisionLabel(Composite container) {

		Label label = buildLabel(
			container,
			JptUiMappingsMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultPrecisionLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultPrecisionLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultPrecisionHolder()) {

			@Override
			protected String transform(Integer value) {

				Integer defaultValue = (subject() != null) ? subject().getDefaultPrecision() :
				                                             Column.DEFAULT_PRECISION;

				return NLS.bind(
					JptUiMappingsMessages.DefaultWithValue,
					defaultValue
				);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildDefaultScaleHolder() {
		return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.DEFAULT_SCALE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getDefaultScale();
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private Control buildDefaultScaleLabel(Composite container) {

		Label label = buildLabel(
			container,
			JptUiMappingsMessages.DefaultWithoutValue
		);

		new LabeledControlUpdater(
			new LabeledLabel(label),
			buildDefaultScaleLabelHolder()
		);

		return label;
	}

	private PropertyValueModel<String> buildDefaultScaleLabelHolder() {

		return new TransformationPropertyValueModel<Integer, String>(buildDefaultScaleHolder()) {

			@Override
			protected String transform(Integer value) {

				Integer defaultValue = (subject() != null) ? subject().getDefaultScale() :
				                                             Column.DEFAULT_SCALE;

				return NLS.bind(
					JptUiMappingsMessages.DefaultWithValue,
					defaultValue
				);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(getSubjectHolder(), Column.SPECIFIED_INSERTABLE_PROPERTY) {
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedInsertable(value);
			}
		};
	}

	private PropertyValueModel<String> buildInsertableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildInsertableHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultInsertable();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.ColumnComposite_insertableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.ColumnComposite_insertable;
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildLengthHolder() {
		return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_LENGTH_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedLength();
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedLength(value);
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildNullableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			Column.DEFAULT_NULLABLE_PROPERTY,
			Column.SPECIFIED_NULLABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedNullable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedNullable(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildNullableStringHolder() {
		return new TransformationPropertyValueModel<Boolean, String>(buildNullableHolder()) {
			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultNullable();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.ColumnComposite_nullableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.ColumnComposite_nullable;
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildPrecisionHolder() {
		return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_PRECISION_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedPrecision();
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedPrecision(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildScaleHolder() {
		return new PropertyAspectAdapter<Column, Integer>(getSubjectHolder(), Column.SPECIFIED_SCALE_PROPERTY) {
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedScale();
			}

			@Override
			protected void setValue_(Integer value) {
				if (value == -1) {
					value = null;
				}
				subject.setSpecifiedScale(value);
			}
		};
	}

	private TableCombo<Column> buildTableCombo(Composite container) {

		return new TableCombo<Column>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(Column.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(Column.SPECIFIED_TABLE_PROPERTY);
			}

			@Override
			protected String defaultValue() {
				return subject().getDefaultTable();
			}

			@Override
			protected String schemaName() {
				return null;
			}

			@Override
			protected void setValue(String value) {
				subject().setSpecifiedTable(value);
			}

			@Override
			protected Table table() {
				return subject().getDbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedTable();
			}

			@Override
			protected Iterator<String> values() {
				return subject().getOwner().getTypeMapping().associatedTableNamesIncludingInherited();
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildUniqueHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			Column.DEFAULT_UNIQUE_PROPERTY,
			Column.SPECIFIED_UNIQUE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedUnique();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedUnique(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildUniqueStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildUniqueHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultUnique();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.ColumnComposite_uniqueWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.ColumnComposite_unique;
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			Column.DEFAULT_UPDATABLE_PROPERTY,
			Column.SPECIFIED_UPDATABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedUpdatable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedUpdatable(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.getValue();
				super.subjectChanged();
				Object newValue = this.getValue();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
			}
		};
	}

	private PropertyValueModel<String> buildUpdatableStringHolder() {

		return new TransformationPropertyValueModel<Boolean, String>(buildUpdatableHolder()) {

			@Override
			protected String transform(Boolean value) {

				if ((subject() != null) && (value == null)) {

					Boolean defaultValue = subject().getDefaultUpdatable();

					if (defaultValue != null) {

						String defaultStringValue = defaultValue ? JptUiMappingsMessages.Boolean_True :
						                                           JptUiMappingsMessages.Boolean_False;

						return NLS.bind(
							JptUiMappingsMessages.ColumnComposite_updatableWithDefault,
							defaultStringValue
						);
					}
				}

				return JptUiMappingsMessages.ColumnComposite_updatable;
			}
		};
	}

	private void initializeDetailsPane(Composite container) {

		// Insertable tri-state check box
		buildTriStateCheckBoxWithDefault(
			buildSubPane(container, 4),
			JptUiMappingsMessages.ColumnComposite_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable tri-state check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);

		// Unique tri-state check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_unique,
			buildUniqueHolder(),
			buildUniqueStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
		);

		// Nullable tri-state check box
		buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_nullable,
			buildNullableHolder(),
			buildNullableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
		);

		// Length widgets
		Spinner lengthSpinner = buildLabeledSpinner(
			container,
			JptUiMappingsMessages.ColumnComposite_length,
			buildLengthHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			buildDefaultLengthLabel(container),
			JpaHelpContextIds.MAPPING_COLUMN_LENGTH
		);

		updateGridData(container, lengthSpinner);

		// Precision widgets
		Spinner precisionSpinner = buildLabeledSpinner(
			container,
			JptUiMappingsMessages.ColumnComposite_precision,
			buildPrecisionHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			buildDefaultPrecisionLabel(container),
			JpaHelpContextIds.MAPPING_COLUMN_PRECISION
		);

		updateGridData(container, precisionSpinner);

		// Scale widgets
		Spinner scaleSpinner = buildLabeledSpinner(
			container,
			JptUiMappingsMessages.ColumnComposite_scale,
			buildScaleHolder(),
			-1,
			-1,
			Integer.MAX_VALUE,
			buildDefaultScaleLabel(container),
			JpaHelpContextIds.MAPPING_COLUMN_SCALE
		);

		updateGridData(container, scaleSpinner);

		// Column Definition widgets
		buildLabeledText(
			container,
			JptUiMappingsMessages.ColumnComposite_columnDefinition,
			buildColumnDefinitionHolder()
		);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Column group pane
		container = buildTitledPane(
			container,
			JptUiMappingsMessages.ColumnComposite_columnSection
		);

		// Column widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnComposite_name,
			buildColumnCombo(container),
			JpaHelpContextIds.MAPPING_COLUMN
		);

		// Table widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnComposite_table,
			buildTableCombo(container),
			JpaHelpContextIds.MAPPING_COLUMN_TABLE
		);

		// Details sub-pane
		container = buildCollapsableSubSection(
			container,
			JptUiMappingsMessages.ColumnComposite_details,
			new SimplePropertyValueModel<Boolean>(Boolean.FALSE)
		);

		initializeDetailsPane(buildSubPane(container, 0, 16));
	}

	/**
	 * Changes the layout of the given container by changing which widget will
	 * grab the excess of horizontal space. By default, the center control grabs
	 * the excess space, we change it to be the right control.
	 *
	 * @param container The container containing the controls needing their
	 * <code>GridData</code> to be modified from the default values
	 * @param spinner The spinner that got created
	 */
	private void updateGridData(Composite container, Spinner spinner) {

		// It is possible the spinner's parent is not the container of the
		// label, spinner and right control (a pane is sometimes required for
		// painting the spinner's border)
		Composite paneContainer = spinner.getParent();

		while (container != paneContainer.getParent()) {
			paneContainer = paneContainer.getParent();
		}

		Control[] controls = paneContainer.getChildren();

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = false;
		gridData.horizontalAlignment       = GridData.BEGINNING;
		controls[1].setLayoutData(gridData);

		controls[2].setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		removeAlignRight(controls[2]);
	}
}