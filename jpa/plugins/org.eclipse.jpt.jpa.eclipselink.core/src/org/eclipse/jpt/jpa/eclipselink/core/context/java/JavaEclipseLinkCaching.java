/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.java;

import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkCaching;

/**
 * EclipseLink Java caching
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface JavaEclipseLinkCaching
	extends EclipseLinkCaching
{
	/**
	 * Return true if the existence-checking model object exists.  
	 * Have to have a separate flag for this since the default existence
	 * type is different depending on whether hasExistenceChecking() returns
	 * true or false.
	 */
	boolean isExistenceChecking();
	void setExistenceChecking(boolean existenceChecking);
		String EXISTENCE_CHECKING_PROPERTY = "existenceChecking"; //$NON-NLS-1$
}
