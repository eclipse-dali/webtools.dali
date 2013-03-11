/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.v2_0.persistence;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkSchemaGeneration;
import org.eclipse.jpt.jpa.eclipselink.ui.JptJpaEclipseLinkUiMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.options.PersistenceXmlSchemaGenerationComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence.DataLoadingComposite;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence.SchemaGenerationComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

public class EclipseLinkPersistenceUnitSchemaGeneration2_5EditorPage
	extends Pane<PersistenceUnit>
{

	// ********** constructor **********
	
	public EclipseLinkPersistenceUnitSchemaGeneration2_5EditorPage(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel,
            Composite parentComposite,
            WidgetFactory widgetFactory,
            ResourceManager resourceManager) {
		super(persistenceUnitModel, parentComposite, widgetFactory, resourceManager);
	}

	@Override
	protected Composite addComposite(Composite parent) {
		return this.addSubPane(parent, 2, 0, 0, 0, 0);
	}

	@Override
	protected void initializeLayout(Composite container) {

		this.buildSchemaGenerationSection(container);
		
		this.buildEclipseLinkSchemaGenerationSection(container);
		
	}

	// ********** SchemaGeneration **********

	protected Section buildSchemaGenerationSection(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		section.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_SCHEMA_GENERATION_TAB_SECTION_TITLE);
		
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		section.setLayoutData(gridData);

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

		SchemaGenerationComposite schemaGenerationComposite = 
			new SchemaGenerationComposite(
				this, 
				this.buildSchemaGenerationHolder(),
				client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		schemaGenerationComposite.getControl().setLayoutData(gridData);
		
		// ********** Data Loading **********

		DataLoadingComposite dataLoadingComposite = 
			new DataLoadingComposite(
				this, 
				this.buildSchemaGenerationHolder(),
				client);
		gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.horizontalSpan = 2;
		dataLoadingComposite.getControl().setLayoutData(gridData);
		
		return section;
	}

	private PropertyValueModel<SchemaGeneration2_1> buildSchemaGenerationHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, SchemaGeneration2_1>(this.getSubjectHolder()) {
			@Override
			protected SchemaGeneration2_1 transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getSchemaGeneration();
			}
		};
	}
	
	// ********** EclipseLink SchemaGeneration **********

	protected Section buildEclipseLinkSchemaGenerationSection(Composite container) {
		Section section = this.getWidgetFactory().createSection(container, ExpandableComposite.TITLE_BAR);
		section.setText(JptJpaEclipseLinkUiMessages.PERSISTENCE_XML_ECLIPSELINK_SCHEMA_GENERATION_TAB_SECTION_TITLE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		gridData.verticalAlignment = SWT.TOP;
		section.setLayoutData(gridData);
		Control schemaGenerationComposite = this.initializeEclipseLinkSchemaGenerationSection(section);
		section.setClient(schemaGenerationComposite);
		return section;
	}

	protected Control initializeEclipseLinkSchemaGenerationSection(Section section) {
		return new PersistenceXmlSchemaGenerationComposite(this, this.buildEclipseLinkSchemaGenerationHolder(), section).getControl();
	}

	private PropertyValueModel<EclipseLinkSchemaGeneration> buildEclipseLinkSchemaGenerationHolder() {
		return new TransformationPropertyValueModel<PersistenceUnit, EclipseLinkSchemaGeneration>(this.getSubjectHolder()) {
			@Override
			protected EclipseLinkSchemaGeneration transform_(PersistenceUnit value) {
				return ((EclipseLinkPersistenceUnit) value).getEclipseLinkSchemaGeneration();
			}
		};
	}
}
