/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.ui.internal.JptUiIcons;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

public class DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition
	implements DefaultJavaAttributeMappingUiDefinition<JavaEclipseLinkVariableOneToOneMapping>
{
	// singleton
	private static final DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition INSTANCE = 
		new DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingUiDefinition<JavaEclipseLinkVariableOneToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition() {
		super();
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
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkVariableOneToOneMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	}
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<JavaEclipseLinkVariableOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		return new EclipseLinkVariableOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}	
}
