/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.menus;

import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.commands.PersistentTypeMapAsHandler;

/**
 * This menu contribution is responsible for populating the "Map As" menu with
 * the registered mapping types defined in the {@link JpaPlatformUi} for
 * {@link PersistentType}s.
 */
public class PersistentTypeMapAsContribution
	extends MapAsContribution<PersistentType>
{
	public PersistentTypeMapAsContribution() {
		super();
	}
	
	@Override
	protected String getCommandID() {
		return PersistentTypeMapAsHandler.COMMAND_ID;
	}
	
	@Override
	protected String getCommandParameterID() {
		return PersistentTypeMapAsHandler.COMMAND_PARAMETER_ID;
	}
	
	@Override
	protected Iterable<MappingUiDefinition> getMappingUiDefinitions(JpaPlatformUi jpaPlatformUI, PersistentType node) {
		return jpaPlatformUI.getTypeMappingUiDefinitions(node);
	}
	
	@Override
	protected DefaultMappingUiDefinition getDefaultMappingUiDefinition(JpaPlatformUi jpaPlatformUI, PersistentType node) {
		return jpaPlatformUI.getDefaultTypeMappingUiDefinition(node.getResourceType());
	}
}
