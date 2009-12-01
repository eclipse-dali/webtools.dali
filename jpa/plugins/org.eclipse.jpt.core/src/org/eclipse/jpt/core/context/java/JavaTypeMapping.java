/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import java.util.Iterator;

import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * Java type mapping (Entity, MappedSuperclass, Embeddable).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaTypeMapping
	extends TypeMapping, JavaJpaContextNode
{
	void initialize(JavaResourcePersistentType jrpt);

	/**
	 * Update the context Java type mapping to match the resource model.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update(JavaResourcePersistentType jrpt);

	String getAnnotationName();

	Iterable<String> getSupportingAnnotationNames();


	// ********** covariant overrides **********

	JavaPersistentType getPersistentType();

	@SuppressWarnings("unchecked")
	Iterator<JavaAttributeMapping> attributeMappings();

}
