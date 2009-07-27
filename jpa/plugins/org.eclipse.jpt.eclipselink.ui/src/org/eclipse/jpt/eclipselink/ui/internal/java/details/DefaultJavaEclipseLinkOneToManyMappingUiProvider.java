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

import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.JptCorePlugin;
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

public class DefaultJavaEclipseLinkOneToManyMappingUiProvider
	extends AbstractOneToManyMappingUiProvider<JavaOneToManyMapping>
	implements DefaultAttributeMappingUiProvider<JavaOneToManyMapping>
{
	// singleton
	private static final DefaultJavaEclipseLinkOneToManyMappingUiProvider INSTANCE = 
		new DefaultJavaEclipseLinkOneToManyMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultAttributeMappingUiProvider<JavaOneToManyMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultJavaEclipseLinkOneToManyMappingUiProvider() {
		super();
	}
		
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
	}
	
	@Override
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return MappingKeys.ONE_TO_MANY_ATTRIBUTE_MAPPING_KEY;
	}
	
	@Override
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.DefaultEclipseLinkOneToManyMappingUiProvider_label;
	}

	@Override
	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.DefaultEclipseLinkOneToManyMappingUiProvider_linkLabel;
	}

	@Override
	public Image getImage() {
		return JpaMappingImageHelper.imageForAttributeMapping(getDefaultKey());
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<JavaOneToManyMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return factory.createJavaOneToManyMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
