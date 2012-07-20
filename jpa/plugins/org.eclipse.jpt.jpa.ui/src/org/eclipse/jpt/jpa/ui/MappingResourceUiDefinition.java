/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * UI definitions for a specific
 * {@link org.eclipse.jpt.common.core.JptResourceType resource type}.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface MappingResourceUiDefinition
	extends ResourceUiDefinition
{
	// ********** type mappings **********

	/**
	 * 
	 */
	JpaComposite buildTypeMappingComposite(String mappingKey, PropertyValueModel<TypeMapping> mappingHolder, Composite parent, WidgetFactory widgetFactory);
	
	/**
	 * 
	 */
	Iterable<MappingUiDefinition<PersistentType, ? extends TypeMapping>> getTypeMappingUiDefinitions();
	
	/**
	 * 
	 */
	MappingUiDefinition<PersistentType, ? extends TypeMapping> getTypeMappingUiDefinition(String mappingKey);
	
	/**
	 * Return a default type mapping ui provider or null
	 */
	DefaultMappingUiDefinition<PersistentType, ? extends TypeMapping> getDefaultTypeMappingUiDefinition();


	// ********** attribute mappings **********

	/**
	 * 
	 */
	JpaComposite buildAttributeMappingComposite(String mappingKey, PropertyValueModel<AttributeMapping> mappingHolder, PropertyValueModel<Boolean> enabledModel, Composite parent, WidgetFactory widgetFactory);
	
	/**
	 * Return the resource's attribute mapping UI definitions.
	 */
	Iterable<MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping>> getAttributeMappingUiDefinitions();
	
	/**
	 * Return the resource's attribute mapping UI definitions.
	 */
	MappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getAttributeMappingUiDefinition(String mappingKey);
	
	/**
	 * Return a default attribute mapping ui definition for the given key or null
	 */
	DefaultMappingUiDefinition<ReadOnlyPersistentAttribute, ? extends AttributeMapping> getDefaultAttributeMappingUiDefinition(String mappingKey);
	
}
