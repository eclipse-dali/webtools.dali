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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.core.context.Column;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.util.ControlEnabler;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
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
 * |              ---------------                                              |
 * |   Length:    |           |I|                                              |
 * |              ---------------                                              |
 * |              ---------------                                              |
 * |   Precision: |           |I|                                              |
 * |              ---------------                                              |
 * |              ---------------                                              |
 * |   Scale:     |           |I|                                              |
 * |              ---------------                                              |
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
 * @TODO repopulate this panel based on the Entity table changing
 *
 * @version 2.0
 * @since 1.0
 */
public class ColumnComposite extends AbstractFormPane<Column>
{
	private WritablePropertyValueModel<Boolean> enablementHolder;

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
				propertyNames.add(NamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(NamedColumn.SPECIFIED_NAME_PROPERTY);
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
			protected Table table() {
				return subject().dbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedName();
			}
		};
	}

	private WritablePropertyValueModel<Boolean> buildInsertableHolder() {
		return new PropertyAspectAdapter<Column, Boolean>(
			getSubjectHolder(),
			Column.DEFAULT_INSERTABLE_PROPERTY,
			Column.SPECIFIED_INSERTABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedInsertable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedInsertable(value);
			}

			@Override
			protected void subjectChanged() {
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

				// Make sure the default value is appended to the text
				if (oldValue == newValue && newValue == null) {
					this.fireAspectChange(Boolean.TRUE, newValue);
				}
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

		return new PropertyAspectAdapter<Column, Integer>(
			getSubjectHolder(),
			Column.DEFAULT_UPDATABLE_PROPERTY,
			Column.SPECIFIED_UPDATABLE_PROPERTY)
		{
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedLength();
			}

			@Override
			protected void setValue_(Integer value) {
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
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

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

		return new PropertyAspectAdapter<Column, Integer>(
			getSubjectHolder(),
			Column.DEFAULT_PRECISION_PROPERTY,
			Column.SPECIFIED_PRECISION_PROPERTY)
		{
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedPrecision();
			}

			@Override
			protected void setValue_(Integer value) {
				subject.setSpecifiedPrecision(value);
			}
		};
	}

	private WritablePropertyValueModel<Integer> buildScaleHolder() {

		return new PropertyAspectAdapter<Column, Integer>(
			getSubjectHolder(),
			Column.DEFAULT_SCALE_PROPERTY,
			Column.SPECIFIED_SCALE_PROPERTY)
		{
			@Override
			protected Integer buildValue_() {
				return subject.getSpecifiedScale();
			}

			@Override
			protected void setValue_(Integer value) {
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
			protected void setValue(String value) {
				subject().setSpecifiedTable(value);
			}

			@Override
			protected Table table() {
				return subject().dbTable();
			}

			@Override
			protected String value() {
				return subject().getSpecifiedTable();
			}

			@Override
			protected Iterator<String> values() {
				return subject().owner().typeMapping().associatedTableNamesIncludingInherited();
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
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

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
				Object oldValue = this.value();
				super.subjectChanged();
				Object newValue = this.value();

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

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void enableWidgets(boolean enabled) {
		super.enableWidgets(enabled);
		enablementHolder.setValue(enabled);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		enablementHolder = new SimplePropertyValueModel<Boolean>(Boolean.TRUE);
	}

	private void initializeDetailsPane(Composite container) {

		Collection<Control> widgets = new ArrayList<Control>();

		// Insertable tri-state check box
		TriStateCheckBox insertableCheckBox = buildTriStateCheckBoxWithDefault(
			buildSubPane(container, 4),
			JptUiMappingsMessages.ColumnComposite_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		widgets.add(insertableCheckBox.getCheckBox());

		// Updatable tri-state check box
		TriStateCheckBox updatableCheckBox = buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);

		widgets.add(updatableCheckBox.getCheckBox());

		// Unique tri-state check box
		TriStateCheckBox uniqueCheckBox = buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_unique,
			buildUniqueHolder(),
			buildUniqueStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_UNIQUE
		);

		widgets.add(uniqueCheckBox.getCheckBox());

		// Nullable tri-state check box
		TriStateCheckBox nullableCheckBox = buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_nullable,
			buildNullableHolder(),
			buildNullableStringHolder(),
			JpaHelpContextIds.MAPPING_COLUMN_NULLABLE
		);

		widgets.add(nullableCheckBox.getCheckBox());

		// Length widgets
		Spinner lengthSpinner = buildLabeledSpinnerWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_length,
			buildLengthHolder(),
			Column.DEFAULT_LENGTH,
			JpaHelpContextIds.MAPPING_COLUMN_LENGTH
		);

		widgets.add(lengthSpinner);

		// Precision widgets
		Spinner precisionSpinner = buildLabeledSpinnerWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_precision,
			buildPrecisionHolder(),
			Column.DEFAULT_PRECISION,
			JpaHelpContextIds.MAPPING_COLUMN_PRECISION
		);

		widgets.add(precisionSpinner);

		// Scale widgets
		Spinner scaleSpinner = buildLabeledSpinnerWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_scale,
			buildScaleHolder(),
			Column.DEFAULT_SCALE,
			JpaHelpContextIds.MAPPING_COLUMN_SCALE
		);

		widgets.add(scaleSpinner);

		installControlEnabler(widgets);
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

	private void installControlEnabler(Collection<Control> widgets) {
		new ControlEnabler(enablementHolder, widgets);
	}
}