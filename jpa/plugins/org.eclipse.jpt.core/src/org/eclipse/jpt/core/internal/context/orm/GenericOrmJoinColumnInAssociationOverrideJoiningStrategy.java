/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnInAssociationOverrideJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public class GenericOrmJoinColumnInAssociationOverrideJoiningStrategy 
	extends AbstractOrmJoinColumnJoiningStrategy
	implements OrmJoinColumnInAssociationOverrideJoiningStrategy
{
	
	public GenericOrmJoinColumnInAssociationOverrideJoiningStrategy(OrmAssociationOverrideRelationshipReference parent, XmlAssociationOverride xao) {
		super(parent, xao);
	}
	
	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}
	
	public TypeMapping getTypeMapping() {
		return getAssociationOverride().getOwner().getTypeMapping();
	}
	
	public boolean isOverridableAssociation() {
		return false;
	}
	
	protected OrmAssociationOverride getAssociationOverride() {
		return this.getRelationshipReference().getAssociationOverride();
	}

	@Override
	public OrmAssociationOverrideRelationshipReference getRelationshipReference() {
		return (OrmAssociationOverrideRelationshipReference) super.getRelationshipReference();
	}

	public TextRange getValidationTextRange() {
		return getRelationshipReference().getValidationTextRange();
	}
	
	public void update(XmlAssociationOverride xao) {
		//TODO can we make resource final and then just have an update() method?
		//would need to update the association overrides with the same resource association override
		this.resource = xao;
		super.update();
	}
	
	

	// ********** join column owner adapter **********

	protected class JoinColumnOwner
		implements OrmJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		protected AssociationOverride getAssociationOverride() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getRelationshipReference().getAssociationOverride();
		}
		
		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return getTypeMapping().getPrimaryTableName();
		}
		
		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}
		
		public Entity getTargetEntity() {
			RelationshipMapping relationshipMapping = getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
		}

		public String getAttributeName() {
			return getAssociationOverride().getName();
		}
		
		public PersistentAttribute getPersistentAttribute() {
			RelationshipMapping relationshipMapping = getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getPersistentAttribute();
		}

		public RelationshipMapping getRelationshipMapping() {
			return getAssociationOverride().getOwner().getRelationshipMapping(GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getRelationshipReference().getAssociationOverride().getName());
		}

		public boolean tableNameIsInvalid(String tableName) {
			return getTypeMapping().tableNameIsInvalid(tableName);
		}

		/**
		 * the join column can be on a secondary table
		 */
		public boolean tableIsAllowed() {
			return true;
		}

		public TypeMapping getTypeMapping() {
			return getAssociationOverride().getOwner().getTypeMapping();
		}

		public Table getDbTable(String tableName) {
			return getTypeMapping().getDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			Entity targetEntity = getTargetEntity();
			return (targetEntity == null) ? null : targetEntity.getPrimaryDbTable();
		}
		
		public boolean isVirtual(BaseJoinColumn joinColumn) {
			return false;
		}

		public int joinColumnsSize() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.joinColumnsSize();
		}
		
		public TextRange getValidationTextRange() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getValidationTextRange();
		}

	}
}
