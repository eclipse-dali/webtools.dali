/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.details.orm;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.orm.OrmTypeMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.orm.OrmXmlUiFactory;
import org.eclipse.jpt.jpa.ui.internal.details.AbstractMappedSuperclassUiDefinition;
import org.eclipse.swt.widgets.Composite;

public class OrmMappedSuperclassUiDefinition 
	extends AbstractMappedSuperclassUiDefinition<PersistentType, OrmMappedSuperclass>
	implements OrmTypeMappingUiDefinition<OrmMappedSuperclass>
{
	// singleton
	private static final OrmMappedSuperclassUiDefinition INSTANCE = 
			new OrmMappedSuperclassUiDefinition();
	
	
	/**
	 * Return the singleton.
	 */
	public static OrmTypeMappingUiDefinition<OrmMappedSuperclass> instance() {
		return INSTANCE;
	}
	
	
	/**
	 * Ensure single instance.
	 */
	private OrmMappedSuperclassUiDefinition() {
		super();
	}
	
	public JpaComposite buildTypeMappingComposite(OrmXmlUiFactory factory, PropertyValueModel<OrmMappedSuperclass> mappingModel, Composite parentComposite, WidgetFactory widgetFactory, ResourceManager resourceManager) {
		return factory.createOrmMappedSuperclassComposite(mappingModel, parentComposite, widgetFactory, resourceManager);
	}
}
