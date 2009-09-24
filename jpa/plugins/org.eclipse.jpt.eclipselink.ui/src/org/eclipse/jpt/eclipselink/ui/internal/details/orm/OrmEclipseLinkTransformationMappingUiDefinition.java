/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.details.orm;

import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkTransformationMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkTransformationMappingComposite;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkTransformationMappingUiDefinition;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkTransformationMappingUiDefinition
	extends EclipseLinkTransformationMappingUiDefinition<OrmEclipseLinkTransformationMapping>
	implements OrmAttributeMappingUiDefinition<OrmEclipseLinkTransformationMapping>
{
	// singleton
	private static final OrmEclipseLinkTransformationMappingUiDefinition INSTANCE = 
		new OrmEclipseLinkTransformationMappingUiDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmEclipseLinkTransformationMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkTransformationMappingUiDefinition() {
		super();
	}
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmEclipseLinkTransformationMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkTransformationMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
