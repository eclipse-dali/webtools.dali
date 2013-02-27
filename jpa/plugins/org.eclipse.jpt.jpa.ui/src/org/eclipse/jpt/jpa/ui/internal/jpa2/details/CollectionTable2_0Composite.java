/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReferenceTable;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.details.JoinColumnsComposite;
import org.eclipse.jpt.jpa.ui.internal.details.ReferenceTableComposite;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class CollectionTable2_0Composite
	extends ReferenceTableComposite<ReferenceTable>
{
	public CollectionTable2_0Composite(
			Pane<?> parentPane,
			PropertyValueModel<? extends ReferenceTable> tableModel,
			Composite parentComposite) {
		super(parentPane, tableModel, parentComposite);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return addTitledGroup(
			parent,
			JptJpaUiDetailsMessages2_0.COLLECTION_TABLE_COMPOSITE_TITLE,
			2,
			null
		);
	}

	@Override
	protected void initializeLayout(Composite container) {
		// Name widgets
		this.addLabel(container, JptJpaUiDetailsMessages2_0.COLLECTION_TABLE_COMPOSITE_NAME);
		this.addTableCombo(container, JpaHelpContextIds.MAPPING_COLLECTION_TABLE_NAME);
		
		// schema widgets
		this.addLabel(container, JptJpaUiDetailsMessages2_0.COLLECTION_TABLE_COMPOSITE_SCHEMA);
		addSchemaCombo(container, JpaHelpContextIds.MAPPING_COLLECTION_TABLE_SCHEMA);
		
		// catalog widgets
		this.addLabel(container, JptJpaUiDetailsMessages2_0.COLLECTION_TABLE_COMPOSITE_CATALOG);
		addCatalogCombo(container, JpaHelpContextIds.MAPPING_COLLECTION_TABLE_CATALOG);

		// Join Columns group pane
		Group joinColumnGroupPane = addTitledGroup(
			container,
			JptJpaUiDetailsMessages2_0.COLLECTION_TABLE_COMPOSITE_JOIN_COLUMN
		);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		joinColumnGroupPane.setLayoutData(gridData);

		// Override Default Join Columns check box
		this.overrideDefaultJoinColumnsCheckBox = addCheckBox(
			joinColumnGroupPane,
			JptJpaUiDetailsMessages2_0.COLLECTION_TABLE_COMPOSITE_OVERRIDE_DEFAULT_JOIN_COLUMNS,
			buildOverrideDefaultJoinColumnHolder(),
			null
		);

		this.joinColumnsComposite = new JoinColumnsComposite<ReferenceTable>(
			this,
			joinColumnGroupPane,
			buildJoinColumnsEditor(),
			buildJoinColumnsEnabledModel()
		);
	}
	
	@Override
	protected boolean tableIsVirtual(ReferenceTable collectionTable) {
		return collectionTable.getPersistentAttribute().isVirtual();
	}
}