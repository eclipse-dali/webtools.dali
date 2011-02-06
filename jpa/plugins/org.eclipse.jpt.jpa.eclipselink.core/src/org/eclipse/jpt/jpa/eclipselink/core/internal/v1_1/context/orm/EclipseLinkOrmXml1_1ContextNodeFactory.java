/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v1_1.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmXmlContextNodeFactory;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlAttributeMapping;


public class EclipseLinkOrmXml1_1ContextNodeFactory extends EclipseLinkOrmXmlContextNodeFactory
{	
	
	// ********** EclipseLink1.1-specific ORM Context Model **********

	@Override
	public OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, org.eclipse.jpt.jpa.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return new OrmEclipseLinkPersistentAttribute1_1(parent, (XmlAttributeMapping) resourceMapping);
	}
}
