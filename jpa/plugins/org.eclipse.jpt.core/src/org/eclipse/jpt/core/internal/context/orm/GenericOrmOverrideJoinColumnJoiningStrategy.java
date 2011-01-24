/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.BaseColumn;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmOverrideRelationship;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.internal.context.BaseColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.db.Table;

public class GenericOrmOverrideJoinColumnJoiningStrategy
	extends AbstractOrmJoinColumnJoiningStrategy
{
	public GenericOrmOverrideJoinColumnJoiningStrategy(OrmOverrideRelationship parent) {
		super(parent);
	}

	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	public boolean isTargetForeignKey() {
		RelationshipMapping relationshipMapping = this.getRelationshipMapping();
		return (relationshipMapping != null) &&
				relationshipMapping.getRelationship().isTargetForeignKey();
	}

	public TypeMapping getRelationshipSource() {
		return this.isTargetForeignKey() ?
				this.getRelationshipMapping().getResolvedTargetEntity() :
				this.getAssociationOverrideContainer().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return this.isTargetForeignKey() ?
				this.getAssociationOverrideContainer().getTypeMapping() :
				this.getRelationshipMappingTargetEntity();
	}

	protected TypeMapping getRelationshipMappingTargetEntity() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return (mapping == null) ? null : mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	@Override
	public RelationshipMapping getRelationshipMapping() {
		return this.getAssociationOverride().getMapping();
	}

	protected String getAttributeName() {
		return this.getAssociationOverride().getName();
	}

	@Override
	public String getTableName() {
		return this.isTargetForeignKey() ?
				super.getTableName() :
				this.getAssociationOverrideContainer().getDefaultTableName();
	}

	@Override
	public Table resolveDbTable(String tableName) {
		return this.isTargetForeignKey() ?
				super.resolveDbTable(tableName) :
				this.getAssociationOverrideContainer().resolveDbTable(tableName);
	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		return this.isTargetForeignKey() ?
				super.tableNameIsInvalid(tableName) :
				this.getAssociationOverrideContainer().tableNameIsInvalid(tableName);
	}

	@Override
	public Iterator<String> candidateTableNames() {
		return this.isTargetForeignKey() ?
				super.candidateTableNames() :
				this.getAssociationOverrideContainer().candidateTableNames();
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}

	public boolean isOverridable() {
		return false;
	}

	protected OrmAssociationOverride getAssociationOverride() {
		return this.getRelationship().getAssociationOverride();
	}

	protected AssociationOverrideContainer getAssociationOverrideContainer() {
		return this.getAssociationOverride().getContainer();
	}

	@Override
	public OrmOverrideRelationship getRelationship() {
		return (OrmOverrideRelationship) super.getRelationship();
	}

	public TextRange getValidationTextRange() {
		return this.getRelationship().getValidationTextRange();
	}


	// ********** join column owner **********

	protected class JoinColumnOwner
		implements OrmJoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getAttributeName();
		}

		public PersistentAttribute getPersistentAttribute() {
			RelationshipMapping relationshipMapping = GenericOrmOverrideJoinColumnJoiningStrategy.this.getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.candidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getReferencedColumnDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return false;
		}

		public int joinColumnsSize() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.joinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return this.getAssociationOverrideContainer().buildColumnValidator(this.getAssociationOverride(), (BaseColumn) column, this, (BaseColumnTextRangeResolver) textRangeResolver);
		}

		protected OrmAssociationOverride getAssociationOverride() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getAssociationOverride();
		}

		protected AssociationOverrideContainer getAssociationOverrideContainer() {
			return GenericOrmOverrideJoinColumnJoiningStrategy.this.getAssociationOverrideContainer();
		}
	}
}
