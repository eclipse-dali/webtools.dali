/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.persistence;

import java.util.List;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXmlDefinition;
import org.eclipse.jpt.jpa.ui.PersistenceResourceUiDefinition;
import org.eclipse.jpt.jpa.ui.editors.JpaEditorPageDefinition;
import org.eclipse.jpt.jpa.ui.internal.AbstractPersistenceResourceUiDefinition;

public class PersistenceXmlUiDefinition
	extends AbstractPersistenceResourceUiDefinition
{
	// singleton
	private static final PersistenceResourceUiDefinition INSTANCE = new PersistenceXmlUiDefinition();
	
	
	/**
	 * Return the singleton
	 */
	public static PersistenceResourceUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Enforce singleton usage
	 */
	private PersistenceXmlUiDefinition() {
		super();
	}

	@Override
	protected void addEditorPageDefinitionsTo(List<JpaEditorPageDefinition> definitions) {
		definitions.add(PersistenceUnitGeneralEditorPageDefinition.instance());
		definitions.add(PersistenceUnitConnectionEditorPageDefinition.instance());
		definitions.add(PersistenceUnitPropertiesEditorPageDefinition.instance());
	}

	public boolean providesUi(JptResourceType resourceType) {
		return resourceType.equals(GenericPersistenceXmlDefinition.instance().getResourceType());
	}
}
