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

import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.AbstractEclipseLinkTransformationMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkTransformationMappingComposite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.java.JavaUiFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkTransformationMappingUiDefinition
	extends AbstractEclipseLinkTransformationMappingUiDefinition<ReadOnlyPersistentAttribute, EclipseLinkTransformationMapping>
	implements JavaAttributeMappingUiDefinition<EclipseLinkTransformationMapping>
{
	// singleton
	private static final JavaEclipseLinkTransformationMappingUiDefinition INSTANCE = 
			new JavaEclipseLinkTransformationMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<EclipseLinkTransformationMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEclipseLinkTransformationMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<EclipseLinkTransformationMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkTransformationMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
