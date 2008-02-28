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


/**
 * Map a string key to an attribute mapping and its corresponding
 * Java annotation adapter.  
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface DefaultJavaAttributeMappingProvider extends JavaAttributeMappingProvider {

	/**
	 * Given the IJavaPersistentAttribute return whether the default mapping applies.
	 * This will be used to determine the default mapping in the case where no 
	 * mapping has been specified.
	 */
	boolean defaultApplies(JavaPersistentAttribute persistentAttribute);

}
