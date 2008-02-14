/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IEntity;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IRelationshipMapping;
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.db.internal.Schema;
import org.eclipse.jpt.db.internal.Table;

/**
 * @version 2.0
 * @since 2.0
 */
public class JoinColumnInAssociationOverrideStateObject extends JoinColumnStateObject
{
	/**
	 * Creates a new <code>JoinColumnInAssociationOverrideStateObject</code>.
	 *
	 * @param associationOverride The owner of the join column to create
	 * @param joinColumn The join column to edit or <code>null</code> if this is
	 * used to create a new one
	 */
	public JoinColumnInAssociationOverrideStateObject(IAssociationOverride associationOverride,
	                                                  IJoinColumn joinColumn) {
		super(associationOverride, joinColumn);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public String defaultTableName() {
		if (getJoinColumn() != null) {
			return getJoinColumn().getDefaultTable();
		}

		return typeMapping().tableName();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getNameTable() {
		return typeMapping().primaryDbTable();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public IAssociationOverride getOwner() {
		return (IAssociationOverride) super.getOwner();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Table getReferencedNameTable() {

		IAssociationOverride associationOverride = getOwner();
		IRelationshipMapping relationshipMapping = associationOverride.owner().relationshipMapping(associationOverride.getName());

		if (relationshipMapping == null){
			return null;
		}

		IEntity targetEntity = relationshipMapping.getResolvedTargetEntity();

		if (targetEntity != null) {
			return targetEntity.primaryDbTable();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public Schema getSchema() {
		return typeMapping().dbSchema();
	}

	/**
	 * Returns the mapping where the join column is located.
	 *
	 * @return The owner of the join column to create or to edit
	 */
	public ITypeMapping typeMapping() {
		return getOwner().owner().typeMapping();
	}
}