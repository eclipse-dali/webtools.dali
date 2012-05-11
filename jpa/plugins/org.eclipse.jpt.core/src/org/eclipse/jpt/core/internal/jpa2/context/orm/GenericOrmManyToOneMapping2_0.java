/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmManyToOneMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmManyToOneRelationshipReference2_0;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;

public class GenericOrmManyToOneMapping2_0 
	extends AbstractOrmManyToOneMapping<XmlManyToOne>
{
	public GenericOrmManyToOneMapping2_0(OrmPersistentAttribute parent, XmlManyToOne resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	protected OrmManyToOneRelationshipReference2_0 buildRelationshipReference() {
		return new GenericOrmManyToOneRelationshipReference2_0(this, this.resourceAttributeMapping);
	}
}
