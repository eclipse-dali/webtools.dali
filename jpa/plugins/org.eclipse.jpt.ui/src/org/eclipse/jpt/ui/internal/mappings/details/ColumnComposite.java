/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IAbstractColumn;
import org.eclipse.jpt.core.internal.context.base.IColumn;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.db.ColumnCombo;
import org.eclipse.jpt.ui.internal.mappings.db.TableCombo;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * ----------------------------------------------------------------------------�??
 * | ------------------------------------------------------------------------�?? |
 * | |                                                                       | |
 * | | ColumnCombo                                                           | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------�?? |
 * | |                                                                       | |
 * | | TableCombo                                                            | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------�?? |
 * | |                                                                       | |
 * | | EnumComboViewer                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * | ------------------------------------------------------------------------�?? |
 * | |                                                                       | |
 * | | EnumComboViewer                                                       | |
 * | |                                                                       | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IColumn
 * @see EnumComboViewer
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
public class ColumnComposite extends BaseJpaComposite<IColumn>
{
	private ColumnCombo columnCombo;
	private EnumComboViewer<IColumn, Boolean> insertableCombo;
	private TableCombo tableCombo;
	private EnumComboViewer<IColumn, Boolean> updatableCombo;

	/**
	 * Creates a new <code>ColumnComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>T</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public ColumnComposite(PropertyValueModel<? extends IColumn> subjectHolder,
	                       Composite parent,
	                       TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	private EnumComboViewer<IColumn, Boolean> buildInsertableCombo(Composite container) {

		return new EnumComboViewer<IColumn, Boolean>(getSubjectHolder(), container, getWidgetFactory()) {

			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultInsertable();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					ColumnComposite.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getSpecifiedInsertable();
			}

			@Override
			protected String propertyName() {
				return IAbstractColumn.SPECIFIED_INSERTABLE_PROPERTY;
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setSpecifiedInsertable(value);
			}
		};
	}

	private EnumComboViewer<IColumn, Boolean> buildUpdatableCombo(Composite container) {

		return new EnumComboViewer<IColumn, Boolean>(getSubjectHolder(), container, getWidgetFactory()) {

			@Override
			protected Boolean[] choices() {
				return new Boolean[] { Boolean.TRUE, Boolean.FALSE };
			}

			@Override
			protected Boolean defaultValue() {
				return subject().getDefaultUpdatable();
			}

			@Override
			protected String displayString(Boolean value) {
				return buildDisplayString(
					JptUiMappingsMessages.class,
					ColumnComposite.this,
					value
				);
			}

			@Override
			protected Boolean getValue() {
				return subject().getSpecifiedUpdatable();
			}

			@Override
			protected String propertyName() {
				return IAbstractColumn.SPECIFIED_UPDATABLE_PROPERTY;
			}

			@Override
			protected void setValue(Boolean value) {
				subject().setSpecifiedUpdatable(value);
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void disengageListeners() {
		super.disengageListeners();

		this.columnCombo.disengageListeners();
		this.tableCombo.disengageListeners();
		this.insertableCombo.disengageListeners();
		this.updatableCombo.disengageListeners();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {
		this.columnCombo.dispose();
		this.tableCombo.dispose();
		this.insertableCombo.dispose();
		this.updatableCombo.dispose();
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();

		this.columnCombo.populate();
		this.tableCombo.populate();
		this.insertableCombo.populate();
		this.updatableCombo.populate();
	}

	protected void enableWidgets(boolean enabled) {
//		this.columnCombo.setEnabled(enabled);
//		this.tableCombo.setEnabled(enabled);
		this.insertableCombo.getControl().setEnabled(enabled);
		this.updatableCombo.getControl().setEnabled(enabled);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void engageListeners() {
		super.engageListeners();

		this.columnCombo.engageListeners();
		this.tableCombo.engageListeners();
		this.insertableCombo.engageListeners();
		this.updatableCombo.engageListeners();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		container = buildTitledPane(
			JptUiMappingsMessages.ColumnComposite_columnSection,
			container
		);

		// Column widgets
		columnCombo = new ColumnCombo(this, container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnChooser_label,
			columnCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN
		);

		// Table widgets
		tableCombo = new TableCombo(this, container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnTableChooser_label,
			tableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_TABLE
		);

		// Insertable widgets
		insertableCombo = buildInsertableCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnComposite_insertable,
			insertableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_INSERTABLE
		);

		// Updatable widgets
		updatableCombo = buildUpdatableCombo(container);

		buildLabeledComposite(
			container,
			JptUiMappingsMessages.ColumnComposite_updatable,
			updatableCombo.getControl(),
			IJpaHelpContextIds.MAPPING_COLUMN_UPDATABLE
		);
	}
}