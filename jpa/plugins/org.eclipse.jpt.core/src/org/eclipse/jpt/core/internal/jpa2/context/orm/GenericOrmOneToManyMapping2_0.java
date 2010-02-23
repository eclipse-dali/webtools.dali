/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmOneToManyMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;


public class GenericOrmOneToManyMapping2_0 extends AbstractOrmOneToManyMapping<XmlOneToMany>
{
	
	public GenericOrmOneToManyMapping2_0(OrmPersistentAttribute parent, XmlOneToMany resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	protected OrmRelationshipReference buildRelationshipReference() {
		return new GenericOrmOneToManyRelationshipReference2_0(this, this.resourceAttributeMapping);
	}

}
