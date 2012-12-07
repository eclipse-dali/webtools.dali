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

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.context.PersistentAttribute;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.commands.PersistentAttributeMapAsHandler;
import org.eclipse.ui.menus.CommandContributionItemParameter;

/**
 * This menu contribution is responsible for populating the "Map As" menu with
 * the registered mapping types defined in the {@link JpaPlatformUi} for
 * {@link PersistentAttribute}s.
 */
public class PersistentAttributeMapAsContribution
	extends MapAsContribution<ReadOnlyPersistentAttribute>
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
	protected CommandContributionItemParameter buildContributionItemParameter(MappingUiDefinition<ReadOnlyPersistentAttribute, ?> mappingUiProvider) {
		CommandContributionItemParameter parameter = super.buildContributionItemParameter(mappingUiProvider);
		String defaultKey = null;
		if (mappingUiProvider instanceof DefaultMappingUiDefinition<?, ?>) {
			defaultKey = ((DefaultMappingUiDefinition<? extends PersistentAttribute, ?>) mappingUiProvider).getDefaultKey();
		}
		parameter.parameters.put(PersistentAttributeMapAsHandler.DEFAULT_MAPPING_COMMAND_PARAMETER_ID, defaultKey);
		return parameter;
	}
	
	@Override
	protected Iterable<? extends MappingUiDefinition<ReadOnlyPersistentAttribute, ?>> getMappingUiDefinitions(JpaPlatformUi jpaPlatformUI, JptResourceType resourceType) {
		return jpaPlatformUI.getAttributeMappingUiDefinitions(resourceType);
	}
	
	@Override
	protected DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ?> getDefaultMappingUiDefinition(JpaPlatformUi jpaPlatformUI, ReadOnlyPersistentAttribute node) {
		return getDefaultMappingUiDefinition(jpaPlatformUI, ((PersistentAttribute) node).getDefaultMappingKey(), node.getResourceType());
	}
	
	protected DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ?> getDefaultMappingUiDefinition(JpaPlatformUi jpaPlatformUI, String defaultMappingKey, JptResourceType resourceType) {
		return jpaPlatformUI.getDefaultAttributeMappingUiDefinition(resourceType, defaultMappingKey);
	}
}
