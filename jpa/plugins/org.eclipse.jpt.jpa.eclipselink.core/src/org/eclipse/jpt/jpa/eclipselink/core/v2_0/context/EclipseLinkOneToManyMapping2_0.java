/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.v2_0.context;

import org.eclipse.jpt.jpa.core.jpa2.context.OneToManyMapping2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkOneToManyMapping;

/**
 * EclipseLink 1:m mapping
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.0
 * @since 3.0
 */
public interface EclipseLinkOneToManyMapping2_0 
	extends EclipseLinkOneToManyMapping, OneToManyMapping2_0
{
	EclipseLinkOneToManyRelationship2_0 getRelationship();
}
