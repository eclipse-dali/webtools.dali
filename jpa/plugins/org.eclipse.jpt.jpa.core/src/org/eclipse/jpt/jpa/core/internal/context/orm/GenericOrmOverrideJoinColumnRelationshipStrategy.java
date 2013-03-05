/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.SpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.BaseColumn;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.context.orm.OrmOverrideRelationship;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.db.Table;

public class GenericOrmOverrideJoinColumnRelationshipStrategy
	extends AbstractOrmJoinColumnRelationshipStrategy<OrmOverrideRelationship>
{
	public GenericOrmOverrideJoinColumnRelationshipStrategy(OrmOverrideRelationship parent) {
		super(parent);
	}

	@Override
	protected JoinColumn.Owner buildJoinColumnOwner() {
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
				this.getRelationship().getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		return this.isTargetForeignKey() ?
				this.getRelationship().getTypeMapping() :
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
		return this.getRelationship().getMapping();
	}

	protected String getAttributeName() {
		return this.getRelationship().getAttributeName();
	}

	@Override
	public String getTableName() {
		return this.isTargetForeignKey() ?
				super.getTableName() :
				this.getRelationship().getDefaultTableName();
	}

	@Override
	public Table resolveDbTable(String tableName) {
		return this.isTargetForeignKey() ?
				super.resolveDbTable(tableName) :
				this.getRelationship().resolveDbTable(tableName);
	}

	@Override
	public boolean tableNameIsInvalid(String tableName) {
		return this.isTargetForeignKey() ?
				super.tableNameIsInvalid(tableName) :
				this.getRelationship().tableNameIsInvalid(tableName);
	}

	@Override
	public Iterable<String> getCandidateTableNames() {
		return this.isTargetForeignKey() ?
				super.getCandidateTableNames() :
				this.getRelationship().getCandidateTableNames();
	}

	public String getColumnTableNotValidDescription() {
		return null;
	}

	public boolean isOverridable() {
		return false;
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
		implements JoinColumn.Owner
	{
		protected JoinColumnOwner() {
			super();
		}

		public String getDefaultTableName() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName(NamedColumn column) {
			return MappingTools.buildJoinColumnDefaultName((JoinColumn) column, this);
		}

		public String getAttributeName() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getAttributeName();
		}

		public SpecifiedPersistentAttribute getPersistentAttribute() {
			RelationshipMapping relationshipMapping = GenericOrmOverrideJoinColumnRelationshipStrategy.this.getRelationshipMapping();
			return relationshipMapping == null ? null : relationshipMapping.getPersistentAttribute();
		}

		public Entity getRelationshipTarget() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterable<String> getCandidateTableNames() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getCandidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public int getJoinColumnsSize() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getJoinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column) {
			return GenericOrmOverrideJoinColumnRelationshipStrategy.this.getRelationship().buildColumnValidator((BaseColumn) column, this);
		}
	}
}
