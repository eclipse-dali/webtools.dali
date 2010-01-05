/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence;

import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.context.persistence.logging.Logging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.logging.EclipseLinkLogging2_0;

/**
 *  EclipseLink2_0PersistenceUnit
 */
public class EclipseLink2_0PersistenceUnit extends EclipseLinkPersistenceUnit
{
	// ********** constructors/initialization **********
	public EclipseLink2_0PersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}

	// ********** factory methods **********
	@Override
	protected Logging buildEclipseLinkLogging() {
		return new EclipseLinkLogging2_0(this);
	}

}
