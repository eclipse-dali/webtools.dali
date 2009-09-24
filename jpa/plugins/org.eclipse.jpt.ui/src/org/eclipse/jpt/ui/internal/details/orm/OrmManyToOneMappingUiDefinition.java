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

import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.ui.WidgetFactory;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.ui.internal.details.AbstractManyToOneMappingUiDefinition;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

public class OrmManyToOneMappingUiDefinition
	extends AbstractManyToOneMappingUiDefinition<OrmManyToOneMapping>
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
			Composite parent,
			WidgetFactory widgetFactory) {
		return factory.createOrmManyToOneMappingComposite(subjectHolder, parent, widgetFactory);
	}
}
