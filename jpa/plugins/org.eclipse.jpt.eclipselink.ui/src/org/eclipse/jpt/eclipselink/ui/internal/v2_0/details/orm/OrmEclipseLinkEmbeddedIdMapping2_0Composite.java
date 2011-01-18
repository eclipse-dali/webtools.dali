/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.v2_0.details.orm;

import org.eclipse.jpt.core.context.EmbeddedIdMapping;
import org.eclipse.jpt.eclipselink.ui.internal.v1_1.details.orm.OrmEclipseLinkEmbeddedIdMapping1_1Composite;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.internal.details.AccessTypeComposite;
import org.eclipse.jpt.ui.internal.details.EmbeddedMappingOverridesComposite;
import org.eclipse.jpt.ui.internal.details.orm.OrmMappingNameChooser;
import org.eclipse.jpt.ui.internal.jpa2.details.EmbeddedIdMapping2_0MappedByRelationshipPane;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkEmbeddedIdMapping2_0Composite
	extends OrmEclipseLinkEmbeddedIdMapping1_1Composite
{
	public OrmEclipseLinkEmbeddedIdMapping2_0Composite(
			PropertyValueModel<? extends EmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		super(subjectHolder, parent, widgetFactory);
	}
	
	
	@Override
	protected void initializeEmbeddedIdSection(Composite container) {
		new OrmMappingNameChooser(this, getSubjectHolder(), container);
		new AccessTypeComposite(this, buildAccessHolderHolder(), container);
		
		new EmbeddedIdMapping2_0MappedByRelationshipPane(this, getSubjectHolder(), container);
		new EmbeddedMappingOverridesComposite(this, container);
	}
}
