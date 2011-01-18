/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Common interface that can be used by clients interested only in a type
 * or attribute's access setting (e.g. a UI composite).
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.2
 */
public interface AccessHolder
	extends ReadOnlyAccessHolder
{
	/**
	 * Return the specified access type;
	 */
	AccessType getSpecifiedAccess();
	
	/**
	 * Set the specified access type.
	 */
	void setSpecifiedAccess(AccessType newSpecifiedAccess);

	/**
	 * String constant associated with changes to the specified access type
	 */
	String SPECIFIED_ACCESS_PROPERTY = "specifiedAccess"; //$NON-NLS-1$

	/**
	 * Return the default access type, never null
	 */
	AccessType getDefaultAccess();

	/**
	 * String constant associated with changes to the default access type
	 */
	String DEFAULT_ACCESS_PROPERTY = "defaultAccess"; //$NON-NLS-1$
}
