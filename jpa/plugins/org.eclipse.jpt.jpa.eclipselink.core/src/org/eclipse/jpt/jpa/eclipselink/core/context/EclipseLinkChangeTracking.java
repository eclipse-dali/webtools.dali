/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;

/**
 * EclipseLink change tracking
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @version 2.1
 * @since 2.1
 */
public interface EclipseLinkChangeTracking
	extends JpaContextModel
{
	/**
	 * Return the specified type if it is present, otherwise return the default
	 * type.
	 */
	EclipseLinkChangeTrackingType getType();

	EclipseLinkChangeTrackingType getSpecifiedType();
	void setSpecifiedType(EclipseLinkChangeTrackingType type);
		String SPECIFIED_TYPE_PROPERTY = "specifiedType"; //$NON-NLS-1$

	EclipseLinkChangeTrackingType getDefaultType();
		String DEFAULT_TYPE_PROPERTY = "defaultType"; //$NON-NLS-1$
		EclipseLinkChangeTrackingType DEFAULT_TYPE = EclipseLinkChangeTrackingType.AUTO;
}
