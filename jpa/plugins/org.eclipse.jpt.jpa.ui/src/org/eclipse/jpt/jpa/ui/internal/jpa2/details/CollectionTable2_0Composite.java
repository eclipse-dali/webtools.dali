/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyReferenceTable;
import org.eclipse.jpt.jpa.core.jpa2.context.CollectionTable2_0;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnsComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ReferenceTableComposite;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

/**
 * The layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |            ------------------------------------------------------------   |
 * |   Name:    |                                                        |v|   |
 * |            ------------------------------------------------------------   |
 * |            ------------------------------------------------------------   |
 * |   Schema:  |                                                        |v|   |
 * |            ------------------------------------------------------------   |
 * |            ------------------------------------------------------------   |
 * |   Catalog: |                                                        |v|   |
 * |            ------------------------------------------------------------   |
 * |                                                                           |
 * | - Join Columns ---------------------------------------------------------- |
 * | |                                                                       | |
 * | | x Override Default                                                    | |
 * | |                                                                       | |
 * | | --------------------------------------------------------------------- | |
 * | | |                                                                   | | |
 * | | | JoinColumnsComposite                                              | | |
 * | | |                                                                   | | |
 * | | --------------------------------------------------------------------- | |
 * | ------------------------------------------------------------------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see {@link CollectionTable2_0}
 * @see {@link JoinColumnsComposite
 *
 * @version 3.0
 * @since 3.0
 */
public class CollectionTable2_0Composite
	extends ReferenceTableComposite<ReadOnlyReferenceTable>
{
	/**
	 * Creates a new <code>CollectionTable2_0Composite</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public CollectionTable2_0Composite(
			Pane<?> parentPane,
			PropertyValueModel<? extends ReadOnlyReferenceTable> subjectHolder,
			Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>CollectionTable2_0Composite</code>.
	 *
	 * @param subjectHolder The holder of the subject <code>CollectionTable2_0</code>
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public CollectionTable2_0Composite(PropertyValueModel<? extends CollectionTable2_0> subjectHolder,
	                          Composite parent,
	                          WidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptUiDetailsMessages2_0.CollectionTable2_0Composite_title,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptUiDetailsMessages2_0.CollectionTable2_0Composite_name);
		this.addTableCombo(container, JpaHelpContextIds.MAPPING_COLLECTION_TABLE_NAME);
		
		// schema widgets
		this.addLabel(container, JptUiDetailsMessages2_0.CollectionTable2_0Composite_schema);
		addSchemaCombo(container, JpaHelpContextIds.MAPPING_COLLECTION_TABLE_SCHEMA);
		
		// catalog widgets
		this.addLabel(container, JptUiDetailsMessages2_0.CollectionTable2_0Composite_catalog);
		addCatalogCombo(container, JpaHelpContextIds.MAPPING_COLLECTION_TABLE_CATALOG);

		// Join Columns group pane
		Group joinColumnGroupPane = addTitledGroup(
			container,
			JptUiDetailsMessages2_0.CollectionTable2_0Composite_joinColumn
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		joinColumnGroupPane.setLayoutData(gridData);

		// Override Default Join Columns check box
		this.overrideDefaultJoinColumnsCheckBox = addCheckBox(
			joinColumnGroupPane,
			JptUiDetailsMessages2_0.CollectionTable2_0Composite_overrideDefaultJoinColumns,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);

		this.joinColumnsComposite = new JoinColumnsComposite<ReadOnlyReferenceTable>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor(),
			buildJoinColumnsEnabledModel()
		);
	}
	
	@Override
	protected boolean tableIsVirtual(ReadOnlyReferenceTable collectionTable) {
		return collectionTable.getPersistentAttribute().isVirtual();
	}
}