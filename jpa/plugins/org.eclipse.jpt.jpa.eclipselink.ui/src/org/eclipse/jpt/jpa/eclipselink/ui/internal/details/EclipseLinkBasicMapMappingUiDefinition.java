/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkBasicMapMappingUiDefinition
	extends AbstractMappingUiDefinition
{
	// singleton
	private static final EclipseLinkBasicMapMappingUiDefinition INSTANCE = 
			new EclipseLinkBasicMapMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private EclipseLinkBasicMapMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.BASIC_MAP_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_BASIC_MAP_MAPPING_UI_PROVIDER_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.ECLIPSELINK_BASIC_MAP_MAPPING_UI_PROVIDER_LINK_LABEL;
	}

	@SuppressWarnings("unchecked")
	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextNode> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new EclipseLinkBasicMapMappingComposite((PropertyValueModel<EclipseLinkBasicMapMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
