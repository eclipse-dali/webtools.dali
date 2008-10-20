/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core;

import org.eclipse.jpt.core.JpaFile;

/**
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface EclipseLinkJpaFile extends JpaFile {
	/**
	 * Constant representing an eclipselink mapping file (e.g. eclipselink-orm.xml) resource type
	 * @see org.eclipse.jpt.core.ResourceModel#getResourceType()
	 */
	static final String ECLIPSELINK_ORM_RESOURCE_TYPE = "ECLIPSELINK_ORM_RESOURCE_TYPE"; //$NON-NLS-1$

}
