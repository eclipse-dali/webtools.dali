/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.menus;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.handlers.PersistentAttributeMapAsHandler;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * This menu contribution is responsible for populating the "Map As" menu with
 * the registered mapping types defined in the {@link JpaPlatformUi} for
 * {@link SpecifiedPersistentAttribute}s.
 */
public class PersistentAttributeMapAsContribution
	extends MapAsContribution<PersistentAttribute>
{
	public PersistentAttributeMapAsContribution() {
		super();
	}

	@Override
	protected String getCommandID() {
		return PersistentAttributeMapAsHandler.COMMAND_ID;
	}
	
	@Override
	protected String getCommandParameterID() {
		return PersistentAttributeMapAsHandler.SPECIFIED_MAPPING_COMMAND_PARAMETER_ID;
	}
	
	@Override
	protected CommandContributionItemParameter buildContributionItemParameter(MappingUiDefinition mappingUiProvider) {
		CommandContributionItemParameter parameter = super.buildContributionItemParameter(mappingUiProvider);
		String defaultKey = null;
		if (mappingUiProvider instanceof DefaultMappingUiDefinition) {
			defaultKey = ((DefaultMappingUiDefinition) mappingUiProvider).getDefaultKey();
		}
		parameter.parameters.put(PersistentAttributeMapAsHandler.DEFAULT_MAPPING_COMMAND_PARAMETER_ID, defaultKey);
		return parameter;
	}
	
	@Override
	protected Iterable<MappingUiDefinition> getMappingUiDefinitions(JpaPlatformUi jpaPlatformUI, PersistentAttribute node) {
		return jpaPlatformUI.getAttributeMappingUiDefinitions(node);
	}
	
	@Override
	protected DefaultMappingUiDefinition getDefaultMappingUiDefinition(JpaPlatformUi jpaPlatformUI, PersistentAttribute node) {
		return getDefaultMappingUiDefinition(jpaPlatformUI, ((SpecifiedPersistentAttribute) node).getDefaultMappingKey(), node.getResourceType());
	}
	
	protected DefaultMappingUiDefinition getDefaultMappingUiDefinition(JpaPlatformUi jpaPlatformUI, String defaultMappingKey, JptResourceType resourceType) {
		return jpaPlatformUI.getDefaultAttributeMappingUiDefinition(resourceType, defaultMappingKey);
	}
}
