/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToOneMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class DefaultJavaEclipseLinkOneToOneMappingUiDefinition
	extends AbstractOneToOneMappingUiDefinition<ReadOnlyPersistentAttribute, JavaOneToOneMapping>
	implements DefaultJavaAttributeMappingUiDefinition<JavaOneToOneMapping>
{
	// singleton
	private static final DefaultJavaEclipseLinkOneToOneMappingUiDefinition INSTANCE = new DefaultJavaEclipseLinkOneToOneMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingUiDefinition<JavaOneToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultJavaEclipseLinkOneToOneMappingUiDefinition() {
		super();
	}
	
	
	@Override
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public String getLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkOneToOneMappingUiProvider_label;
	}
	
	@Override
	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkOneToOneMappingUiProvider_linkLabel;
	}

	public JpaComposite buildAttributeMappingComposite(JavaUiFactory factory, PropertyValueModel<JavaOneToOneMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createJavaOneToOneMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
