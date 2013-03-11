/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.jpa.core.context.GeneratorContainer;

/**
 * Container for a table generator and/or sequence generator and/or uuid generator.
 * Used by entities and ID mappings.
 * <p>
 * <strong>NB:</strong> The <code>eclipselink-orm.xml</code> entity mappings element can
 * hold more than a single uuid generator, so it does not use this
 * container.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface EclipseLinkGeneratorContainer
	extends GeneratorContainer
{
	// ********** uuid generator **********

	String UUID_GENERATOR_PROPERTY = "uuidGenerator"; //$NON-NLS-1$

	EclipseLinkUuidGenerator getUuidGenerator();

	EclipseLinkUuidGenerator addUuidGenerator();

	void removeUuidGenerator();
}
