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

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractManyToOneMappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmManyToOneMappingUiDefinition
	extends AbstractManyToOneMappingUiDefinition<ReadOnlyPersistentAttribute, OrmManyToOneMapping>
	implements OrmAttributeMappingUiDefinition<OrmManyToOneMapping>
{
	// singleton
	private static final OrmManyToOneMappingUiDefinition INSTANCE = 
			new OrmManyToOneMappingUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmManyToOneMapping> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmManyToOneMappingUiDefinition() {
		super();
	}
	
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory,
			PropertyValueModel<OrmManyToOneMapping> subjectHolder,
			PropertyValueModel<Boolean> enabledModel,
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return factory.createOrmManyToOneMappingComposite(subjectHolder, enabledModel, parent, widgetFactory);
	}
}
