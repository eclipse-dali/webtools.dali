/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.java;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.JpaUiFactory;
import org.eclipse.jpt.jpa.ui.details.JptJpaUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class NullJavaTypeMappingUiDefinition
	extends AbstractMappingUiDefinition
	implements DefaultMappingUiDefinition
{
	// singleton
	private static final NullJavaTypeMappingUiDefinition INSTANCE = new NullJavaTypeMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultMappingUiDefinition instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private NullJavaTypeMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.NULL_TYPE_MAPPING_KEY;
	}

	public String getDefaultKey() {
		return MappingKeys.NULL_TYPE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaUiDetailsMessages.NULL_TYPE_MAPPING_UI_PROVIDER_LABEL;
	}

	/**
	 * The MapAsComposite in the JPA Details view will display this text for
	 * an unmapped type:
	 * Type 'Far' is not mapped, click here to change the mapping type.
	 * 
	 * We are returning the part of the string that will appear to the user as 
	 * a link that they can click: 'click here'
	 */
	public String getLinkLabel() {
		return JptJpaUiDetailsMessages.MAP_AS_COMPOSITE_UNMAPPED_TYPE_TEXT_LINK_LABEL;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.NULL_TYPE_MAPPING;
	}

	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new NullComposite(mappingModel, parentComposite, widgetFactory, resourceManager);
	}
}
