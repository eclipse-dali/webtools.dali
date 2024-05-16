/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa3_1.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa3_1.context.persistence.schemagen.SchemaGeneration3_1;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class PersistenceUnitSchemaGenerationEditorPage3_1 extends Pane<SchemaGeneration3_1> {
	public PersistenceUnitSchemaGenerationEditorPage3_1(PropertyValueModel<SchemaGeneration3_1> subjectModel,
			Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		super(subjectModel, parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section section = this.getWidgetFactory().createSection(container,
				ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiPersistenceMessages2_1.PERSISTENCE_UNIT_SCHEMA_GENERATION2_1_EDITOR_PAGE_TITLE);
		section.setDescription(
				JptJpaUiPersistenceMessages2_1.PERSISTENCE_UNIT_SCHEMA_GENERATION2_1_EDITOR_PAGE_DESCRIPTION);

		Composite client = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		layout.marginTop = 0;
		layout.marginLeft = 0;
		layout.marginBottom = 0;
		layout.marginRight = 0;
		client.setLayout(layout);
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		// ********** Schema Generation properties **********

		SchemaGenerationComposite3_1 schemaGenerationComposite = new SchemaGenerationComposite3_1(this, client);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		schemaGenerationComposite.getControl().setLayoutData(gridData);

		// ********** Data Loading **********

		DataLoadingComposite3_1 dataLoadingComposite = new DataLoadingComposite3_1(this, client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		dataLoadingComposite.getControl().setLayoutData(gridData);

	}
}
