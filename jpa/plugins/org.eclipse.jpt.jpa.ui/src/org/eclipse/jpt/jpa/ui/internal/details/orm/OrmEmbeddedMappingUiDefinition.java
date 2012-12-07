/*******************************************************************************
 *  Copyright (c) 2008, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractEmbeddedMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmEmbeddedMappingUiDefinition
	extends AbstractEmbeddedMappingUiDefinition<ReadOnlyPersistentAttribute, OrmEmbeddedMapping>
	implements OrmAttributeMappingUiDefinition<OrmEmbeddedMapping>
{
	// singleton
	private static final OrmEmbeddedMappingUiDefinition INSTANCE = 
			new OrmEmbeddedMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmEmbeddedMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEmbeddedMappingUiDefinition() {
		super();
	}
	
	public JpaComposite buildAttributeMappingComposite(OrmXmlUiFactory factory, PropertyValueModel<OrmEmbeddedMapping> mappingModel, PropertyValueModel<Boolean> enabledModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createOrmEmbeddedMappingComposite(mappingModel, enabledModel, parentComposite, widgetFactory, resourceManager);
	}
}
