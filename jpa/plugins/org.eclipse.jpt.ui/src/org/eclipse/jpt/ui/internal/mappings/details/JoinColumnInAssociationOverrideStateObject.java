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
	private IAssociationOverride associationOverride;

	/**
	 * Creates a new <code>JoinColumnInAssociationOverrideStateObject</code>.
	 */
	public JoinColumnInAssociationOverrideStateObject(IAssociationOverride associationOverride) {
		super();
		this.associationOverride = associationOverride;
	}

	/**
	 * Creates a new <code>JoinColumnInAssociationOverrideStateObject</code>.
	 *
	 * @param joinColumn Either the join column to edit or <code>null</code> if
	 * this state object is used to create a new one
	 */
	public JoinColumnInAssociationOverrideStateObject(IAssociationOverride associationOverride, IJoinColumn joinColumn) {
		super(joinColumn);
		this.associationOverride = associationOverride;
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

	public IAssociationOverride getAssociationOverride() {
		return this.associationOverride;
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
	public Table getReferencedNameTable() {
		IRelationshipMapping relationshipMapping = this.associationOverride.owner().relationshipMapping(this.associationOverride.getName());

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

	public ITypeMapping typeMapping() {
		return this.associationOverride.owner().typeMapping();
	}
}
