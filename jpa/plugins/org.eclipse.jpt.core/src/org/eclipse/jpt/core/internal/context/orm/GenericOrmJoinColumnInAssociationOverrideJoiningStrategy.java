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

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.BaseJoinColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumnInAssociationOverrideJoiningStrategy;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
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

	public boolean isTargetForeignKeyRelationship() {
		RelationshipMapping relationshipMapping = getRelationshipMapping();
		if (relationshipMapping != null) {
			return relationshipMapping.getRelationshipReference().isTargetForeignKeyRelationship();
		}
		return false;
	}

	public TypeMapping getRelationshipSource() {
		if (isTargetForeignKeyRelationship()) {
			return getRelationshipMapping().getResolvedTargetEntity();
		}
		return getAssociationOverrideOwner().getTypeMapping();
	}
	
	public TypeMapping getRelationshipTarget() {
		if (isTargetForeignKeyRelationship()) {
			return getAssociationOverrideOwner().getTypeMapping();
		}
		RelationshipMapping relationshipMapping = getRelationshipMapping();
		return relationshipMapping == null ? null : relationshipMapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping relationshipTarget = getRelationshipTarget();
		return (relationshipTarget != null) && (relationshipTarget.getKey() == MappingKeys.ENTITY_TYPE_MAPPING_KEY) ? (Entity) relationshipTarget : null;
	}
	
	@Override
	public RelationshipMapping getRelationshipMapping() {
		return getAssociationOverrideOwner().getRelationshipMapping(getAttributeName());
	}
	
	protected String getAttributeName() {
		return this.getAssociationOverride().getName();
	}
	
	@Override
	public String getTableName() {
		if (isTargetForeignKeyRelationship()) {
			return super.getTableName();
		}
		return getAssociationOverrideOwner().getDefaultTableName();
	}

	@Override
	public Table getDbTable(String tableName) {
		if (isTargetForeignKeyRelationship()) {
			return super.getDbTable(tableName);
		}
		return getAssociationOverrideOwner().getDbTable(tableName);
	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		if (isTargetForeignKeyRelationship()) {
			return super.tableNameIsInvalid(tableName);
		}
		return getAssociationOverrideOwner().tableNameIsInvalid(tableName);
	}

	@Override
	public Iterator<String> candidateTableNames() {
		if (isTargetForeignKeyRelationship()) {
			return super.candidateTableNames();
		}
		return getAssociationOverrideOwner().candidateTableNames();
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}

	public boolean isOverridableAssociation() {
		return false;
	}
	
	protected OrmAssociationOverride getAssociationOverride() {
		return this.getRelationshipReference().getAssociationOverride();
	}
	
	protected OrmAssociationOverride.Owner getAssociationOverrideOwner() {
		return getAssociationOverride().getOwner();
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
		
		public String getDefaultTableName() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getTableName();
		}
		
		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getAttributeName();
		}
		
		public PersistentAttribute getPersistentAttribute() {
			RelationshipMapping relationshipMapping = getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.candidateTableNames();
		}

		public Table getDbTable(String tableName) {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericOrmJoinColumnInAssociationOverrideJoiningStrategy.this.getReferencedColumnDbTable();
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

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return getAssociationOverrideOwner().buildColumnValidator(getAssociationOverride(), (BaseColumn) column, this, (BaseColumnTextRangeResolver) textRangeResolver);
		}
	}
}
