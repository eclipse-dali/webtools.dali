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
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.core.context.Entity;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.PersistentAttribute;
import org.eclipse.jpt.core.context.ReadOnlyBaseJoinColumn;
import org.eclipse.jpt.core.context.RelationshipMapping;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn.Owner;
import org.eclipse.jpt.core.context.orm.OrmMappingJoinColumnRelationship;
import org.eclipse.jpt.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.NamedColumnTextRangeResolver;
import org.eclipse.jpt.core.internal.jpa1.context.EntityTableDescriptionProvider;
import org.eclipse.jpt.core.internal.jpa1.context.JoinColumnValidator;
import org.eclipse.jpt.core.internal.validation.JpaValidationDescriptionMessages;
import org.eclipse.jpt.db.Table;

public class GenericOrmMappingJoinColumnRelationshipStrategy
	extends AbstractOrmJoinColumnRelationshipStrategy
{
	protected final boolean targetForeignKey;


	/**
	 * The default strategy is for a "source" foreign key.
	 */
	public GenericOrmMappingJoinColumnRelationshipStrategy(OrmMappingJoinColumnRelationship parent) {
		this(parent, false);
	}

	public GenericOrmMappingJoinColumnRelationshipStrategy(OrmMappingJoinColumnRelationship parent, boolean targetForeignKey) {
		super(parent);
		this.targetForeignKey = targetForeignKey;
	}


	@Override
	protected Owner buildJoinColumnOwner() {
		return new JoinColumnOwner();
	}

	public boolean isOverridable() {
		return true;
	}

	public TypeMapping getRelationshipSource() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return this.targetForeignKey ?
				mapping.getResolvedTargetEntity() :
				mapping.getTypeMapping();
	}

	public TypeMapping getRelationshipTarget() {
		RelationshipMapping mapping = this.getRelationshipMapping();
		return this.targetForeignKey ?
				mapping.getTypeMapping() :
				mapping.getResolvedTargetEntity();
	}

	protected Entity getRelationshipTargetEntity() {
		TypeMapping target = this.getRelationshipTarget();
		return (target instanceof Entity) ? (Entity) target : null;
	}

	public boolean isTargetForeignKey() {
		return this.targetForeignKey;
	}


	// ********** validation **********

	public String getColumnTableNotValidDescription() {
		return JpaValidationDescriptionMessages.NOT_VALID_FOR_THIS_ENTITY;
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

		/**
		 * by default, the join column is in the type mapping's primary table
		 */
		public String getDefaultTableName() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getTableName();
		}

		public String getDefaultColumnName() {
			//built in MappingTools.buildJoinColumnDefaultName()
			return null;
		}

		public String getAttributeName() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getRelationshipMapping().getName();
		}

		protected PersistentAttribute getPersistentAttribute() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getRelationshipMapping().getPersistentAttribute();
		}

		public TypeMapping getTypeMapping() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getRelationshipSource();
		}

		public Entity getRelationshipTarget() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getRelationshipTargetEntity();
		}

		public boolean tableNameIsInvalid(String tableName) {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.tableNameIsInvalid(tableName);
		}

		public Iterator<String> candidateTableNames() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.candidateTableNames();
		}

		public Table resolveDbTable(String tableName) {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.resolveDbTable(tableName);
		}

		public Table getReferencedColumnDbTable() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getReferencedColumnDbTable();
		}

		public boolean joinColumnIsDefault(ReadOnlyBaseJoinColumn joinColumn) {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.defaultJoinColumn == joinColumn;
		}

		public int joinColumnsSize() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.joinColumnsSize();
		}

		public TextRange getValidationTextRange() {
			return GenericOrmMappingJoinColumnRelationshipStrategy.this.getValidationTextRange();
		}

		public JptValidator buildColumnValidator(NamedColumn column, NamedColumnTextRangeResolver textRangeResolver) {
			return new JoinColumnValidator(getPersistentAttribute(), (JoinColumn) column, this, (JoinColumnTextRangeResolver) textRangeResolver, new EntityTableDescriptionProvider());
		}
	}
}
