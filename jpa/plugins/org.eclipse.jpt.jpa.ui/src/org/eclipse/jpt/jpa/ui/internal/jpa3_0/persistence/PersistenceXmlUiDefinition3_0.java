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

import java.util.List;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.internal.jpa3_0.context.persistence.GenericPersistenceXmlDefinition3_0;
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

public class PersistenceXmlUiDefinition3_0 extends AbstractPersistenceResourceUiDefinition {
	// singleton
	private static final PersistenceResourceUiDefinition INSTANCE = new PersistenceXmlUiDefinition3_0();

	/**
	 * Return the singleton
	 */
	public static PersistenceResourceUiDefinition instance() {
		return INSTANCE;
	}

	/**
	 * Enforce singleton usage
	 */
	private PersistenceXmlUiDefinition3_0() {
		super();
	}

	@Override
	protected void addEditorPageDefinitionsTo(List<JpaEditorPageDefinition> definitions) {
		definitions.add(PersistenceUnitGeneralEditorPageDefinition.instance());
		definitions.add(PersistenceUnitConnectionEditorPageDefinition2_0.instance());
		definitions.add(PersistenceUnitOptionsEditorPageDefinition2_0.instance());
		definitions.add(PersistenceUnitSchemaGenerationEditorPageDefinition2_1.instance());
		definitions.add(PersistenceUnitPropertiesEditorPageDefinition.instance());
		definitions.add(PersistenceUnitSchemaGenerationEditorPageDefinition3_0.instance());
	}

	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericPersistenceXmlDefinition3_0.instance().getResourceType());
	}

	@Override
	protected void addJavaManagedTypeUiDefinitionsTo(List<JavaManagedTypeUiDefinition> definitions) {
		super.addJavaManagedTypeUiDefinitionsTo(definitions);
		definitions.add(JavaConverterTypeUiDefinition2_1.instance());
	}
}
