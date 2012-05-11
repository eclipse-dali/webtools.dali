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

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnEnabledRelationshipReference;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumnsMapping;

public class GenericOrmTargetForiegnKeyJoinColumnJoiningStrategy 
	extends AbstractOrmJoinColumnInRelationshipMappingJoiningStrategy
{

	public GenericOrmTargetForiegnKeyJoinColumnJoiningStrategy(
		OrmJoinColumnEnabledRelationshipReference parent,
		XmlJoinColumnsMapping resource) {
		super(parent, resource);
	}

	public TypeMapping getRelationshipSource() {
		RelationshipMapping relationshipMapping = getRelationshipMapping();
		return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
	}

	public TypeMapping getRelationshipTarget() {
		return getRelationshipMapping().getTypeMapping();
	}

	@Override
	protected Entity getRelationshipTargetEntity() {
		TypeMapping relationshipTarget = getRelationshipTarget();
		return relationshipTarget.getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY ? (Entity) relationshipTarget : null;
	}

	public boolean isTargetForeignKeyRelationship() {
		return true;
	}

}
