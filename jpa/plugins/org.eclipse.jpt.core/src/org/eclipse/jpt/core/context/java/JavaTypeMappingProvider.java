/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.java;

import org.eclipse.jpt.core.JpaFactory;

/**
 * Map a string key to a type mapping and its corresponding
 * Java annotation adapter.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JavaTypeMappingProvider {

	/**
	 * A unique String that corresponds to the IJavaTypeMapping key 
	 */
	String key();

	String annotationName();
	
	/**
	 * Create an IJavaTypeMapping for the given attribute.  Use the IJpaFactory
	 * for creation so that extenders can create their own IJpaFactory instead of 
	 * creating their own typeMappingProvider.
	 * @param type
	 * @param jpaFactory
	 */
	public JavaTypeMapping buildMapping(JavaPersistentType parent, JpaFactory factory);

}
