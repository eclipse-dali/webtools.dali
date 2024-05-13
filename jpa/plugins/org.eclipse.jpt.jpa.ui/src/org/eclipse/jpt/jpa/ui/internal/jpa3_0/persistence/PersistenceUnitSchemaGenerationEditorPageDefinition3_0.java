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
package org.eclipse.jpt.jpa.ui.internal.jpa3_0.persistence;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.PersistenceUnit3_0;
import org.eclipse.jpt.jpa.core.jpa3_0.context.persistence.schemagen.SchemaGeneration3_0;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitEditorPageDefinition2_0;
import org.eclipse.jpt.jpa.ui.jpa2_1.persistence.JptJpaUiPersistenceMessages2_1;
import org.eclipse.swt.widgets.Composite;

/**
 * PersistenceUnitSchemaGeneration2_1EditorPageDefinition
 */
public class PersistenceUnitSchemaGenerationEditorPageDefinition3_0 extends PersistenceUnitEditorPageDefinition2_0 {
	// singleton
	private static final JpaEditorPageDefinition INSTANCE = new PersistenceUnitSchemaGenerationEditorPageDefinition3_0();

	/**
	 * Return the singleton.
	 */
	public static JpaEditorPageDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private PersistenceUnitSchemaGenerationEditorPageDefinition3_0() {
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
	protected void buildEditorPageContent(Composite parent, WidgetFactory widgetFactory,
			ResourceManager resourceManager, PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		new PersistenceUnitSchemaGenerationEditorPage3_0(buildSchemaGenerationModel(persistenceUnitModel), parent,
				widgetFactory, resourceManager);
	}

	public static TransformationPropertyValueModel<PersistenceUnit, SchemaGeneration3_0> buildSchemaGenerationModel(
			PropertyValueModel<PersistenceUnit> persistenceUnitModel) {
		return new TransformationPropertyValueModel<PersistenceUnit, SchemaGeneration3_0>(persistenceUnitModel,
				SCHEMAGEN_TRANSFORMER);
	}

	public static final Transformer<PersistenceUnit, SchemaGeneration3_0> SCHEMAGEN_TRANSFORMER = new SchemaGenerationTransformer();

	public static class SchemaGenerationTransformer extends AbstractTransformer<PersistenceUnit, SchemaGeneration3_0> {
		@Override
		protected SchemaGeneration3_0 transform_(PersistenceUnit persistenceUnit) {
			return ((PersistenceUnit3_0) persistenceUnit).getSchemaGeneration();
		}
	}
}
