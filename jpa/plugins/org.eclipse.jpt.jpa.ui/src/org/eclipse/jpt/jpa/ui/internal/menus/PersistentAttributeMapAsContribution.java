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
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.commands.PersistentAttributeMapAsHandler;
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
	extends MapAsContribution<ReadOnlyPersistentAttribute>
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
	protected CommandContributionItemParameter createParameter(MappingUiDefinition<ReadOnlyPersistentAttribute, ?> mappingUiProvider) {
		CommandContributionItemParameter parameter = super.createParameter(mappingUiProvider);
		String defaultKey = null;
		if (mappingUiProvider instanceof DefaultMappingUiDefinition<?, ?>) {
			defaultKey = ((DefaultMappingUiDefinition<? extends PersistentAttribute, ?>) mappingUiProvider).getDefaultKey();
		}
		parameter.parameters.put(PersistentAttributeMapAsHandler.DEFAULT_MAPPING_COMMAND_PARAMETER_ID, defaultKey);
		return parameter;
	}
	
	@Override
	protected Iterator<? extends MappingUiDefinition<ReadOnlyPersistentAttribute, ?>> mappingUiDefinitions(
			JpaPlatformUi jpaPlatformUi, JptResourceType resourceType) {
		
		return jpaPlatformUi.attributeMappingUiDefinitions(resourceType);
	}
	
	@Override
	protected DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ?> getDefaultMappingUiDefinition(
			JpaPlatformUi jpaPlatformUi, ReadOnlyPersistentAttribute node) {
		
		return getDefaultMappingUiDefinition(
				jpaPlatformUi, 
				((PersistentAttribute) node).getDefaultMappingKey(), 
				node.getResourceType());
	}
	
	protected DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ?> getDefaultMappingUiDefinition(
			JpaPlatformUi jpaPlatformUi, String defaultMappingKey, JptResourceType resourceType) {
		
		return jpaPlatformUi.getDefaultAttributeMappingUiDefinition(resourceType, defaultMappingKey);
	}
}
