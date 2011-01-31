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
import org.eclipse.jpt.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractBasicMappingUiDefinition;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmBasicMappingUiDefinition
	extends AbstractBasicMappingUiDefinition<ReadOnlyPersistentAttribute, OrmBasicMapping>
	implements OrmAttributeMappingUiDefinition<OrmBasicMapping>
{
	// singleton
	private static final OrmBasicMappingUiDefinition INSTANCE = 
		new OrmBasicMappingUiDefinition();
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmBasicMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmBasicMappingUiDefinition() {
		super();
	}
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory, 
			PropertyValueModel<OrmBasicMapping> subjectHolder, 
			Composite parent, 
			WidgetFactory widgetFactory) {
		
		return factory.createOrmBasicMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
