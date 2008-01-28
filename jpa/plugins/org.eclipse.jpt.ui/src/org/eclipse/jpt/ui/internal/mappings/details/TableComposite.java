/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |          ---------------------------------------------------------------- |
 * | Table:   | TableCombo                                                   | |
 * |          ---------------------------------------------------------------- |
 * |          ---------------------------------------------------------------- |
 * | Catalog: | CatalogCombo                                                 | |
 * |          ---------------------------------------------------------------- |
 * |          ---------------------------------------------------------------- |
 * | Schema:  | SchemaCombo                                                  | |
 * |          ---------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see IBasicMapping
 * @see BaseJpaUiFactory
 * @see TableCombo
 * @see CatalogCombo
 * @see SchemaCombo
 *
 * @TODO repopulate this panel based on the Entity table changing
 *
 * @version 2.0
 * @since 1.0
 */
public class TableComposite extends AbstractFormPane<ITable>
{
	/**
	 * Creates a new <code>TableComposite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>ITable</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public TableComposite(PropertyValueModel<? extends ITable> subjectHolder,
	                      Composite parent,
	                      IWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/**
	 * Creates a new <code>TableComposite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of the subject
	 * @param parent The parent container
	 */
	public TableComposite(AbstractFormPane<?> parentPane,
	                      PropertyValueModel<? extends ITable> subjectHolder,
	                      Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		Group columnGroup = buildTitledPane(
			container,
			JptUiMappingsMessages.TableComposite_tableSection
		);

		// Table widgets
		TableCombo tableCombo = new TableCombo(this, columnGroup);

		buildLabeledComposite(
			columnGroup,
			JptUiMappingsMessages.TableChooser_label,
			tableCombo.getControl(),
			IJpaHelpContextIds.ENTITY_TABLE
		);

		// Catalog widgets
		CatalogCombo catalogCombo = new CatalogCombo(this, columnGroup);

		buildLabeledComposite(
			columnGroup,
			JptUiMappingsMessages.CatalogChooser_label,
			catalogCombo.getControl(),
			IJpaHelpContextIds.ENTITY_CATALOG
		);

		// Schema widgets
		SchemaCombo schemaCombo = new SchemaCombo(this, columnGroup);

		buildLabeledComposite(
			columnGroup,
			JptUiMappingsMessages.SchemaChooser_label,
			schemaCombo.getControl(),
			IJpaHelpContextIds.ENTITY_SCHEMA
		);
	}
}