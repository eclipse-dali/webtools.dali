/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
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
import org.eclipse.jpt.jpa.core.context.TransientMapping;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class TransientMappingUiDefinition
	extends AbstractMappingUiDefinition
{
	// singleton
	private static final TransientMappingUiDefinition INSTANCE = 
			new TransientMappingUiDefinition();


	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private TransientMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}		

	public String getLabel() {
		return JptJpaUiDetailsMessages.TransientMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return JptJpaUiDetailsMessages.TransientMappingUiProvider_linkLabel;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.TRANSIENT;
	}

	@SuppressWarnings("unchecked")
	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createTransientMappingComposite((PropertyValueModel<TransientMapping>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
