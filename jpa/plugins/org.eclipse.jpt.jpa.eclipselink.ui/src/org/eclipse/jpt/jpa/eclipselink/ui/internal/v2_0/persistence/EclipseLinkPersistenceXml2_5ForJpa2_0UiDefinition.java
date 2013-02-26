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

import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.persistence.GenericPersistenceXml2_0Definition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.connection.EclipseLinkPersistenceUnitConnectionEditorPageDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.persistence.general.EclipseLinkPersistenceUnitGeneralEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.PersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.ResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractPersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.persistence.PersistenceUnitPropertiesEditorPageDefinition;

public class EclipseLinkPersistenceXml2_5ForJpa2_0UiDefinition
	extends AbstractPersistenceResourceUiDefinition
{
	// singleton
	private static final PersistenceResourceUiDefinition INSTANCE = new EclipseLinkPersistenceXml2_5ForJpa2_0UiDefinition();

	/**
	 * Return the singleton
	 */
	public static ResourceUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private EclipseLinkPersistenceXml2_5ForJpa2_0UiDefinition() {
		super();
	}

	@Override
	protected void addEditorPageDefinitionsTo(List<JpaEditorPageDefinition> definitions) {
		definitions.add(EclipseLinkPersistenceUnitGeneralEditorPageDefinition.instance());
		definitions.add(EclipseLinkPersistenceUnitConnectionEditorPageDefinition.instance());
		definitions.add(EclipseLinkPersistenceUnitCustomization2_0EditorPageDefinition.instance());
		definitions.add(EclipseLinkPersistenceUnitCaching2_0EditorPageDefinition.instance());
		definitions.add(EclipseLinkPersistenceUnitOptions2_4EditorPageDefinition.instance());
		definitions.add(PersistenceUnitPropertiesEditorPageDefinition.instance());
	}

	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericPersistenceXml2_0Definition.instance().getResourceType());
	}
}
