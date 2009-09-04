/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEntity;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlEntity;

public class GenericOrmEntity2_0
	extends AbstractOrmEntity
{
	
	public GenericOrmEntity2_0(OrmPersistentType parent, XmlEntity resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	protected XmlAssociationOverride buildResourceAssociationOverride() {
		return Orm2_0Factory.eINSTANCE.createXmlAssociationOverride();
	}

}