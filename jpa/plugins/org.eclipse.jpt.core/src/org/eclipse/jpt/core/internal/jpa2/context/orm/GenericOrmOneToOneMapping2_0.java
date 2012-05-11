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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmOneToOneMapping;
import org.eclipse.jpt.core.jpa2.context.orm.OrmOneToOneRelationshipReference2_0;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;


public class GenericOrmOneToOneMapping2_0 
	extends AbstractOrmOneToOneMapping<XmlOneToOne>
{

	// ********** constructor **********
	public GenericOrmOneToOneMapping2_0(OrmPersistentAttribute parent, XmlOneToOne resourceMapping) {
		super(parent, resourceMapping);
	}

	@Override
	protected OrmOneToOneRelationshipReference2_0 buildRelationshipReference() {
		return new GenericOrmOneToOneRelationshipReference2_0(this, this.resourceAttributeMapping);
	}
}