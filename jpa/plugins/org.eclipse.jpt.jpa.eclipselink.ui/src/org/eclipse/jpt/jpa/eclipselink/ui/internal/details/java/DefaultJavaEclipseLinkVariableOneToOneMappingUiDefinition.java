/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details.java;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkMappingKeys;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkVariableOneToOneMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkUiDetailsMessages;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkVariableOneToOneMappingComposite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.DefaultJavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition
	extends AbstractMappingUiDefinition<ReadOnlyPersistentAttribute, EclipseLinkVariableOneToOneMapping>
	implements DefaultJavaAttributeMappingUiDefinition<EclipseLinkVariableOneToOneMapping>
{
	// singleton
	private static final DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition INSTANCE = new DefaultJavaEclipseLinkVariableOneToOneMappingUiDefinition();

	/**
	 * Return the singleton.
	 */
	public static DefaultJavaAttributeMappingUiDefinition<EclipseLinkVariableOneToOneMapping> instance() {
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
	
	public String getLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkVariableOneToOneMappingUiProvider_label;
	}

	public String getLinkLabel() {
		return EclipseLinkUiDetailsMessages.DefaultEclipseLinkVariableOneToOneMappingUiProvider_linkLabel;
	}
	
	public JpaComposite buildAttributeMappingComposite(JavaUiFactory factory, PropertyValueModel<EclipseLinkVariableOneToOneMapping> subjectHolder, PropertyValueModel<Boolean> enabledModel, Composite parent, WidgetFactory widgetFactory) {
		return new EclipseLinkVariableOneToOneMappingComposite(subjectHolder, enabledModel, parent, widgetFactory);
	}	
}
