/*******************************************************************************
 * Copyright (c) 2024 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa3_0;

import org.eclipse.jpt.jpa.core.jpa2_2.JpaProject2_2;
import org.eclipse.wst.common.project.facet.core.IProjectFacetVersion;

/**
 * JPA 3.0 project.
 * <p>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 */
public interface JpaProject3_0 extends JpaProject2_2 {
	// ********** JPA facet **********

	/**
	 * The JPA 3.0 project facet version string.
	 * <p>
	 * Value: {@value}
	 */
	String FACET_VERSION_STRING = "3.0"; //$NON-NLS-1$

	/**
	 * The JPA 3.0 project facet version.
	 */
	IProjectFacetVersion FACET_VERSION = FACET.getVersion(FACET_VERSION_STRING);

}
