/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkArrayMapping2_3;
import org.eclipse.jpt.jpa.eclipselink.ui.details.JptJpaEclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class EclipseLinkArrayMappingUiDefinition2_3
	extends AbstractMappingUiDefinition
{
	// singleton
	private static final EclipseLinkArrayMappingUiDefinition2_3 INSTANCE = 
			new EclipseLinkArrayMappingUiDefinition2_3();


	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private EclipseLinkArrayMappingUiDefinition2_3() {
		super();
	}

	public String getKey() {
		return EclipseLinkMappingKeys.ARRAY_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.ARRAY_MAPPING_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaEclipseLinkUiDetailsMessages.ARRAY_MAPPING_LINK_LABEL;
	}

	@SuppressWarnings("unchecked")
	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new EclipseLinkArrayMappingComposite2_3((PropertyValueModel<EclipseLinkArrayMapping2_3>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
