/*******************************************************************************
 * Copyright (c) 2013, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.PropertyValueModelTools;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.PersistenceUnit2_1;
import org.eclipse.jpt.jpa.core.jpa2_1.context.persistence.schemagen.SchemaGeneration2_1;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition2_0;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.widgets.Composite;

/**
 *  PersistenceUnitSchemaGeneration2_1EditorPageDefinition
 */
public class PersistenceUnitSchemaGenerationEditorPageDefinition2_1
	extends PersistenceUnitEditorPageDefinition2_0
{
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = 
			new PersistenceUnitSchemaGenerationEditorPageDefinition2_1();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private PersistenceUnitSchemaGenerationEditorPageDefinition2_1() {
		super();
	}


	public ImageDescriptor getTitleImageDescriptor() {
		return null;
	}

	public String getTitleText() {	
		return JptJpaUiPersistenceMessages2_1.SCHEMA_GENERATION_COMPOSITE_SCHEMA_GENERATION_TAB_TITLE;
	}

	public String getHelpID() {
		// TODO 
		return null;
	}

	@Override
	@SuppressWarnings("unused")
	protected void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory, ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new PersistenceUnitSchemaGenerationEditorPage2_1(buildSchemaGenerationModel(persistenceUnitModel), parent, widgetFactory, resourceManager);
	}

	public static PropertyValueModel<SchemaGeneration2_1> buildSchemaGenerationModel(PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		return PropertyValueModelTools.transform(persistenceUnitModel, PersistenceUnit2_1.SCHEMA_GENERATION_TRANSFORMER);
	}
}
