/*******************************************************************************
 *  Copyright (c) 2008, 2009  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractEmbeddedIdMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmEmbeddedIdMappingUiDefinition
	extends AbstractEmbeddedIdMappingUiDefinition<ReadOnlyPersistentAttribute, OrmEmbeddedIdMapping>
	implements OrmAttributeMappingUiDefinition<OrmEmbeddedIdMapping>
{
	// singleton
	private static final OrmEmbeddedIdMappingUiDefinition INSTANCE = 
			new OrmEmbeddedIdMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmEmbeddedIdMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmEmbeddedIdMappingUiDefinition() {
		super();
	}	
	
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmEmbeddedIdMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createOrmEmbeddedIdMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
