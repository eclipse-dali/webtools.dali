/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.persistence;

import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.GenericPersistenceXmlDefinition2_0;
import org.eclipse.jpt.jpa.ui.PersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractPersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitGeneralEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitPropertiesEditorPageDefinition;

public class PersistenceXmlUiDefinition2_0
	extends AbstractPersistenceResourceUiDefinition
{
	// singleton
	private static final PersistenceResourceUiDefinition INSTANCE = new PersistenceXmlUiDefinition2_0();

	/**
	 * Return the singleton
	 */
	public static PersistenceResourceUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private PersistenceXmlUiDefinition2_0() {
		super();
	}

	@Override
	protected void addEditorPageDefinitionsTo(List<JpaEditorPageDefinition> definitions) {
		definitions.add(PersistenceUnitGeneralEditorPageDefinition.instance());
		definitions.add(PersistenceUnitConnectionEditorPageDefinition2_0.instance());
		definitions.add(PersistenceUnitOptionsEditorPageDefinition2_0.instance());
		definitions.add(PersistenceUnitPropertiesEditorPageDefinition.instance());
	}

	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericPersistenceXmlDefinition2_0.instance().getResourceType());
	}
}
