/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.iterables.ListIterable;
import org.eclipse.jpt.jpa.core.context.PersistentType;

/**
 * Context Java persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.0
 */
public interface JavaPersistentType
	extends PersistentType, JavaJpaContextNode
{
	// ********** covariant overrides **********

	JavaTypeMapping getMapping();
	
	ListIterable<JavaPersistentAttribute> getAttributes();
		String ATTRIBUTES_LIST = "attributes"; //$NON-NLS-1$
	
	JavaPersistentAttribute getAttributeNamed(String attributeName);
	
	
	// ********** Java **********
	
	/**
	 * Return whether any attribute in this persistent type is annotated
	 */
	boolean hasAnyAnnotatedAttributes();
	
	/**
	 * Return the Java resource persistent type.
	 */
	JavaResourceType getJavaResourceType();
	
	JavaPersistentAttribute getAttributeFor(JavaResourceAttribute javaResourceAttribute);
}
