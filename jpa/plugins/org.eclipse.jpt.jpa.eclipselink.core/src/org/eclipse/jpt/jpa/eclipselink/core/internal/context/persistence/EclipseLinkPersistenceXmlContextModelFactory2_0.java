/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.persistence.PersistenceXmlContextModelFactory2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging2_0;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOptions2_0;


public class EclipseLinkPersistenceXmlContextModelFactory2_0
	extends EclipseLinkAbstractPersistenceXmlContextModelFactory
	implements PersistenceXmlContextModelFactory2_0
{
	public EclipseLinkConnection2_0 buildConnection(PersistenceUnit parent) {
		return new EclipseLinkConnectionImpl2_0((PersistenceUnit2_0) parent);
	}
	
	public EclipseLinkOptions2_0 buildOptions(PersistenceUnit parent) {
		return new EclipseLinkOptionsImpl2_0((PersistenceUnit2_0) parent);
	}

	public EclipseLinkLogging2_0 buildLogging(PersistenceUnit parent) {
		return new EclipseLinkLoggingImpl2_0((PersistenceUnit2_0) parent);
	}
}
