/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0;

import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.EclipseLinkPersistenceUnit2_0;

/**
 *  EclipseLink2_0JpaFactory
 */
public class EclipseLink2_0JpaFactory
	extends EclipseLinkJpaFactory
{
	protected EclipseLink2_0JpaFactory() {
		super();
	}
	

	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new EclipseLinkPersistenceUnit2_0(parent, xmlPersistenceUnit);
	}

}