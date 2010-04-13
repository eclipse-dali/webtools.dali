/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.PersistentType;

/**
 * JPA 2.0 context persistent type.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public interface PersistentType2_0
	extends PersistentType, MetamodelSourceType
{
	/**
	 * Return the name of the persistent type's "declaring type".
	 * Return <code>null</code> if the persistent type is a top-level type.
	 * The declaring type may or may not be a persistent type.
	 */
	String getDeclaringTypeName();
		String DECLARING_TYPE_NAME_PROPERTY = "declaringTypeName"; //$NON-NLS-1$

}
