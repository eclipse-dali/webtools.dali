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

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.OrmEclipseLinkBasicCollectionMapping;
import org.eclipse.jpt.eclipselink.ui.internal.details.AbstractEclipseLinkBasicCollectionMappingUiDefinition;
import org.eclipse.jpt.eclipselink.ui.internal.details.EclipseLinkBasicCollectionMappingComposite;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmEclipseLinkBasicCollectionMappingUiDefinition
	extends AbstractEclipseLinkBasicCollectionMappingUiDefinition<ReadOnlyPersistentAttribute, OrmEclipseLinkBasicCollectionMapping>
	implements OrmAttributeMappingUiDefinition<OrmEclipseLinkBasicCollectionMapping>
{
	// singleton
	private static final OrmEclipseLinkBasicCollectionMappingUiDefinition INSTANCE = 
			new OrmEclipseLinkBasicCollectionMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmEclipseLinkBasicCollectionMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEclipseLinkBasicCollectionMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmEclipseLinkBasicCollectionMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return new EclipseLinkBasicCollectionMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
