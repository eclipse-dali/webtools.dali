/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.resource.java;

import org.eclipse.core.resources.IFile;
import org.eclipse.jdt.core.IType;

/**
 * Java persistent type cache - used to hold "external" types
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JavaResourceTypeCache
	extends JavaResourceNode.Root
{

	/**
	 * Return the size of the cache's types.
	 */
	int getTypesSize();

	/**
	 * Add a Java resource type for the specified JDT type to the
	 * cache. Return the new type.
	 */
	JavaResourceAbstractType addType(IType jdtType);

	/**
	 * Remove all the types associated with the specified JAR file.
	 * Return whether any types were removed.
	 */
	boolean removeTypes(IFile jarFile);

}
