/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure;

public class EclipseLinkOrmXml2_3ContextNodeFactory
	extends EclipseLinkOrmXml2_0ContextNodeFactory
{

	public OrmEclipseLinkArrayMapping2_3 buildOrmEclipseLinkArrayMapping(OrmPersistentAttribute parent, XmlArray resourceMapping) {
		return new OrmEclipseLinkArrayMapping2_3(parent, resourceMapping);
	}

	public OrmEclipseLinkStructureMapping2_3 buildOrmEclipseLinkStructureMapping(OrmPersistentAttribute parent, XmlStructure resourceMapping) {
		return new OrmEclipseLinkStructureMapping2_3(parent, resourceMapping);
	}

}
