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

import java.util.List;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.internal.jpa2_2.context.persistence.GenericPersistenceXmlDefinition2_2;
import org.eclipse.jpt.jpa.ui.JavaManagedTypeUiDefinition;
import org.eclipse.jpt.jpa.ui.PersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractPersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitConnectionEditorPageDefinition2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2.persistence.PersistenceUnitOptionsEditorPageDefinition2_0;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.JavaConverterTypeUiDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.jpa2_1.persistence.PersistenceUnitSchemaGenerationEditorPageDefinition2_1;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitGeneralEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitPropertiesEditorPageDefinition;

public class PersistenceXmlUiDefinition2_2 extends AbstractPersistenceResourceUiDefinition {
	// singleton
	private static final PersistenceResourceUiDefinition INSTANCE = new PersistenceXmlUiDefinition2_2();

	/**
	 * Return the singleton
	 */
	public static PersistenceResourceUiDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private PersistenceXmlUiDefinition2_2() {
		super();
	}

	@Override
	protected void addEditorPageDefinitionsTo(List<JpaEditorPageDefinition> definitions) {
		definitions.add(PersistenceUnitGeneralEditorPageDefinition.instance());
		definitions.add(PersistenceUnitConnectionEditorPageDefinition2_0.instance());
		definitions.add(PersistenceUnitOptionsEditorPageDefinition2_0.instance());
		definitions.add(PersistenceUnitSchemaGenerationEditorPageDefinition2_1.instance());
		definitions.add(PersistenceUnitPropertiesEditorPageDefinition.instance());
		definitions.add(PersistenceUnitSchemaGenerationEditorPageDefinition2_2.instance());
	}

	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericPersistenceXmlDefinition2_2.instance().getResourceType());
	}

	@Override
	protected void addJavaManagedTypeUiDefinitionsTo(List<JavaManagedTypeUiDefinition> definitions) {
		super.addJavaManagedTypeUiDefinitionsTo(definitions);
		definitions.add(JavaConverterTypeUiDefinition2_1.instance());
	}
}
