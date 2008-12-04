/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.java.details;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JpaMappingImageHelper;
import org.eclipse.jpt.ui.internal.details.AbstractOneToManyMappingUiProvider;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class DefaultOneToManyMappingUiProvider
	extends AbstractOneToManyMappingUiProvider<JavaOneToManyMapping>
	implements DefaultAttributeMappingUiProvider<JavaOneToManyMapping>
{
	// singleton
	private static final DefaultOneToManyMappingUiProvider INSTANCE = 
		new DefaultOneToManyMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultAttributeMappingUiProvider<JavaOneToManyMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultOneToManyMappingUiProvider() {
		super();
	}
	
	
	@Override
	public String getMappingKey() {
		return null;
	}
	
	public String getDefaultMappingKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.DefaultOneToManyMappingUiProvider_label;
	}

	@Override
	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.DefaultOneToManyMappingUiProvider_linkLabel;
	}

	@Override
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getDefaultMappingKey());
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<JavaOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return factory.createJavaOneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
