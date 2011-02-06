/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkBasicMapMapping;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.AbstractEclipseLinkBasicMapMappingUiDefinition;
import org.eclipse.jpt.jpa.eclipselink.ui.internal.details.EclipseLinkBasicMapMappingComposite;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.java.JavaAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.java.JavaUiFactory;
import org.eclipse.swt.widgets.Composite;

public class JavaEclipseLinkBasicMapMappingUiDefinition
	extends AbstractEclipseLinkBasicMapMappingUiDefinition<ReadOnlyPersistentAttribute, EclipseLinkBasicMapMapping>
	implements JavaAttributeMappingUiDefinition<EclipseLinkBasicMapMapping>
{
	// singleton
	private static final JavaEclipseLinkBasicMapMappingUiDefinition INSTANCE = 
			new JavaEclipseLinkBasicMapMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static JavaAttributeMappingUiDefinition<EclipseLinkBasicMapMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private JavaEclipseLinkBasicMapMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			JavaUiFactory factory,
			PropertyValueModel<EclipseLinkBasicMapMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkBasicMapMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
