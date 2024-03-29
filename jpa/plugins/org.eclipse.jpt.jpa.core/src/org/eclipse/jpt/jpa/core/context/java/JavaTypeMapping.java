/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.Annotation;
import org.eclipse.jpt.jpa.core.context.TypeMapping;

/**
 * Java type mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 2.0
 */
public interface JavaTypeMapping
	extends TypeMapping
{
	Annotation getMappingAnnotation();


	// ********** covariant overrides **********

	JavaPersistentType getPersistentType();

	@SuppressWarnings("unchecked")
	Iterable<JavaAttributeMapping> getAttributeMappings();

	@SuppressWarnings("unchecked")
	Iterable<JavaAttributeMapping> getAttributeMappings(String mappingKey);
}
