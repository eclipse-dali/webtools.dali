/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_2.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2_2.context.persistence.schemagen.SchemaGeneration2_2;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class PersistenceUnitSchemaGenerationEditorPage2_2 extends Pane<SchemaGeneration2_2> {
	public PersistenceUnitSchemaGenerationEditorPage2_2(PropertyValueModel<SchemaGeneration2_2> subjectModel,
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

		SchemaGenerationComposite2_2 schemaGenerationComposite = new SchemaGenerationComposite2_2(this, client);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		schemaGenerationComposite.getControl().setLayoutData(gridData);

		// ********** Data Loading **********

		DataLoadingComposite2_2 dataLoadingComposite = new DataLoadingComposite2_2(this, client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		dataLoadingComposite.getControl().setLayoutData(gridData);

	}
}
