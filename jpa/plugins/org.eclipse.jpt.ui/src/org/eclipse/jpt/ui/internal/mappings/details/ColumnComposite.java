/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import java.util.Collection;
import java.util.Iterator;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.widgets.TriStateCheckBox;
import org.eclipse.jpt.utility.internal.model.value.PropertyAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
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
 * | x Insertable                                                              |
 * |                                                                           |
 * | x Updatable                                                               |
 * |                                                                           |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IColumn
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
public class ColumnComposite extends AbstractFormPane<IColumn>
{
	private TriStateCheckBox insertableCheckBox;
	private TriStateCheckBox updatableCheckBox;

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 */
	public ColumnComposite(AbstractFormPane<?> parentPane,
	                       PropertyValueModel<? extends IColumn> subjectHolder,
	                       Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>IColumn</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ColumnComposite(PropertyValueModel<? extends IColumn> subjectHolder,
	                       Composite parent,
	                       IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private ColumnCombo<IColumn> buildColumnCombo(Composite container) {

		return new ColumnCombo<IColumn>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(INamedColumn.DEFAULT_NAME_PROPERTY);
				propertyNames.add(INamedColumn.SPECIFIED_NAME_PROPERTY);
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
		return new PropertyAspectAdapter<IColumn, Boolean>(
			getSubjectHolder(),
			IColumn.DEFAULT_INSERTABLE_PROPERTY,
			IColumn.SPECIFIED_INSERTABLE_PROPERTY)
		{
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

	private TableCombo<IColumn> buildTableCombo(Composite container) {

		return new TableCombo<IColumn>(this, container) {

			@Override
			protected void addPropertyNames(Collection<String> propertyNames) {
				super.addPropertyNames(propertyNames);
				propertyNames.add(IColumn.DEFAULT_TABLE_PROPERTY);
				propertyNames.add(IColumn.SPECIFIED_TABLE_PROPERTY);
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

	private WritablePropertyValueModel<Boolean> buildUpdatableHolder() {
		return new PropertyAspectAdapter<IColumn, Boolean>(
			getSubjectHolder(),
			IColumn.DEFAULT_UPDATABLE_PROPERTY,
			IColumn.SPECIFIED_UPDATABLE_PROPERTY)
		{
			@Override
			protected Boolean buildValue_() {
				return subject.getSpecifiedUpdatable();
			}

			@Override
			protected void setValue_(Boolean value) {
				subject.setSpecifiedUpdatable(value);
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
		insertableCheckBox.setEnabled(enabled);
		updatableCheckBox.setEnabled(enabled);
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
			JptUiMappingsMessages.ColumnChooser_label,
			buildColumnCombo(container),
			IJpaHelpContextIds.MAPPING_COLUMN
		);

		// Table widgets
		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnTableChooser_label,
			buildTableCombo(container),
			IJpaHelpContextIds.MAPPING_COLUMN_TABLE
		);

		// Insertable widgets
		insertableCheckBox = buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_insertable,
			buildInsertableHolder(),
			buildInsertableStringHolder(),
			IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable widgets
		updatableCheckBox = buildTriStateCheckBoxWithDefault(
			container,
			JptUiMappingsMessages.ColumnComposite_updatable,
			buildUpdatableHolder(),
			buildUpdatableStringHolder(),
			IJpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
	}
}