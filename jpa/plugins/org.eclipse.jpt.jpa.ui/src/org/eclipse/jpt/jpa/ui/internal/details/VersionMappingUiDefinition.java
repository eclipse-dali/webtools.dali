/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.VersionMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class VersionMappingUiDefinition
	extends AbstractMappingUiDefinition
{
	// singleton
	private static final VersionMappingUiDefinition INSTANCE = 
			new VersionMappingUiDefinition();


	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private VersionMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.VERSION_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaUiDetailsMessages.VERSION_MAPPING_UI_PROVIDER_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaUiDetailsMessages.VERSION_MAPPING_UI_PROVIDER_LINK_LABEL;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.VERSION;
	}

	@SuppressWarnings("unchecked")
	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createVersionMappingComposite((PropertyValueModel<VersionMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
