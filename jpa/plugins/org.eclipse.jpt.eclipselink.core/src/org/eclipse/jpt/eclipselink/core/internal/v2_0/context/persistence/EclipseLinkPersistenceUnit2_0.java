/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
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
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.EclipseLinkOptions;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.options.EclipseLinkOptions2_0;
import org.eclipse.jpt.eclipselink.core.v2_0.context.persistence.options.Options2_0;

/**
 *  EclipseLinkPersistenceUnit2_0
 */
public class EclipseLinkPersistenceUnit2_0
	extends EclipseLinkPersistenceUnit
{

	public EclipseLinkPersistenceUnit2_0(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		super(parent, xmlPersistenceUnit);
	}
	
	// **************** factory methods *********************************************

	@Override
	protected EclipseLinkOptions buildEclipseLinkOptions() {
		return new EclipseLinkOptions2_0(this);
	}

	// **************** properties *********************************************

	@Override
	public Options2_0 getOptions() {
		return (Options2_0) this.options;
	}
	
}
