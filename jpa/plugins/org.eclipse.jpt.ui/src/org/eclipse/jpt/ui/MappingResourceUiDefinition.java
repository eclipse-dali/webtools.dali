/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.ui.details.JpaComposite;
import org.eclipse.jpt.ui.details.MappingUiDefinition;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingResourceUiDefinition extends ResourceUiDefinition
{
	/**
	 * 
	 */
	JpaComposite buildAttributeMappingComposite(
			String key, 
			PropertyValueModel<AttributeMapping> mappingHolder, 
			Composite parent, WidgetFactory widgetFactory);
	
	/**
	 * Return an iterator of attribute mapping ui definitions appropriate for this file type
	 */
	Iterator<MappingUiDefinition<PersistentAttribute, ? extends AttributeMapping>> 
			attributeMappingUiDefinitions();
	
	/**
	 * Return a default attribute mapping ui definition for the given key or null
	 */
	DefaultMappingUiDefinition<PersistentAttribute, ? extends AttributeMapping> 
			getDefaultAttributeMappingUiDefinition(String key);
	
	/**
	 * 
	 */
	JpaComposite buildTypeMappingComposite(
			String key, 
			PropertyValueModel<TypeMapping> mappingHolder, 
			Composite parent, WidgetFactory widgetFactory);
	
	/**
	 * 
	 */
	Iterator<MappingUiDefinition<PersistentType, ? extends TypeMapping>> 
			typeMappingUiDefinitions();
	
	/**
	 * Return a default type mapping ui provider or null
	 */
	DefaultMappingUiDefinition<PersistentType, ? extends TypeMapping> 
			getDefaultTypeMappingUiDefinition();
}
