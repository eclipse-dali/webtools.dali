/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_0.resource.java;

/**
 * EclipseLink Java-related stuff (annotations etc.)
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
@SuppressWarnings("nls")
public interface EclipseLink2_0 {

	// EclipseLink package
	String PACKAGE = "org.eclipse.persistence.annotations"; //$NON-NLS-1$
	String PACKAGE_ = PACKAGE + ".";


	// ********** API **********

	// EclispeLink 2.0 annotations
	String MAP_KEY_CONVERT = PACKAGE_ + "MapKeyConvert";
		String MAP_KEY_CONVERT__VALUE = "value";
}
