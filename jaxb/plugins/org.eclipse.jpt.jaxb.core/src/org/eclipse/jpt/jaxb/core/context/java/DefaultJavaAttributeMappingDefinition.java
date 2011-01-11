/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jaxb.core.context.java;

import org.eclipse.jpt.jaxb.core.context.JaxbPersistentAttribute;



/**
 * Map a string key to an attribute mapping and its corresponding
 * Java annotation.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface DefaultJavaAttributeMappingDefinition extends JavaAttributeMappingDefinition
{

	/**
	 * Return whether this mapping provider should be used for the given {@link JaxbPersistentAttribute} 
	 * in the default (ignoring all mapping annotations) case.
	 */
	boolean isDefault(JaxbPersistentAttribute persistentAttribute);
}
