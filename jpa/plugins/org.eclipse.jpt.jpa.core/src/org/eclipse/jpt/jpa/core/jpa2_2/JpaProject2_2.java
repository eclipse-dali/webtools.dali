/*******************************************************************************
 * Copyright (c) 2018 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2_2;

import org.eclipse.jpt.jpa.core.jpa2_1.JpaProject2_1;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JPA 2.2 project.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 */
public interface JpaProject2_2 extends JpaProject2_1 {
	// ********** JPA facet **********

	/**
	 * The JPA 2.2 project facet version string.
	 * <p>
	 * Value: {@value}
	 */
	String FACET_VERSION_STRING = "2.2"; //$NON-NLS-1$

	/**
	 * The JPA 2.1 project facet version.
	 */
	IProjectFacetVersion FACET_VERSION = FACET.getVersion(FACET_VERSION_STRING);

}
