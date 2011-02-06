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
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractOneToOneMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmOneToOneMappingUiDefinition
	extends AbstractOneToOneMappingUiDefinition<ReadOnlyPersistentAttribute, OrmOneToOneMapping>
	implements OrmAttributeMappingUiDefinition<OrmOneToOneMapping>
{
	// singleton
	private static final OrmOneToOneMappingUiDefinition INSTANCE = 
			new OrmOneToOneMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmOneToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmOneToOneMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmOneToOneMapping> subjectHolder,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createOrmOneToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
