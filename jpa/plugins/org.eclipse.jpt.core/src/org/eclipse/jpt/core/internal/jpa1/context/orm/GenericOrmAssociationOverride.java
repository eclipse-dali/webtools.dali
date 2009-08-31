/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;

public class GenericOrmAssociationOverride extends AbstractOrmAssociationOverride
{

	public GenericOrmAssociationOverride(XmlContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride xmlAssociationOverride) {
		super(parent, owner, xmlAssociationOverride);
	}
	
	@Override
	protected OrmAssociationOverrideRelationshipReference buildRelationshipReference(XmlAssociationOverride xmlAssociationOverride) {
		return getJpaFactory().buildOrmAssociationOverrideRelationshipReference(this, xmlAssociationOverride);
	}

}
