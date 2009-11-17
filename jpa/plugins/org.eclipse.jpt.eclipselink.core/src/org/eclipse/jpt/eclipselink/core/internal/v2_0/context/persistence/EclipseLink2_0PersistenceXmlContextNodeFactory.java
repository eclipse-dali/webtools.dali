/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence;

import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXmlContextNodeFactory;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.connection.JpaConnection2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.options.JpaOptions2_0;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkJarFileRef;
import org.eclipse.jpt.eclipselink.core.internal.context.persistence.EclipseLinkPersistenceUnit;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.connection.EclipseLinkConnection2_0;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.persistence.options.EclipseLinkOptions2_0;


public class EclipseLink2_0PersistenceXmlContextNodeFactory extends AbstractPersistenceXmlContextNodeFactory
{
	
	@Override
	public PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new EclipseLinkPersistenceUnit(parent, xmlPersistenceUnit);
	}
	
	@Override
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new EclipseLinkJarFileRef(parent, xmlJarFileRef);
	}
	
	public JpaConnection2_0 buildConnection(PersistenceUnit parent) {
		return new EclipseLinkConnection2_0((PersistenceUnit2_0) parent);
	}
	
	public JpaOptions2_0 buildOptions(PersistenceUnit parent) {
		return new EclipseLinkOptions2_0((PersistenceUnit2_0) parent);
	}

}
