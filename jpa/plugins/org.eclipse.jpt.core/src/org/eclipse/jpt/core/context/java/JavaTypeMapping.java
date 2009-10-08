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
import java.util.ListIterator;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaTypeMapping extends TypeMapping, JavaJpaContextNode
{
	void initialize(JavaResourcePersistentType jrpt);
	
	/**
	 * Update the JavaTypeMapping context model object to match the JavaResourcePersistentType 
	 * resource model object. see {@link org.eclipse.jpt.core.JpaProject#update()}
	 */
	void update(JavaResourcePersistentType jrpt);

	String getAnnotationName();
	
	Iterator<String> supportingAnnotationNames();
	
	
	// ********** covariant overrides **********

	JavaPersistentType getPersistentType();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaAttributeMapping> attributeMappings();
	
	@SuppressWarnings("unchecked")
	Iterator<JavaRelationshipMapping> overridableAssociations();
}