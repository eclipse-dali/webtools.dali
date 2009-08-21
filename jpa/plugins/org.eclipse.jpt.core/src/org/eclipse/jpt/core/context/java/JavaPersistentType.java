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

import java.util.ListIterator;

import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;

/**
 * Context Java persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaPersistentType
	extends PersistentType, JavaJpaContextNode
{
	// ********** covariant overrides **********

	JavaTypeMapping getMapping();
	
	@SuppressWarnings("unchecked")
	ListIterator<JavaPersistentAttribute> attributes();
	
	JavaPersistentAttribute getAttributeNamed(String attributeName);
	
	
	// ********** Java **********
	
	/**
	 * Return whether any attribute in this persistent type is annotated
	 */
	boolean hasAnyAnnotatedAttributes();
	
	/**
	 * Return the Java resource persistent type.
	 */
	JavaResourcePersistentType getResourcePersistentType();


	// ********** updating **********

	/**
	 * Synchronize the Java persistent type with the specified resource type.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update(JavaResourcePersistentType jrpt);

	/**
	 * Re-synchronize the Java persistent type with its resource type.
	 * @see org.eclipse.jpt.core.JpaProject#update()
	 */
	void update();

}
