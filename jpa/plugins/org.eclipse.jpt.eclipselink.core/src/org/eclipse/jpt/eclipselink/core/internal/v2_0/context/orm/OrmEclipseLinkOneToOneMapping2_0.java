/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmOneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneRelationshipReference2_0;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.AbstractOrmEclipseLinkOneToOneMapping;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlOneToOne;

public class OrmEclipseLinkOneToOneMapping2_0 extends AbstractOrmEclipseLinkOneToOneMapping
{

	public OrmEclipseLinkOneToOneMapping2_0(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	protected OrmOneToOneRelationshipReference2_0 buildRelationshipReference() {
		return new GenericOrmOneToOneRelationshipReference2_0(this, this.resourceAttributeMapping);
	}
}