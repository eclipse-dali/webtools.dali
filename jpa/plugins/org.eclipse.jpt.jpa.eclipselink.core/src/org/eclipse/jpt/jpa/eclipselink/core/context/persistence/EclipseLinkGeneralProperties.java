/*******************************************************************************
* Copyright (c) 2008, 2016 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 *  GeneralProperties
 */
public interface EclipseLinkGeneralProperties
	extends PersistenceUnitProperties
{
	Boolean getExcludeEclipseLinkOrm();
	void setExcludeEclipseLinkOrm(Boolean newExcludeEclipselinkOrm);
		String EXCLUDE_ECLIPSELINK_ORM_PROPERTY = "excludeEclipselinkOrm"; //$NON-NLS-1$
		// EclipseLink key string
		String ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM = "eclipselink.exclude-eclipselink-orm"; //$NON-NLS-1$
	Boolean getDefaultExcludeEclipseLinkOrm();
		String DEFAULT_EXCLUDE_ECLIPSELINK_ORM_PROPERTY = "defaultExcludeEclipselinkOrm"; //$NON-NLS-1$
		Boolean DEFAULT_EXCLUDE_ECLIPSELINK_ORM = Boolean.FALSE;
}
