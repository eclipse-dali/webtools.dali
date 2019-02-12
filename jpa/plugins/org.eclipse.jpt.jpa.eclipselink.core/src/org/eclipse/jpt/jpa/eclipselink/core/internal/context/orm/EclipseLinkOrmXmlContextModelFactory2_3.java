/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.jpa.core.context.orm.OrmSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlArray;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlStructure;

public class EclipseLinkOrmXmlContextModelFactory2_3
	extends EclipseLinkOrmXmlContextModelFactory2_0
{
	public EclipseLinkOrmArrayMapping2_3 buildOrmEclipseLinkArrayMapping(OrmSpecifiedPersistentAttribute parent, XmlArray resourceMapping) {
		return new EclipseLinkOrmArrayMapping2_3(parent, resourceMapping);
	}

	public EclipseLinkOrmStructureMapping2_3 buildOrmEclipseLinkStructureMapping(OrmSpecifiedPersistentAttribute parent, XmlStructure resourceMapping) {
		return new EclipseLinkOrmStructureMapping2_3(parent, resourceMapping);
	}
}
