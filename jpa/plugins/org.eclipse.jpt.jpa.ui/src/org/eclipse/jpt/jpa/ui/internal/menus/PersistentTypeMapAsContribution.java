/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.menus;

import java.util.Iterator;
import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaPlatform;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.commands.PersistentTypeMapAsHandler;

/**
 * This menu contribution is responsible to populate the Map As menu with the
 * registered mapping types defined in the <code>JptPlatformUi</code> for
 * <code>PersistentType</code> objects.
 *
 * @see JpaPlatform
 * @see JpaPlatformUi
 * @see PersistentType
 */
public class PersistentTypeMapAsContribution
	extends MapAsContribution<PersistentType>
{
	/**
	 * Creates a new <code>PersistentTypeMapAsContribution</code>.
	 */
	public PersistentTypeMapAsContribution() {
		super();
	}
	
	
	@Override
	protected String getCommandId() {
		return PersistentTypeMapAsHandler.COMMAND_ID;
	}
	
	@Override
	protected String getCommandParameterId() {
		return PersistentTypeMapAsHandler.COMMAND_PARAMETER_ID;
	}
	
	@Override
	protected Iterator<? extends MappingUiDefinition<PersistentType, ?>> mappingUiDefinitions(
			JpaPlatformUi jpaPlatformUi, JptResourceType resourceType) {
		
		return jpaPlatformUi.typeMappingUiDefinitions(resourceType);
	}
	
	@Override
	protected DefaultMappingUiDefinition<PersistentType, ?> getDefaultMappingUiDefinition(
			JpaPlatformUi jpaPlatformUi, PersistentType node) {
		
		return jpaPlatformUi.getDefaultTypeMappingUiDefinition(node.getResourceType());
	}
}
