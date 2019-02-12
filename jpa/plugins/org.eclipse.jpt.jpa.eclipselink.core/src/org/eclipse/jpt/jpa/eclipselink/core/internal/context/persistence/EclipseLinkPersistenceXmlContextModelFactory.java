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
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkConnection;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkLogging;
import org.eclipse.jpt.jpa.eclipselink.core.context.persistence.EclipseLinkOptions;

public class EclipseLinkPersistenceXmlContextModelFactory
	extends EclipseLinkAbstractPersistenceXmlContextModelFactory
{
	public EclipseLinkLogging buildLogging(PersistenceUnit parent) {
		return new EclipseLinkLoggingImpl(parent);
	}

	public EclipseLinkConnection buildConnection(PersistenceUnit parent) {
		return new EclipseLinkConnectionImpl(parent);
	}

	public EclipseLinkOptions buildOptions(PersistenceUnit parent) {
		return new EclipseLinkOptionsImpl(parent);
	}
}
