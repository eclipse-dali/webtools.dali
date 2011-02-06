/*******************************************************************************
* Copyright (c) 2008, 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence.general;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 *  GeneralProperties
 */
public interface GeneralProperties extends PersistenceUnitProperties
{

	Boolean getDefaultExcludeEclipselinkOrm();
	Boolean getExcludeEclipselinkOrm();
	void setExcludeEclipselinkOrm(Boolean newExcludeEclipselinkOrm);
		static final String EXCLUDE_ECLIPSELINK_ORM_PROPERTY = "excludeEclipselinkOrm"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM = "eclipselink.exclude-eclipselink-orm"; //$NON-NLS-1$
		static final Boolean DEFAULT_EXCLUDE_ECLIPSELINK_ORM = Boolean.FALSE;
		
}
