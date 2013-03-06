/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging2_4;

public class EclipseLinkPersistenceXmlContextModelFactory2_4
	extends EclipseLinkPersistenceXmlContextModelFactory2_0
{
	@Override
	public EclipseLinkLogging2_4 buildLogging(PersistenceUnit parent) {
		return new EclipseLinkLoggingImpl2_4((PersistenceUnit2_0) parent);
	}
}
