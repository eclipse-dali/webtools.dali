/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;

public class GenericOrmJoinColumnJoiningStrategy 
	extends AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy
{
	
	public GenericOrmJoinColumnJoiningStrategy(
			OrmJoinColumnEnabledRelationshipReference parent,
			XmlJoinColumnsMapping resource) {
		super(parent, resource);
	}

	public TypeMapping getRelationshipSource() {
		return getRelationshipMapping().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return getRelationshipTargetEntity();
	}

	@Override
	protected Entity getRelationshipTargetEntity() {
		return getRelationshipMapping().getResolvedTargetEntity();
	}

	public boolean isTargetForeignKeyRelationship() {
		return false;
	}
}
