/*******************************************************************************
 * Copyright (c) 2009, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.ui.WidgetFactory;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.ui.details.DefaultMappingUiDefinition;
import org.eclipse.jpt.jpa.ui.details.JpaComposite;
import org.eclipse.jpt.jpa.ui.details.MappingUiDefinition;
import org.eclipse.swt.widgets.Composite;

/**
 * UI definitions for a specific mapping file
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
	 * Build a type mapping composite for the specified mapping.
	 */
	JpaComposite buildTypeMappingComposite(
			String mappingKey,
			PropertyValueModel<TypeMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager
		);

	/**
	 * Return the resource's type mapping UI definitions.
	 */
	Iterable<MappingUiDefinition> getTypeMappingUiDefinitions();

	/**
	 * Return the resource's type mapping UI definition for the specified
	 * mapping.
	 */
	MappingUiDefinition getTypeMappingUiDefinition(String mappingKey);

	/**
	 * Return the resource's default type mapping UI definition.
	 */
	DefaultMappingUiDefinition getDefaultTypeMappingUiDefinition();


	// ********** attribute mappings **********

	/**
	 * Build an attribute mapping composite for the specified mapping.
	 */
	JpaComposite buildAttributeMappingComposite(
			String mappingKey,
			PropertyValueModel<AttributeMapping> mappingModel,
			PropertyValueModel<Boolean> enabledModel,
			Composite parentComposite,
			WidgetFactory widgetFactory,
			ResourceManager resourceManager
		);

	/**
	 * Return the resource's attribute mapping UI definitions.
	 */
	Iterable<MappingUiDefinition> getAttributeMappingUiDefinitions();

	/**
	 * Return the resource's attribute mapping UI definition for the specified
	 * mapping.
	 */
	MappingUiDefinition getAttributeMappingUiDefinition(String mappingKey);

	/**
	 * Return the resource's default attribute mapping UI definition
	 * for the specified mapping.
	 */
	DefaultMappingUiDefinition getDefaultAttributeMappingUiDefinition(String mappingKey);
}
