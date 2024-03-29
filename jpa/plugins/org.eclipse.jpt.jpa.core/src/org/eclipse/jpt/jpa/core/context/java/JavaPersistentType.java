/*******************************************************************************
 * Copyright (c) 2006, 2015 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.java;

import org.eclipse.jpt.common.core.resource.java.JavaResourceAttribute;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
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
 * @version 3.3
 * @since 2.0
 */
public interface JavaPersistentType
	extends PersistentType, JavaManagedType
{
	// ********** covariant overrides **********

	Class<JavaPersistentType> getStructureType();

	JavaTypeMapping getMapping();
	
	ListIterable<JavaSpecifiedPersistentAttribute> getAttributes();
		String ATTRIBUTES_LIST = "attributes"; //$NON-NLS-1$
	
	JavaSpecifiedPersistentAttribute getAttributeNamed(String attributeName);
	
	
	// ********** Java **********
	
	/**
	 * Return whether any attribute in this persistent type is annotated
	 */
	boolean hasAnyAnnotatedAttributes();
	
	JavaSpecifiedPersistentAttribute getAttributeFor(JavaResourceAttribute javaResourceAttribute);


	// ********** parent **********

	interface Parent
		extends PersistentType.Parent
	{
		/**
		 * One of the persistent type's persistent attribute changed.
		 * Notify interested parties (e.g. "virtual" copies of the persistent attribute).
		 */
		void attributeChanged(JavaSpecifiedPersistentAttribute javaAttribute);
	}
}
