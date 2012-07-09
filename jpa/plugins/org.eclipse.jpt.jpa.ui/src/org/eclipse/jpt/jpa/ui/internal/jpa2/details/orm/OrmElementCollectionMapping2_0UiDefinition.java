/*******************************************************************************
 *  Copyright (c) 2009, 2012  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details.orm;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.jpa2.context.orm.OrmElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmAttributeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.jpa2.details.AbstractElementCollectionMapping2_0UiDefinition;
import org.eclipse.jpt.jpa.ui.jpa2.details.orm.OrmXmlUiFactory2_0;
import org.eclipse.swt.widgets.Composite;

public class OrmElementCollectionMapping2_0UiDefinition
	extends AbstractElementCollectionMapping2_0UiDefinition<ReadOnlyPersistentAttribute, OrmElementCollectionMapping2_0>
	implements OrmAttributeMappingUiDefinition<OrmElementCollectionMapping2_0>
{
	// singleton
	private static final OrmElementCollectionMapping2_0UiDefinition INSTANCE = 
		new OrmElementCollectionMapping2_0UiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmAttributeMappingUiDefinition<OrmElementCollectionMapping2_0> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmElementCollectionMapping2_0UiDefinition() {
		super();
	}
	
	public JpaComposite buildAttributeMappingComposite(
			OrmXmlUiFactory factory, 
			PropertyValueModel<OrmElementCollectionMapping2_0> subjectHolder, 
			PropertyValueModel<Boolean> enabledModel, 
			Composite parent,
			WidgetFactory widgetFactory) {
		
		return ((OrmXmlUiFactory2_0) factory).createOrmElementCollectionMapping2_0Composite(subjectHolder, enabledModel, parent, widgetFactory);
	}
}
