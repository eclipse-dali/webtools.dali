/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.context.MappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface OrmMappedSuperclass
	extends MappedSuperclass, OrmTypeMapping
{
	/**
	 * Return the Java Mapped Superclass this ORM MappedSuperclass corresponds to.  
	 * Return null if there is no java persistent type or it is not a mapped superclass.
	 */
	JavaMappedSuperclass getJavaMappedSuperclass();

	Iterator<OrmRelationshipMapping> overridableAssociations();

}
