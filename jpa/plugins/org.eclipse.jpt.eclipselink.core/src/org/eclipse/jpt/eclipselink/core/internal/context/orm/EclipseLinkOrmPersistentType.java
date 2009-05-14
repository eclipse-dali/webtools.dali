/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentType;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;

public class EclipseLinkOrmPersistentType extends AbstractOrmPersistentType
{
	
	public EclipseLinkOrmPersistentType(EclipseLinkEntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	protected EclipseLinkJpaFactory getJpaFactory() {
		return (EclipseLinkJpaFactory)  super.getJpaFactory();
	}
	
	@Override
	protected Attributes createResourceAttributes() {
		return EclipseLinkOrmFactory.eINSTANCE.createAttributes();
	}
	
	@Override
	protected OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentAttribute.Owner owner, XmlAttributeMapping resourceMapping) {
		return getJpaFactory().buildEclipseLinkOrmPersistentAttribute(this, owner, resourceMapping);
	}

}
