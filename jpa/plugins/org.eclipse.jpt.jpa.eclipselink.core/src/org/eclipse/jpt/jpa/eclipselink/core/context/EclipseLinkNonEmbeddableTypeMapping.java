/*******************************************************************************
 * Copyright (c) 2010, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.core.context.IdTypeMapping;


/**
 * EclipseLink non-embeddable type mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.6
 * @since 3.3
 */
public interface EclipseLinkNonEmbeddableTypeMapping
		extends IdTypeMapping, EclipseLinkTypeMapping {
	
	EclipseLinkCaching getCaching();

	EclipseLinkReadOnly getReadOnly();

	EclipseLinkMultitenancy2_3 getMultitenancy();

	/**
	 * Used for validating whether tenant discriminator columns may be specified
	 */
	boolean isMultitenantMetadataAllowed();

	GeneratorContainer getGeneratorContainer(); //supported on MappedSuperclasses in EL 2.1

}
