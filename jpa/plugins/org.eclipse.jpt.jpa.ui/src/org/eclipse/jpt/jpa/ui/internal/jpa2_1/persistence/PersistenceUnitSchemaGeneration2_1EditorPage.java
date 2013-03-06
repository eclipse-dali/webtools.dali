/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *  PersistenceUnitSchemaGeneration2_1EditorPage
 */
public class PersistenceUnitSchemaGeneration2_1EditorPage
	extends Pane<SchemaGeneration2_1>
{
	public PersistenceUnitSchemaGeneration2_1EditorPage(
		PropertyValueModel<SchemaGeneration2_1> subjectModel,
        Composite parent,
        WidgetFactory widgetFactory,
        ResourceManager resourceManager) {
	super(subjectModel, parent, widgetFactory, resourceManager);
	}

	@Override
	protected void initializeLayout(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		section.setText(JptJpaUiPersistenceMessages2_1.PersistenceUnitSchemaGeneration2_1EditorPage_title);
		section.setDescription(JptJpaUiPersistenceMessages2_1.PersistenceUnitSchemaGeneration2_1EditorPage_description);

		Composite client = this.getWidgetFactory().createComposite(section);
		GridLayout layout = new GridLayout(2, false);
		layout.marginHeight = 0;
		layout.marginWidth  = 0;
		layout.marginTop    = 0;
		layout.marginLeft   = 0;
		layout.marginBottom = 0;
		layout.marginRight  = 0;
		client.setLayout(layout);
		client.setLayoutData(new GridData(GridData.FILL_BOTH));
		section.setClient(client);

		// ********** Schema Generation properties **********

		SchemaGenerationComposite<SchemaGeneration2_1> schemaGenerationComposite = new SchemaGenerationComposite<SchemaGeneration2_1>(this, client);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		schemaGenerationComposite.getControl().setLayoutData(gridData);
		
		// ********** Data Loading **********

		DataLoadingComposite<SchemaGeneration2_1> dataLoadingComposite = new DataLoadingComposite<SchemaGeneration2_1>(this, client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		dataLoadingComposite.getControl().setLayoutData(gridData);

	}

}
