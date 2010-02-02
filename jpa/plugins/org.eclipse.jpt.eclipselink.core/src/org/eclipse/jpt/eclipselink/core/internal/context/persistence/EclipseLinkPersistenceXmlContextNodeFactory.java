/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.persistence;

import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.connection.EclipseLinkConnection;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.logging.EclipseLinkLogging;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.options.EclipseLinkOptions;


public class EclipseLinkPersistenceXmlContextNodeFactory
	extends AbstractPersistenceXmlContextNodeFactory
		implements org.eclipse.jpt.eclipselink.core.context.persistence.EclipseLinkPersistenceXmlContextNodeFactory
{
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, xmlPersistenceUnit);
	}
	
	@Override
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new EclipseLinkJarFileRef(parent, xmlJarFileRef);
	}

	public PersistenceUnitProperties buildConnection(PersistenceUnit parent) {
		return new EclipseLinkConnection(parent);
	}
	
	public PersistenceUnitProperties buildOptions(PersistenceUnit parent) {
		return new EclipseLinkOptions(parent);
	}
	
	public PersistenceUnitProperties buildLogging(PersistenceUnit parent) {
		return new EclipseLinkLogging(parent);
	}
}
