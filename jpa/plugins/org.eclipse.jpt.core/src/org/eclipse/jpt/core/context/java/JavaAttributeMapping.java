/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.resource.java.Annotation;

/**
 * Java attribute mapping
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JavaAttributeMapping
	extends AttributeMapping, JavaJpaContextNode
{
	/**
	 * Covariant override.
	 */
	JavaPersistentAttribute getPersistentAttribute();

	Annotation getMappingAnnotation();

	void initialize(Annotation mappingAnnotation);

	/**
	 * Update the JavaAttributeMapping context model object to match the JavaResourcePersistentAttribute 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	//TODO want to remove parameter from the update method, but we have to have
	//it because of GenericJavaPersistentAttribute.setSpecifiedMappingKey(), it is unable
	//to call initialize and pass the resource object in before the update is called.
	void update(Annotation mappingAnnotation);
	
	String getAnnotationName();
	
	/**
	 * Return all fully qualified annotation names that are supported with this mapping type.
	 * This includes all possible annotations, not just the ones that currently exist on the attribute.
	 */
	Iterable<String> getSupportingAnnotationNames();
}
