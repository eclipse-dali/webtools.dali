/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.EclipseLinkUiMappingsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.mappings.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.ui.JpaUiFactory;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.DefaultAttributeMappingUiProvider;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider
	implements DefaultAttributeMappingUiProvider<JavaEclipseLinkVariableOneToOneMapping>
{
	// singleton
	private static final DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider INSTANCE = 
		new DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultAttributeMappingUiProvider<JavaEclipseLinkVariableOneToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultJavaEclipseLinkVariableOneToOneMappingUiProvider() {
		super();
	}
	
	public IContentType getContentType() {
		return JptCorePlugin.JAVA_SOURCE_CONTENT_TYPE;
	}
	
	public String getKey() {
		return null;
	}
	
	public String getDefaultKey() {
		return EclipseLinkMappingKeys.VARIABLE_ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY;
	}	
	
	public Image getImage() {
		return JptUiPlugin.getImage(JptUiIcons.JPA_CONTENT);
	}
	
	public String getLabel() {
		return EclipseLinkUiMappingsMessages.DefaultEclipseLinkVariableOneToOneMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return EclipseLinkUiMappingsMessages.DefaultEclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JpaUiFactory factory,
			PropertyValueModel<JavaEclipseLinkVariableOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkVariableOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}	
}
