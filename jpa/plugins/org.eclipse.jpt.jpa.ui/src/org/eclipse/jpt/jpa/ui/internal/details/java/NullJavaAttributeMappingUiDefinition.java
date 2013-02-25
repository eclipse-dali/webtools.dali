/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.ui.internal.details.MapAsComposite;
import org.eclipse.swt.widgets.Composite;

public class NullJavaAttributeMappingUiDefinition
	extends AbstractMappingUiDefinition
	implements DefaultMappingUiDefinition
{
	// singleton
	private static final NullJavaAttributeMappingUiDefinition INSTANCE = new NullJavaAttributeMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultMappingUiDefinition instance() {
		return INSTANCE;
	}


	/**
	 * Ensure single instance.
	 */
	private NullJavaAttributeMappingUiDefinition() {
		super();
	}

	public String getKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}

	public String getDefaultKey() {
		return MappingKeys.NULL_ATTRIBUTE_MAPPING_KEY;
	}

	public String getLabel() {
		return JptJpaUiDetailsMessages.NullAttributeMappingUiProvider_label;
	}

	/**
	 * The {@link MapAsComposite} in the JPA Details view will display this text for
	 * an unmapped attribute:<pre>
	 * Attribute 'foo' is not mapped, click here to change the mapping type.
	 * </pre>
	 * We are returning the part of the string that will appear to the user as 
	 * a link that they can click; i.e. 'click here'.
	 */
	public String getLinkLabel() {
		return JptJpaUiDetailsMessages.MapAsComposite_unmappedAttributeText_linkLabel;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return JptJpaUiImages.NULL_ATTRIBUTE_MAPPING;
	}

	public JpaComposite buildMappingComposite(JpaUiFactory factory, PropertyValueModel<? extends JpaContextModel> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return new NullComposite(mappingModel, parentComposite, widgetFactory, resourceManager);
	}
}
