/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.menus;

import java.util.Iterator;
import org.eclipse.jpt.core.JpaResourceType;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.ui.internal.commands.PersistentAttributeMapAsHandler;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * This menu contribution is responsible to populate the Map As menu with the
 * registered attribute mapping types defined in the <code>JptPlatformUi</code>
 * for <code>PersistentAttribute</code> objects.
 *
 * @see PersistentAttributeMapAsHandler
 * @see PersistentAttribute
 *
 * @version 2.2
 * @since 2.0
 */
public class PersistentAttributeMapAsContribution
	extends MapAsContribution<PersistentAttribute>
{
	/**
	 * Creates a new <code>PersistentAttributeMapAsContribution</code>.
	 */
	public PersistentAttributeMapAsContribution() {
		super();
	}

	@Override
	protected String getCommandId() {
		return PersistentAttributeMapAsHandler.COMMAND_ID;
	}
	
	@Override
	protected String getCommandParameterId() {
		return PersistentAttributeMapAsHandler.SPECIFIED_MAPPING_COMMAND_PARAMETER_ID;
	}
	
	@Override
	protected CommandContributionItemParameter createParameter(MappingUiDefinition<PersistentAttribute, ?> mappingUiProvider) {
		CommandContributionItemParameter parameter = super.createParameter(mappingUiProvider);
		String defaultKey = null;
		if (mappingUiProvider instanceof DefaultMappingUiDefinition<?, ?>) {
			defaultKey = ((DefaultMappingUiDefinition<? extends PersistentAttribute, ?>) mappingUiProvider).getDefaultKey();
		}
		parameter.parameters.put(PersistentAttributeMapAsHandler.DEFAULT_MAPPING_COMMAND_PARAMETER_ID, defaultKey);
		return parameter;
	}
	
	@Override
	protected Iterator<? extends MappingUiDefinition<PersistentAttribute, ?>> mappingUiDefinitions(
			JpaPlatformUi jpaPlatformUi, JpaResourceType resourceType) {
		
		return jpaPlatformUi.attributeMappingUiDefinitions(resourceType);
	}
	
	@Override
	protected DefaultMappingUiDefinition<PersistentAttribute, ?> getDefaultMappingUiDefinition(
			JpaPlatformUi jpaPlatformUi, PersistentAttribute node) {
		
		return getDefaultMappingUiDefinition(
				jpaPlatformUi, 
				((PersistentAttribute) node).getDefaultMappingKey(), 
				node.getResourceType());
	}
	
	protected DefaultMappingUiDefinition<PersistentAttribute, ?> getDefaultMappingUiDefinition(
			JpaPlatformUi jpaPlatformUi, String defaultMappingKey, JpaResourceType resourceType) {
		
		return jpaPlatformUi.getDefaultAttributeMappingUiDefinition(resourceType, defaultMappingKey);
	}
}
