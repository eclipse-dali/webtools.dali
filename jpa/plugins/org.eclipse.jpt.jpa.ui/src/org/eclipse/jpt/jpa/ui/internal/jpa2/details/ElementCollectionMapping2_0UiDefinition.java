/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.jpa2.MappingKeys2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.ElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.jpa2.details.JpaUiFactory2_0;
import org.eclipse.jpt.jpa.ui.jpa2.details.JptJpaUiDetailsMessages2_0;
import org.eclipse.swt.widgets.Composite;

public class ElementCollectionMapping2_0UiDefinition
	extends AbstractMappingUiDefinition
{
	// singleton
	private static final ElementCollectionMapping2_0UiDefinition INSTANCE = 
			new ElementCollectionMapping2_0UiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static MappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private ElementCollectionMapping2_0UiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys2_0.ELEMENT_COLLECTION_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LABEL;
	}

	public String getLinkLabel() {
		return JptJpaUiDetailsMessages2_0.ELEMENT_COLLECTION_MAPPING_LINK_LABEL;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.ELEMENT_COLLECTION;
	}

	@SuppressWarnings("unchecked")
	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return ((JpaUiFactory2_0) factory).createElementCollectionMapping2_0Composite((PropertyValueModel<ElementCollectionMapping2_0>) mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
